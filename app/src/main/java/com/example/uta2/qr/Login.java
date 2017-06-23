package com.example.uta2.qr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Classes.Activity;
import Classes.Event;
import Classes.HttpClient;
import cz.msebera.android.httpclient.Header;

public class Login extends AppCompatActivity {
    Button login;
    JSONObject response;
    String token;
    String role;
    String fullName;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefs = getSharedPreferences("storage", Context.MODE_PRIVATE);

        login = (Button) findViewById(R.id.btn_login);
        String a = convertToBase64("contraseña");
    }

    public void open_menu(View view) {
        String name = "";
        String pass = "";
        send_post(name, pass);
        //
    }

    public void send_post(String name, String pass){
        login.setText("Cargando");
        login.setBackgroundColor(getResources().getColor(R.color.white));
        login.setClickable(false);

        //Realizar el login mediante una petición POST
        RequestParams params = new RequestParams();
        params.put("email", "su@gmail.com");
        params.put("password", convertToBase64("contraseña"));

        HttpClient rali = new HttpClient(params, "/sessions");

        rali.post(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject my_response) {
                response = my_response;
                getAttributes(my_response);
                prefs.edit().putLong("connected", 1).apply();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
            }

            public void onFailure(int statusCode, Header[] headers, String message, Throwable e){
                Toast.makeText(getApplicationContext(), "No hay servidor", Toast.LENGTH_LONG).show();
            }

            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response){
                login.setText("ENTRAR");
                login.setBackgroundColor(getResources().getColor(R.color.white));
                login.setClickable(true);
                try {
                    System.out.println(response);
                    JSONArray errors = response.getJSONArray("errors");
                    Toast.makeText(getApplicationContext(), errors.getString(0) , Toast.LENGTH_LONG).show();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                } catch (NullPointerException e2){
                    Toast.makeText(getApplicationContext(), "Hubo un error, intenta de nuevo en unos segundos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getAttributes(JSONObject response) {
        JSONArray events_json;
        ArrayList<Event> events = new ArrayList<>();
        try {
            System.out.println(response.toString(4));

            events_json = response.getJSONArray("events");
            for(int i=0; i < events_json.length(); i++){
                JSONObject event = (JSONObject) events_json.get(i);
                String name = event.getString("name");

                String initialDate = formatDate(event.getString("initial_date"));
                String finalDate = formatDate(event.getString("final_date"));

                String url = event.getString("url");
                String logo = event.getString("logo");

                JSONArray activities_json = event.getJSONArray("activities");;
                ArrayList<Activity> activities = new ArrayList<>();
                for(int j=0; j < activities_json.length(); j++){
                    JSONObject activity  = (JSONObject) activities_json.get(j);
                    int id = activity.getInt("id");
                    String nameAct = activity.getString("name");
                    String description = activity.getString("description");

                    String initialTime = formatDate(activity.getString("initial_time"));
                    String finalTime = formatDate(activity.getString("final_time"));

                    int quota = activity.getInt("quota");
                    int currentQuota = activity.getInt("current_quota");
                    String place = activity.getString("place");
                    Activity act = new Activity(id, nameAct, description, currentQuota, quota, place, initialTime, finalTime, j );

                    activities.add(act);
                }
                Event eventJ = new Event(name, initialDate, finalDate, logo, url, activities, i);
                events.add(eventJ);
            }

            Intent intent = new Intent(getApplicationContext(), EventsList.class);
            intent.putExtra("Events", events);
            startActivity(intent);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    String formatDate(String my_date){
        Date date = null;
        try{
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(my_date);
        } catch (ParseException e){
            e.printStackTrace();
        }
        String newString = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
        System.out.println(newString);
        return newString;
    }

    public String convertToBase64(String pass){
        String base64 = pass;

        for(int i=0; i<7; i++) {
            byte[] data = new byte[0];
            try{
                data = base64.getBytes("UTF-8");
            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            base64 = Base64.encodeToString(data, Base64.DEFAULT);
        }

        System.out.println(base64);
        //Toast.makeText(this, base64, Toast.LENGTH_LONG ).show();

        return base64;
    }
}
