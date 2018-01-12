package pt.ipp.estg.sportcenter;

/**
 * Created by pmms8 on 24/11/2017.
 */

public class Pessoa {
    private int id;
    private String nome, apelido;

    public Pessoa(int id, String nome, String apelido) {
        this.id = id;
        this.nome = nome;
        this.apelido = apelido;
    }

    public Pessoa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String n) {
        nome = n;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String a) {
        apelido = a;
    }

}
