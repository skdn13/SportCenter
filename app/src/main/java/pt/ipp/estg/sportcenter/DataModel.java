package pt.ipp.estg.sportcenter;

import android.graphics.Bitmap;

/**
 * Created by pmms8 on 1/7/2018.
 */

public class DataModel {
    private String name;
    private URLlist url;
    private boolean isFromDataBase;
    private Bitmap picture;

    public DataModel(String name, URLlist url) {
        this.name = name;
        this.url = url;
    }

    public DataModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URLlist getUrl() {
        return url;
    }

    public void setUrl(URLlist url) {
        this.url = url;
    }

    public boolean isFromDataBase() {
        return isFromDataBase;
    }

    public void setFromDataBase(boolean fromDataBase) {
        isFromDataBase = fromDataBase;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public void setFromDatabase(boolean fromDatabase) {
        this.isFromDataBase = fromDatabase;
    }

}
