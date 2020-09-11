package com.plaltair.tvseriesandmoviesdiary;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Pierluca Lippi on 13/08/17.
 */

public class FixSize {

    public static final int MAX_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels; //Current screen width
    public static final int MAX_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels; //Current screen height

    public final int STANDARD_WIDTH; //Test device width
    public final int STANDARD_HEIGHT; //Test device height

    public FixSize() {
        STANDARD_WIDTH = 768; //Nexus 4 width
        STANDARD_HEIGHT = 1184; //Nexts 4 height
    }

    public FixSize(int testWidth, int testHeight) {
        //If we have a test device other than nexus 4...

        STANDARD_WIDTH = testWidth;
        STANDARD_HEIGHT = testHeight;
    }

    //With this method we can adjust a horizontal size, such as the width
    public int fixHorizontal(int horizontalSize) {

        //Proportion that returns horizontalSize adjusted according to the current width of the screen
        return (int)(horizontalSize * ((double)MAX_WIDTH / (double)STANDARD_WIDTH));
    }

    //With this method we can adjust a vertical size, such as the height
    public int fixVertical(int verticalSize) {

        //Proportion that returns verticalSize adjusted according to the current height of the screen
        return (int)(verticalSize * ((double)MAX_HEIGHT / (double)STANDARD_HEIGHT));
    }

    //With this method we can adjust the font size of a view
    public void fixTextSize(TextView textView, int fontSize) {

        //The view font is adjusted according to the size of the screen
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (MAX_WIDTH/fontSize));
    }

    //With this method we can adjust the margins of a view
    public void fixMargins (View view, int left, int top, int right, int bottom) {
        left = fixHorizontal(left); //Left margin is adjusted
        top = fixVertical(top); //Top margin is adjusted
        right = fixHorizontal(right); //Right margin is adjusted
        bottom = fixVertical(bottom); //Bottom margin is adjusted

        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams(); //Getting the MarginLayoutParams of the view...

            //Setting the margins according to the screen size...
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    //With this method we can adjust the padding of a view
    public void fixPadding(View view, int left, int top, int right, int bottom) {
        left = fixHorizontal(left); //Left padding is adjusted
        top = fixVertical(top); //Top padding is adjusted
        right = fixHorizontal(right); // Right padding is adjusted
        bottom = fixVertical(bottom); //Bottom padding is adjusted
        view.setPadding(left, top, right, bottom); //The padding of the view is adjusted according to the screen size
    }
}
