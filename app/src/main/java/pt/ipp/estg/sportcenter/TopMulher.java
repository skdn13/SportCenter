package pt.ipp.estg.sportcenter;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import basesDeDados.BDProduto;
import butterknife.BindView;
import butterknife.ButterKnife;
import encomendas.Item;
import encomendas.WishAdapter;


public class TopMulher extends AppCompatActivity {

    private ArrayList<Item> wishs;
    private WishAdapter adapter;
    private SharedPreferences preferences;
    @BindView(R.id.nomeWish)
    TextView wish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish);
        ButterKnife.bind(this);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("SportCenter");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView rvWishs = findViewById(R.id.rvItens);
        wishs = new ArrayList<>();
        reloadWishList(wishs);
        if (wishs.isEmpty()) {
            wish.setText("Sem artigos desejados!");
        } else {
            wish.setText("Top de artigos");
        }
        adapter = new WishAdapter(this, wishs);
        adapter.notifyDataSetChanged();
        rvWishs.setAdapter(adapter);
        rvWishs.setLayoutManager(new LinearLayoutManager(this));
    }

    public void reloadWishList(ArrayList<Item> list) {
        BDProduto
                dbHelper = new BDProduto(this);
        SQLiteDatabase db =
                dbHelper.getWritableDatabase();
        list.clear();
        Cursor c = db.rawQuery("SELECT	*	FROM	tblProduto WHERE sexo='Feminino' ORDER BY favorito DESC", null);
        if (c != null && c.moveToFirst()) {
            do {
                Log.d("Num", String.valueOf(c.getInt(11)));
                if (c.getInt(11) > 0) {
                    Item p = new Item();
                    p.setId(c.getInt(0));
                    p.setNome(c.getString(3));
                    list.add(p);
                }
            } while (c.moveToNext());
        }
        c.close();
        db.close();
    }

    public void onResume() {
        super.onResume();
        invalidateOptionsMenu();
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
