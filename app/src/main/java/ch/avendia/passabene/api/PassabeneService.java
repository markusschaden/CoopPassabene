package ch.avendia.passabene.api;

import android.content.Context;
import android.text.TextUtils;

import java.io.IOException;

import ch.avendia.passabene.PassabeneSettings;
import ch.avendia.passabene.api.json.DTO;
import ch.avendia.passabene.api.json.Item;
import ch.avendia.passabene.api.json.Session;
import ch.avendia.passabene.network.Sender;
import ch.avendia.passabene.shopping.ShoppingCardHolder;

/**
 * Created by Markus on 11.01.2015.
 */
public class PassabeneService {

    private static PassabeneService instance;
    private final String BASE_URL = "http://smartphopb.coop.ch/SelfScanWebService/SelfScanAPI.mdc/";
    private String sessionId;
    private PassabeneSettings settings;
    private Session session;
    private ShoppingCardHolder shoppingCardHolder = new ShoppingCardHolder();
    private boolean catalogAvailable = true;
    private boolean connected = false;

    private PassabeneService() {
        //TODO: load from file
        settings = new PassabeneSettings();
    }

    public static PassabeneService getInstance() {
        if(instance == null) {
            synchronized (PassabeneService.class) {
                if(instance == null) {
                    instance = new PassabeneService();
                }
            }
        }
        return instance;
    }

    public void execute(ApiCall apiCall) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(catalogAvailable && (apiCall instanceof AddItemApiCall || apiCall instanceof  DeleteItemApiCall)) {
            //edit catalog direct, update over queue
            if (apiCall instanceof AddItemApiCall) {
                //TODO: add to queue
                Item item = shoppingCardHolder.getItemFromBarcode(((AddItemApiCall)apiCall).getBarcode());
                if(item != null) {
                    item.setQuantity(item.getQuantity() + ((AddItemApiCall) apiCall).getQuantity());
                } else {
                    //lookup over sql
                    Item newProduct = new Item("unknown product", 10000);
                    shoppingCardHolder.addItem(newProduct);
                }

            } else if(apiCall instanceof DeleteItemApiCall) {
                //TODO: add to queue
                Item item = shoppingCardHolder.getItemFromBarcode(((DeleteItemApiCall)apiCall).getBarcode());
                if(item.getQuantity() <= ((DeleteItemApiCall)apiCall).getQuantity()) {
                    //delete item
                    shoppingCardHolder.removeItem(item);
                } else {
                    //reduce quantity
                    item.setQuantity(item.getQuantity()-((DeleteItemApiCall)apiCall).getQuantity());
                }


            }


        } else {

            DTO dto = apiCall.execute(session);

            if (apiCall instanceof AddItemApiCall || apiCall instanceof DeleteItemApiCall) {
                if (dto != null && dto.getTicket() != null && dto.getTicket().getItems() != null) {
                    shoppingCardHolder.setShoppingCart(dto.getTicket().getItems());
                } else {
                    shoppingCardHolder.getShoppingCart().add(new Item("test", 20000));
                }
            } else if (apiCall instanceof StartSessionApiCall) {
                if (dto != null && dto.getSession() != null) {
                    this.session = dto.getSession();
                }
            } else if (apiCall instanceof EndSessionApiCall) {
                if (dto != null) {
                    this.session = null;
                }

            }
        }

    }

    public DTO executeWithResult(BasicApiCall apiCall) {
        return apiCall.execute();
    }

    public String getStoreNumber(double lat, double lon) {
        DTO dto = executeWithResult(new GetStoreApiCall(lat, lon));
        if(dto != null) {
            return dto.getText();
        }
        return "6258";
        //return null;
    }


    public DTO getStatus() {
        return executeWithResult(new GetStatusApiCall());
    }

    public boolean isConnected() {
        connected=!TextUtils.equals(null, new Sender().sendGet("http://www.avendia.ch"));
        return connected;
    }

    public boolean isReady() {
        return connected;
    }


    /*public void logMessage(String message) throws IOException {
        String url = "LogMessage?sessionId={0}";

        requestHandler.sendPostRequest(BASE_URL + url, message);
    }*/

    public boolean downloadCatalog(Context context) {
        return new DownloadProductCatalogApiCall(context).execute(session);
    }

    @Deprecated
    public void getStatusEx() {

    }
}
