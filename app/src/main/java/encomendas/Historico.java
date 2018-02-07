package encomendas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import basesDeDados.BDEncomendas;
import butterknife.BindView;
import butterknife.ButterKnife;
import pt.ipp.estg.sportcenter.R;


public class Historico extends AppCompatActivity {
    @BindView(R.id.checkout)
    FloatingActionButton checkout;
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
            carrinho.setText("Compras efetuadas");
        }

    }

    public void onResume() {
        super.onResume();
        invalidateOptionsMenu();
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

    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.contadorcarrinho, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.count);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = view.findViewById(R.id.count);
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
        MenuItem menuItem2 = menu.findItem(R.id.wish);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int counter = preferences.getInt("image_data", 0);
        menuItem.setIcon(buildCounterDrawable(counter, R.drawable.ic_action_cart));
        int counterWishs = preferences.getInt("wishs", 0);
        menuItem2.setIcon(buildCounterDrawable(counterWishs, R.drawable.fav));
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
            case R.id.wish:
                startActivity(new Intent(getApplicationContext(), encomendas.Wishlist.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}