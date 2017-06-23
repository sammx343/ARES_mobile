package com.example.uta2.qr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Classes.Activity;

public class ActivityView extends AppCompatActivity {
    Activity activity;
    TextView title, detail, place, start_time, end_time, currentQuota, maxQuota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Intent i = getIntent();
        activity = (Activity) i.getSerializableExtra("Activity");

        title = (TextView) findViewById(R.id.activity_title);
        detail = (TextView) findViewById(R.id.activity_description);
        place = (TextView) findViewById(R.id.activity_place);
        start_time = (TextView) findViewById(R.id.activity_hour_begin);
        end_time = (TextView) findViewById(R.id.activity_hour_end);
        currentQuota = (TextView) findViewById(R.id.invited_number);
        maxQuota = (TextView) findViewById(R.id.invited_max);

        //Toast.makeText(this, activity.getName(), Toast.LENGTH_LONG).show();

        title.setText(activity.getName());
        detail.setText(activity.getDescription());
        place.setText("Lugar: " + activity.getPlace());
        start_time.setText("Hora de Entrada: " + formatDateToHour(activity.getHourDateStart()));
        end_time.setText("Hora de Salida: " + formatDateToHour(activity.getHourDateEnd()));
        currentQuota.setText(activity.getInvitedNumber() + "");
        maxQuota.setText(activity.getInvitedMaxNumber() + "");
    }

    public void openNote(View view) {
        Intent intent;
        intent = new Intent(getApplicationContext(), QRActivity.class);
        intent.putExtra("Activity", activity);
        startActivity(intent);
    }

    String formatDateToHour(String my_date){
        Date date = null;
        try{
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(my_date);
        } catch (ParseException e){
            e.printStackTrace();
        }
        String newString = new SimpleDateFormat("HH:mm").format(date);
        System.out.println(newString);
        return newString;
    }
}
