package com.droplr.service.parsing;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class MandatoryFieldException extends RuntimeException {

    // constructors ---------------------------------------------------------------------------------------------------

    public MandatoryFieldException() {
    }

    public MandatoryFieldException(String message) {
        super(message);
    }

    public MandatoryFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public MandatoryFieldException(Throwable cause) {
        super(cause);
    }
}
