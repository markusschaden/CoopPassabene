package ch.avendia.passabene.account;

/**
 * Created by Markus on 22.01.2015.
 */
public class AccountGeneral {

    /**
     * Account type id
     */
    public static final String ACCOUNT_TYPE = "ch.avendia.passabene.account";

    /**
     * Account name
     */
    public static final String ACCOUNT_NAME = "Passabene";

    /**
     * Auth token types
     */
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "Full access to passabene";

    public static final ServerAuthenticate sServerAuthenticate = new ParseComServerAuthenticate();
}
