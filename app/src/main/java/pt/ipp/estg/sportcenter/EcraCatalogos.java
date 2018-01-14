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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import basesDeDados.BDProduto;
import catalogos.ColecaoCrianca;
import catalogos.ColecaoHomem;
import catalogos.ColecaoMulher;
import catalogos.Product;
import catalogos.ProductActivity;


public class EcraCatalogos extends AppCompatActivity {
    private CardView catalogo, colecaoHomem, colecaoMulher, colecaoCrianca;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_cat);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("SportCenter");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        catalogo = findViewById(R.id.b);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                startActivity(intent);
            }
        });
        colecaoHomem = findViewById(R.id.c);
        colecaoHomem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ColecaoHomem.class);
                startActivity(i);
            }
        });
        colecaoMulher = findViewById(R.id.d);
        colecaoMulher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ColecaoMulher.class);
                startActivity(i);
            }
        });
        colecaoCrianca = findViewById(R.id.crianca);
        colecaoCrianca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ColecaoCrianca.class);
                startActivity(i);
            }
        });
    }

    private void apagarProduto(Product p) throws Exception {
        BDProduto dbHelper = new BDProduto(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.delete("tblProduto", p.getNome(), null);
        db.close();
    }

    private void apagarTabelaProduto() throws Exception {
        BDProduto dbHelper = new BDProduto(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("tblProduto", null, null);
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
