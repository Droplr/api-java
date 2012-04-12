package com.droplr.service;

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
        // TODO
        return true;
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
}
