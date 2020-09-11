package com.plaltair.tvseriesandmoviesdiary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Main extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static View secondView;

    private static View settingsView;

    private static FloatingActionButton fab;

    private EditText editText;;

    public static View getSecondView() {
        return secondView;
    }

    public static View getSettingsView() {
        return settingsView;
    }

    public static FloatingActionButton getFloatingActionButton() {
        return fab;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FixSize fixSize = new FixSize();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (secondView.getVisibility() == View.VISIBLE) {
                    onBackPressed();
                }
                else if (Tab1.getSecondViewTab1().getVisibility() == View.VISIBLE) {
                    onBackPressed();
                }
                else if (Tab2.getSecondViewTab2().getVisibility() == View.VISIBLE) {
                    onBackPressed();
                }
                return false;
            }
        });

        mViewPager.setCurrentItem(getIntent().getIntExtra("item", 0));

        final ActionBar actionBar = getSupportActionBar();

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

        settingsView = findViewById(R.id.settingsView);
        final TextView titleSettings = (TextView)findViewById(R.id.titleSettings);
        Button buttonConfirm = (Button)findViewById(R.id.buttonConfirm);
        Button buttonCancel = (Button)findViewById(R.id.buttonCancel);
        buttonConfirm.getLayoutParams().width = fixSize.fixHorizontal(200);
        buttonConfirm.getLayoutParams().height = fixSize.fixVertical(75);
        buttonCancel.getLayoutParams().width = fixSize.fixHorizontal(200);
        buttonCancel.getLayoutParams().height = fixSize.fixVertical(75);
        fixSize.fixTextSize(titleSettings, 25);
        fixSize.fixTextSize(buttonConfirm, 20);
        fixSize.fixTextSize(buttonCancel, 20);
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


        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mViewPager.getCurrentItem() == 0) {
                    SQLiteDatabase database = SQLiteDatabase.openDatabase("data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READWRITE);
                    database.delete("tv_series_intro", "title = ?", new String[]{Tab1.getTitleItem()});
                    database.execSQL("DROP TABLE [tv_series_" + Tab1.getTitleItem() + "]");
                    database.close();

                    File file = new File(getFilesDir() + File.separator + "Images" + File.separator + "Intro" + File.separator + "TV Series - " + Tab1.getTitleItem());
                    file.delete();

                    finish();
                    startActivity(getIntent());
                }
                else {
                    SQLiteDatabase database = SQLiteDatabase.openDatabase("data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READWRITE);
                    database.delete("movies_intro", "title = ?", new String[]{Tab2.getTitleItem()});
                    database.execSQL("DROP TABLE [movies_" + Tab2.getTitleItem() + "]");
                    database.close();

                    File file = new File(getFilesDir() + File.separator + "Images" + File.separator + "Intro" + File.separator + "Movies - " + Tab2.getTitleItem());
                    file.delete();

                    finish();
                    startActivity(getIntent());
                }
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == true) {
                    actionBar.hide();
                }
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tab1.getListView().setEnabled(false);
                Tab2.getListView().setEnabled(false);
                if (mViewPager.getCurrentItem() == 0) {
                    secondView.setVisibility(View.VISIBLE);
                    ColorDrawable[] color = {new ColorDrawable(Color.parseColor("#FAFAFA")), new ColorDrawable(Color.parseColor("#B2B2B2"))};
                    TransitionDrawable trans = new TransitionDrawable(color);
                    Tab1.getViewTab1().setBackground(trans);
                    trans.startTransition(500);
                    fab.hide();
                }
                else {
                    secondView.setVisibility(View.VISIBLE);
                    ColorDrawable[] color = {new ColorDrawable(Color.parseColor("#FAFAFA")), new ColorDrawable(Color.parseColor("#B2B2B2"))};
                    TransitionDrawable trans = new TransitionDrawable(color);
                    Tab2.getViewTab2().setBackground(trans);
                    trans.startTransition(500);
                    fab.hide();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tab1.getListView().setEnabled(true);
                if (mViewPager.getCurrentItem() == 0) {

                    String title = editText.getText().toString();
                    String subtitle = "0 " + getResources().getString(R.string.tab1Name);

                    SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READONLY);
                    Cursor cursor = db.rawQuery("SELECT * FROM tv_series_intro WHERE title = '" + title + "'", null);

                    if (title.equals("")) {
                        Toast.makeText(Main.this, getResources().getString(R.string.emptyText), Toast.LENGTH_SHORT).show();
                    }

                    else if (cursor.getCount() >= 1) {
                        Toast.makeText(Main.this, getResources().getString(R.string.nameAlreadyInUse), Toast.LENGTH_SHORT).show();
                    }

                    else {
                        RowItem item = new RowItem(title, subtitle, "tv_series_intro");
                        Tab1.getRowItems().add(item);
                        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), Tab1.getRowItems());
                        Tab1.getListView().setAdapter(customAdapter);

                        SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READWRITE);
                        database.execSQL("CREATE TABLE [tv_series_" + title + "] (id INTEGER PRIMARY KEY, title VARCHAR, season VARCHAR, episode VARCHAR, note VARCHAR, tag VARCHAR, date char(10), image CHAR(1));");
                        database.execSQL("INSERT INTO tv_series_intro (title, subtitle, image) VALUES (" + "'" + title + "'" + ", " + "'" + subtitle + "', 'N');");
                        database.close();

                        onBackPressed();
                    }

                    cursor.close();
                    db.close();
                }
                else {

                    String title = editText.getText().toString();
                    String subtitle = "0 " + getResources().getString(R.string.tab2Name);

                    SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READONLY);
                    Cursor cursor = db.rawQuery("SELECT * FROM movies_intro WHERE title = '" + title + "'", null);

                    if (title.equals("")) {
                        Toast.makeText(Main.this, getResources().getString(R.string.emptyText), Toast.LENGTH_SHORT).show();
                    }

                    else if (cursor.getCount() >= 1) {
                        Toast.makeText(Main.this, getResources().getString(R.string.nameAlreadyInUse), Toast.LENGTH_SHORT).show();
                    }

                    else {
                        RowItem item = new RowItem(title, subtitle, "movies_intro");
                        Tab2.getRowItems().add(item);
                        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), Tab2.getRowItems());
                        Tab2.getListView().setAdapter(customAdapter);

                        SQLiteDatabase database = SQLiteDatabase.openDatabase("/data/data/com.plaltair.tvseriesandmoviesdiary/databases/database", null, SQLiteDatabase.OPEN_READWRITE);
                        database.execSQL("CREATE TABLE [movies_" + title + "] (id INTEGER PRIMARY KEY, title VARCHAR, note VARCHAR, tag VARCHAR, date CHAR(10), image CHAR(1));");
                        database.execSQL("INSERT INTO movies_intro (title, subtitle, image) VALUES (" + "'" + title + "'" + ", " + "'" + subtitle + "', 'N');");
                        database.close();

                        onBackPressed();
                    }

                    cursor.close();
                    db.close();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //deleteDatabase("database");

        File file = getDatabasePath("database");

        if (file.exists()) {
            System.out.println("Existing database");
        }

        else {
            SQLiteDatabase database = openOrCreateDatabase("database", MODE_PRIVATE, null);
            System.out.println("Database created");

            String title1 = getResources().getString(R.string.listItem1Tab1);
            String title2 = getResources().getString(R.string.listItem2Tab1);
            String title3 = getResources().getString(R.string.listItem3Tab1);
            String title4 = getResources().getString(R.string.listItem1Tab2);
            String title5 = getResources().getString(R.string.listItem2Tab2);

            String queries[] = new String[12];

            queries[0] = "CREATE TABLE tv_series_intro (id INTEGER PRIMARY KEY, title VARCHAR, subtitle INTEGER, image CHAR(1));";
            queries[1] = "CREATE TABLE movies_intro (id INTEGER PRIMARY KEY, title VARCHAR, subtitle INTEGER, image CHAR(1));";
            queries[2] = "CREATE TABLE [tv_series_" + title1 + "] (id INTEGER PRIMARY KEY, title VARCHAR, season VARCHAR, episode VARCHAR, note VARCHAR, tag VARCHAR, date char(10), image CHAR(1));";
            queries[3] = "CREATE TABLE [tv_series_" + title2 + "] (id INTEGER PRIMARY KEY, title VARCHAR, season VARCHAR, episode VARCHAR, note VARCHAR, tag VARCHAR, date char(10), image CHAR(1));";
            queries[4] = "CREATE TABLE [tv_series_" + title3 + "] (id INTEGER PRIMARY KEY, title VARCHAR, season VARCHAR, episode VARCHAR, note VARCHAR, tag VARCHAR, date char(10), image CHAR(1));";
            queries[5] = "CREATE TABLE [movies_" + title4 + "] (id INTEGER PRIMARY KEY, title VARCHAR, note VARCHAR, tag VARCHAR, date CHAR(10), image CHAR(1));";
            queries[6] = "CREATE TABLE [movies_" + title5 + "] (id INTEGER PRIMARY KEY, title VARCHAR, note VARCHAR, tag VARCHAR, date CHAR(10), image CHAR(1));";
            queries[7] = "INSERT INTO tv_series_intro (title, subtitle, image) VALUES (" + "'" + title1 + "', 0, 'N');";
            queries[8] = "INSERT INTO tv_series_intro (title, subtitle, image) VALUES (" + "'" + title2 + "', 0, 'N');";
            queries[9] = "INSERT INTO tv_series_intro (title, subtitle, image) VALUES (" + "'" + title3 + "', 0, 'N');";
            queries[10] = "INSERT INTO movies_intro (title, subtitle, image) VALUES (" + "'" + title4 + "', 0, 'N');";
            queries[11] = "INSERT INTO movies_intro (title, subtitle, image) VALUES (" + "'" + title5 + "', 0, 'N');";

            for (int i = 0; i < queries.length; i++) {
                database.execSQL(queries[i]);
            }

            database.close();

            System.out.println("Queries executed");
        }

        SharedPreferences sharedPreferences = getSharedPreferences("com.plaltair.tvseriesandmoviesdiary", Context.MODE_PRIVATE);
        String string = sharedPreferences.getString("dateFormat", "null");
        if (string.equals("null")) {
            startActivity(new Intent(Main.this, Intro.class));
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Tab1 tab1 = new Tab1();
                    return tab1;
                case 1:
                    Tab2 tab2 = new Tab2();
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.tab1Name);
                case 1:
                    return getResources().getString(R.string.tab2Name);
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        if (secondView.getVisibility() == View.VISIBLE) {
            secondView.setVisibility(View.GONE);
            ColorDrawable[] color = {new ColorDrawable(Color.parseColor("#B2B2B2")), new ColorDrawable(Color.parseColor("#FAFAFA"))};
            TransitionDrawable trans = new TransitionDrawable(color);
            Tab1.getViewTab1().setBackground(trans);
            trans.startTransition(500);
            TransitionDrawable trans2 = new TransitionDrawable(color);
            Tab2.getViewTab2().setBackground(trans2);
            trans2.startTransition(500);
            fab.show();
            Tab1.getListView().setEnabled(true);
            Tab2.getListView().setEnabled(true);
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
            ActionBar actionBar = getSupportActionBar();
            actionBar.show();
            editText.setText("");
        }
        else if (Tab1.getSecondViewTab1().getVisibility() == View.VISIBLE) {
            Tab1.getSecondViewTab1().setVisibility(View.GONE);
            ColorDrawable[] color = {new ColorDrawable(Color.parseColor("#B2B2B2")), new ColorDrawable(Color.parseColor("#FAFAFA"))};
            TransitionDrawable trans = new TransitionDrawable(color);
            Tab1.getViewTab1().setBackground(trans);
            trans.startTransition(500);
            Tab1.getListView().setEnabled(true);
            fab.show();
        }
        else if (Tab2.getSecondViewTab2().getVisibility() == View.VISIBLE) {
            Tab2.getSecondViewTab2().setVisibility(View.GONE);
            ColorDrawable[] color = {new ColorDrawable(Color.parseColor("#B2B2B2")), new ColorDrawable(Color.parseColor("#FAFAFA"))};
            TransitionDrawable trans = new TransitionDrawable(color);
            Tab2.getViewTab2().setBackground(trans);
            trans.startTransition(500);
            Tab2.getListView().setEnabled(true);
            fab.show();
        }
        else if (settingsView.getVisibility() == View.VISIBLE) {
            settingsView.setVisibility(View.GONE);
        }
        else {
            super.onBackPressed();
        }
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
