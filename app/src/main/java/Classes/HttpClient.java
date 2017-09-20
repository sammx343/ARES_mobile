package Classes;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by UTA2 on 1/02/17.
 */

public class HttpClient {

    public RequestParams params;

    public String action;

    HttpEntity entity;

    String contentType;

    JSONObject j;

    String[] base = {"https://ares-sammx343.c9users.io/api/v1",
            "http://201.150.96.82:3001/api/v1/"};
    String URL = base[0];

    AsyncHttpClient petition = new AsyncHttpClient();

    public HttpClient( RequestParams params, String action){
        this.params = params;
        this.action = URL + action;
    }

    public HttpClient(JSONObject j, String url){
        this.action = url;
        this.j = j;
    }

    public void post(AsyncHttpResponseHandler responseHandler) {
        petition.post(action, params, responseHandler);
    }

    public void post2(AsyncHttpResponseHandler responseHandler, Context context) {
        ByteArrayEntity entity = null;
        try {
            entity = new ByteArrayEntity(j.toString().getBytes("UTF-8"));
            System.out.println("Esta entrando aqui");

            System.out.println(j.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        petition.post(context ,action, entity, "application/json" , responseHandler);
    }

    public void get(AsyncHttpResponseHandler responseHandler) {
        petition.get(action, params, responseHandler);
    }
}
