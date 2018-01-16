package catalogos;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Image implements Serializable {
    private String name;
    private LinkImagem url;
    private boolean isFromDataBase;
    private Bitmap picture;

    public Image(String name, LinkImagem url) {
        this.name = name;
        this.url = url;
    }

    public Image() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkImagem getUrl() {
        return url;
    }

    public void setUrl(LinkImagem url) {
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
