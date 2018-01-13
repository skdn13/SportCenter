package pt.ipp.estg.sportcenter;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import basesDeDados.BDProduto;
import catalogos.ColecaoCrianca;
import catalogos.ColecaoHomem;
import catalogos.ColecaoMulher;
import catalogos.Product;
import catalogos.ProductActivity;


public class EcraCatalogos extends AppCompatActivity {
    private CardView catalogo, colecaoHomem, colecaoMulher, colecaoCrianca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_cat);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("SportCenter");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        catalogo = findViewById(R.id.b);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                startActivity(intent);
            }
        });
        colecaoHomem = findViewById(R.id.c);
        colecaoHomem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ColecaoHomem.class);
                startActivity(i);
            }
        });
        colecaoMulher = findViewById(R.id.d);
        colecaoMulher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ColecaoMulher.class);
                startActivity(i);
            }
        });
        colecaoCrianca = findViewById(R.id.crianca);
        colecaoCrianca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ColecaoCrianca.class);
                startActivity(i);
            }
        });
        try {
            if (!existeTabela()) {
                inserirProduto(new Product(10.95f, "Tshirt RipCurl", "Feminino", 1, "M", "Azul", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product(20.95f, "Camisola BillaBong", "Masculino", 2, "XL", "Preto", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product(5.95f, "Chinelos Roxy", "Feminino", 3, "36", "Rosa", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product(4.95f, "Chinelos QuickSilver", "Masculino", 4, "44", "Verde", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product(12.95f, "Calças Nike", "Crianca", 5, "16", "Amarelo", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product(34.95f, "Calções Adidas", "Masculino", 6, "XL", "Cinzento", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product(28.95f, "Sweat Asics", "Feminino", 7, "S", "Branco", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product(24.95f, "Tshirt Puma", "Crianca", 8, "14", "Preto", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product(80.0f, "Sapatilhas New Balance", "Masculino", 9, "42", "Laranja", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product(10.0f, "Gorro Berg", "Feminino", 10, "L", "Verde", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product(12.0f, "Cachecol Adidas", "Crianca", 11, "S", "Azul", "...", "100% Algodão", "Sim"));
            }
        } catch (Exception e) {
            Log.w("drop", "contentCreated");
        }
    }

    private void inserirProduto(Product p) throws Exception {
        BDProduto dbHelper = new BDProduto(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("referencia", p.getReferencia());
        values.put("preco", p.getPreco());
        values.put("sexo", p.getSexo());
        values.put("nome", p.getNome());
        values.put("tamanho", p.getTamanho());
        values.put("cor", p.getCor());
        values.put("composicao", p.getComposicao());
        values.put("imagem", p.getImagem());
        values.put("disponivel", p.isDisponivel());
        long rowId = db.insert("tblProduto", null, values);
        db.close();
        if (rowId < 0) {
            throw new Exception("Não foi possível inserir o produto na Base de Dados");
        }
    }

    private void apagarProduto(Product p) throws Exception {
        BDProduto dbHelper = new BDProduto(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.delete("tblProduto", p.getNome(), null);
        db.close();
    }

    private void apagarTabelaProduto() throws Exception {
        BDProduto dbHelper = new BDProduto(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("tblProduto", null, null);
        db.close();
    }

    private boolean existeTabela() {
        BDProduto dbHelper = new BDProduto(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("SELECT * FROM sqlite_master WHERE name ='tblProduto' and type='table'");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(), pt.ipp.estg.sportcenter.Preferences.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
