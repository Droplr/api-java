package com.droplr.service;

import com.droplr.common.ContentType;
import com.droplr.common.TextUtils;
import com.droplr.http.CannotExecuteRequestException;
import com.droplr.http.client.DefaultHttpClient;
import com.droplr.http.client.HttpClient;
import com.droplr.http.future.HttpRequestFuture;
import com.droplr.http.processor.HttpResponseProcessor;
import com.droplr.service.auth.AuthUtils;
import com.droplr.service.domain.AbstractDrop;
import com.droplr.service.domain.Account;
import com.droplr.service.domain.Drop;
import com.droplr.service.domain.DropCreation;
import com.droplr.service.operation.*;
import com.droplr.service.parsing.HeadersAccountResponseProcessor;
import com.droplr.service.parsing.HeadersDropCreationResponseProcessor;
import com.droplr.service.parsing.HeadersDropResponseProcessor;
import org.jboss.netty.handler.codec.http.*;

import java.security.SignatureException;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class DefaultDroplrService implements DroplrService, OperationSubmissionHandler {

    // constants ------------------------------------------------------------------------------------------------------

    public static final String  USER_AGENT_TEMPLATE     = "DroplrService.java/" + VERSION + " %s";
    public static final String  DEVELOPMENT_HOST        = "dev.droplr.com";
    public static final int     DEVELOPMENT_PORT        = 8069;
    public static final boolean DEVELOPMENT_USE_HTTPS   = false;
    public static final String  PRODUCTION_HOST         = "api.droplr.com";
    public static final int     PRODUCTION_PORT         = 443;
    public static final boolean PRODUCTION_USE_HTTPS    = true;
    public static final Format  DEFAULT_FORMAT          = Format.HEADERS;
    public static final int     DEFAULT_REQUEST_TIMEOUT = 20000;
    public static final int     DEFAULT_UPLOAD_TIMEOUT  = 0; // no timeout

    // configuration --------------------------------------------------------------------------------------------------

    private final String          userAgent;
    private final AppCredentials  appCredentials;
    private final String          host;
    private final int             port;
    private final boolean         useHttps;
    private       Format          format;
    private       UserCredentials defaultUserCredentials;

    // internal vars --------------------------------------------------------------------------------------------------

    private HttpClient client;

    // constructors ---------------------------------------------------------------------------------------------------

    private DefaultDroplrService(String userAgent, AppCredentials appCredentials,
                                 String host, int port, boolean useHttps) {
        if ((userAgent == null) || userAgent.isEmpty() || (userAgent.length() > 64)) {
            throw new IllegalAccessError("UserAgent cannot be null and its length must be > and <= 64");
        }

        this.userAgent = String.format(USER_AGENT_TEMPLATE, userAgent);
        this.appCredentials = appCredentials;
        this.host = host;
        this.port = port;
        this.useHttps = useHttps;
        this.format = DEFAULT_FORMAT;
    }

    // public static methods ------------------------------------------------------------------------------------------

    public static DefaultDroplrService developmentService(String userAgent, AppCredentials appCredentials) {
        return service(userAgent, appCredentials, DEVELOPMENT_HOST, DEVELOPMENT_PORT, DEVELOPMENT_USE_HTTPS);
    }

    public static DefaultDroplrService productionService(String userAgent, AppCredentials appCredentials) {
        return service(userAgent, appCredentials, PRODUCTION_HOST, PRODUCTION_PORT, PRODUCTION_USE_HTTPS);
    }

    public static DefaultDroplrService service(String userAgent, AppCredentials appCredentials,
                                               String host, int port, boolean useHttps) {
        return new DefaultDroplrService(userAgent, appCredentials, host, port, useHttps);
    }

    // DroplrService --------------------------------------------------------------------------------------------------

    @Override
    public boolean init() {
        DefaultHttpClient client = new DefaultHttpClient();
        client.setUseSsl(this.useHttps);
        client.setUseNio(true);
        client.setRequestTimeoutInMillis(20000);
        client.setConnectionTimeoutInMillis(3000);

        this.client = client;

        return client.init();
    }

    @Override
    public void terminate() {
        this.client.terminate();
    }

    @Override
    public ReadAccountOperation readAccount()
            throws IllegalArgumentException, IllegalStateException {

        return readAccount(this.defaultUserCredentials);
    }

    @Override
    public ReadAccountOperation readAccount(UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException {
        // Create & sign the request
        HttpRequest request = this.createRequest(HttpMethod.GET, "/account");
        request.setHeader(HttpHeaders.Names.CONTENT_LENGTH, 0);
        this.authorizeRequest(request, credentials);

        HttpResponseProcessor<Account> responseProcessor;
        if (this.format == Format.JSON) {
            responseProcessor = null;
        } else {
            responseProcessor = new HeadersAccountResponseProcessor();
        }

        return new ReadAccountOperation(request, responseProcessor, this);
    }

    @Override
    public Object editAccount(Account account)
            throws IllegalArgumentException, IllegalStateException {
        return editAccount(account, this.defaultUserCredentials);
    }

    @Override
    public Object editAccount(Account account, UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException {
        // Create & sign the request
        HttpRequest request = this.createRequest(HttpMethod.POST, "/account");
        request.setHeader(HttpHeaders.Names.CONTENT_LENGTH, 0);
        this.authorizeRequest(request, credentials);

        HttpResponseProcessor<Account> responseProcessor;
        if (this.format == Format.JSON) {
            responseProcessor = null;
        } else {
            responseProcessor = new HeadersAccountResponseProcessor();
        }

        return new ReadAccountOperation(request, responseProcessor, this);
    }

    @Override
    public CreateDropOperation shortenLink(String link)
            throws IllegalArgumentException, IllegalStateException {
        return this.shortenLink(link, this.defaultUserCredentials);
    }

    @Override
    public CreateDropOperation shortenLink(String link, UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException {
        // Check if the link is valid (not null, not empty, a valid URL)
        if (!Validations.isValidLink(link)) {
            throw new IllegalArgumentException("Link is not valid; please use Validations.isValidLink()");
        }

        // Check if the link's raw data (UTF8 byte array) fits under the maximum allowed size for links
        byte[] rawLinkData = link.getBytes(TextUtils.UTF_8);
        if (!Validations.isValidLinkRawData(rawLinkData)) {
            throw new IllegalArgumentException("Link is too long; when converted to byte[], it must not exceed " +
                                               Validations.MAX_LINK_SIZE + " bytes");
        }

        // Create & sign the upload HTTP request
        HttpRequest request = this.createUploadRequest(HttpMethod.POST, "/links", ContentType.TEXT_PLAIN, rawLinkData);
        this.authorizeRequest(request, credentials);

        // Assign a responseProcessor to this request, based on the current data format
        HttpResponseProcessor<DropCreation> responseProcessor;
        if (this.format == Format.JSON) {
            responseProcessor = null;
        } else {
            responseProcessor = new HeadersDropCreationResponseProcessor();
        }

        return new CreateDropOperation(request, rawLinkData, responseProcessor, this);
    }

    @Override
    public CreateDropOperation createNote(String content, AbstractDrop.NoteType type)
            throws IllegalArgumentException, IllegalStateException {
        return this.createNote(content, type, this.defaultUserCredentials);
    }

    @Override
    public CreateDropOperation createNote(String content, AbstractDrop.NoteType type, UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException {
        // Check if the link is valid (not null, not empty, a valid URL)
        if (!Validations.isValidNote(content)) {
            throw new IllegalArgumentException("Note is not valid; please use Validations.isValidNote()");
        }

        // Check if the link's raw data (UTF8 byte array) fits under the maximum allowed size for links
        byte[] rawNoteData = content.getBytes(TextUtils.UTF_8);
        if (!Validations.isValidNoteRawData(rawNoteData)) {
            throw new IllegalArgumentException("Note is too long; when converted to byte[], it must not exceed " +
                                               Validations.MAX_NOTE_SIZE + " bytes");
        }

        // Get the appropriate ContentType for the note type
        ContentType contentType = this.contentTypeForNoteType(type);

        // Create & sign the upload HTTP request
        HttpRequest request = this.createUploadRequest(HttpMethod.POST, "/notes", contentType, rawNoteData);
        this.authorizeRequest(request, credentials);

        // Assign a responseProcessor to this request, based on the current data format
        HttpResponseProcessor<DropCreation> responseProcessor;
        if (this.format == Format.JSON) {
            responseProcessor = null;
        } else {
            responseProcessor = new HeadersDropCreationResponseProcessor();
        }

        return new CreateDropOperation(request, rawNoteData, responseProcessor, this);
    }

    @Override
    public CreateDropOperation uploadFile(byte[] data, ContentType contentType, String filename)
            throws IllegalArgumentException, IllegalStateException {
        return this.uploadFile(data, contentType, filename, this.defaultUserCredentials);
    }

    @Override
    public CreateDropOperation uploadFile(byte[] data, ContentType contentType,
                                          String filename, UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException {
        // Make sure the data to upload is valid; doesn't cover the case where the user is not pro and attempts to
        // upload more than what he can, but that must be externally verified with Validations.canUpload()
        if ((data == null) || (data.length == 0) || (data.length > Validations.MAX_UPLOAD_SIZE_PRO)) {
            throw new IllegalArgumentException("Data cannot be null or empty and must be smaller than " +
                                               Validations.MAX_UPLOAD_SIZE_PRO + " bytes");
        }

        if (contentType == null) {
            throw new IllegalArgumentException("ContentType cannot be null");
        }

        if (!Validations.isValidFilename(filename)) {
            throw new IllegalArgumentException("Filename is not valid; please use Validations.isValidFilename()");
        }

        // Create the upload HTTP request
        HttpRequest request = this.createUploadRequest(HttpMethod.POST, "/files", contentType, data);
        this.authorizeRequest(request, credentials);

        // Assign a responseProcessor to this request, based on the current data format
        HttpResponseProcessor<DropCreation> responseProcessor;
        if (this.format == Format.JSON) {
            responseProcessor = null;
        } else {
            responseProcessor = new HeadersDropCreationResponseProcessor();
        }

        return new CreateDropOperation(request, data, responseProcessor, this);
    }

    @Override
    public ReadDropOperation readDrop(String dropCode)
            throws IllegalArgumentException, IllegalStateException {
        return this.readDrop(dropCode, this.defaultUserCredentials);
    }

    @Override
    public ReadDropOperation readDrop(String dropCode, UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException {
        HttpRequest request = this.createRequest(HttpMethod.GET, "/drops/" + dropCode);
        request.addHeader(HttpHeaders.Names.CONTENT_LENGTH, 0);
        this.authorizeRequest(request, credentials);

        HttpResponseProcessor<Drop> parser;
        if (this.format == Format.JSON) {
            parser = null;
        } else {
            parser = new HeadersDropResponseProcessor();
        }

        return new ReadDropOperation(request, parser, this);
    }

    @Override
    public Object editDrop(Drop drop)
            throws IllegalArgumentException, IllegalStateException {
        return this.editDrop(drop, this.defaultUserCredentials);
    }

    @Override
    public Object editDrop(Drop drop, UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object listDrops()
            throws IllegalArgumentException, IllegalStateException {
        return this.listDrops(null, this.defaultUserCredentials);
    }

    @Override
    public Object listDrops(UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException {
        return this.listDrops(null, credentials);
    }

    @Override
    public Object listDrops(Object filter)
            throws IllegalArgumentException, IllegalStateException {
        return this.listDrops(filter, this.defaultUserCredentials);
    }

    @Override
    public Object listDrops(Object filter, UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object deleteDrop(String dropCode)
            throws IllegalArgumentException, IllegalStateException {
        return this.deleteDrop(dropCode, this.defaultUserCredentials);
    }

    @Override
    public Object deleteDrop(String dropCode, UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    // OperationSubmissionHandler -------------------------------------------------------------------------------------

    @Override
    public <T> HttpRequestFuture<T> submit(AbstractOperation<T> operation)
            throws CannotExecuteRequestException {
        if (operation.isUpload() && (operation.getDataSinkListener() != null)) {
            return this.client.execute(this.host, this.port, DEFAULT_UPLOAD_TIMEOUT, operation.getRequest(),
                                       operation.getResponseProcessor(), operation.getDataSinkListener());
        } else {
            return this.client.execute(this.host, this.port, operation.getRequest(), operation.getResponseProcessor());
        }
    }

    // private helpers ------------------------------------------------------------------------------------------------

    private HttpRequest createRequest(HttpMethod method, String uri) {
        String uriWithFormat = uri + this.format.getSuffix();

        HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, method, uriWithFormat);
//        request.setHeader(HttpHeaders.Names.HOST, this.host + ':' + this.port);
        request.setHeader(HttpHeaders.Names.USER_AGENT, this.userAgent);
        request.setHeader(HttpHeaders.Names.DATE, System.currentTimeMillis());

        return request;
    }

    private HttpRequest createUploadRequest(HttpMethod method, String uri, ContentType contentType, byte[] data) {
        HttpRequest request = this.createRequest(method, uri);
        request.setHeader(HttpHeaders.Names.CONTENT_TYPE, contentType);
        request.setHeader(HttpHeaders.Names.CONTENT_LENGTH, data.length);
        request.setHeader(HttpHeaders.Names.EXPECT, HttpHeaders.Values.CONTINUE);

        return request;
    }

    private void authorizeRequest(HttpRequest request, UserCredentials userCredentials)
            throws IllegalStateException {
        UserCredentials credentialsToUse;
        if (userCredentials != null) {
            credentialsToUse = userCredentials;
        } else {
            credentialsToUse = this.defaultUserCredentials;
        }

        if (credentialsToUse == null) {
            throw new IllegalStateException("Default user credentials not set");
        }

        try {
            String signature = AuthUtils.calculateDroplrSignature(this.appCredentials, credentialsToUse, request);
            request.addHeader(HttpHeaders.Names.AUTHORIZATION, signature);
        } catch (SignatureException e) {
            throw new RuntimeException("Failed to generate signature for request", e);
        }
    }

    private ContentType contentTypeForNoteType(AbstractDrop.NoteType type) {
        switch (type) {
            case CODE:
                return ContentType.TEXT_CODE;

            case MARKDOWN:
                return ContentType.TEXT_MARKDOWN;

            case TEXTILE:
                return ContentType.TEXT_TEXTILE;

            case HTML:
                return ContentType.TEXT_HTML;

            case PLAIN:
            default:
                return ContentType.TEXT_PLAIN;
        }
    }

    // getters & setters ----------------------------------------------------------------------------------------------

    public String getUserAgent() {
        return userAgent;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isUseHttps() {
        return useHttps;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public UserCredentials getDefaultUserCredentials() {
        return defaultUserCredentials;
    }

    public void setDefaultUserCredentials(UserCredentials defaultUserCredentials) {
        this.defaultUserCredentials = defaultUserCredentials;
    }

    // object overrides -----------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return new StringBuilder()
                .append("DefaultDroplrService{")
                .append("userAgent='").append(this.userAgent).append('\'')
                .append(", host='").append(this.host).append('\'')
                .append(", port=").append(this.port)
                .append(", useHttps=").append(this.useHttps)
                .append(", format=").append(this.format)
                .append('}')
                .toString();
    }
}
