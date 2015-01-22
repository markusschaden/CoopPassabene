package ch.avendia.passabene.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;

/**
 * Created by Markus on 22.01.2015.
 */
public class PassabeneAccountManager {

    public PassabeneAccount getAccount(Context ctx) {
        AccountManager am = AccountManager.get(ctx);
        Account[] accounts = am.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);
        for(Account acc : accounts) {
            String password = am.getPassword(acc);
            String username = acc.name;
            Log.i("Passabene", "Username: " + username + " , Password:" + password);
            return new PassabeneAccount(username, password);
        }

        return null;
    }

}
