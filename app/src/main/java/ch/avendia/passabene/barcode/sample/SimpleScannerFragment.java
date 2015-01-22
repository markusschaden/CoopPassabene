package ch.avendia.passabene.barcode.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.zxing.Result;

import ch.avendia.passabene.Constants;
import ch.avendia.passabene.MainActivity;
import ch.avendia.passabene.R;
import ch.avendia.passabene.api.json.Item;
import ch.avendia.passabene.barcode.ZXingScannerView;
import ch.avendia.passabene.shopping.ShoppingCardHolder;

public class SimpleScannerFragment extends Fragment implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private TextView barcodeViewTotalQuantity;
    private TextView barcodeViewTotalPrice;
    private ShoppingCardHolder shoppingCardHolder = new ShoppingCardHolder();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());

        barcodeViewTotalQuantity = (TextView)mScannerView.findViewById(R.id.barcodeViewTotalQuantity);
        barcodeViewTotalPrice = (TextView)mScannerView.findViewById(R.id.barcodeViewTotalPrice);

        calcTotalPrice();
        calcTotalQuantity();



        return mScannerView;
    }

    public void calcTotalPrice() {
        double result = 0;
        for (Item item : shoppingCardHolder.getShoppingCart()) {
            result += (item.getQuantity() * item.getPrice() / 100);
        }
        if(barcodeViewTotalPrice != null) {
            barcodeViewTotalPrice.setText(String.format(Constants.FORMAT_TWO_DIGITS, result));
        }
    }

    public void calcTotalQuantity() {
        int result = 0;
        for (Item item : shoppingCardHolder.getShoppingCart()) {
            result += item.getQuantity();
        }
        if(barcodeViewTotalQuantity != null) {
            barcodeViewTotalQuantity.setText(result + " " + "Artikel");
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    /*@Override
    public void handleResult(Result rawResult) {
        Toast.makeText(getActivity(), "Contents = " + rawResult.getText() +
                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
        mScannerView.startCamera();
    }*/

    @Override
    public void handleResult(Result rawResult) {
        //Toast.makeText(this, "Contents = " + rawResult.getText() +
        //        ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
        //mScannerView.startCamera();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(MainActivity.BARCODE_RESULT_DATA, rawResult.getText());
        getActivity().setResult(Activity.RESULT_OK, resultIntent);
        getActivity().finish();

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
}
