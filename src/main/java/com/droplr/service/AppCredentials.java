package com.droplr.service;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class AppCredentials {

    // internal vars --------------------------------------------------------------------------------------------------

    private final String publicKey;
    private final String privateKey;

    // constructors ---------------------------------------------------------------------------------------------------

    private AppCredentials(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    // public static methods ------------------------------------------------------------------------------------------

    public static AppCredentials credentials(String publicKey, String privateKey) {
        return new AppCredentials(publicKey.toLowerCase(), privateKey);
    }

    // getters & setters ----------------------------------------------------------------------------------------------

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    // object overrides -----------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return new StringBuilder()
                .append("AppCredentials{")
                .append("publicKey='").append(this.publicKey).append('\'')
                .append(", privateKey='").append(this.privateKey).append('\'')
                .append('}')
                .toString();
    }
}
