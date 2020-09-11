package com.plaltair.tvseriesandmoviesdiary;

public class RowItem {

    private String title;
    private String subtitle;
    private String databaseTableName;

    public RowItem(String title, String subtitle, String databaseTableName) {

        this.title = title;
        this.subtitle = subtitle;
        this.databaseTableName = databaseTableName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDatabaseTableName() {
        return databaseTableName;
    }

    public void setDatabaseTableName(String databaseTableName) {
        this.databaseTableName = databaseTableName;
    }
}
