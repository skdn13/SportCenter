package pt.ipp.estg.sportcenter;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import basesDeDados.BDProduto;
import catalogos.Product;
import catalogos.Promocoes;
import encomendas.Historico;


public class EcraInicial extends AppCompatActivity {
    private CardView catalogo, contacto, encomendas, promocoes;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("SportCenter");
        setSupportActionBar(myToolbar);
        catalogo = findViewById(R.id.a);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EcraCatalogos.class);
                startActivity(intent);
            }
        });
        contacto = findViewById(R.id.contacto);
        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        encomendas = findViewById(R.id.historico);
        encomendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Historico.class);
                startActivity(intent);
            }
        });
        promocoes = findViewById(R.id.promocoes);
        promocoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Promocoes.class);
                startActivity(intent);
            }
        });
        try {
            if (!existeTabela()) {
                inserirProduto(new Product("Sim", 5.95f, 10.95f, "Tshirt RipCurl", "Feminino", 1, "M", "Azul", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Não", 20.95f, 20.95f, "Camisola BillaBong", "Masculino", 2, "XL", "Preto", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Não", 5.95f, 5.95f, "Chinelos Roxy", "Feminino", 3, "36", "Rosa", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Não", 4.95f, 4.95f, "Chinelos QuickSilver", "Masculino", 4, "44", "Verde", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Não", 12.95f, 12.95f, "Calças Nike", "Crianca", 5, "16", "Amarelo", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Não", 34.95f, 34.95f, "Calções Adidas", "Masculino", 6, "XL", "Cinzento", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Não", 28.95f, 28.95f, "Sweat Asics", "Feminino", 7, "S", "Branco", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Sim", 12.95f, 24.95f, "Tshirt Puma", "Crianca", 8, "14", "Preto", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Sim", 60.95f, 80.0f, "Sapatilhas New Balance", "Masculino", 9, "42", "Laranja", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Sim", 8.95f, 10.0f, "Gorro Berg", "Feminino", 10, "L", "Verde", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Sim", 7.50f, 12.0f, "Cachecol Adidas", "Crianca", 11, "S", "Azul", "...", "100% Algodão", "Sim"));
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
        values.put("promocao", p.getPromocao());
        values.put("precoPromocao", p.getPrecoPromocao());
        long rowId = db.insert("tblProduto", null, values);
        db.close();
        if (rowId < 0) {
            throw new Exception("Não foi possível inserir o produto na Base de Dados");
        }
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

    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.contadorcarrinho, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.count);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = (TextView) view.findViewById(R.id.count);
            textView.setText("" + count);
        }

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.badge);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int counter = preferences.getInt("image_data", 0);
        menuItem.setIcon(buildCounterDrawable(counter, R.drawable.ic_action_cart));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(), pt.ipp.estg.sportcenter.Preferences.class));
                return true;
            case R.id.badge:
                startActivity(new Intent(getApplicationContext(), encomendas.CarrinhoCompras.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
