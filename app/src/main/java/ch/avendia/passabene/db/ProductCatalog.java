package ch.avendia.passabene.db;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import ch.avendia.passabene.Constants;
import ch.avendia.passabene.api.json.Item;

/**
 * Created by Markus on 24.01.2015.
 */
public class ProductCatalog {

    private ProductCatalogDatabase database;
    private boolean isDatabaseAvailable = false;

    public ProductCatalog(Context context, String storeNumber) {
        database = new ProductCatalogDatabase(context, storeNumber);
        try {
            database.openDataBase();
            isDatabaseAvailable = true;
        } catch(SQLException e) {
            Log.e(Constants.TAG, e.getMessage());
        }
    }

    public Item findItem(String barcode) {
        if(isDatabaseAvailable) {

        }
        return null;
    }


}
