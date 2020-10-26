package com.example.represent;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
    Activity context;
    String[] names;
    static class ViewHolder{
        public TextView text;
        public ImageView image;
    }
    public MyAdapter(Activity context, String[] names) {
        this.context = context;
        this.names = names;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.single_list_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) rowView.findViewById(R.id.rep_pic);
            viewHolder.text = (TextView) rowView.findViewById(R.id.rep_name);
            ViewHolder viewHolder2 = new ViewHolder();
            viewHolder2.text = (TextView) rowView.findViewById(R.id.more_info);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        String s = names[position];
        holder.text.setText(s);
        holder.image.setImageResource(R.mipmap.ic_launcher);
        return rowView;
    }
}
