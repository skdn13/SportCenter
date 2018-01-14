package encomendas;

import java.util.ArrayList;

/**
 * Created by pmms8 on 1/14/2018.
 */

public class Encomenda {
    private int numero;
    private String nome;
    private String conteudo;

    public Encomenda(int numero, String nome, String conteudo) {
        this.numero = numero;
        this.nome = nome;
        this.conteudo = conteudo;
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
