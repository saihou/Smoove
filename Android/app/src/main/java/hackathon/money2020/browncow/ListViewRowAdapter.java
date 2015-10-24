package hackathon.money2020.browncow;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.txtDesc = (TextView)row.findViewById(R.id.txtDesc);

            row.setTag(holder);
        }
        else
        {
            holder = (ListViewRowHolder)row.getTag();
        }

        ListViewRow lvr = data[position];
        holder.txtTitle.setText(lvr.title);
        holder.imgIcon.setImageResource(lvr.icon);
        holder.txtDesc.setText(lvr.desc);

        return row;
    }

    static class ListViewRowHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtDesc;
    }
}
