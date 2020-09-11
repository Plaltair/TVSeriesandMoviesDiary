package com.plaltair.tvseriesandmoviesdiary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pierlucalippi on 31/08/17.
 */

public class CustomAdapterSpinner extends BaseAdapter {

    private Context context;
    private List<RowItemSpinner> rowItems;

    private FixSize fixSize = new FixSize();

    CustomAdapterSpinner(Context context, List<RowItemSpinner> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CustomAdapterSpinner.ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        holder = new ViewHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_spinner, null);

            holder.textView = (TextView)convertView.findViewById(R.id.spinnerText);
            holder.imageView = (ImageView)convertView.findViewById(R.id.spinnerImage);
            holder.imageView.getLayoutParams().height = fixSize.fixVertical(75);
            fixSize.fixTextSize(holder.textView, 25);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        
        RowItemSpinner row_pos = rowItems.get(position);

        if (row_pos.isText() == false) {

            switch (row_pos.getColor()) {
                case "red":
                    holder.imageView.setBackgroundColor(Color.parseColor("#FF0000"));
                    break;
                case "green":
                    holder.imageView.setBackgroundColor(Color.parseColor("#00CC00"));
                    break;
                case "blue":
                    holder.imageView.setBackgroundColor(Color.parseColor("#0000FF"));
                    break;
                case "yellow":
                    holder.imageView.setBackgroundColor(Color.parseColor("#F0F000"));
                    break;
                case "cyan":
                    holder.imageView.setBackgroundColor(Color.parseColor("#0080FF"));
                    break;
                case "orange":
                    holder.imageView.setBackgroundColor(Color.parseColor("#FF8000"));
                    break;
                case "pink":
                    holder.imageView.setBackgroundColor(Color.parseColor("#FF33FF"));
                    break;
                case "lightpink":
                    holder.imageView.setBackgroundColor(Color.parseColor("#FF69B4"));
                    break;
                case "blueviolet":
                    holder.imageView.setBackgroundColor(Color.parseColor("#7F00FF"));
                    break;
                case "fuchsia":
                    holder.imageView.setBackgroundColor(Color.parseColor("#FF007F"));
                    break;
                case "black":
                    holder.imageView.setBackgroundColor(Color.parseColor("#000000"));
                    break;
                case "gray":
                    holder.imageView.setBackgroundColor(Color.parseColor("#808080"));
                    break;
                default:
                    break;
            }
        }
        else {
            holder.textView.setText(row_pos.getText());
        }

        return convertView;
    }

}
