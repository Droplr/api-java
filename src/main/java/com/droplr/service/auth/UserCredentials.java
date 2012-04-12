package com.droplr.service.auth;

import com.droplr.common.TextUtils;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class UserCredentials {

    // internal vars --------------------------------------------------------------------------------------------------

    private final String email;
    private final String hashedPassword;

    // constructors ---------------------------------------------------------------------------------------------------

    private UserCredentials(String email, String hashedPassword) {
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    // public static methods ------------------------------------------------------------------------------------------

    public static UserCredentials credentialsWithClearPassword(String email, String clearPassword) {
        return new UserCredentials(email.toLowerCase(), TextUtils.sha1(clearPassword));
    }

    public static UserCredentials credentialsWithHashedPassword(String email, String hashedPassword) {
        return new UserCredentials(email.toLowerCase(), hashedPassword);
    }

    // getters & setters ----------------------------------------------------------------------------------------------

    public String getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    // object overrides -----------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return new StringBuilder()
                .append("UserCredentials{")
                .append("email='").append(this.email).append('\'')
                .append(", hashedPassword='").append(this.hashedPassword).append('\'')
                .append('}')
                .toString();
    }
}
