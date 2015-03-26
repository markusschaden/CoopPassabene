package ch.avendia.passabene;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;

import ch.avendia.passabene.account.PassabeneAccount;
import ch.avendia.passabene.account.PassabeneAccountManager;
import ch.avendia.passabene.api.PassabeneService;
import ch.avendia.passabene.api.StartSessionApiCall;
import ch.avendia.passabene.exception.SSIDNotFoundException;
import ch.avendia.passabene.gps.GpsProvider;
import ch.avendia.passabene.scandit.ScanditActivity;
import ch.avendia.passabene.wifi.CoopWifiManager;


public class SetupActivity extends ActionBarActivity {

    private PlaceholderFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        fragment = new PlaceholderFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }

        new Drawer()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(false)
                .addDrawerItems(
                        //pass your items here
                )
                .build();


        //restoreActionBar();
    }


    public void restoreActionBar() {

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //android.app.ActionBar actionBar = this.getActionBar();
        //actionBar.setDisplayShowHomeEnabled(false);

        Typeface coopRgFont = Typeface.createFromAsset(getAssets(), "fonts/CoopRg.ttf");
        Typeface coopExpRgFont = Typeface.createFromAsset(getAssets(), "fonts/CoopExpRg.ttf");

        SpannableString s = new SpannableString(getString(R.string.title_activity_main));
        s.setSpan(new CustomTypefaceSpan(this, "CoopExpRg.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        toolbar.setTitle(s);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private TextView circle_1_text;
        private TextView circle_2_text;
        private TextView circle_3_text;
        private TextView circle_1;
        private TextView circle_2;
        private TextView circle_3;
        private ImageView qr_image;
        private Button startShoppingButton;
        private static final int BARCODE_INTENT_ID = 10;
        public static final String BARCODE_RESULT_DATA = "barcode";


        private CoopWifiManager coopWifiManager;
        private PassabeneService passabeneService;
        private GpsProvider gpsProvider;
        private View rootView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_setup, container, false);

            circle_1_text = (TextView) rootView.findViewById(R.id.circle_1_text);
            circle_2_text = (TextView) rootView.findViewById(R.id.circle_2_text);
            circle_3_text = (TextView) rootView.findViewById(R.id.circle_3_text);
            circle_2 = (TextView) rootView.findViewById(R.id.circle_2);
            circle_3 = (TextView) rootView.findViewById(R.id.circle_3);
            circle_1 = (TextView) rootView.findViewById(R.id.circle_1);
            qr_image = (ImageView) rootView.findViewById(R.id.qr_image);
            startShoppingButton = (Button) rootView.findViewById(R.id.startShoppingButton);

            WifiManager wifi = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
            this.gpsProvider = new GpsProvider(getActivity());
            coopWifiManager = new CoopWifiManager(wifi);
            passabeneService = PassabeneService.getInstance();

            restoreActionBar();

            disableAllSteps();
            startChecks();

            return rootView;
        }

        public void restoreActionBar() {

            // Handle Toolbar
            Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);


            //android.app.ActionBar actionBar = this.getActionBar();
            //actionBar.setDisplayShowHomeEnabled(false);

            SpannableString s = new SpannableString(getString(R.string.title_activity_main));
            s.setSpan(new CustomTypefaceSpan(getActivity(), "CoopExpRg.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            toolbar.setTitle(s);

            ActionBarActivity activity = (ActionBarActivity) getActivity();
            activity.setSupportActionBar(toolbar);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case (BARCODE_INTENT_ID): {
                    if (resultCode == Activity.RESULT_OK) {
                        String barcode = data.getStringExtra(BARCODE_RESULT_DATA);
                        if (barcode != null && barcode.startsWith("StoreNumber=")) {
                            storeNumber = barcode.split("=")[1];
                            passabeneService.setStoreNumber(storeNumber);
                            startChecks();
                        }
                    }
                    break;
                }
            }
        }

        private void disableAllSteps() {
            qr_image.setVisibility(View.GONE);
            circle_1.setBackgroundResource(R.drawable.circle_inactive);
            circle_2.setBackgroundResource(R.drawable.circle_inactive);
            circle_3.setBackgroundResource(R.drawable.circle_inactive);
            circle_1_text.setTextColor(0xffe2e2e2);
            circle_2_text.setTextColor(0xffe2e2e2);
            circle_3_text.setTextColor(0xffe2e2e2);
        }

        private void activateStep0() {
            circle_1.setBackgroundResource(R.drawable.circle);
            circle_1_text.setTextColor(Color.BLACK);
        }

        private void activateStep1() {
            circle_1.setBackgroundResource(R.drawable.circle_filled);
            circle_1.setTextColor(Color.WHITE);

            circle_2.setBackgroundResource(R.drawable.circle);
            circle_2_text.setTextColor(Color.BLACK);
        }

        private void activateStep2() {
            circle_2.setBackgroundResource(R.drawable.circle_filled);
            circle_2.setTextColor(Color.WHITE);

            circle_3.setBackgroundResource(R.drawable.circle);
            circle_3_text.setTextColor(Color.BLACK);
        }

        private void enableStartShoppingButton() {
            circle_3.setBackgroundResource(R.drawable.circle_filled);
            circle_3.setTextColor(Color.WHITE);
            startShoppingButton.setBackgroundColor(0xFF99C13C);
            startShoppingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                    getActivity().setResult(RESULT_OK, mainIntent);
                    bundle.putBoolean(MainActivity.SETUP_FINISHED, true);
                    bundle.putString(MainActivity.STORE_NUMER, storeNumber);
                    bundle.putBoolean(MainActivity.CATALOG_AVAILABLE, mainIntent.getBooleanExtra(MainActivity.CATALOG_AVAILABLE, catalogAvailable));
                    getActivity().startActivity(mainIntent, bundle);

                    getActivity().finish();
                }
            });
        }

        private int stepNumber = 0;
        private String storeNumber = null;
        private boolean catalogAvailable;
        private boolean ssidFound = true;
        private int setupWifiTries = 0;

        private void startChecks() {

            switch (stepNumber) {
                case 0:
                    activateStep0();
                    checkConnection();
                    break;

                case 1:
                    activateStep1();
                    checkStoreLocation();
                    break;

                case 2:
                    if (storeNumber == null) {
                        showManualScan();
                    } else {
                        qr_image.setVisibility(View.GONE);
                        activateStep2();
                        startSession();

                    }
                    break;

                default:

                    break;
            }

            if (stepNumber == 3) {
                enableStartShoppingButton();

            }
        }

        private void showManualScan() {
            qr_image.setVisibility(View.VISIBLE);
            qr_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent scanIntent = new Intent(getActivity(), ScanditActivity.class);
                    scanIntent.putExtra(Constants.SHOW_SHOPPINGCART, false);
                    startActivityForResult(scanIntent, BARCODE_INTENT_ID);
                }
            });
        }


        private void startSession() {
            new Thread() {
                @Override
                public void run() {
                    passabeneService.startSession(getActivity().getBaseContext());

                    checkCatalog();
                }
            }.start();
        }


        private void setupWifi() {
            if (setupWifiTries < 3) {
                Log.w(Constants.TAG, "SSID not found, add it again");
                PassabeneAccountManager passabeneAccountManager = new PassabeneAccountManager();
                PassabeneAccount account = passabeneAccountManager.getAccount(getActivity());
                coopWifiManager.addWifi(account.getUsername(), account.getPassword());
                try {
                    coopWifiManager.connectToCoopWifi();
                } catch (SSIDNotFoundException e) {
                    setupWifiTries++;
                    setupWifi();
                }
            }

        }

        private void checkConnection() {
            AsyncTask<String, Void, Intent> task = new AsyncTask<String, Void, Intent>() {
                public static final String LOCATION_UNKNOWN = "LOCATION_UNKNOWN";
                boolean connected = false;

                @Override
                protected Intent doInBackground(String[] objects) {
                    Bundle data = new Bundle();
                    final Intent res = new Intent();

                    try {
                        coopWifiManager.connectToCoopWifi();
                    } catch (SSIDNotFoundException e) {
                        setupWifi();
                    }

                    while (!connected) {

                        connected = passabeneService.isConnected();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                    data.putString("RESULT", "SUCCESS");
                    res.putExtras(data);
                    return res;
                }

                @Override
                protected void onPostExecute(Intent intent) {
                    if (intent.hasExtra("RESULT") && "SUCCESS".equals(intent.getStringExtra("RESULT"))) {
                        stepNumber = 1;
                        startChecks();
                    } else {
                        //TODO: show error
                        throw new RuntimeException("Should not happen");
                    }
                }
            }.execute();
        }

        private void checkStoreLocation() {
            AsyncTask<String, Void, Intent> task = new AsyncTask<String, Void, Intent>() {
                public static final String LOCATION_UNKNOWN = "LOCATION_UNKNOWN";
                boolean connected = false;

                @Override
                protected Intent doInBackground(String[] objects) {
                    Bundle data = new Bundle();


                    Location location = gpsProvider.getLastBestLocation();
                    if (location != null) {
                        storeNumber = passabeneService.localizeStore(location.getLatitude(), location.getLongitude());
                    }

                    final Intent res = new Intent();
                    data.putString("RESULT", "SUCCESS");
                    res.putExtras(data);
                    return res;
                }

                @Override
                protected void onPostExecute(Intent intent) {
                    if (intent.hasExtra("RESULT") && "SUCCESS".equals(intent.getStringExtra("RESULT"))) {
                        stepNumber = 2;
                        startChecks();
                    } else {
                        //TODO: show error
                        throw new RuntimeException("Should not happen");
                    }
                }
            }.execute();
        }

        private void checkCatalog() {
            AsyncTask<String, Void, Intent> task = new AsyncTask<String, Void, Intent>() {
                public static final String LOCATION_UNKNOWN = "LOCATION_UNKNOWN";

                @Override
                protected Intent doInBackground(String[] objects) {
                    Bundle data = new Bundle();

                    catalogAvailable = passabeneService.downloadCatalog(getActivity());

                    final Intent res = new Intent();
                    data.putString("RESULT", "SUCCESS");
                    res.putExtras(data);
                    return res;
                }

                @Override
                protected void onPostExecute(Intent intent) {
                    if (intent.hasExtra("RESULT") && "SUCCESS".equals(intent.getStringExtra("RESULT"))) {
                        stepNumber = 3;
                        startChecks();
                    } else {
                        //TODO: show error
                        throw new RuntimeException("Should not happen");
                    }
                }
            }.execute();
        }

    }
}
