package catalogos;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import basesDeDados.BDItens;
import basesDeDados.BDProduto;
import basesDeDados.BDImagens;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import encomendas.Item;
import pt.ipp.estg.sportcenter.R;
import pt.ipp.estg.sportcenter.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalhesProduto extends AppCompatActivity implements ImageListener {
    @BindView(R.id.textView9)
    TextView nome;
    @BindView(R.id.textView13)
    TextView cor;
    @BindView(R.id.textView11)
    TextView tamanho;
    @BindView(R.id.textView15)
    TextView composicao;
    @BindView(R.id.textView17)
    TextView sexo;
    @BindView(R.id.textView19)
    TextView preco;
    private String text2 = "", text3 = "", text4 = "", text5 = "", promocao = "";
    private Float text6 = null, precoPromocao = null;
    private List<Product> details;
    private String nomeProduto = "";
    private ImageAdapter adapter2;
    private ArrayList<Image> images;
    private REST mREST;
    private BDImagens mDatabase;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("SportCenter");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nomeProduto = getIntent().getStringExtra("text");
        this.details = new ArrayList<>();
        mREST = new REST();
        mDatabase = new BDImagens(this);
        nome.setText(nomeProduto);
        reloadProductDetails((ArrayList<Product>) details);
        for (Product d : details) {
            if (d.getNome().equals(nomeProduto)) {
                text2 = d.getCor();
                text3 = d.getTamanho();
                text4 = d.getComposicao();
                text5 = d.getSexo();
                text6 = d.getPreco();
                promocao = d.getPromocao();
                precoPromocao = d.getPrecoPromocao();
            }
        }
        cor.setText(text2);
        tamanho.setText(text3);
        composicao.setText(text4);
        sexo.setText(text5);
        if (promocao.equals("Sim")) {
            preco.setText(String.valueOf(precoPromocao) + "€");
        } else {
            preco.setText(String.valueOf(text6) + "€");
        }
        images = new ArrayList<>();
        images.add(new Image("RipCurl", new LinkImagem("https://photos6.spartoo.pt/photos/594/5946468/5946468_500_A.jpg")));
        images.add(new Image("Chinelos", new LinkImagem("http://i.overboard.com.br/imagens/produtos/0471717003/Detalhes2/chinelo-quiksilver-basis-nitro-preto.jpg")));
        images.add(new Image("Nike", new LinkImagem("http://static5.netshoes.net/Produtos/calca-nike-drifit-racer-knit-track-masculina/66/D12-5977-266/D12-5977-266_zoom1.jpg")));
        images.add(new Image("Puma", new LinkImagem("https://images.sportsdirect.com/images/products/59701202_l.jpg")));
        images.add(new Image("Adidas", new LinkImagem("http://youridstore.com.br/media/catalog/product/cache/1/image/950x/472321edac810f9b2465a359d8cdc0b5/b/k/bk7634-_273_-2.jpg")));
        adapter2 = new ImageAdapter(this, images);
        adapter2.notifyDataSetChanged();
        RecyclerView rvImages = findViewById(R.id.rvImages);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvImages.addItemDecoration(itemDecoration);
        rvImages.setAdapter(adapter2);
        rvImages.setLayoutManager(new LinearLayoutManager(this));
        getFeed();
    }

    private void getFeed() {
        // adapter2.reset();
        if (Utility.isNetworkAvailable(getApplicationContext())) {
            loadData();
        } else getFeedFromDatabase();

    }

    private void getFeedFromDatabase() {
        mDatabase.fetchData(this);
    }

    @OnClick(R.id.button2)
    public void addicionar(TextView view) {
        final Float precoProduto = text6;
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int counter = preferences.getInt("image_data", 0);
        SharedPreferences.Editor edit = preferences.edit();
        ArrayList<Item> lista = new ArrayList<>();
        reloadItemList(lista);
        boolean existe = false;
        for (Item i : lista) {
            if (i.getNome().equals(nomeProduto)) {
                existe = true;
            }
        }
        try {
            if (!existeTabela() && !existe) {
                inserirItem(new Item(counter, precoProduto, 1, nomeProduto));
                counter++;
                edit.putInt("image_data", counter);
                edit.commit();
                view.setText("Adicionado ao carrinho!");
                Toast.makeText(getApplicationContext(), "Novo item no carrinho", Toast.LENGTH_SHORT).show();
                invalidateOptionsMenu();
            } else {
                view.setText("Já existe no carrinho!");
                Toast.makeText(getApplicationContext(), "Item já foi adicionado", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean existeTabela() {
        BDItens dbHelper = new BDItens(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("SELECT * FROM sqlite_master WHERE name ='tblItem' and type='table'");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void reloadItemList(ArrayList<Item> list) {
        BDItens
                dbHelper = new BDItens(this);
        SQLiteDatabase db =
                dbHelper.getWritableDatabase();
        list.clear();
        Cursor c = db.rawQuery("SELECT	*	FROM	tblItem", null);
        if (c != null && c.moveToFirst()) {
            do {
                Item p = new Item();
                p.setId(c.getInt(0));
                p.setPreco(c.getFloat(1));
                p.setQuantidade(c.getInt(2));
                p.setNome(c.getString(3));
                list.add(p);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
    }

    private void inserirItem(Item p) throws Exception {
        BDItens dbHelper = new BDItens(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", p.getId());
        values.put("preco", p.getPreco());
        values.put("quantidade", p.getQuantidade());
        values.put("nome", p.getNome());
        long rowId = db.insert("tblItem", null, values);
        db.close();
        if (rowId < 0) {
            throw new Exception("Não foi possível inserir o item na Base de Dados");
        }
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

    public class SaveIntoDatabase extends AsyncTask<Image, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Image... params) {
            Image image = params[0];
            try {
                InputStream inputStream = new URL(image.getUrl().getMedium()).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                image.setPicture(bitmap);
                mDatabase.addData(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void loadData() {
        Call<List<Image>> listData = mREST.getFlowerService().getAllData();
        listData.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                if (response.isSuccessful()) {
                    List<Image> datalist = response.body();
                    for (int i = 0; i < datalist.size(); i++) {
                        Image data = datalist.get(i);
                        SaveIntoDatabase task = new SaveIntoDatabase();
                        task.execute(data);
                        adapter2.addData(data);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
            }
        });

    }

    @Override
    public void onDeliverData(Image image) {
        adapter2.addData(image);
    }

    @Override
    public void onHideDialog() {
    }

    public void reloadProductDetails(ArrayList<Product> list) {
        BDProduto
                dbHelper = new BDProduto(this);
        SQLiteDatabase db =
                dbHelper.getWritableDatabase();
        list.clear();
        Cursor c = db.rawQuery("SELECT	*	FROM	tblProduto", null);
        if (c != null && c.moveToFirst()) {
            do {
                Product p = new Product();
                p.setReferencia(c.getInt(0));
                p.setPreco(c.getFloat(1));
                p.setSexo(c.getString(2));
                p.setNome(c.getString(3));
                p.setTamanho(c.getString(4));
                p.setCor(c.getString(5));
                p.setComposicao(c.getString(6));
                p.setImagem(c.getString(7));
                p.setDisponivel(c.getString(8));
                p.setPromocao(c.getString(9));
                p.setPrecoPromocao(c.getFloat(10));
                list.add(p);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
