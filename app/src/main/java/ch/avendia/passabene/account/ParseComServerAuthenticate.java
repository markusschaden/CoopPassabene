package ch.avendia.passabene.account;

import java.io.Serializable;

/**
 * Created by Markus on 22.01.2015.
 */
public class ParseComServerAuthenticate implements ServerAuthenticate{

    @Override
    public String userSignIn(String user, String pass, String authType) throws Exception {

        /*Log.d("udini", "userSignIn");

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.parse.com/1/login";


        String query = null;
        try {
            query = String.format("%s=%s&%s=%s", "username", URLEncoder.encode(user, "UTF-8"), "password", pass);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        url += "?" + query;

        HttpGet httpGet = new HttpGet(url);

        httpGet.addHeader("X-Parse-Application-Id", "XUafJTkPikD5XN5HxciweVuSe12gDgk2tzMltOhr");
        httpGet.addHeader("X-Parse-REST-API-Key", "8L9yTQ3M86O4iiucwWb4JS7HkxoSKo7ssJqGChWx");

        HttpParams params = new BasicHttpParams();
        params.setParameter("username", user);
        params.setParameter("password", pass);
        httpGet.setParams(params);
//        httpGet.getParams().setParameter("username", user).setParameter("password", pass);

        String authtoken = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);

            String responseString = EntityUtils.toString(response.getEntity());
            if (response.getStatusLine().getStatusCode() != 200) {
                ParseComError error = new Gson().fromJson(responseString, ParseComError.class);
                throw new Exception("Error signing-in ["+error.code+"] - " + error.error);
            }

            User loggedUser = new Gson().fromJson(responseString, User.class);
            authtoken = loggedUser.sessionToken;

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //String authtoken = "12324";
        //return authtoken;
        if("8707".equals(pass)) {
            return "123456";
        } else {
            throw new Exception("invalid password");
        }
    }

    @Override
    public String checkCredentials(String user, String pass) throws Exception {

        Thread.sleep(3000);

        if("8707".equals(pass)) {
            return "valid token";
        } else {
            throw new Exception("invalid password");
        }

    }

    @Override
    public String signInToStore(String user, String pass) throws Exception {
        return null;
    }


    private class ParseComError implements Serializable {
        int code;
        String error;
    }


}
