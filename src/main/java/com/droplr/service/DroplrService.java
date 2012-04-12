package com.droplr.service;

import com.droplr.common.ContentType;
import com.droplr.service.domain.AbstractDrop;
import com.droplr.service.domain.Account;
import com.droplr.service.domain.Drop;
import com.droplr.service.operation.CreateDropOperation;
import com.droplr.service.operation.ReadAccountOperation;
import com.droplr.service.operation.ReadDropOperation;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public interface DroplrService {

    // constants ------------------------------------------------------------------------------------------------------

    static final String VERSION = "1.0.0";

    // public methods -------------------------------------------------------------------------------------------------

    boolean init();
    void terminate();

    ReadAccountOperation readAccount()
            throws IllegalArgumentException, IllegalStateException;
    ReadAccountOperation readAccount(UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException;

    Object editAccount(Account account)
            throws IllegalArgumentException, IllegalStateException;
    Object editAccount(Account account, UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException;

    CreateDropOperation shortenLink(String link)
            throws IllegalArgumentException, IllegalStateException;
    CreateDropOperation shortenLink(String link, UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException;

    CreateDropOperation createNote(String content, AbstractDrop.NoteType type)
            throws IllegalArgumentException, IllegalStateException;
    CreateDropOperation createNote(String content, AbstractDrop.NoteType type, UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException;

    CreateDropOperation uploadFile(byte[] data, ContentType contentType, String filename)
            throws IllegalArgumentException, IllegalStateException;
    CreateDropOperation uploadFile(byte[] data, ContentType contentType, String filename, UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException;

    ReadDropOperation readDrop(String dropCode)
            throws IllegalArgumentException, IllegalStateException;
    ReadDropOperation readDrop(String dropCode, UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException;

    Object editDrop(Drop drop)
            throws IllegalArgumentException, IllegalStateException;
    Object editDrop(Drop drop, UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException;

    Object listDrops()
            throws IllegalArgumentException, IllegalStateException;
    Object listDrops(UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException;
    Object listDrops(Object filter)
            throws IllegalArgumentException, IllegalStateException;
    Object listDrops(Object filter, UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException;

    Object deleteDrop(String dropCode)
            throws IllegalArgumentException, IllegalStateException;
    Object deleteDrop(String dropCode, UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException;

    // enums ----------------------------------------------------------------------------------------------------------

    public static enum Format {

        // constants --------------------------------------------------------------------------------------------------

        HEADERS(""), JSON(".json");

        // internal vars ----------------------------------------------------------------------------------------------

        private final String suffix;

        // constructors -----------------------------------------------------------------------------------------------

        private Format(String suffix) {
            this.suffix = suffix;
        }

        // getters & setters ------------------------------------------------------------------------------------------

        public String getSuffix() {
            return suffix;
        }
    }
}
