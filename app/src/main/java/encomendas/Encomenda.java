package encomendas;

import java.util.ArrayList;

/**
 * Created by pmms8 on 1/14/2018.
 */

public class Encomenda {
    private int numero;
    private String nome;
    private String conteudo;
    private float total;

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Encomenda(int numero, String nome, String conteudo, float total) {
        this.numero = numero;
        this.nome = nome;
        this.conteudo = conteudo;
        this.total = total;
    }

    public Encomenda() {

    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
