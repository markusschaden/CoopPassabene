package ch.avendia.passabene;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import ch.avendia.passabene.api.PassabeneService;
import ch.avendia.passabene.gps.GpsProvider;
import ch.avendia.passabene.wifi.CoopWifiManager;


public class SetupActivity extends ActionBarActivity {

    private CoopWifiManager coopWifiManager;
    private PassabeneService passabeneService;
    private GpsProvider gpsProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        restoreActionBar();

        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        coopWifiManager = new CoopWifiManager(wifi);
        passabeneService = PassabeneService.getInstance();

        gpsProvider = new GpsProvider(getBaseContext());

        startCheckConnection();
    }

    private void startCheckConnection() {
        AsyncTask<String, Void, Intent > task = new AsyncTask<String, Void, Intent>() {
            public static final String LOCATION_UNKNOWN = "LOCATION_UNKNOWN";
            boolean connected = false;

            @Override
            protected Intent doInBackground(String[] objects) {
                Bundle data = new Bundle();
                coopWifiManager.connectToCoopWifi();
                while(!connected) {

                    connected = passabeneService.isConnected();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                String store = null;
                Location location = gpsProvider.getLastBestLocation();
                if(location != null) {
                    store = passabeneService.getStoreNumber(location.getLatitude(), location.getLongitude());
                }

                if(store == null) {
                    data.putString(LOCATION_UNKNOWN, "true");
                } else {
                    //download Product catalog
                    boolean catalogAvailable = passabeneService.downloadCatalog(getBaseContext());
                    data.putBoolean(MainActivity.CATALOG_AVAILABLE, catalogAvailable);
                }

                final Intent res = new Intent();
                res.putExtras(data);
                return res;
            }

            @Override
            protected void onPostExecute(Intent intent) {

                if (intent.hasExtra(LOCATION_UNKNOWN)) {
                    //show message, scan store barcode
                } else {
                    Bundle bundle = new Bundle();
                    Intent mainIntent = new Intent(getBaseContext(), MainActivity.class);
                    setResult(RESULT_OK, mainIntent);
                    bundle.putBoolean(MainActivity.SETUP_FINISHED, true);
                    bundle.putBoolean(MainActivity.CATALOG_AVAILABLE, intent.getBooleanExtra(MainActivity.CATALOG_AVAILABLE, false));
                    startActivity(mainIntent, bundle);

                    finish();
                }
            }
        }.execute();
    }


    public void restoreActionBar() {
        android.app.ActionBar actionBar = this.getActionBar();

        Typeface coopRgFont = Typeface.createFromAsset(getAssets(), "fonts/CoopRg.ttf");
        Typeface coopExpRgFont = Typeface.createFromAsset(getAssets(), "fonts/CoopExpRg.ttf");

        SpannableString s = new SpannableString(getString(R.string.title_activity_main));
        s.setSpan(new CustomTypefaceSpan(this, "CoopExpRg.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        actionBar.setTitle(s);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_setup, container, false);
            return rootView;
        }
    }
}
