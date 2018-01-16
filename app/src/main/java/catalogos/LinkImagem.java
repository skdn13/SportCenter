package catalogos;

import java.io.Serializable;

public class LinkImagem implements Serializable {
    private String medium;

    public LinkImagem(String medium) {
        this.medium = medium;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}