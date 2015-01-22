package ch.avendia.passabene.barcode.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.google.zxing.Result;

import ch.avendia.passabene.MainActivity;
import ch.avendia.passabene.barcode.ZXingScannerView;

public class SimpleScannerActivity extends ActionBarActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        //Toast.makeText(this, "Contents = " + rawResult.getText() +
        //        ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
        //mScannerView.startCamera();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(MainActivity.BARCODE_RESULT_DATA, rawResult.getText());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();

    }
}
