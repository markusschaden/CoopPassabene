package ch.avendia.passabene.api;

import java.io.IOException;
import java.security.spec.PSSParameterSpec;

import ch.avendia.passabene.PassabeneSettings;
import ch.avendia.passabene.api.json.DTO;
import ch.avendia.passabene.api.json.Session;

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

    @Deprecated
    public static boolean authenticate(String username, String password) {
        return true;
    }

    @Deprecated
    public static String getToken() {
        return "test";
    }

    public DTO addItem(String barcode, int quantity) {
        String url = "AddItem?sessionId={0}&barcode={1}&quantity=1&linkedBarcode=";

        return requestHandler.sendGetRequest(BASE_URL + url);
    }

    public DTO removeItem(String guid, int quantity) {
        String url = "RemoveItem?sessionId={0}&guid={1}&quantity={2}&removeLinkedItem=false";

        return requestHandler.sendGetRequest(BASE_URL + url);
    }

    public DTO startSession(String storeNumber) {
        String url = "StartSessionEx?cardNumber={0}&storeNumber={1}&language={2}&sdkVersion=2.5.2.4&originType=iPhone";

        DTO dto = requestHandler.sendGetRequest(BASE_URL + url);

        Session session = dto.getSession();
        if(session != null) {
            this.sessionId = session.getSessionId();
            this.session = session;
        }

        return dto;
    }

    public DTO endSession(int pos) {
        String url = "EndSession?sessionId={0}&posNumber={1}";

        return requestHandler.sendGetRequest(BASE_URL + url);
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
