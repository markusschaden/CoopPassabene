package ch.avendia.passabene;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import ch.avendia.passabene.api.AddItemApiCall;
import ch.avendia.passabene.api.DeleteItemApiCall;
import ch.avendia.passabene.api.PassabeneService;
import ch.avendia.passabene.api.json.Item;
import ch.avendia.passabene.shopping.ShoppingCardHolder;

public class DetailitemActivity extends ActionBarActivity {


    private PlaceholderFragment detailFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailitem);
        int id = getIntent().getIntExtra(MainActivity.ITEM_ID, 0);
        detailFragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ID",id);
        detailFragment.setArguments(bundle);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, detailFragment)
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
        getMenuInflater().inflate(R.menu.menu_detailitem, menu);
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
            shoppingCardHolder = new ShoppingCardHolder();
        }

        private TextView itemDetail;
        private TextView itemQuantityTV;
        private Button incQuantityBtn;
        private Button decQuantityBtn;
        private ImageButton deleteImageBtn;
        private Item sci;
        private TextView itemUnitPrice;
        private TextView itemTotalPrice;
        private Drawable quantityselector;
        private Drawable quantityButtonActive;
        private Drawable quantityButtonInactive;
        private ShoppingCardHolder shoppingCardHolder;
        private PassabeneService passabeneService = PassabeneService.getInstance();
        private int itemId = 0;
        private ProgressDialog progressDialog;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detailitem, container, false);

            itemDetail = (TextView)rootView.findViewById(R.id.itemDetail);

            decQuantityBtn = (Button)rootView.findViewById(R.id.btnDecQuantity);
            incQuantityBtn = (Button)rootView.findViewById(R.id.btnIncQuantity);
            deleteImageBtn = (ImageButton)rootView.findViewById(R.id.btnRemoveItem);
            itemQuantityTV = (TextView)rootView.findViewById(R.id.itemQuantity);

            itemUnitPrice = (TextView)rootView.findViewById(R.id.itemPricePerItem);
            itemTotalPrice = (TextView)rootView.findViewById(R.id.itemTotalPrice);

            quantityselector = getResources().getDrawable(R.drawable.quantityselector);
            quantityButtonActive = getResources().getDrawable(R.drawable.quantityactive);
            quantityButtonInactive = getResources().getDrawable(R.drawable.quantityinactive);

            decQuantityBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showLoading();

                    new AsyncTask<String, Void, Intent>() {
                        @Override
                        protected Intent doInBackground(String... strings) {
                            passabeneService.execute(new DeleteItemApiCall(sci.getBarcode(), 1));
                            return null;
                        }
                        @Override
                        protected void onPostExecute(Intent intent) {
                            showItem();
                            hideLoading();
                        }
                    }.execute();
                }
            });

            incQuantityBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showLoading();
                    new AsyncTask<String, Void, Intent>() {
                        @Override
                        protected Intent doInBackground(String... strings) {
                            passabeneService.execute(new AddItemApiCall(sci.getBarcode(), 1));
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Intent intent) {
                            showItem();
                            hideLoading();
                        }
                    }.execute();
                }
            });

            deleteImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showLoading();

                    new AsyncTask<String, Void, Intent>() {
                        @Override
                        protected Intent doInBackground(String... strings) {
                            passabeneService.execute(new DeleteItemApiCall(sci.getBarcode(), sci.getQuantity()));

                            return null;
                        }

                        @Override
                        protected void onPostExecute(Intent intent) {
                            hideLoading();
                            //shoppingCardHolder.getShoppingCart().remove(sci);
                            getActivity().finish();
                        }
                    }.execute();
                }

            });

            itemId = getArguments().getInt("ID");
            showItem();

            return rootView;
        }

        private void hideLoading() {
            progressDialog.hide();
        }

        private void showLoading() {
            progressDialog = ProgressDialog.show(getActivity(), "Update", "Please Wait ...", true);
        }


        public void showItem() {
            sci = shoppingCardHolder.getLocalItemFromId(itemId);

            itemDetail.setText(sci.getDescription());
            itemTotalPrice.setText(String.format(Constants.FORMAT_TWO_DIGITS, sci.getQuantity() * sci.getPrice() / 100.0));

            if(sci.getQuantity() >= 2) {
                itemUnitPrice.setText("Stück " + String.format(Constants.FORMAT_TWO_DIGITS, sci.getPrice() / 100.0));
                itemQuantityTV.setBackground(quantityselector);
                itemQuantityTV.setText(""+sci.getQuantity());
                decQuantityBtn.setBackground(quantityButtonActive);
                decQuantityBtn.setTextColor(0xFF000000);
            } else if(sci.getQuantity() == 1) {
                itemUnitPrice.setText("");
                itemQuantityTV.setText(sci.getQuantity()+"x");
                itemQuantityTV.setBackgroundColor(Color.WHITE);
                decQuantityBtn.setBackground(quantityButtonInactive);
                decQuantityBtn.setTextColor(0xffc1c1c1);
            }
        }
    }
}
