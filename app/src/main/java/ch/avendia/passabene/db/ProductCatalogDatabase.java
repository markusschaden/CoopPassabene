package ch.avendia.passabene.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ch.avendia.passabene.Constants;

/**
 * Created by Markus on 24.01.2015.
 */
public class ProductCatalogDatabase extends SQLiteOpenHelper {

    private Context context;
    private static String DB_PATH = "/data/data/ch.avendia.passabene/databases/";

    public static String DB_NAME = "";

    private SQLiteDatabase dataBase;

    public ProductCatalogDatabase(Context context, String storeNumber) {
        super(context, "productCatalog-"+storeNumber+".sqlite", null, 1);
        DB_NAME = "productCatalog-"+storeNumber+".sqlite";
        this.context = context;

        copyServerDatabase();
    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        dataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    /**
     * Copies your database from your local downloaded database that is copied from the server
     * into the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyServerDatabase() {
        // by calling this line an empty database will be created into the default system path
        // of this app - we will then overwrite this with the database from the server
        SQLiteDatabase db = getReadableDatabase();
        db.close();


        OutputStream os = null;
        InputStream is = null;
        try {
            // Log.d(TAG, "Copying DB from server version into app");
            is = context.openFileInput(DB_NAME);
            os = new FileOutputStream(DB_PATH);

            copyFile(os, is);
        } catch (Exception e) {
            Log.e(Constants.TAG, "Server Database was not found - did it download correctly?", e);
        } finally {
            try {
                //Close the streams
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                Log.e(Constants.TAG, "failed to close databases");
            }
        }
        // Log.d(TAG, "Done Copying DB from server");
    }


    private void copyFile(OutputStream os, InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }
        os.flush();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
