package encomendas;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

import basesDeDados.BDEncomendas;
import basesDeDados.BDItens;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.ipp.estg.sportcenter.R;


public class CarrinhoCompras extends AppCompatActivity {
    private ArrayList<Item> itens;
    private ItemAdapter adapter;
    private SharedPreferences preferences;
    @BindView(R.id.checkout)
    Button checkout;
    private float totalCarrinho = 0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrinho);
        ButterKnife.bind(this);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("SportCenter");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView rvProducts = findViewById(R.id.rvItens);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvProducts.addItemDecoration(itemDecoration);
        itens = new ArrayList<>();
        reloadItemList(itens);
        TextView total = findViewById(R.id.textView20);
        TextView carrinho = findViewById(R.id.carr);
        for (Item f : itens) {
            totalCarrinho += f.getPreco();
        }
        total.append(": " + String.valueOf(totalCarrinho) + "€");
        adapter = new ItemAdapter(this, itens);
        adapter.notifyDataSetChanged();
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        if (itens.isEmpty()) {
            checkout.setVisibility(View.INVISIBLE);
            total.setText("");
            carrinho.setText("Carrinho vazio!");
        }
    }

    @OnClick(R.id.checkout)
    public void checkout() {
        BDItens dbHelper = new BDItens(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Gson gson = new Gson();
        String inputString = gson.toJson(itens);
        for (Item i : itens) {
            long rowId = db.delete("tblItem", "id=?", new String[]{Integer.toString(i.getId())});
        }
        itens.clear();
        db.close();
        int random = new Random().nextInt(10000);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String email = preferences.getString("email", "");
        try {
            inserirEncomenda(new Encomenda(random, email, inputString, totalCarrinho));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int counter = preferences.getInt("image_data", 0);
        counter = 0;
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt("image_data", counter);
        edit.commit();
        Intent myIntent = new Intent(getApplicationContext(), encomendas.Checkout.class);
        myIntent.putExtra("numero", random);
        startActivity(myIntent);
    }

    private void inserirEncomenda(Encomenda p) throws Exception {
        BDEncomendas dbHelper = new BDEncomendas(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("numero", p.getNumero());
        values.put("nome", p.getNome());
        values.put("conteudo", p.getConteudo());
        values.put("total", p.getTotal());
        long rowId = db.insert("tblEncomenda", null, values);
        db.close();
        if (rowId < 0) {
            throw new Exception("Não foi possível inserir o item na Base de Dados");
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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
