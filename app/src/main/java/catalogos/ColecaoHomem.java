package catalogos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import basesDeDados.BDProduto;
import pt.ipp.estg.sportcenter.R;


public class ColecaoHomem extends AppCompatActivity {

    private ArrayList<Product> products;
    private ProductsAdapter adapter;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_list);

        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("SportCenter");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView rvProducts = findViewById(R.id.rvProducts);
        products = new ArrayList<>();
        reloadProductList(products);
        adapter = new ProductsAdapter(this, products);
        adapter.notifyDataSetChanged();
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    public void reloadProductList(ArrayList<Product> list) {
        BDProduto
                dbHelper = new BDProduto(this);
        SQLiteDatabase db =
                dbHelper.getWritableDatabase();
        list.clear();
        Cursor c = db.rawQuery("SELECT	*	FROM	tblProduto WHERE sexo='Masculino'", null);
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
                p.setFavourited(c.getInt(11));
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
