package ch.avendia.passabene.account;

/**
 * Created by Markus on 22.01.2015.
 */

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ch.avendia.passabene.R;

/**
 * The Authenticator activity.
 *
 * Called by the Authenticator and in charge of identifing the user.
 *
 * It sends back to the Authenticator the result.
 */
import ch.avendia.passabene.account.AccountGeneral.*;
import ch.avendia.passabene.api.PassabeneService;
import ch.avendia.passabene.wifi.CoopWifiManager;

public class AuthenticatorActivity extends AccountAuthenticatorActivity {

    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";

    public static final String KEY_ERROR_MESSAGE = "ERR_MSG";

    public final static String PARAM_USER_PASS = "USER_PASS";

    private final int REQ_SIGNUP = 1;

    private final String TAG = this.getClass().getSimpleName();

    private AccountManager mAccountManager;
    private String mAuthTokenType;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        mAccountManager = AccountManager.get(getBaseContext());

        String accountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);
        mAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);
        if (mAuthTokenType == null)
            mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;

        if (accountName != null) {
            ((TextView) findViewById(R.id.username)).setText(accountName);
        }

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        /*findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Since there can only be one AuthenticatorActivity, we call the sign up activity, get his results,
                // and return them in setAccountAuthenticatorResult(). See finishLogin().
                Intent signup = new Intent(getBaseContext(), SignUpActivity.class);
                signup.putExtras(getIntent().getExtras());
                startActivityForResult(signup, REQ_SIGNUP);
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // The sign up activity returned that the user has successfully created an account
        if (requestCode == REQ_SIGNUP && resultCode == RESULT_OK) {
            finishLogin(data);
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    public void submit() {

        final TextView usernameTV = (TextView)findViewById(R.id.username);
        final TextView passwordTV = (TextView)findViewById(R.id.password);

        final String username = usernameTV.getText().toString();
        final String password = passwordTV.getText().toString();

        final String accountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);


        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !TextUtils.isDigitsOnly(password) || password.length() != 4) {
            passwordTV.setError("Password invalid, only 4 digits");
            focusView = passwordTV;
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username) || username.length() != 13) {
            usernameTV.setError("Username invalid, has to be 13 digits");
            focusView = usernameTV;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            final ProgressDialog progressDialog = ProgressDialog.show(this, "Login...", "Please Wait ...", true);

            new AsyncTask<String, Void, Intent>() {

                @Override
                protected Intent doInBackground(String... params) {

                    Log.d("udinic", TAG + "> Started authenticating");

                    String authtoken = null;
                    Bundle data = new Bundle();
                    try {
                        authtoken = AccountGeneral.sServerAuthenticate.checkCredentials(username, password);

                        data.putString(AccountManager.KEY_ACCOUNT_NAME, username);
                        data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                        data.putString(AccountManager.KEY_AUTHTOKEN, authtoken);
                        data.putString(AccountManager.KEY_PASSWORD, password);
                        data.putString(PARAM_USER_PASS, password);

                        if("".equals(authtoken)) {
                            data.putString(KEY_ERROR_MESSAGE, "Invalid Password");
                        }
                    } catch (Exception e) {
                        data.putString(KEY_ERROR_MESSAGE, e.getMessage());
                    }

                    final Intent res = new Intent();
                    res.putExtras(data);
                    return res;
                }

                @Override
                protected void onPostExecute(Intent intent) {
                    if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                        progressDialog.hide();

                        Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                    } else {

                        WifiManager wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
                        CoopWifiManager coopWifiManager = new CoopWifiManager(wifi);
                        coopWifiManager.addWifi(username, password);

                        progressDialog.hide();

                        finishLogin(intent);
                    }
                }
            }.execute();

        }






    }

    private void finishLogin(Intent intent) {
        Log.d("udinic", TAG + "> finishLogin");

        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));

        if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
            Log.d("udinic", TAG + "> finishLogin > addAccountExplicitly");
            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            String authtokenType = mAuthTokenType;

            // Creating the account on the device and setting the auth token we got
            // (Not setting the auth token will cause another call to the server to authenticate the user)
            mAccountManager.addAccountExplicitly(account, accountPassword, null);
            mAccountManager.setAuthToken(account, authtokenType, authtoken);
        } else {
            Log.d("udinic", TAG + "> finishLogin > setPassword");
            mAccountManager.setPassword(account, accountPassword);
        }

        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }
}