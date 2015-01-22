
package ch.avendia.passabene.api.json;

import com.google.gson.annotations.Expose;

public class Session {

    @Expose
    private String DatabaseUrl;
    @Expose
    private String SessionId;
    @Expose
    private String StoreLocation;

    /**
     * 
     * @return
     *     The DatabaseUrl
     */
    public String getDatabaseUrl() {
        return DatabaseUrl;
    }

    /**
     * 
     * @param DatabaseUrl
     *     The DatabaseUrl
     */
    public void setDatabaseUrl(String DatabaseUrl) {
        this.DatabaseUrl = DatabaseUrl;
    }

    /**
     * 
     * @return
     *     The SessionId
     */
    public String getSessionId() {
        return SessionId;
    }

    /**
     * 
     * @param SessionId
     *     The SessionId
     */
    public void setSessionId(String SessionId) {
        this.SessionId = SessionId;
    }

    /**
     * 
     * @return
     *     The StoreLocation
     */
    public String getStoreLocation() {
        return StoreLocation;
    }

    /**
     * 
     * @param StoreLocation
     *     The StoreLocation
     */
    public void setStoreLocation(String StoreLocation) {
        this.StoreLocation = StoreLocation;
    }

}
