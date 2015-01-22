package ch.avendia.passabene.account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Markus on 22.01.2015.
 */
public class PassabeneAuthenticatorService extends Service {
    @Override
    public IBinder onBind(Intent intent) {

        PassabeneAuthenticator authenticator = new PassabeneAuthenticator(this);
        return authenticator.getIBinder();
    }
}
