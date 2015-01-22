package ch.avendia.passabene.barcodezbar.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import ch.avendia.passabene.R;

public class ScannerFragmentActivity extends ActionBarActivity {
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_scanner_fragment);
    }
}