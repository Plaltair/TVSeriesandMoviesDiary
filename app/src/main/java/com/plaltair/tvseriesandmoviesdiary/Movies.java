package com.plaltair.tvseriesandmoviesdiary;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Movies extends AppCompatActivity {

    private ListView listView;

    private List<RowItemMovies> rowItems;

    private EditText editText;

    private View view;

    private View secondView;

    private View thirdView;

    private View settingsView;

    private EditText editTextSettings;

    private FloatingActionButton fab;

    private List<RowItemSpinner> rowItemsSpinner;

    private String titleItem;

    private String spinnerItemText;
    private int spinnerItemColor;

    private boolean moveSelected = false;
    private boolean changeTagSelected = false;
    private boolean addNoteSelected = false;
    private boolean deleteSelected = false;

    private String tableName;

    private final int RESULT_LOAD_IMAGE = 1234;

    private CustomAdapterMovies customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FixSize fixSize = new FixSize();

        final Intent intent = getIntent();

        tableName = "[movies_" + intent.getStringExtra("tableName") + "]";

        view = findViewById(R.id.moviesView);
        secondView = findViewById(R.id.secondView);
        TextView title = (TextView)findViewById(R.id.title);
        editText = (EditText)findViewById(R.id.editText);
        Button button = (Button)findViewById(R.id.button);
        Button button2 = (Button)findViewById(R.id.button2);
        fixSize.fixTextSize(title, 20);
        fixSize.fixTextSize(editText, 20);
        fixSize.fixTextSize(button, 23);
        fixSize.fixTextSize(button2, 23);
        editText.getLayoutParams().height = fixSize.fixVertical(100);
        button.getLayoutParams().width = fixSize.fixHorizontal(200);
        button.getLayoutParams().height = fixSize.fixVertical(75);
        button2.getLayoutParams().width = fixSize.fixHorizontal(200);
        button2.getLayoutParams().height = fixSize.fixVertical(75);
        secondView.getLayoutParams().width = fixSize.fixHorizontal(650);
        secondView.getLayoutParams().height = fixSize.fixVertical(300);

        thirdView = findViewById(R.id.thirdView);
        final Button move = (Button)findViewById(R.id.move);
        Button button4 = (Button)findViewById(R.id.button4);
        Button defaultImage = (Button)findViewById(R.id.defaultImage);
        Button deleteImage = (Button)findViewById(R.id.deleteImage);
        Button button5 = (Button)findViewById(R.id.button5);
        Button cancel = (Button)findViewById(R.id.cancel);
        final Button changeTag = (Button)findViewById(R.id.changeTag);
        Button deleteTag = (Button)findViewById(R.id.deleteTag);
        Button addNote = (Button)findViewById(R.id.addNote);
        button4.getLayoutParams().width = (FixSize.MAX_WIDTH - 50) / 3 - 25;
        defaultImage.getLayoutParams().width = (FixSize.MAX_WIDTH - 50) / 3 - 25;
        deleteImage.getLayoutParams().width = (FixSize.MAX_WIDTH - 50) / 3 - 25;
        changeTag.getLayoutParams().width = (FixSize.MAX_WIDTH - 50) / 2 - 25;
        deleteTag.getLayoutParams().width = (FixSize.MAX_WIDTH - 50) / 2 - 25;
        changeTag.getLayoutParams().height = fixSize.fixVertical(90);
        deleteTag.getLayoutParams().height = fixSize.fixVertical(90);
        button4.getLayoutParams().height = fixSize.fixVertical(90);
        defaultImage.getLayoutParams().height = fixSize.fixVertical(90);
        deleteImage.getLayoutParams().height = fixSize.fixVertical(90);
        move.getLayoutParams().height = fixSize.fixVertical(90);
        button5.getLayoutParams().height = fixSize.fixVertical(90);
        cancel.getLayoutParams().height = fixSize.fixVertical(90);
        changeTag.getLayoutParams().height = fixSize.fixVertical(90);
        addNote.getLayoutParams().height = fixSize.fixVertical(90);
        fixSize.fixTextSize(move, 25);
        fixSize.fixTextSize(button4, 25);
        fixSize.fixTextSize(defaultImage, 25);
        fixSize.fixTextSize(deleteImage, 25);
        fixSize.fixTextSize(button5, 25);
        fixSize.fixTextSize(cancel, 25);
        fixSize.fixTextSize(changeTag, 25);
        fixSize.fixTextSize(addNote, 25);
        fixSize.fixTextSize(deleteTag, 25);
        thirdView.getLayoutParams().width = FixSize.MAX_WIDTH - 50;
        thirdView.getLayoutParams().height = fixSize.fixVertical(600);

        settingsView = findViewById(R.id.settingsView);
        final TextView titleSettings = (TextView)findViewById(R.id.titleSettings);
        final Spinner spinner = (Spinner)findViewById(R.id.spinner);
        editTextSettings = (EditText)findViewById(R.id.editTextSettings);
        Button buttonConfirm = (Button)findViewById(R.id.buttonConfirm);
        Button buttonCancel = (Button)findViewById(R.id.buttonCancel);
        buttonConfirm.getLayoutParams().width = fixSize.fixHorizontal(200);
        buttonConfirm.getLayoutParams().height = fixSize.fixVertical(75);
        buttonCancel.getLayoutParams().width = fixSize.fixHorizontal(200);
        buttonCancel.getLayoutParams().height = fixSize.fixVertical(75);
        fixSize.fixTextSize(titleSettings, 20);
        fixSize.fixTextSize(editTextSettings, 20);
        fixSize.fixTextSize(buttonConfirm, 23);
        fixSize.fixTextSize(buttonCancel, 23);
        settingsView.getLayoutParams().width = fixSize.fixHorizontal(650);
        settingsView.getLayoutParams().height = fixSize.fixVertical(300);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText.getText().toString().contains("[")) {
                    editText.setText(editText.getText().toString().replace("[", ""));
                    editText.setSelection(editText.getText().toString().length());
                }
                else if (editText.getText().toString().contains("]")) {
                    editText.setText(editText.getText().toString().replace("]", ""));
                    editText.setSelection(editText.getText().toString().length());
                }
                else if (editText.getText().toString().length() == 1 && editText.getText().toString().equals(" ")) {
                    editText.setText("");
                }
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewFab) {
                secondView.setVisibility(View.VISIBLE);
                ColorDrawable[] color = {new ColorDrawable(Color.parseColor("#FAFAFA")), new ColorDrawable(Color.parseColor("#B2B2B2"))};
                TransitionDrawable trans = new TransitionDrawable(color);
                view.setBackground(trans);
                trans.startTransition(500);
                fab.hide();
            }
        });

        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thirdView.setVisibility(View.GONE);
                settingsView.setVisibility(View.VISIBLE);
                editTextSettings.setVisibility(View.GONE);
                titleSettings.setText(getResources().getString(R.string.choose));

                moveSelected = true;
                changeTagSelected = false;
                addNoteSelected = false;
                deleteSelected = false;

                SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READONLY);
                Cursor cursor = database.rawQuery("SELECT title FROM movies_intro", null);
                cursor.moveToFirst();

                String titles[] = new String[cursor.getCount()];

                for (int i = 0; i < cursor.getCount(); i++) {
                    titles[i] = cursor.getString(0);
                    cursor.moveToNext();
                }

                cursor.close();
                database.close();

                rowItemsSpinner = new ArrayList<RowItemSpinner>();

                for (int i = 0; i < titles.length; i++) {
                    RowItemSpinner item = new RowItemSpinner(titles[i], true);
                    rowItemsSpinner.add(item);
                }

                CustomAdapterSpinner customAdapter = new CustomAdapterSpinner(getApplicationContext(), rowItemsSpinner);
                spinner.setAdapter(customAdapter);
            }
        });

        changeTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thirdView.setVisibility(View.GONE);
                settingsView.setVisibility(View.VISIBLE);
                editTextSettings.setVisibility(View.GONE);
                titleSettings.setText(getResources().getString(R.string.choose));

                moveSelected = false;
                changeTagSelected = true;
                addNoteSelected = false;
                deleteSelected = false;

                String colors[] = {"red", "green", "blue", "yellow", "cyan", "orange", "pink", "lightpink", "blueviolet", "fuchsia", "black", "gray"};

                rowItemsSpinner = new ArrayList<RowItemSpinner>();

                for (int i = 0; i < colors.length; i++) {
                    RowItemSpinner item = new RowItemSpinner(colors[i], false);
                    rowItemsSpinner.add(item);
                }

                CustomAdapterSpinner customAdapter = new CustomAdapterSpinner(getApplicationContext(), rowItemsSpinner);
                spinner.setAdapter(customAdapter);
            }
        });

        deleteTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READWRITE);

                database.execSQL("UPDATE " + tableName + " SET tag = '' WHERE title = '" + titleItem + "'");
                database.close();
                finish();
                startActivity(getIntent());
            }
        });

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thirdView.setVisibility(View.GONE);
                settingsView.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
                titleSettings.setText(getResources().getString(R.string.addNote));

                moveSelected = false;
                changeTagSelected = false;
                addNoteSelected = true;
                deleteSelected = false;
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
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
                database.execSQL("UPDATE " + tableName + " SET image = 'N' WHERE title = '" + titleItem + "';");
                database.close();

                File file = new File(getFilesDir() + File.separator + "Images" + File.separator + "Movies" + File.separator + intent.getStringExtra("tableName") + " - " + titleItem);
                file.delete();

                finish();
                startActivity(getIntent());
            }
        });

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READWRITE);
                database.execSQL("UPDATE " + tableName + " SET image = 'X' WHERE title = '" + titleItem + "';");
                database.close();

                File file = new File(getFilesDir() + File.separator + "Images" + File.separator + "Movies" + File.separator + intent.getStringExtra("tableName") + " - " + titleItem);
                file.delete();

                finish();
                startActivity(getIntent());
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thirdView.setVisibility(View.GONE);
                settingsView.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
                editTextSettings.setVisibility(View.GONE);
                titleSettings.setText(getResources().getString(R.string.sure));

                moveSelected = false;
                changeTagSelected = false;
                addNoteSelected = false;
                deleteSelected = true;
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moveSelected == true) {
                    SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READWRITE);
                    Cursor cursor = database.rawQuery("SELECT * FROM " + tableName + " WHERE title = '" + titleItem + "'", null);
                    cursor.moveToFirst();

                    SharedPreferences sharedPreferences = getSharedPreferences("com.plaltair.tvseriesandmoviesdiary", Context.MODE_PRIVATE);
                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sharedPreferences.getString("dateFormat", "dd/MM/yyyy"));

                    String title = cursor.getString(1);
                    String note = cursor.getString(2);
                    String tag = cursor.getString(3);
                    String image = cursor.getString(5);
                    String dateString = simpleDateFormat.format(date);

                    Cursor cursorNewTable = database.rawQuery("SELECT subtitle FROM movies_intro WHERE title = '" + spinnerItemText + "'", null);
                    cursorNewTable.moveToFirst();
                    int n = cursorNewTable.getInt(0) + 1;
                    Cursor cursorOldTable = database.rawQuery("SELECT subtitle FROM movies_intro WHERE title = '" + intent.getStringExtra("tableName") + "'", null);
                    cursorOldTable.moveToFirst();
                    int n2 = cursorOldTable.getInt(0) - 1;

                    boolean found = false;

                    Cursor comparision = database.rawQuery("SELECT title FROM [movies_" + spinnerItemText + "]", null);
                    comparision.moveToFirst();

                    for (int i = 0; i < comparision.getCount(); i++) {
                        if (comparision.getString(0).equals(title)) {
                            found = true;
                        }
                        comparision.moveToNext();
                    }

                    if (found == false) {

                        database.delete(tableName, "title = ?", new String[]{titleItem});
                        database.execSQL("INSERT INTO " + "[movies_" + spinnerItemText + "] (title, note, tag, date, image) VALUES ('" + title + "', '" + note + "', '" + tag + "', '" + dateString + "', '" + image + "');");

                        database.execSQL("UPDATE movies_intro SET subtitle = " + n + " WHERE title = '" + spinnerItemText + "';");
                        database.execSQL("UPDATE movies_intro SET subtitle = " + n2 + " WHERE title = '" + intent.getStringExtra("tableName") + "';");

                        cursor.close();
                        database.close();

                        File olderFile = new File(getFilesDir() + File.separator + "Images" + File.separator + "Movies" + File.separator + intent.getStringExtra("tableName") + " - " + titleItem);
                        File newFile = new File(getFilesDir() + File.separator + "Images" + File.separator + "Movies" + File.separator + spinnerItemText + " - " + titleItem);
                        olderFile.renameTo(newFile);

                        Intent intent = new Intent(Movies.this, Main.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(Movies.this, getResources().getString(R.string.nameAlreadyInUse), Toast.LENGTH_SHORT).show();
                    }
                }
                else if (changeTagSelected == true) {
                    String keyColor = "";
                    SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READWRITE);
                    switch(spinnerItemColor) {
                        case 0:
                            keyColor = "red";
                            break;
                        case 1:
                            keyColor = "green";
                            break;
                        case 2:
                            keyColor = "blue";
                            break;
                        case 3:
                            keyColor = "yellow";
                            break;
                        case 4:
                            keyColor = "cyan";
                            break;
                        case 5:
                            keyColor = "orange";
                            break;
                        case 6:
                            keyColor = "pink";
                            break;
                        case 7:
                            keyColor = "lightpink";
                            break;
                        case 8:
                            keyColor = "blueviolet";
                            break;
                        case 9:
                            keyColor = "fuchsia";
                            break;
                        case 10:
                            keyColor = "black";
                            break;
                        case 11:
                            keyColor = "gray";
                            break;
                        default:
                            break;
                    }
                    database.execSQL("UPDATE " + tableName + " SET tag = " + "'" + keyColor + "' WHERE title = " + "'" + titleItem + "'");
                    database.close();
                    finish();
                    startActivity(getIntent());
                }
                else if (addNoteSelected == true) {
                    SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READWRITE);
                    database.execSQL("UPDATE " + tableName + " SET note = " + "'" + editTextSettings.getText().toString() + "' WHERE title = " + "'" + titleItem + "'");
                    editTextSettings.setText("");
                    database.close();
                    finish();
                    startActivity(getIntent());
                }

                else if (deleteSelected == true) {
                    SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READWRITE);
                    database.delete(tableName, "title = ?", new String[] {titleItem});

                    Cursor cursor = database.rawQuery("SELECT subtitle FROM movies_intro WHERE title = '" + intent.getStringExtra("tableName") + "'", null);
                    cursor.moveToFirst();
                    int newSubtitle = cursor.getInt(0) - 1;
                    database.execSQL("UPDATE movies_intro SET subtitle = " + newSubtitle + " WHERE title = '" + intent.getStringExtra("tableName") + "';");

                    cursor.close();
                    database.close();

                    File file = new File(getFilesDir() + File.separator + "Images" + File.separator + "Movies" + File.separator + intent.getStringExtra("tableName") + " - " + titleItem);
                    file.delete();

                    finish();
                    startActivity(getIntent());
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("com.plaltair.tvseriesandmoviesdiary", Context.MODE_PRIVATE);
                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sharedPreferences.getString("dateFormat", "dd/MM/yyyy"));

                String title = editText.getText().toString();
                String note = "";
                String tag = "";
                String dateString = simpleDateFormat.format(date);

                SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READONLY);
                Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE title = '" + title + "'", null);

                if (title.equals("")) {
                    Toast.makeText(Movies.this, getResources().getString(R.string.emptyText), Toast.LENGTH_SHORT).show();
                }

                else if (cursor.getCount() >= 1) {
                    Toast.makeText(Movies.this, getResources().getString(R.string.nameAlreadyInUse), Toast.LENGTH_SHORT).show();
                }

                else {
                    RowItemMovies item = new RowItemMovies(title, "", "", dateString, intent.getStringExtra("tableName"));
                    rowItems.add(item);
                    customAdapter = new CustomAdapterMovies(getApplicationContext(), rowItems);
                    listView.setAdapter(customAdapter);

                    SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READWRITE);
                    database.execSQL("INSERT INTO " + tableName + " (title, note, tag, date, image) VALUES ('" + title + "', '" + note + "', '" + tag + "', '" + dateString + "', 'N');");
                    Cursor cursor2 = database.rawQuery("SELECT subtitle FROM movies_intro WHERE title = '" + intent.getStringExtra("tableName") + "'", null);
                    cursor2.moveToFirst();
                    int newSubtitle = cursor2.getInt(0) + 1;
                    database.execSQL("UPDATE movies_intro SET subtitle = " + newSubtitle + " WHERE title = '" + intent.getStringExtra("tableName") + "';");

                    cursor2.close();
                    database.close();

                    onBackPressed();
                }

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName, null);
        int n = cursor.getCount();

        String listTitles[] = new String[n];
        String listNotes[] = new String[n];
        String listKeyColors[] = new String[n];
        String listDates[] = new String[n];

        cursor.moveToFirst();

        for (int i = 0; i < n; i++) {
            listTitles[i] = cursor.getString(1);
            listNotes[i] = cursor.getString(2);
            listKeyColors[i] = cursor.getString(3);
            listDates[i] = cursor.getString(4);
            cursor.moveToNext();
        }

        cursor.close();
        database.close();

        rowItems = new ArrayList<RowItemMovies>();

        for (int i = 0; i < listTitles.length; i++) {
            RowItemMovies item = new RowItemMovies(listTitles[i], listNotes[i], listKeyColors[i], listDates[i], intent.getStringExtra("tableName"));
            rowItems.add(item);
        }

        TextView emptyListView = (TextView)findViewById(R.id.emptyListView);
        fixSize.fixTextSize(emptyListView, 15);

        listView = (ListView)findViewById(R.id.listViewMovies);
        listView.setEmptyView(emptyListView);
        customAdapter = new CustomAdapterMovies(this, rowItems);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View viewItem, int i, long l) {
                ColorDrawable[] color = {new ColorDrawable(Color.parseColor("#FAFAFA")), new ColorDrawable(Color.parseColor("#B2B2B2"))};
                TransitionDrawable trans = new TransitionDrawable(color);
                view.setBackground(trans);
                trans.startTransition(500);
                thirdView.setVisibility(View.VISIBLE);
                fab.hide();
                listView.setEnabled(false);

                TextView textView = (TextView)viewItem.findViewById(R.id.title);

                titleItem = textView.getText().toString();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (moveSelected == true) {
                    TextView itemSelected = view.findViewById(R.id.spinnerText);

                    spinnerItemText = itemSelected.getText().toString();
                }
                else {
                    spinnerItemColor = i;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ViewCompat.setNestedScrollingEnabled(listView, true);
    }

    @Override
    public void onBackPressed() {
        if (secondView.getVisibility() == View.VISIBLE) {
            secondView.setVisibility(View.GONE);
            fab.show();
            ColorDrawable[] color = {new ColorDrawable(Color.parseColor("#B2B2B2")), new ColorDrawable(Color.parseColor("#FAFAFA"))};
            TransitionDrawable trans = new TransitionDrawable(color);
            view.setBackground(trans);
            trans.startTransition(500);
            editText.setText("");
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
        }
        else if (thirdView.getVisibility() == View.VISIBLE) {
            thirdView.setVisibility(View.GONE);
            fab.show();
            ColorDrawable[] color = {new ColorDrawable(Color.parseColor("#B2B2B2")), new ColorDrawable(Color.parseColor("#FAFAFA"))};
            TransitionDrawable trans = new TransitionDrawable(color);
            view.setBackground(trans);
            trans.startTransition(500);
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
            listView.setEnabled(true);
        }
        else if (settingsView.getVisibility() == View.VISIBLE) {
            settingsView.setVisibility(View.GONE);
            editTextSettings.setText("");
            fab.show();
            ColorDrawable[] color = {new ColorDrawable(Color.parseColor("#B2B2B2")), new ColorDrawable(Color.parseColor("#FAFAFA"))};
            TransitionDrawable trans = new TransitionDrawable(color);
            view.setBackground(trans);
            trans.startTransition(500);
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
            listView.setEnabled(true);
        }
        else {
            Intent intent = new Intent(Movies.this, Main.class);
            intent.putExtra("item", 1);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();

        int searchIconId = searchView.getContext().getResources().getIdentifier("android:id/search_button",null, null);
        ImageView searchIcon = (ImageView) searchView.findViewById(searchIconId);
        searchIcon.setImageResource(R.drawable.search);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.hide();
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                fab.show();
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            Uri uri = data.getData();

            File check = new File(getFilesDir() + File.separator + "Images" + File.separator + "Movies");

            if (!check.exists()) {
                createDirectory();
            }

            File file = new File(getRealPathFromURI(uri));
            File destFile = new File(getFilesDir() + File.separator + "Images" + File.separator + "Movies" + File.separator + getIntent().getStringExtra("tableName") + " - " + titleItem);

            try {
                copyFile(file, destFile);

                SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READWRITE);
                database.execSQL("UPDATE " + tableName + " SET image = 'S' WHERE title = '" + titleItem + "';");
                database.close();

                finish();
                startActivity(getIntent());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            finish();
            startActivity(getIntent());
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = Movies.this.getContentResolver().query(contentURI, null, null, null, null);
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
        int result = ContextCompat.checkSelfPermission(Movies.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(Movies.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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
        File directory = new File(getFilesDir() + File.separator + "Images" + File.separator + "Movies");
        directory.mkdirs();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
