package encomendas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import basesDeDados.BDItens;
import basesDeDados.BDProduto;
import catalogos.Product;
import catalogos.ProductsAdapter;
import pt.ipp.estg.sportcenter.R;


public class CarrinhoCompras extends AppCompatActivity {
    private ArrayList<Item> itens;
    private ItemAdapter adapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrinho);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("SportCenter");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView rvProducts = findViewById(R.id.rvItens);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvProducts.addItemDecoration(itemDecoration);
        itens = new ArrayList<>();
        reloadItemList(itens);
        adapter = new ItemAdapter(this, itens);
        adapter.notifyDataSetChanged();
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
