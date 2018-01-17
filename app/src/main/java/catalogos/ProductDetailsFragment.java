package catalogos;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import basesDeDados.BDImagens;
import basesDeDados.BDItens;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import encomendas.Item;
import pt.ipp.estg.sportcenter.R;
import pt.ipp.estg.sportcenter.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsFragment extends Fragment implements ImageListener {
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
    @BindView(R.id.rvImages)
    RecyclerView rvImages;
    private String text2 = "", text3 = "", text4 = "", text5 = "", promocao = "";
    private Float text6 = null, precoPromocao = null;
    private List<Product> details;
    private String nomeProduto = "";
    private ImageAdapter adapter2;
    private ArrayList<Image> images;
    private REST mREST;
    private BDImagens mDatabase;
    private SharedPreferences preferences;
    private Product produto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(getActivity());
        this.details = new ArrayList<>();
        setHasOptionsMenu(true);
        mREST = new REST();
        mDatabase = new BDImagens(getActivity());
        images = new ArrayList<>();
        images.add(new Image("RipCurl", new LinkImagem("https://photos6.spartoo.pt/photos/594/5946468/5946468_500_A.jpg")));
        images.add(new Image("Chinelos", new LinkImagem("http://i.overboard.com.br/imagens/produtos/0471717003/Detalhes2/chinelo-quiksilver-basis-nitro-preto.jpg")));
        images.add(new Image("Nike", new LinkImagem("http://static5.netshoes.net/Produtos/calca-nike-drifit-racer-knit-track-masculina/66/D12-5977-266/D12-5977-266_zoom1.jpg")));
        images.add(new Image("Puma", new LinkImagem("https://images.sportsdirect.com/images/products/59701202_l.jpg")));
        images.add(new Image("Adidas", new LinkImagem("http://youridstore.com.br/media/catalog/product/cache/1/image/950x/472321edac810f9b2465a359d8cdc0b5/b/k/bk7634-_273_-2.jpg")));
        adapter2 = new ImageAdapter(getActivity(), images);
        adapter2.notifyDataSetChanged();
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rvImages.addItemDecoration(itemDecoration);
        rvImages.setAdapter(adapter2);
        rvImages.setLayoutManager(new LinearLayoutManager(getActivity()));
        getFeed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View
                mContentView = inflater.inflate(R.layout.activity_main2, container, false);
        if (this.produto != null) {
            this.nome.setText(produto.getNome());
            this.cor.setText(produto.getCor());
            this.composicao.setText(produto.getComposicao());
            this.tamanho.setText(produto.getTamanho());
            this.sexo.setText(produto.getSexo());
            if (produto.getPromocao().equals("Sim")) {
                this.precoPromocao = produto.getPrecoPromocao();
                this.preco.setText(String.valueOf(precoPromocao) + "€");
            } else {
                this.preco.setText(String.valueOf(produto.getPreco()) + "€");
            }
        }
        return mContentView;
    }

    public void updateProduct(Product produto) {
        this.produto = produto;
        if (this.produto != null) {
            this.nome.setText(produto.getNome());
            this.cor.setText(produto.getCor());
            this.composicao.setText(produto.getComposicao());
            this.tamanho.setText(produto.getTamanho());
            this.sexo.setText(produto.getSexo());
            if (produto.getPromocao().equals("Sim")) {
                this.precoPromocao = produto.getPrecoPromocao();
                this.preco.setText(String.valueOf(precoPromocao) + "€");
            } else {
                this.preco.setText(String.valueOf(produto.getPreco()) + "€");
            }
        }
    }

    private void getFeed() {
        // adapter2.reset();
        if (Utility.isNetworkAvailable(getActivity())) {
            loadData();
        } else getFeedFromDatabase();

    }

    private void getFeedFromDatabase() {
        mDatabase.fetchData(this);
    }


    @OnClick(R.id.button2)
    public void addicionar(TextView view) {
        final Float precoProduto = text6;
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
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
                Toast.makeText(getActivity(), "Novo item no carrinho", Toast.LENGTH_SHORT).show();
                getActivity().invalidateOptionsMenu();
            } else {
                view.setText("Já existe no carrinho!");
                Toast.makeText(getActivity(), "Item já foi adicionado", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean existeTabela() {
        BDItens dbHelper = new BDItens(getActivity());
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
                dbHelper = new BDItens(getActivity());
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
        BDItens dbHelper = new BDItens(getActivity());
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
                        ProductDetailsFragment.SaveIntoDatabase task = new ProductDetailsFragment.SaveIntoDatabase();
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

}
