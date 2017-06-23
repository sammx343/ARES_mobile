package com.example.uta2.qr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import Classes.Event;
import Classes.EventsAdapter;

public class EventsList extends AppCompatActivity {

    private ListView list;
    private EventsAdapter adapter;
    private Context mycontext;
    List<Event> entries;
    int pos = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
        mycontext = this;

        list = (ListView) findViewById(R.id.list);

        Intent i = getIntent();
        entries = (List<Event>) i.getSerializableExtra("Events");

        adapter = new EventsAdapter(mycontext, entries);
        list.setDivider(null);
        list.setAdapter(adapter);

        adapter.setData(entries);
        ((EventsAdapter) list.getAdapter()).notifyDataSetChanged();
    }

    public void openNote(View view){
        int index = (Integer) view.getTag();
        Intent intent;
        intent = new Intent(getApplicationContext(), ActivitiesList.class);
        System.out.println(index);
        Event v = entries.get(index);
        intent.putExtra("Event", v);
        startActivity(intent);
        Toast.makeText(this, entries.get(index).getName(), Toast.LENGTH_SHORT).show();
    }
}
