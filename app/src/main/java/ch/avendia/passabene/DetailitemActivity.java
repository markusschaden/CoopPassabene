package ch.avendia.passabene;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.internal.de;

import ch.avendia.passabene.shopping.ShoppingCardHolder;
import ch.avendia.passabene.shopping.ShoppingCartItem;


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

        }

        private TextView itemDetail;
        private TextView itemQuantityTV;
        private Button incQuantityBtn;
        private Button decQuantityBtn;
        private ImageButton deleteImageBtn;
        private ShoppingCartItem sci;
        private TextView itemUnitPrice;
        private TextView itemTotalPrice;
        private Drawable quantityselector;
        private Drawable quantityButtonActive;
        private Drawable quantityButtonInactive;

        private final String FORMAT_TWO_DIGITS = "%.2f";

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
                    setQuantity(-1);
                }
            });

            incQuantityBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setQuantity(1);
                }
            });

            deleteImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShoppingCardHolder.shoppingCart.remove(sci);
                    getActivity().finish();
                }
            });

            sci = ShoppingCardHolder.shoppingCart.get(getArguments().getInt("ID"));
            showItem(sci);

            return rootView;
        }

        private void setQuantity(int addQuantity) {
            if(sci.getQuantity() + addQuantity >= 1) {
                sci.setQuantity(sci.getQuantity() + addQuantity);
                showItem(sci);
            }
        }

        public void showItem(ShoppingCartItem sci) {
            itemDetail.setText(sci.getProduct().getName());
            itemTotalPrice.setText(String.format(FORMAT_TWO_DIGITS, sci.getQuantity() * sci.getProduct().getPrice()));

            if(sci.getQuantity() >= 2) {
                itemUnitPrice.setText("Stück " + String.format(FORMAT_TWO_DIGITS, sci.getProduct().getPrice()));
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
