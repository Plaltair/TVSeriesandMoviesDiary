package com.plaltair.tvseriesandmoviesdiary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Intro extends AppCompatActivity {

    private String stringFinal;

    private View view;
    private View secondView;

    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        FixSize fixSize = new FixSize();

        view = findViewById(R.id.firstView);
        secondView = findViewById(R.id.secondView);

        TextView title = (TextView)findViewById(R.id.title);
        radioButton1 = (RadioButton)findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton)findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton)findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton)findViewById(R.id.radioButton4);
        TextView textView1 = (TextView)findViewById(R.id.textView1);
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        TextView textView3 = (TextView)findViewById(R.id.textView3);
        TextView textView4 = (TextView)findViewById(R.id.textView4);

        TextView title2 = (TextView)findViewById(R.id.title2);
        TextView description = (TextView)findViewById(R.id.description);
        Button button = (Button)findViewById(R.id.button);
        Button button2 = (Button)findViewById(R.id.button2);

        fixSize.fixTextSize(title, 15);
        fixSize.fixTextSize(radioButton1, 20);
        fixSize.fixTextSize(radioButton2, 20);
        fixSize.fixTextSize(radioButton3, 20);
        fixSize.fixTextSize(radioButton4, 20);
        fixSize.fixTextSize(textView1, 15);
        fixSize.fixTextSize(textView2, 15);
        fixSize.fixTextSize(textView3, 15);
        fixSize.fixTextSize(textView4, 15);

        fixSize.fixMargins(radioButton1, 50, 175, 0, 0);
        fixSize.fixMargins(radioButton2, 0, 175, 50, 0);
        fixSize.fixMargins(radioButton3, 50, 0, 0, 0);
        fixSize.fixMargins(radioButton4, 0, 0, 50, 0);
        fixSize.fixMargins(textView1, 50, 0, 0, 0);
        fixSize.fixMargins(textView2, 0, 0, 50, 0);
        fixSize.fixMargins(textView3, 50, 0, 0, 175);
        fixSize.fixMargins(textView4, 0, 0, 50, 175);

        fixSize.fixTextSize(title2, 15);
        fixSize.fixTextSize(description, 20);
        fixSize.fixTextSize(button, 20);
        fixSize.fixTextSize(button2, 20);
        description.getLayoutParams().height = fixSize.fixVertical(100);
        button.getLayoutParams().width = fixSize.fixHorizontal(200);
        button.getLayoutParams().height = fixSize.fixVertical(75);
        button2.getLayoutParams().width = fixSize.fixHorizontal(200);
        button2.getLayoutParams().height = fixSize.fixVertical(75);
        secondView.getLayoutParams().width = fixSize.fixHorizontal(650);
        secondView.getLayoutParams().height = fixSize.fixVertical(300);

        title.setText(getResources().getString(R.string.introText));

        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        if (day != month) {
            textView1.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.INVISIBLE);
            textView3.setVisibility(View.INVISIBLE);
            textView4.setVisibility(View.INVISIBLE);
        }

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat("MM/dd/yy");

        String string1 = simpleDateFormat1.format(date);
        String string2 = simpleDateFormat2.format(date);
        String string3 = simpleDateFormat3.format(date);
        String string4 = simpleDateFormat4.format(date);

        radioButton1.setText(string1);
        radioButton2.setText(string2);
        radioButton3.setText(string3);
        radioButton4.setText(string4);

        ColorDrawable colorDrawable[] = new ColorDrawable[] {new ColorDrawable(Color.parseColor("#FAFAFA")), new ColorDrawable(Color.parseColor("#B2B2B2"))};
        final TransitionDrawable transitionDrawable = new TransitionDrawable(colorDrawable);

        ColorDrawable colorDrawable2[] = new ColorDrawable[] {new ColorDrawable(Color.parseColor("#B2B2B2")), new ColorDrawable(Color.parseColor("#FAFAFA"))};
        final TransitionDrawable transitionDrawable2 = new TransitionDrawable(colorDrawable2);

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewClick) {
                secondView.setVisibility(View.VISIBLE);
                view.setBackground(transitionDrawable);
                transitionDrawable.startTransition(500);

                radioButton1.setClickable(false);
                radioButton2.setClickable(false);
                radioButton3.setClickable(false);
                radioButton4.setClickable(false);

                stringFinal = "dd/MM/yyyy";
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewClick) {
                secondView.setVisibility(View.VISIBLE);
                view.setBackground(transitionDrawable);
                transitionDrawable.startTransition(500);

                radioButton1.setClickable(false);
                radioButton2.setClickable(false);
                radioButton3.setClickable(false);
                radioButton4.setClickable(false);

                stringFinal = "dd/MM/yy";
            }
        });

        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewClick) {
                secondView.setVisibility(View.VISIBLE);
                view.setBackground(transitionDrawable);
                transitionDrawable.startTransition(500);

                radioButton1.setClickable(false);
                radioButton2.setClickable(false);
                radioButton3.setClickable(false);
                radioButton4.setClickable(false);

                stringFinal = "MM/dd/yyyy";
            }
        });

        radioButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewClick) {
                secondView.setVisibility(View.VISIBLE);
                view.setBackground(transitionDrawable);
                transitionDrawable.startTransition(500);

                radioButton1.setClickable(false);
                radioButton2.setClickable(false);
                radioButton3.setClickable(false);
                radioButton4.setClickable(false);

                stringFinal = "MM/dd/yy";
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewClick) {
                secondView.setVisibility(View.GONE);
                view.setBackground(transitionDrawable2);
                transitionDrawable2.startTransition(500);

                radioButton1.setClickable(true);
                radioButton2.setClickable(true);
                radioButton3.setClickable(true);
                radioButton4.setClickable(true);

                radioButton1.setChecked(false);
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                radioButton4.setChecked(false);

                SharedPreferences sharedPreferences = getSharedPreferences("com.plaltair.tvseriesandmoviesdiary", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("dateFormat", stringFinal);
                editor.commit();

                Intent intent = new Intent(Intro.this, Main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewClick) {
                secondView.setVisibility(View.GONE);
                view.setBackground(transitionDrawable2);
                transitionDrawable2.startTransition(500);

                radioButton1.setClickable(true);
                radioButton2.setClickable(true);
                radioButton3.setClickable(true);
                radioButton4.setClickable(true);

                radioButton1.setChecked(false);
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                radioButton4.setChecked(false);
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (secondView.getVisibility() == View.VISIBLE) {
            ColorDrawable colorDrawable[] = new ColorDrawable[] {new ColorDrawable(Color.parseColor("#B2B2B2")), new ColorDrawable(Color.parseColor("#FAFAFA"))};
            final TransitionDrawable transitionDrawable = new TransitionDrawable(colorDrawable);

            secondView.setVisibility(View.GONE);
            view.setBackground(transitionDrawable);
            transitionDrawable.startTransition(500);

            radioButton1.setClickable(true);
            radioButton2.setClickable(true);
            radioButton3.setClickable(true);
            radioButton4.setClickable(true);

            radioButton1.setChecked(false);
            radioButton2.setChecked(false);
            radioButton3.setChecked(false);
            radioButton4.setChecked(false);
        }
    }
}
