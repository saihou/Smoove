package hackathon.money2020.smoove;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by sai on 10/24/15.
 */
public class ListViewRowAdapter extends ArrayAdapter<ListViewRow> {
    Context context;
    int layoutResourceId;
    ListViewRow[] data = null;

    public ListViewRowAdapter(Context context, int layoutResourceId, ListViewRow[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ListViewRowHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ListViewRowHolder();
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.txtDesc = (TextView)row.findViewById(R.id.txtDesc);
            holder.txtId = (TextView)row.findViewById(R.id.merchantId);
            View bg = row.findViewById(R.id.row);
            if (bg != null) {
                setRandomBackground(bg);
            }

            row.setTag(holder);
        }
        else
        {
            holder = (ListViewRowHolder)row.getTag();
        }

        ListViewRow lvr = data[position];
        holder.txtTitle.setText(lvr.title);
        holder.txtDesc.setText(lvr.desc);
        holder.txtId.setText(lvr.id);

        return row;
    }

    public void setRandomBackground(View bg) {
        int rdm = (int) Math.round(Math.random() * 4.0f);
        switch (rdm) {
            case 0:
                bg.setBackgroundResource(R.drawable.laksa);
                break;
            case 1:
                bg.setBackgroundResource(R.drawable.chicken_rice);
                break;
            case 2:
                bg.setBackgroundResource(R.drawable.hokkien_mee);
                break;
            case 3:
                bg.setBackgroundResource(R.drawable.char_kway_teow);
                break;
            case 4:
                bg.setBackgroundResource(R.drawable.wanton_mee);
                break;
        }
    }

    static class ListViewRowHolder
    {
        TextView txtTitle;
        TextView txtDesc;
        TextView txtId;
    }
}
