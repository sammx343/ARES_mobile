package Classes;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.uta2.qr.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by UTA2 on 11/05/17.
 */

public class EventsAdapter extends BaseAdapter {

    private Context context;
    private List<Event> data = Collections.emptyList();

    public EventsAdapter(Context context, List<Event> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(List<Event> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Event event = (Event) getItem(position);

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row, null);
        }

        TextView tvRow = (TextView) view.findViewById(R.id.tvRow);
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        Button btn = (Button) view.findViewById(R.id.btnRow);
        CardView cardRow = (CardView) view.findViewById(R.id.cardRow);

        tvRow.setText(event.getName());
        tvDate.setText(event.getDateStart());
        cardRow.setTag(event.getPosition());
        btn.setTag(event.getPosition());

        return view;
    }
}
