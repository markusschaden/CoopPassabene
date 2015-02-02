package ch.avendia.passabene;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
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
import android.widget.Button;

import ch.avendia.passabene.scandit.ScanditActivity;


public class FinishActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }


        restoreActionBar();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        SpannableString s = new SpannableString("passabene");
        s.setSpan(new CustomTypefaceSpan(this, "CoopExpRg.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        actionBar.setTitle(s);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_finish, menu);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final int BARCODE_INTENT_ID = 10;

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_finish, container, false);

            Button button = (Button)rootView.findViewById(R.id.finishButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent scanIntent = new Intent(getActivity(), ScanditActivity.class);
                    startActivityForResult(scanIntent, BARCODE_INTENT_ID);
                }
            });


            return rootView;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case (BARCODE_INTENT_ID): {
                    if (resultCode == Activity.RESULT_OK) {
                        String barcode = data.getStringExtra(MainActivity.BARCODE_RESULT_DATA);
                        if (barcode != null && barcode.startsWith("270")) {
                            AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                                private ProgressDialog dialog;

                                @Override
                                protected Void doInBackground(Void... voids) {

                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    return null;
                                }

                                @Override
                                protected void onPreExecute() {
                                    //Looper.prepare();
                                    Looper.loop();
                                    dialog = new ProgressDialog(getActivity());
                                    dialog.setMessage("Loading...");
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.show();
                                }

                                @Override
                                protected void onPostExecute(Void result) {
                                    if(dialog.isShowing()) {
                                        dialog.dismiss();
                                    }

                                }
                            }.execute();

                        }
                    }
                    break;
                }
            }
        }
    }
}
