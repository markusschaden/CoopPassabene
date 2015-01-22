package ch.avendia.passabene.account;

/**
 * Created by Markus on 22.01.2015.
 */
public interface ServerAuthenticate {
    public String userSignIn(final String user, final String pass, String authType) throws Exception;

    public String checkCredentials(final String user, final String pass) throws Exception;
    public String signInToStore(final String user, final String pass) throws Exception;
}
