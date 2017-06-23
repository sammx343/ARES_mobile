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
 * Created by UTA2 on 9/05/17.
 */

public class ActivitiesAdapter extends BaseAdapter {

    private Context context;
    private List<Activity> data = Collections.emptyList();

    public ActivitiesAdapter(Context context, List<Activity> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(List<Activity> data) {
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
        Activity activity = (Activity) getItem(position);

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.row, null);
        }

        TextView tvRow = (TextView) view.findViewById(R.id.tvRow);
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        Button btn = (Button) view.findViewById(R.id.btnRow);
        CardView cardRow = (CardView) view.findViewById(R.id.cardRow);

        tvRow.setText(activity.getName());
        tvDate.setText(activity.getHourDateStart());
        cardRow.setTag(activity.getPosition());
        btn.setTag(activity.getPosition());

        return view;
    }
}
