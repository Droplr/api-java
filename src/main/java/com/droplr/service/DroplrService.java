package com.droplr.service;

import com.droplr.common.ContentType;
import com.droplr.service.auth.UserCredentials;
import com.droplr.service.domain.AbstractDrop;
import com.droplr.service.domain.Account;
import com.droplr.service.domain.Drop;
import com.droplr.service.domain.DropListFilter;
import com.droplr.service.operation.*;

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

    EditAccountOperation editAccount(Account account)
            throws IllegalArgumentException, IllegalStateException;
    EditAccountOperation editAccount(Account account, UserCredentials credentials)
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

    EditDropOperation editDrop(Drop drop)
            throws IllegalArgumentException, IllegalStateException;
    EditDropOperation editDrop(Drop drop, UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException;

    ListDropsOperation listDrops()
            throws IllegalArgumentException, IllegalStateException;
    ListDropsOperation listDrops(UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException;
    ListDropsOperation listDrops(DropListFilter filter)
            throws IllegalArgumentException, IllegalStateException;
    ListDropsOperation listDrops(DropListFilter filter, UserCredentials credentials)
            throws IllegalArgumentException, IllegalStateException;

    DeleteDropOperation deleteDrop(String dropCode)
            throws IllegalArgumentException, IllegalStateException;
    DeleteDropOperation deleteDrop(String dropCode, UserCredentials credentials)
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
