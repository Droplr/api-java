package com.droplr.service.auth;

import com.droplr.common.TextUtils;
import com.droplr.service.AppCredentials;
import com.droplr.service.UserCredentials;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SignatureException;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class AuthUtils {

    // constants ------------------------------------------------------------------------------------------------------

    public static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    // constructors ---------------------------------------------------------------------------------------------------

    private AuthUtils() {
    }

    // public static methods ------------------------------------------------------------------------------------------

    public static String calculateDroplrSignature(AppCredentials appCredentials,
                                                  UserCredentials userCredentials,
                                                  HttpRequest request)
            throws SignatureException {

        String accessKey = accessKey(appCredentials.getPublicKey(), userCredentials.getEmail());
        String accessSecret = accessSecret(appCredentials.getPrivateKey(), userCredentials.getHashedPassword());
        String stringToSign = createStringToSign(request);
        String signature = calculateRfc2104Hmac(stringToSign, accessSecret);

        // "droplr <accesskey>:<signature>"
        return new StringBuilder(7 + accessKey.length() + 1 + signature.length())
                .append("droplr ").append(accessKey).append(':').append(signature).toString();
    }

    public static String accessKey(String publicKey, String email) {
        String accessKey = new StringBuilder(publicKey.length() + 1 + email.length())
                .append(publicKey).append(':').append(email).toString();
        return TextUtils.base64Encode(accessKey);
    }

    public static String accessSecret(String publicKey, String password) {
        return new StringBuilder(publicKey.length() + 1 + password.length())
                .append(publicKey).append(':').append(password).toString();
    }

    public static String createStringToSign(HttpRequest request) {
        String contentType = HttpHeaders.getHeader(request, HttpHeaders.Names.CONTENT_TYPE);
        if (contentType == null) {
            contentType = "";
        }

        String date = HttpHeaders.getHeader(request, HttpHeaders.Names.DATE);
        if ((date == null) || date.isEmpty()) {
            throw new IllegalArgumentException("Request does not containt a Date header");
        }

        String uri = null;
        try {
            uri = new URI(request.getUri()).getPath();
        } catch (URISyntaxException ignored) {
            // only happens when a screwed up uri is provided
        }

        if (uri == null) {
            uri = request.getUri();
        }

        return new StringBuilder()
                .append(request.getMethod()).append(' ')
                .append(uri).append(' ')
                .append(request.getProtocolVersion()).append('\n')
                .append(contentType).append('\n')
                .append(date)
                .toString();
    }

    /**
     * Computes RFC 2104-compliant HMAC signature.
     *
     * @param data The data to be signed.
     * @param key The signing key.
     *
     * @return The Base64-encoded RFC 2104-compliant HMAC signature.
     *
     * @throws java.security.SignatureException when signature generation fails
     */
    public static String calculateRfc2104Hmac(String data, String key) throws SignatureException {
        String result;
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes());
            // base64-encode the hmac
            result = TextUtils.base64Encode(rawHmac);
        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC: " + e.getMessage());
        }

        return result;
    }
}
