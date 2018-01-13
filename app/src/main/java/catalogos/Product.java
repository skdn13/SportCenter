package catalogos;

public class Product {
    private String nome;
    private Integer referencia;
    private String tamanho;
    private String cor;
    private String imagem;
    private String composicao;
    private String sexo;
    private String disponivel;
    private float preco;

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public Product() {

    }

    public Product(float preco, String nome, String sexo, Integer referencia, String tamanho, String cor, String imagem, String composicao, String disponivel) {
        this.nome = nome;
        this.referencia = referencia;
        this.tamanho = tamanho;
        this.cor = cor;
        this.imagem = imagem;
        this.composicao = composicao;
        this.sexo = sexo;
        this.disponivel = disponivel;
        this.preco = preco;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getReferencia() {
        return referencia;
    }

    public void setReferencia(Integer referencia) {
        this.referencia = referencia;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getComposicao() {
        return composicao;
    }

    public void setComposicao(String composicao) {
        this.composicao = composicao;
    }

    public String isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(String disponivel) {
        this.disponivel = disponivel;
    }
}
