package gwrsg_dongsunmac.weather;

/**
 * Created by gwrsg-dongsunmac on 1/10/16.
 */
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * Created by gwrsg-dongsunmac on 1/10/16.
 */
public class DataListAdapter extends BaseAdapter {
    private List<WeatherForecast> dataList;
    private Context context;

    public void setData(Context context, List<WeatherForecast> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        WeatherForecast data = dataList.get(position);
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_item,null);
        }

        if(data!=null){
            TextView name = (TextView)v.findViewById(R.id.name);
            TextView location = (TextView)v.findViewById(R.id.location);

            name.setText(data.getName());
            location.setText(data.getLat()+" "+data.getLon());
        }
        return v;
    }
}

