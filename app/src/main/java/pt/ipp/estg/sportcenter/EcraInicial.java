package pt.ipp.estg.sportcenter;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import basesDeDados.BDProduto;
import catalogos.Product;
import catalogos.Promocoes;
import encomendas.Historico;


public class EcraInicial extends AppCompatActivity {
    private CardView catalogo, contacto, encomendas, promocoes;
    private static final int REQUEST_FINE_LOCATION = 100;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("SportCenter");
        setSupportActionBar(myToolbar);
        catalogo = findViewById(R.id.a);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EcraCatalogos.class);
                startActivity(intent);
            }
        });
        contacto = findViewById(R.id.contacto);
        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        encomendas = findViewById(R.id.historico);
        encomendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Historico.class);
                startActivity(intent);
            }
        });
        promocoes = findViewById(R.id.promocoes);
        promocoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Promocoes.class);
                startActivity(intent);
            }
        });
        try {
            if (!existeTabela()) {
                inserirProduto(new Product("Sim", 5.95f, 10.95f, "Tshirt RipCurl", "Feminino", 1, "M", "Azul", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Não", 20.95f, 20.95f, "Camisola BillaBong", "Masculino", 2, "XL", "Preto", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Não", 5.95f, 5.95f, "Chinelos Roxy", "Feminino", 3, "36", "Rosa", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Não", 4.95f, 4.95f, "Chinelos QuickSilver", "Masculino", 4, "44", "Verde", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Não", 12.95f, 12.95f, "Calças Nike", "Crianca", 5, "16", "Amarelo", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Não", 34.95f, 34.95f, "Calções Adidas", "Masculino", 6, "XL", "Cinzento", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Não", 28.95f, 28.95f, "Sweat Asics", "Feminino", 7, "S", "Branco", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Sim", 12.95f, 24.95f, "Tshirt Puma", "Crianca", 8, "14", "Preto", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Sim", 60.95f, 80.0f, "Sapatilhas New Balance", "Masculino", 9, "42", "Laranja", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Sim", 8.95f, 10.0f, "Gorro Berg", "Feminino", 10, "L", "Verde", "...", "100% Algodão", "Sim"));
                inserirProduto(new Product("Sim", 7.50f, 12.0f, "Cachecol Adidas", "Crianca", 11, "S", "Azul", "...", "100% Algodão", "Sim"));
            }
        } catch (Exception e) {
            Log.w("drop", "contentCreated");
        }


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Log.d("latitude", String.valueOf(location.getLatitude()));
                    Log.d("longitude", String.valueOf(location.getLongitude()));
                    if (location.getLatitude() == 41.088095 && location.getLongitude() == -8.2439617) {
                        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext());
                        notification.setContentTitle("SportCenter");
                        notification.setContentText("Olá! Vemos que está próximo da nossa loja, visite-nos!");
                        notification.setAutoCancel(true);
                        notification.setSmallIcon(R.drawable.ic_stat_name);
                        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify(1, notification.build());
                    }
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {

                }
            }
        };
        startLocationUpdates();
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions();
            return;
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
    }

    private void inserirProduto(Product p) throws Exception {
        BDProduto dbHelper = new BDProduto(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("referencia", p.getReferencia());
        values.put("preco", p.getPreco());
        values.put("sexo", p.getSexo());
        values.put("nome", p.getNome());
        values.put("tamanho", p.getTamanho());
        values.put("cor", p.getCor());
        values.put("composicao", p.getComposicao());
        values.put("imagem", p.getImagem());
        values.put("disponivel", p.isDisponivel());
        values.put("promocao", p.getPromocao());
        values.put("precoPromocao", p.getPrecoPromocao());
        long rowId = db.insert("tblProduto", null, values);
        db.close();
        if (rowId < 0) {
            throw new Exception("Não foi possível inserir o produto na Base de Dados");
        }
    }

    private boolean existeTabela() {
        BDProduto dbHelper = new BDProduto(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("SELECT * FROM sqlite_master WHERE name ='tblProduto' and type='table'");
        } catch (Exception e) {
            return false;
        }
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
