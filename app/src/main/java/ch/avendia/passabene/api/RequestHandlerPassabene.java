package ch.avendia.passabene.api;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import ch.avendia.passabene.api.json.DTO;

/**
 * Created by Markus on 13.01.2015.
 */
public class RequestHandlerPassabene {

    public DTO sendGetRequest(String url)  {
        try {
            return sendPostRequest(url, null);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DTO sendPostRequest(String url, String post) throws IOException {

        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = httpclient.execute(new HttpGet(url));
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.getEntity().writeTo(out);
            out.close();
            String responseString = out.toString();
            Gson gson = new Gson();
            DTO dto = gson.fromJson(responseString, DTO.class);
            return dto;
        } else {
            response.getEntity().getContent().close();
            throw new IOException(statusLine.getReasonPhrase());
        }
    }


    public void downloadFile(String url, String databaseUrl) {
    }
}
