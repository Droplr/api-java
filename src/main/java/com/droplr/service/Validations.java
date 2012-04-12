package com.droplr.service;

import com.droplr.service.domain.Account;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class Validations {

    // constants ------------------------------------------------------------------------------------------------------

    public static final int MAX_LINK_SIZE           = 10240;      // 10K
    public static final int MAX_NOTE_SIZE           = 1048576;    // 1MB
    public static final int MAX_UPLOAD_SIZE_REGULAR = 26214400;   // 25MB for regular users
    public static final int MAX_UPLOAD_SIZE_PRO     = 1073741824; // 1GB for pro users

    // constructor ----------------------------------------------------------------------------------------------------

    private Validations() {
    }

    // public static methods ------------------------------------------------------------------------------------------

    public static boolean isValidLink(String link) {
        if ((link == null) || link.isEmpty()) {
            return false;
        }

        URI uri;
        try {
            uri = new URI(link);
        } catch (URISyntaxException e) {
            return false;
        }

        // Only valid if scheme is either http ir https
        return (uri.getScheme() != null) &&
               ("http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme()));
    }

    public static boolean isValidLinkRawData(byte[] linkRawUtf8Data) {
        return (linkRawUtf8Data != null) &&
               (linkRawUtf8Data.length > 0) &&
               (linkRawUtf8Data.length <= MAX_LINK_SIZE);
    }

    public static boolean isValidNote(String note) {
        return !((note == null) || note.isEmpty());
    }

    public static boolean isValidNoteRawData(byte[] noteRawUtf8Data) {
        return (noteRawUtf8Data != null) &&
               (noteRawUtf8Data.length > 0) &&
               (noteRawUtf8Data.length <= MAX_NOTE_SIZE);
    }

    public static boolean isValidFilename(String filename) {
        return (filename != null) &&
               (filename.length() > 0) &&
               (filename.length() <= 1024);
    }

    public static boolean canUpload(Account account, int uploadSize) {
        switch (account.getType()) {
            case PRO:
                return uploadSize <= MAX_UPLOAD_SIZE_PRO;

            case REGULAR:
            default:
                return uploadSize <= MAX_UPLOAD_SIZE_REGULAR;
        }
    }
}
