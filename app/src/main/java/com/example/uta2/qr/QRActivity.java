package com.example.uta2.qr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Classes.Activity;
import Classes.HttpClient;
import cz.msebera.android.httpclient.Header;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QRActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    Activity activity;
    MediaPlayer mp;
    EditText identification;
    String my_result;
    TextView title;
    boolean qr = false;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        title = (TextView) findViewById(R.id.activity_title);

        Intent i = getIntent();
        activity = (Activity) i.getSerializableExtra("Activity");

        Toast.makeText(getApplicationContext(),activity.getId() + "", Toast.LENGTH_LONG).show();

        identification = (EditText) findViewById(R.id.identification);
        title.setText(activity.getName());

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        5);
            }
        }
    }

    public void onClick(View view) {
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        qr = true;
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(mScannerView != null){
            Intent intent;
            intent = new Intent(getApplicationContext(), QRActivity.class);
            intent.putExtra("Activity", activity);
            startActivity(intent);
        }
    }

    @Override
    public void handleResult(final Result result) {
        Log.w("handleResult", result.getText());
        my_result = result.getText();
        sendUser(my_result);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void addUser(View view) {
        sendUser(identification.getText()+"");
        identification.setText("");
    }

    public void sendUser(String qrToken){

        RequestParams params = new RequestParams();
        params.put("user_id", qrToken);

        HttpClient rali = new HttpClient(params, "/activities/" + activity.getId() + "/check_inscription");

        rali.get(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject my_response) {
                try {
                    if(my_response.getBoolean("enrolled"))
                        alert("Registrado!", "El usuario se encuentra registrado", R.style.AlertDialogTrue, R.raw.pi);
                    else
                        alert("No Registrado!" ,"El usuario no se encuentra registrado", R.style.AlertDialogFalse, R.raw.wrong);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
            }

            public void onFailure(int statusCode, Header[] headers, String message, Throwable e){
                Toast.makeText(getApplicationContext(), "No hay servidor", Toast.LENGTH_LONG).show();
            }

            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response){
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

    public void alert(String title, String result, int r, int sound){
        mp = MediaPlayer.create(this, sound);
        mp.start();
        AlertDialog.Builder builder = new AlertDialog.Builder(this, r);
        builder.setTitle(title);
        builder.setMessage(result);
        builder.setPositiveButton(
                "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(qr){
                            qr = false;
                            mScannerView.removeAllViews(); //<- here remove all the views, it will make an Activity having no View
                            mScannerView.stopCamera(); //<- then stop the camera
                            finish();
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
