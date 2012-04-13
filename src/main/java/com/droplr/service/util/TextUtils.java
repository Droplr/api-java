package com.droplr.service.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class TextUtils {

    // constants ------------------------------------------------------------------------------------------------------

    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final String  MD5   = "MD5";
    public static final String  SHA_1 = "SHA-1";

    // constructors ---------------------------------------------------------------------------------------------------

    private TextUtils() {
    }

    // public static methods ------------------------------------------------------------------------------------------

    public static String base64Encode(byte[] toEncode) {
        return Base64.encodeToString(toEncode, false);
    }

    public static String base64Encode(Object toEncode) {
        return base64Encode(toEncode.toString().getBytes());
    }

    public static String base64Decode(String toDecode) throws RuntimeException {
        byte[] decoded = base64DecodeToBytes(toDecode);

        return new String(decoded, UTF_8);
    }

    public static byte[] base64DecodeToBytes(String toDecode) throws RuntimeException {
        byte[] decoded = Base64.decodeFast(toDecode);
        if (decoded == null) {
            throw new RuntimeException("Invalid base64 content: '" + toDecode + "'");
        }

        return decoded;
    }

    /**
     * Hash a string, with md5
     *
     * @param toHash String to be hashed.
     *
     * @return Hashed string.
     */
    public static String md5(Object toHash) {
        try {
            return digest(MD5, toHash);
        } catch (NoSuchAlgorithmException e) {
            // Never happens
            return toHash.toString();
        }
    }

    public static String sha1(Object toHash) {
        try {
            return digest(SHA_1, toHash);
        } catch (NoSuchAlgorithmException e) {
            // Never happens
            return toHash.toString();
        }
    }

    public static String digest(String algorithm, Object toHash) throws NoSuchAlgorithmException {
        String hashString = toHash.toString();
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(hashString.getBytes(), 0, hashString.length());
        return convertToHex(md.digest());
    }

    public static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte aData : data) {
            int halfbyte = (aData >>> 4) & 0x0F;
            int twoHalfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = aData & 0x0F;
            } while (twoHalfs++ < 1);
        }
        return buf.toString();
    }
}
