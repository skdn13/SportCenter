package catalogos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import basesDeDados.BDProduto;
import basesDeDados.BDImagens;
import pt.ipp.estg.sportcenter.R;
import pt.ipp.estg.sportcenter.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalhesProduto extends AppCompatActivity implements DataFetchListner {
    private TextView mTextView, t2, t3, t4, t5, t6;
    private List<Product> details;
    private String nomeProduto = "";
    private DataAdapter adapter2;
    private ArrayList<DataModel> images;
    private RestManager mRestManager;
    private BDImagens mDatabase;
    private DataFetchListner lis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("SportCenter");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nomeProduto = getIntent().getStringExtra("text");
        this.details = new ArrayList<>();
        mRestManager = new RestManager();
        mTextView = (TextView) findViewById(R.id.textView9);
        mTextView.setText(nomeProduto);
        reloadProductDetails((ArrayList<Product>) details);
        t2 = (TextView) findViewById(R.id.textView13);
        String text2 = "", text3 = "", text4 = "", text5 = "";
        Float text6 = null;
        for (Product d : details) {
            if (d.getNome().equals(nomeProduto)) {
                text2 = d.getCor();
                text3 = d.getTamanho();
                text4 = d.getComposicao();
                text5 = d.getSexo();
                text6 = d.getPreco();
            }
        }
        t2.setText(text2);
        t3 = (TextView) findViewById(R.id.textView11);
        t3.setText(text3);
        t4 = (TextView) findViewById(R.id.textView15);
        t4.setText(text4);
        t5 = (TextView) findViewById(R.id.textView17);
        t5.setText(text5);
        t6 = (TextView) findViewById(R.id.textView19);
        t6.setText(String.valueOf(text6));

        images = new ArrayList<>();
        images.add(new DataModel("RipCurl", new URLlist("https://photos6.spartoo.pt/photos/594/5946468/5946468_500_A.jpg")));
        images.add(new DataModel("Billa", new URLlist("https://static.lvengine.net/bazardesportivo/Imgs/produtos/product_35806/f2ls06bif7-21_.jpg")));
        images.add(new DataModel("Chinelos", new URLlist("http://i.overboard.com.br/imagens/produtos/0471717003/Detalhes2/chinelo-quiksilver-basis-nitro-preto.jpg")));
        images.add(new DataModel("Nike", new URLlist("http://static5.netshoes.net/Produtos/calca-nike-drifit-racer-knit-track-masculina/66/D12-5977-266/D12-5977-266_zoom1.jpg")));
        images.add(new DataModel("Puma", new URLlist("https://images.sportsdirect.com/images/products/59701202_l.jpg")));
        images.add(new DataModel("Adidas", new URLlist("http://youridstore.com.br/media/catalog/product/cache/1/image/950x/472321edac810f9b2465a359d8cdc0b5/b/k/bk7634-_273_-2.jpg")));
        adapter2 = new DataAdapter(this, images);
        adapter2.notifyDataSetChanged();
        RecyclerView rvImages = findViewById(R.id.rvImages);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvImages.addItemDecoration(itemDecoration);
        rvImages.setAdapter(adapter2);
        rvImages.setLayoutManager(new LinearLayoutManager(this));
        if (Utility.isNetworkAvailable(this)) {
            this.loadData();
        } else {
            mDatabase.fetchData(lis);
        }
    }

    public class SaveIntoDatabase extends AsyncTask<DataModel, Void, Void> {
        // can use UI thread here
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // automatically done on worker thread (separate from UI thread)
        @Override
        protected Void doInBackground(DataModel... params) {
            DataModel dataModel = params[0];
            try {
                InputStream inputStream = new URL(dataModel.getUrl().getMedium()).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                //set bitmap value to Picture
                dataModel.setPicture(bitmap);
                //add data to database (shows in next step)
                mDatabase.addData(dataModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void loadData() {
        // Prepare the HTTP request
        Call<List<DataModel>> listData = mRestManager.getFlowerService().getAllData();
        // Asynchronously execute HTTP request
        listData.enqueue(new Callback<List<DataModel>>() {
            /**
             * onResponse is called when any kind of response has been received.
             */
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                // isSuccess is true if response code => 200 and <= 300
                if (response.isSuccessful()) {
                    // if parsing the JSON body failed,response.body() returns null
                    List<DataModel> datalist = response.body();
                    //Traversing through the whole list to get all data to save database
                    for (int i = 0; i < datalist.size(); i++) {
                        DataModel data = datalist.get(i);
                        SaveIntoDatabase task = new SaveIntoDatabase();
                        task.execute(data);
                        adapter2.addData(data);
                    }
                }
            }

            /**
             * onFailure gets called when the HTTP request didn't get through.
             * For instance if the URL is invalid / host not reachable
             */
            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
            }
        });

    }

    @Override
    public void onDeliverData(DataModel dataModel) {
// pass data to adapter to display in recycler
        adapter2.addData(dataModel);
    }

    @Override
    public void onHideDialog() {
// hide dialog here
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
