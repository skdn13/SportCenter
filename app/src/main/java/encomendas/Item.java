package encomendas;

/**
 * Created by pmms8 on 1/13/2018.
 */

public class Item {
    private int id;
    private float preco;
    private int quantidade;
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Item(int id, float preco, int quantidade, String nome) {
        this.id = id;
        this.preco = preco;
        this.quantidade = quantidade;
        this.nome = nome;
    }

    public Item() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
