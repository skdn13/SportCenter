package encomendas;

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

import java.util.ArrayList;

import basesDeDados.BDEncomendas;
import butterknife.BindView;
import butterknife.ButterKnife;
import pt.ipp.estg.sportcenter.R;


public class Historico extends AppCompatActivity {
    @BindView(R.id.checkout)
    Button checkout;
    @BindView(R.id.textView20)
    TextView total;
    @BindView(R.id.carr)
    TextView carrinho;
    private ArrayList<Encomenda> historico;
    private EncomendaAdapter adapter;
    private SharedPreferences preferences;
    private String email = "";

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
        historico = new ArrayList<>();
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        email = preferences.getString("email", "");
        reloadEncomendaList(historico);
        adapter = new EncomendaAdapter(this, historico);
        adapter.notifyDataSetChanged();
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        checkout.setVisibility(View.INVISIBLE);
        total.setText("");
        if (historico.isEmpty()) {
            carrinho.setText("Sem compras efetuadas!");
        } else {
            carrinho.setText("Compras efetuadas: ");
        }

    }

    public void reloadEncomendaList(ArrayList<Encomenda> list) {
        BDEncomendas
                dbHelper = new BDEncomendas(this);
        SQLiteDatabase db =
                dbHelper.getWritableDatabase();
        list.clear();
        String query = "select	*	from	tblEncomenda where  nome =?";
        Cursor c = db.rawQuery(query, new String[]{email});
        if (c != null && c.moveToFirst()) {
            do {
                Encomenda p = new Encomenda();
                p.setNumero(c.getInt(0));
                p.setNome(c.getString(1));
                p.setConteudo(c.getString(2));
                p.setTotal(c.getFloat(3));
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