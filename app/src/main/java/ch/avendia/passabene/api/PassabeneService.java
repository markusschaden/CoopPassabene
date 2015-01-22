package ch.avendia.passabene.api;

import java.io.IOException;

import ch.avendia.passabene.PassabeneSettings;
import ch.avendia.passabene.api.json.DTO;
import ch.avendia.passabene.api.json.Item;
import ch.avendia.passabene.api.json.Session;
import ch.avendia.passabene.shopping.ShoppingCardHolder;

/**
 * Created by Markus on 11.01.2015.
 */
public class PassabeneService {

    private static PassabeneService instance;
    private final String BASE_URL = "http://smartphopb.coop.ch/SelfScanWebService/SelfScanAPI.mdc/";
    private RequestHandlerPassabene requestHandler;
    private String sessionId;
    private PassabeneSettings settings;
    private Session session;
    private ShoppingCardHolder shoppingCardHolder = new ShoppingCardHolder();
    private boolean catalogAvailable = true;

    private PassabeneService() {
        requestHandler = new RequestHandlerPassabene();
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


    public DTO getStoreNumber(double lat, double lon) {
        String url = "GetStoreNumber?geoLocation=%2B{0}%3A%2B{1}";

        return requestHandler.sendGetRequest(BASE_URL + url);
    }

    public DTO getStatus() {
        String url = "GetStatus?appVersion=2.0.0";

        return requestHandler.sendGetRequest(BASE_URL + url);
    }

    public void logMessage(String message) throws IOException {
        String url = "LogMessage?sessionId={0}";

        requestHandler.sendPostRequest(BASE_URL + url, message);
    }

    public void downloadCatalog() {
        if(session != null) {
            requestHandler.downloadFile("DATABASE-" + session.getStoreLocation() + ".sqlite", session.getDatabaseUrl());
        }
    }

    @Deprecated
    public void getStatusEx() {

    }
}
