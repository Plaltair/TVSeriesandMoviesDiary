package com.plaltair.tvseriesandmoviesdiary;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierlucalippi on 28/08/17.
 */

public class Tab1 extends Fragment {

    private static ListView listView;

    private static List<RowItem> rowItems;

    private boolean click = true;
    private boolean longClick = false;

    private static View view;
    private static View secondView;

    private static String titleItem;

    private final int RESULT_LOAD_IMAGE = 1234;

    public static View getViewTab1() {
        return view;
    }
    public static View getSecondViewTab1() {
        return secondView;
    }

    public static ListView getListView() {
        return listView;
    }

    public static List<RowItem> getRowItems() {
        return rowItems;
    }

    public static String getTitleItem() {
        return titleItem;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);
        FixSize fixSize = new FixSize();

        view = rootView.findViewById(R.id.tab1View);

        secondView = rootView.findViewById(R.id.secondView);

        Button button = (Button)rootView.findViewById(R.id.button);
        Button button2 = (Button)rootView.findViewById(R.id.button2);
        Button cancel = (Button)rootView.findViewById(R.id.cancel);
        Button defaultImage = (Button)rootView.findViewById(R.id.defaultImage);
        Button deleteImage = (Button)rootView.findViewById(R.id.deleteImage);
        button.getLayoutParams().width = (FixSize.MAX_WIDTH - 50) / 3 - 25;
        defaultImage.getLayoutParams().width = (FixSize.MAX_WIDTH - 50) / 3 - 25;
        deleteImage.getLayoutParams().width = (FixSize.MAX_WIDTH - 50) / 3 - 25;
        button.getLayoutParams().height = fixSize.fixVertical(100);
        defaultImage.getLayoutParams().height = fixSize.fixVertical(100);
        deleteImage.getLayoutParams().height = fixSize.fixVertical(100);
        cancel.getLayoutParams().height = fixSize.fixVertical(100);
        button2.getLayoutParams().height = fixSize.fixVertical(100);
        fixSize.fixTextSize(button, 25);
        fixSize.fixTextSize(button2, 25);
        fixSize.fixTextSize(cancel, 25);
        fixSize.fixTextSize(defaultImage, 25);
        fixSize.fixTextSize(deleteImage, 25);
        fixSize.fixMargins(button2, 0, 20, 0, 0);
        fixSize.fixMargins(cancel, 0, 30, 0, 0);
        secondView.getLayoutParams().width = FixSize.MAX_WIDTH - 50;
        secondView.getLayoutParams().height = fixSize.fixVertical(500);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable[] color = {new ColorDrawable(Color.parseColor("#B2B2B2")), new ColorDrawable(Color.parseColor("#FAFAFA"))};
                TransitionDrawable trans = new TransitionDrawable(color);
                view.setBackground(trans);
                trans.startTransition(500);
                listView.setEnabled(true);
                secondView.setVisibility(View.GONE);
                Main.getFloatingActionButton().show();
                longClick = false;
                click = true;
                listView.setEnabled(true);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!checkPermission()) {
                        requestPermission();
                    }
                    else {
                        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                    }
                }
            }
        });

        defaultImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READWRITE);
                database.execSQL("UPDATE tv_series_intro SET image = 'N' WHERE title = '" + titleItem + "';");
                database.close();

                File file = new File(getActivity().getFilesDir() + File.separator + "Images" + File.separator + "Intro" + File.separator + "TV Series - " + titleItem);
                file.delete();

                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        });

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READWRITE);
                database.execSQL("UPDATE tv_series_intro SET image = 'X' WHERE title = '" + titleItem + "';");
                database.close();

                File file = new File(getActivity().getFilesDir() + File.separator + "Images" + File.separator + "Intro" + File.separator + "TV Series - " + titleItem);
                file.delete();

                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondView.setVisibility(View.GONE);
                Main.getSettingsView().setVisibility(View.VISIBLE);
            }
        });

        SQLiteDatabase database = SQLiteDatabase.openDatabase("data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = database.rawQuery("SELECT * FROM tv_series_intro", null);
        int n = cursor.getCount();

        final String listTitles[] = new String[n];
        final String listSubtitles[] = new String[n];

        cursor.moveToFirst();

        for (int i = 0; i < n; i++) {
            String string;
            listTitles[i] = cursor.getString(1);
            if (cursor.getInt(2) == 1) {
                string = getResources().getString(R.string.tab1NameSingular);
            }
            else {
                string = getResources().getString(R.string.tab1Name);
            }
            listSubtitles[i] = cursor.getInt(2) + " " + string;
            cursor.moveToNext();
        }

        cursor.close();
        database.close();

        rowItems = new ArrayList<RowItem>();

        for (int i = 0; i < listTitles.length; i++) {
            RowItem item = new RowItem(listTitles[i], listSubtitles[i], "tv_series_intro");
            rowItems.add(item);
        }

        listView = (ListView)rootView.findViewById(R.id.listViewTab1);
        final CustomAdapter customAdapter = new CustomAdapter(getContext(), rowItems);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (click) {

                    TextView textViewTitle = (TextView)view.findViewById(R.id.title);

                    Intent intent = new Intent(getContext(), TVSeries.class);
                    intent.putExtra("tableName", textViewTitle.getText().toString());
                    startActivity(intent);

                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View viewItem, int i, long l) {
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

                click = false;
                longClick = true;
                listView.setEnabled(false);

                TextView textView = (TextView)viewItem.findViewById(R.id.title);
                titleItem = textView.getText().toString();

                ColorDrawable[] color = {new ColorDrawable(Color.parseColor("#FAFAFA")), new ColorDrawable(Color.parseColor("#B2B2B2"))};
                TransitionDrawable trans = new TransitionDrawable(color);
                view.setBackground(trans);
                trans.startTransition(500);

                secondView.setVisibility(View.VISIBLE);

                Main.getFloatingActionButton().hide();

                return false;
            }
        });

        ViewCompat.setNestedScrollingEnabled(listView, true);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == getActivity().RESULT_OK) {

            Uri uri = data.getData();

            File check = new File(getActivity().getFilesDir() + File.separator + "Images" + File.separator + "Intro");

            if (!check.exists()) {
                createDirectory();
            }

            File file = new File(getRealPathFromURI(uri));
            File destFile = new File(getActivity().getFilesDir() + File.separator + "Images" + File.separator + "Intro" + File.separator + "TV Series - " + titleItem);

            try {
                copyFile(file, destFile);

                SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READWRITE);
                database.execSQL("UPDATE tv_series_intro SET image = 'S' WHERE title = '" + titleItem + "';");
                database.close();

                getActivity().finish();
                startActivity(getActivity().getIntent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            getActivity().finish();
            startActivity(getActivity().getIntent());
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }
    private void createDirectory() {
        File directory = new File(getActivity().getFilesDir() + File.separator + "Images" + File.separator + "Intro");
        directory.mkdirs();

    }
}
