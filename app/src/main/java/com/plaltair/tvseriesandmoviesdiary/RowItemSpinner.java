package com.plaltair.tvseriesandmoviesdiary;

/**
 * Created by pierlucalippi on 31/08/17.
 */

public class RowItemSpinner {

    private String color;
    private String text;
    private boolean isText;

    public RowItemSpinner(String string, boolean isText) {
        if (isText == true) {
            text = string;
        }
        else {
            color = string;
        }
        this.isText = isText;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String string) {
        text = string;
    }

    public boolean isText() {
        return isText;
    }

    public void setIsText(boolean isText) {
        this.isText = isText;
    }

}
