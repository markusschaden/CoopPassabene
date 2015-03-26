package ch.avendia.passabene.scandit;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mirasense.scanditsdk.ScanditSDKAutoAdjustingBarcodePicker;
import com.mirasense.scanditsdk.ScanditSDKBarcodePicker;
import com.mirasense.scanditsdk.interfaces.ScanditSDK;
import com.mirasense.scanditsdk.interfaces.ScanditSDKListener;

import ch.avendia.passabene.Constants;
import ch.avendia.passabene.CustomTypefaceSpan;
import ch.avendia.passabene.MainActivity;
import ch.avendia.passabene.R;
import ch.avendia.passabene.api.json.Item;
import ch.avendia.passabene.shopping.ShoppingCardHolder;

/**
 * Simple demo application illustrating the use of the Scandit SDK.
 */

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing premissions and
 * limitations under the License.
 */
public class ScanditActivity extends ActionBarActivity implements ScanditSDKListener {

    // The main object for recognizing a displaying barcodes.
    private ScanditSDK mBarcodePicker;
    
    // Enter your Scandit SDK App key here.
    // Your Scandit SDK App key is available via your Scandit SDK web account.
    public static final String sScanditSdkAppKey = "Kv4uSb1PgQDzbKH7sYBmWP1ErZV/7VCvklTYsh9Y408";

    private TextView barcodeViewTotalQuantity;
    private TextView barcodeViewTotalPrice;
    private ShoppingCardHolder shoppingCardHolder = new ShoppingCardHolder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //restoreActionBar();

        // Initialize and start the bar code recognition.
        initializeAndStartBarcodeScanning();

        barcodeViewTotalQuantity = (TextView)findViewById(R.id.barcodeViewTotalQuantity);
        barcodeViewTotalPrice = (TextView)findViewById(R.id.barcodeViewTotalPrice);

        calcTotalPrice();
        calcTotalQuantity();
    }

    public void restoreActionBar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        SpannableString s = new SpannableString(getString(R.string.title_activity_main));
        s.setSpan(new CustomTypefaceSpan(this, "CoopExpRg.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toolbar.setTitle(s);
        setSupportActionBar(toolbar);
    }



    @Override
    protected void onPause() {
        // When the activity is in the background immediately stop the 
        // scanning to save resources and free the camera.
        mBarcodePicker.stopScanning();

        super.onPause();
    }
    
    @Override
    protected void onResume() {
        // Once the activity is in the foreground again, restart scanning.
        mBarcodePicker.startScanning();
        super.onResume();
    }

    /**
     * Initializes and starts the bar code scanning.
     */
    public void initializeAndStartBarcodeScanning() {
        // Switch to full screen.
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);*/
        setContentView(R.layout.fragment_scandit);
        
        // We instantiate the automatically adjusting barcode picker that will
        // choose the correct picker to instantiate. Be aware that this picker
        // should only be instantiated if the picker is shown full screen as the
        // legacy picker will rotate the orientation and not properly work in
        // non-fullscreen.
        ScanditSDKBarcodePicker picker = new ScanditSDKBarcodePicker(
                    this, sScanditSdkAppKey, ScanditSDKAutoAdjustingBarcodePicker.CAMERA_FACING_BACK);



        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.scanner_fragment);
        mainLayout.addView(picker);

        Bundle extras = getIntent().getExtras();
        RelativeLayout r = (RelativeLayout)findViewById(R.id.bottomBar);
        if(extras != null) {
            if (extras.getBoolean(Constants.SHOW_SHOPPINGCART, false)) {
                r.setVisibility(View.VISIBLE);
            } else {
                r.setVisibility(View.GONE);
            }
        } else {
            r.setVisibility(View.GONE);
        }


        // Add both views to activity, with the scan GUI on top.
        //setContentView(picker);
        mBarcodePicker = picker;

        // Restrict the area in which the recognition actively searches for barcodes.
        /*mBarcodePicker.restrictActiveScanningArea(true);
// Reduce the active area to 10% of the display's height.
        mBarcodePicker.setScanningHotSpotHeight(0.1f);
// Reduce the size of the white viewfinder rectangle.
// This only affects the rectangle - not the active scanning area!
        mBarcodePicker.getOverlayView().setViewfinderDimension(0.1f, 0.6f, 0.1f, 0.6f);
        */

        // Register listener, in order to be notified about relevant events 
        // (e.g. a successfully scanned bar code).
        mBarcodePicker.getOverlayView().addListener(this);
        mBarcodePicker.getOverlayView().setVibrateEnabled(true);

        restoreActionBar();
    }

    /** 
     *  Called when a barcode has been decoded successfully.
     *  
     *  @param barcode Scanned barcode content.
     *  @param symbology Scanned barcode symbology.
     */
    public void didScanBarcode(String barcode, String symbology) {
        // Remove non-relevant characters that might be displayed as rectangles
        // on some devices. Be aware that you normally do not need to do this.
        // Only special GS1 code formats contain such characters.
        String cleanedBarcode = "";
        for (int i = 0 ; i < barcode.length(); i++) {
            if (barcode.charAt(i) > 30) {
                cleanedBarcode += barcode.charAt(i);
            }
        }
        
        //Toast.makeText(this, symbology + ": " + cleanedBarcode, Toast.LENGTH_LONG).show();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(MainActivity.BARCODE_RESULT_DATA, cleanedBarcode);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
    
    /** 
     * Called when the user entered a bar code manually.
     * 
     * @param entry The information entered by the user.
     */
    public void didManualSearch(String entry) {
    	Toast.makeText(this, "User entered: " + entry, Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void didCancel() {
        mBarcodePicker.stopScanning();
        finish();
    }
    
    @Override
    public void onBackPressed() {
        mBarcodePicker.stopScanning();
        finish();
    }

    private void calcTotalPrice() {
        double result = 0;
        for (Item item : shoppingCardHolder.getShoppingCart()) {
            result += (item.getQuantity() * item.getPrice() / 100);
        }
        if(barcodeViewTotalPrice != null) {
            barcodeViewTotalPrice.setText(String.format(Constants.FORMAT_TWO_DIGITS, result));
        }
    }

    private void calcTotalQuantity() {
        int result = 0;
        for (Item item : shoppingCardHolder.getShoppingCart()) {
            result += item.getQuantity();
        }
        if(barcodeViewTotalQuantity != null) {
            barcodeViewTotalQuantity.setText(result + " " + "Artikel");
        }
    }
}
