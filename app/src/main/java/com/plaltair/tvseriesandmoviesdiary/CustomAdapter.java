package com.plaltair.tvseriesandmoviesdiary;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import circleimageview.CircleImageView;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private List<RowItem> rowItems;
    
    private FixSize fixSize = new FixSize();

    CustomAdapter(Context context, List<RowItem> rowItems) {
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
        CircleImageView imageView;
        ImageView img;
        TextView title;
        TextView subtitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        holder = new ViewHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);

            holder.imageView = (CircleImageView)convertView.findViewById(R.id.imageView);
            holder.img = (ImageView)convertView.findViewById(R.id.img);
            holder.title = (TextView)convertView.findViewById(R.id.title);
            holder.subtitle = (TextView)convertView.findViewById(R.id.subtitle);

            holder.imageView.getLayoutParams().width = fixSize.fixHorizontal(175);
            holder.imageView.getLayoutParams().height = fixSize.fixHorizontal(175);
            holder.img.getLayoutParams().width = fixSize.fixHorizontal(175);
            holder.img.getLayoutParams().height = fixSize.fixHorizontal(175);
            holder.title.getLayoutParams().width = fixSize.fixHorizontal(425);
            fixSize.fixTextSize(holder.title, 20);
            fixSize.fixTextSize(holder.subtitle, 20);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        RowItem row_pos = rowItems.get(position);

        String string;
        String image = "";

        if (row_pos.getDatabaseTableName().equals("tv_series_intro")) {
            string = "TV Series - ";
        }
        else {
            string = "Movies - ";
        }

        SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor;
        if (string.equals("TV Series - ")) {
            cursor = database.rawQuery("SELECT image FROM tv_series_intro WHERE title = '" + row_pos.getTitle() + "'", null);
            cursor.moveToFirst();
            image = cursor.getString(0);
            cursor.close();
            database.close();
        }
        else {
            cursor = database.rawQuery("SELECT image from movies_intro WHERE title = '" + row_pos.getTitle() + "'", null);
            cursor.moveToFirst();
            image = cursor.getString(0);
            cursor.close();
            database.close();
        }

        File file = new File(context.getFilesDir() + File.separator + "Images" + File.separator + "Intro" + File.separator + string + row_pos.getTitle());

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
        holder.subtitle.setText(row_pos.getSubtitle());

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
