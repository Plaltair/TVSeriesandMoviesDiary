package com.plaltair.tvseriesandmoviesdiary;

/**
 * Created by pierlucalippi on 29/08/17.
 */

public class RowItemTVSeries {

    private String title;
    private String episodeToBeSeen;
    private String note;
    private String keyColor;
    private String date;
    private String tableName;

    public RowItemTVSeries(String title, String episodeToBeSeen, String note, String keyColor, String date, String tableName) {
        this.title = title;
        this.episodeToBeSeen = episodeToBeSeen;
        this.note = note;
        this.keyColor = keyColor;
        this.date = date;
        this.tableName = tableName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEpisodeToBeSeen() {
        return episodeToBeSeen;
    }

    public void setEpisodeToBeSeen(String episodeToBeSeen) {
        this.episodeToBeSeen = episodeToBeSeen;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getKeyColor() {
        return keyColor;
    }

    public void setKeyColor(String keyColor) {
        this.keyColor = keyColor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
