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
    private String promocao;
    private float precoPromocao;
    private int favourited;

    public int getFavourited() {
        return favourited;
    }

    public void setFavourited(int favourited) {
        this.favourited = favourited;
    }

    public String getDisponivel() {
        return disponivel;
    }

    public String getPromocao() {
        return promocao;
    }

    public void setPromocao(String promocao) {
        this.promocao = promocao;
    }

    public float getPrecoPromocao() {
        return precoPromocao;
    }

    public void setPrecoPromocao(float precoPromocao) {
        this.precoPromocao = precoPromocao;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public Product() {

    }

    public Product(String emPromocao, float precoPromocao, float preco, String nome, String sexo, Integer referencia, String tamanho, String cor, String imagem, String composicao, String disponivel) {
        this.favourited = 0;
        this.promocao = emPromocao;
        this.precoPromocao = precoPromocao;
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

    public void setDisponivel(String disponivel) {
        this.disponivel = disponivel;
    }
}
