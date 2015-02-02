package ch.avendia.passabene;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ch.avendia.passabene.account.AccountGeneral;
import ch.avendia.passabene.account.AuthenticatorActivity;
import ch.avendia.passabene.account.PassabeneAccount;
import ch.avendia.passabene.account.PassabeneAccountManager;
import ch.avendia.passabene.api.AddItemApiCall;
import ch.avendia.passabene.api.PassabeneService;
import ch.avendia.passabene.scandit.ScanditActivity;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, ItemFragment.ItemFragmentListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    public static final String BARCODE_RESULT_DATA = "barcode";
    public static final String STORE_NUMER = "STORE_NUMBER";
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private static final int BARCODE_INTENT_ID = 10;
    private static final int SETUP_INTENT_ID = 11;
    private static final int LOGIN_INTENT_ID = 120;
    public static final String ITEM_ID = "item_id";
    public static final String SETUP_FINISHED = "SETUP_FINISHED";
    public static final String CATALOG_AVAILABLE = "CATALOG_AVAILABLE";


    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private boolean loggedIn = false;
    private ItemFragment itemFragment;
    private final String TITLE_PASSABENE = "passabene";
    private PassabeneService passabeneService;
    private boolean setup_finished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        passabeneService = passabeneService.getInstance();


        FragmentManager fragmentManager = getSupportFragmentManager();
        itemFragment = ItemFragment.newInstance();

        PassabeneAccountManager passabeneAccountManager = new PassabeneAccountManager();
        PassabeneAccount account = passabeneAccountManager.getAccount(this.getBaseContext());

        if (account == null) {

            Intent intent = new Intent(this, AuthenticatorActivity.class);
            intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, AccountGeneral.ACCOUNT_TYPE);
            intent.putExtra(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
            intent.putExtra(AuthenticatorActivity.ARG_START_FROM_APP, true);
            startActivityForResult(intent, LOGIN_INTENT_ID);
            finish();
        } else {

            fragmentManager.beginTransaction()
                    .replace(R.id.container, itemFragment)
                    .commit();

            restoreActionBar();

            if (!passabeneService.isReady()) {
                Intent intent = new Intent(this, SetupActivity.class);
                startActivityForResult(intent, SETUP_INTENT_ID);
                finish();
            }

        }
    }

    private void createSidebar() {
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        //mNavigationDrawerFragment.setMenuVisibility(false);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        /*switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }*/
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        Typeface coopRgFont = Typeface.createFromAsset(getAssets(), "fonts/CoopRg.ttf");
        Typeface coopExpRgFont = Typeface.createFromAsset(getAssets(), "fonts/CoopExpRg.ttf");

        SpannableString s = new SpannableString(getString(R.string.title_activity_main));
        s.setSpan(new CustomTypefaceSpan(this, "CoopExpRg.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        actionBar.setTitle(s);
    }


    @Override
    protected void onResume() {
        super.onResume();

        itemFragment.refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Settings Activity", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (BARCODE_INTENT_ID): {
                if (resultCode == Activity.RESULT_OK) {
                    String barcode = data.getStringExtra(BARCODE_RESULT_DATA);
                    // TODO Update your TextView.
                    Toast.makeText(this, "Barcode: " + barcode, Toast.LENGTH_SHORT).show();

                    passabeneService.execute(new AddItemApiCall(barcode, 1));
                }
                break;
            }

            case LOGIN_INTENT_ID:
                if (resultCode == Activity.RESULT_OK) {
                    Intent setupIntent = new Intent(this, SetupActivity.class);
                    //TODO: setup und store auswahl
                    startActivityForResult(setupIntent, SETUP_INTENT_ID);
                } else {
                    //Exit app
                }
                break;

            case SETUP_INTENT_ID:
                if (resultCode == Activity.RESULT_OK) {
                    setup_finished = data.getBooleanExtra(SETUP_FINISHED, false);
                    Toast.makeText(this, "Setup: " + setup_finished, Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "Catalog: " + data.getBooleanExtra(CATALOG_AVAILABLE, false), Toast.LENGTH_SHORT).show();
                } else {
                    //exit app
                    finish();
                }

                break;
        }
    }


    @Override
    public void onScanClick() {
        //Intent intent = new Intent(this, SimpleScannerActivity.class);
        //Intent intent = new Intent(this, SimpleScannerFragmentActivity.class);
        //this.startActivityForResult(intent, BARCODE_INTENT_ID);

        Intent scanIntent = new Intent(this, ScanditActivity.class);
        scanIntent.putExtra(Constants.SHOW_SHOPPINGCART, true);
        startActivityForResult(scanIntent, BARCODE_INTENT_ID);

    }

    @Override
    public void onItemClick(String id) {

    }

    @Override
    public void onCounterClick() {
        Intent finishIntent = new Intent(this, FinishActivity.class);
        startActivity(finishIntent);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


}
