package com.example.uta2.qr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import Classes.ActivitiesAdapter;
import Classes.Activity;
import Classes.Event;

public class ActivitiesList extends AppCompatActivity {

    private ListView list;
    private ActivitiesAdapter adapter;
    private Context mycontext;
    List<Activity> entries;
    Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mycontext = this;

        list = (ListView) findViewById(R.id.list);
        Intent i = getIntent();
        event = (Event) i.getSerializableExtra("Event");
        entries = event.getActivities();

        adapter = new ActivitiesAdapter(mycontext, entries);
        list.setDivider(null);
        list.setAdapter(adapter);

        adapter.setData(entries);
        ((ActivitiesAdapter) list.getAdapter()).notifyDataSetChanged();
    }

    public void openNote(View view){
        int index = (Integer) view.getTag();
        Intent intent;
        intent = new Intent(getApplicationContext(), ActivityView.class);
        Activity v = entries.get(index);
        intent.putExtra("Activity", v);
        startActivity(intent);
        Toast.makeText(this, entries.get(index).getName(), Toast.LENGTH_SHORT).show();
    }
}
