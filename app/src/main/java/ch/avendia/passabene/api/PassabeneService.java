package ch.avendia.passabene.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import ch.avendia.passabene.Constants;
import ch.avendia.passabene.Language;
import ch.avendia.passabene.MainActivity;
import ch.avendia.passabene.PassabeneSettings;
import ch.avendia.passabene.account.PassabeneAccount;
import ch.avendia.passabene.account.PassabeneAccountManager;
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
    private String storeNumber;
    private PassabeneWorkerThread passabeneWorkerThread;

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

    public void execute(BasicApiCall apiCall) {
        if(apiCall == null) {
            return;
        }
        //always blocking
        DTO dto = apiCall.execute();

        if (apiCall instanceof StartSessionApiCall) {
            if (dto != null && dto.getSession() != null) {
                this.session = dto.getSession();
                passabeneWorkerThread = new PassabeneWorkerThread(session);
            } else {
                Log.e(Constants.TAG, "StartSession was unsuccessful");
            }
        } else {
            Log.w(Constants.TAG, "unknown BasicApiCall type, " + apiCall.getClass().getSimpleName());
            throw new RuntimeException("unknown BasicApiCall type, " + apiCall.getClass().getSimpleName());
        }
    }

    public void execute(AdvancedApiCall apiCall) {

        if(isNonBlockingAvailable(apiCall)) {
            runNonBlockingExecute(apiCall);

        } else {

            DTO dto = apiCall.execute(session);
            apiCall.doUpdate(dto, true);
            /*if (apiCall instanceof AddItemApiCall || apiCall instanceof DeleteItemApiCall) {
                if (dto != null && dto.getTicket() != null && dto.getTicket().getItems() != null) {
                    shoppingCardHolder.setShoppingCart(dto.getTicket().getItems());
                } else {
                    shoppingCardHolder.getShoppingCart().add(new Item("test", 20000));
                }

            } else if (apiCall instanceof EndSessionApiCall) {
                if (dto != null) {
                    this.session = null;
                    //todo: destroy passabeneWorkerThread correctly
                } else {
                    Log.e(Constants.TAG, "EndSession was unsuccessful");
                }

            }*/
        }

    }

    private void runNonBlockingExecute(AdvancedApiCall apiCall) {
        passabeneWorkerThread.addApiCallToQueue(apiCall);
        if (apiCall instanceof AddItemApiCall) {

            Item item = shoppingCardHolder.getLocalItemFromBarcode(((AddItemApiCall) apiCall).getBarcode());
            if(item != null) {
                item.setQuantity(item.getQuantity() + ((AddItemApiCall) apiCall).getQuantity());
            } else {
                //lookup over sql
                Item newProduct = new Item("unknown product", 10000);
                shoppingCardHolder.addLocalItem(newProduct);
            }

        } else if(apiCall instanceof DeleteItemApiCall) {

            Item item = shoppingCardHolder.getLocalItemFromBarcode(((DeleteItemApiCall) apiCall).getBarcode());
            if(item.getQuantity() <= ((DeleteItemApiCall)apiCall).getQuantity()) {
                //delete item
                shoppingCardHolder.removeLocalItem(item);
            } else {
                //reduce quantity
                item.setQuantity(item.getQuantity()-((DeleteItemApiCall)apiCall).getQuantity());
            }


        }
    }

    private boolean isNonBlockingAvailable(ApiCall apiCall) {
        if (passabeneWorkerThread != null && catalogAvailable && (apiCall instanceof AddItemApiCall || apiCall instanceof DeleteItemApiCall)) {
            return true;
        }
        return false;
    }
    public DTO executeWithResult(BasicApiCall apiCall) {
        return apiCall.execute();
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public String localizeStore(double lat, double lon) {
        DTO dto = executeWithResult(new GetStoreApiCall(lat, lon));
        if(dto != null) {
            storeNumber = dto.getText();
            return dto.getText();
        } else {
            //return null;
        }
        return "6258";
        //return null;
    }


    public DTO getStatus() {
        return executeWithResult(new GetStatusApiCall());
    }

    public boolean isConnected() {
        connected=!TextUtils.equals(null, new Sender().sendGet("http://www.markus.cc"));
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

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public void startSession(Context context) {
        PassabeneAccountManager passabeneAccountManager = new PassabeneAccountManager();
        PassabeneAccount account = passabeneAccountManager.getAccount(context);

        StartSessionApiCall startSessionApiCall = new StartSessionApiCall(account.getUsername(), storeNumber, Language.DE);
        execute(startSessionApiCall);
    }
}
