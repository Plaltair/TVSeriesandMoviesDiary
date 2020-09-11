package com.plaltair.tvseriesandmoviesdiary;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import circleimageview.CircleImageView;

/**
 * Created by pierlucalippi on 30/08/17.
 */

public class CustomAdapterMovies extends BaseAdapter implements Filterable {

    private Context context;
    private List<RowItemMovies> rowItems;
    private List<RowItemMovies> rowItems2;
    
    private FixSize fixSize = new FixSize();

    CustomAdapterMovies(Context context, List<RowItemMovies> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
        rowItems2 = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems2.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems2.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems2.indexOf(getItem(position));
    }

    private class ViewHolder {
        CircleImageView imageView;
        ImageView img;
        TextView title;
        TextView note;
        ImageView tag;
        TextView date;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (constraint.length() == 0) {
                    rowItems2 = (ArrayList<RowItemMovies>) results.values;
                    notifyDataSetChanged();
                }
                else if (results.count == 0) {
                    rowItems2.clear();
                    notifyDataSetInvalidated();
                }
                else {
                    rowItems2 = (ArrayList<RowItemMovies>) results.values;
                    notifyDataSetChanged();
                }

            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String filterString = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();

                int count = rowItems.size();

                ArrayList<RowItemMovies> filteredList = new ArrayList<RowItemMovies>();

                for (int i = 0; i < count; i++) {
                    if (rowItems.get(i).getTitle().toLowerCase().contains(filterString)) {
                        filteredList.add(rowItems.get(i));
                    }
                }

                results.values = filteredList;
                results.count = filteredList.size();

                return results;
            }
        };

        return filter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CustomAdapterMovies.ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        holder = new ViewHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_movies, null);

            holder.imageView = (CircleImageView)convertView.findViewById(R.id.imageView);
            holder.img = (ImageView)convertView.findViewById(R.id.img);
            holder.title = (TextView)convertView.findViewById(R.id.title);
            holder.note = (TextView)convertView.findViewById(R.id.note);
            holder.tag = (ImageView) convertView.findViewById(R.id.tag);
            holder.date = (TextView)convertView.findViewById(R.id.date);

            holder.imageView.getLayoutParams().width = fixSize.fixHorizontal(175);
            holder.imageView.getLayoutParams().height = fixSize.fixHorizontal(175);
            holder.img.getLayoutParams().width = fixSize.fixHorizontal(175);
            holder.img.getLayoutParams().height = fixSize.fixHorizontal(175);
            fixSize.fixTextSize(holder.title, 20);
            fixSize.fixTextSize(holder.note, 20);
            fixSize.fixTextSize(holder.date, 20);
            holder.title.getLayoutParams().width = fixSize.fixHorizontal(425);
            holder.tag.getLayoutParams().width = fixSize.fixHorizontal(200);
            holder.tag.getLayoutParams().height = fixSize.fixVertical(15);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        RowItemMovies row_pos = rowItems2.get(position);

        String image = "";

        SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = database.rawQuery("SELECT image FROM [movies_" + row_pos.getTableName() + "] WHERE title = '" + row_pos.getTitle() + "'", null);
        cursor.moveToFirst();
        image = cursor.getString(0);
        cursor.close();
        database.close();

        File file = new File(context.getFilesDir() + File.separator + "Images" + File.separator + "Movies" + File.separator + row_pos.getTableName() + " - " + row_pos.getTitle());

        if (image.equals("X")) {
            holder.imageView.setVisibility(View.INVISIBLE);
            holder.img.setVisibility(View.INVISIBLE);
        }

        else if (image.equals("N") || !file.exists() || !checkPermission()) {
            holder.imageView.setVisibility(View.INVISIBLE);
            holder.img.setImageResource(R.drawable.diaryimage);
            holder.img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }

        else {
            holder.img.setVisibility(View.INVISIBLE);
            Bitmap bitmap = decodeAndResizeFile(file);
            holder.imageView.setImageBitmap(bitmap);
        }
        holder.title.setText(row_pos.getTitle());
        holder.note.setText(row_pos.getNote());
        holder.date.setText(row_pos.getDate());

        switch (row_pos.getKeyColor()) {
            case "red":
                holder.tag.setBackgroundColor(Color.parseColor("#FF0000"));
                break;
            case "green":
                holder.tag.setBackgroundColor(Color.parseColor("#00CC00"));
                break;
            case "blue":
                holder.tag.setBackgroundColor(Color.parseColor("#0000FF"));
                break;
            case "yellow":
                holder.tag.setBackgroundColor(Color.parseColor("#F0F000"));
                break;
            case "cyan":
                holder.tag.setBackgroundColor(Color.parseColor("#0080FF"));
                break;
            case "orange":
                holder.tag.setBackgroundColor(Color.parseColor("#FF8000"));
                break;
            case "pink":
                holder.tag.setBackgroundColor(Color.parseColor("#FF33FF"));
                break;
            case "lightpink":
                holder.tag.setBackgroundColor(Color.parseColor("#FF69B4"));
                break;
            case "blueviolet":
                holder.tag.setBackgroundColor(Color.parseColor("#7F00FF"));
                break;
            case "fuchsia":
                holder.tag.setBackgroundColor(Color.parseColor("#FF007F"));
                break;
            case "black":
                holder.tag.setBackgroundColor(Color.parseColor("#000000"));
                break;
            case "gray":
                holder.tag.setBackgroundColor(Color.parseColor("#808080"));
                break;
            default:
                break;
        }

        return convertView;
    }

    private static Bitmap decodeAndResizeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            return false;
        }
    }

}
