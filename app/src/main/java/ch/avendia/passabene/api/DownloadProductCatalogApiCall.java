package ch.avendia.passabene.api;

import android.content.Context;

import ch.avendia.passabene.api.json.DTO;
import ch.avendia.passabene.api.json.Session;
import ch.avendia.passabene.db.ProductCatalogDatabase;
import ch.avendia.passabene.network.Sender;

/**
 * Created by Markus on 22.01.2015.
 */
public class DownloadProductCatalogApiCall {

    private Sender sender = new Sender();
    private Context context;

    public DownloadProductCatalogApiCall(Context context) {
        this.context = context;
    }

    public boolean execute(Session session) {
        if(session == null) {
            return false;
        }

        return sender.downloadFile(context, "productCatalog-"+session.getStoreLocation()+".sqlite", session.getDatabaseUrl());

    }
}
