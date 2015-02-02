package ch.avendia.passabene.api;

import ch.avendia.passabene.api.json.DTO;
import ch.avendia.passabene.api.json.Session;

/**
 * Created by Markus on 22.01.2015.
 */
public class EndSessionApiCall extends AdvancedApiCall {

    private String posNumber;
    private Session session;

    public EndSessionApiCall(String posNumber) {
        this.posNumber = posNumber;
    }

    @Override
    public DTO execute(Session session) {
        if(session == null) {
            return null;
        }
        this.session = session;

        String url = "EndSession?sessionId="+session.getSessionId()+"&posNumber="+posNumber;

        String json = sender.sendGet(BASE_URL  + url);
        return stringToDTO(json);
    }

    @Override
    public void doUpdate(DTO dto, boolean blockedMode) {
        if (dto != null) {
            this.session = null;
        }
    }
}
