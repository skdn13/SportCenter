package pt.ipp.estg.sportcenter;

import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import basesDeDados.BDProduto;
import butterknife.ButterKnife;
import butterknife.OnClick;
import catalogos.Product;
import catalogos.Promocoes;
import encomendas.Historico;


public class EcraInicial extends AppCompatActivity {
    private static final int REQUEST_FINE_LOCATION = 100;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private SharedPreferences preferences;
    private DatabaseReference mFirebaseDatabase;
    private String productID;
    private FirebaseDatabase mFirebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        ButterKnife.bind(this);
        Intent svc = new Intent(this, BackgroundSoundService.class);
        startService(svc);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("SportCenter");
        setSupportActionBar(myToolbar);
        try {
            if (!existeTabela()) {
                this.insertAllProducts();
            }
        } catch (Exception e) {
            Log.w("drop", "contentCreated");
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Log.d("latitude", String.valueOf(location.getLatitude()));
                    Log.d("longitude", String.valueOf(location.getLongitude()));
                    if ((location.getLatitude() >= 41.088 || location.getLatitude() <= 41.0882) && (location.getLongitude() <= -8.2439 || location.getLongitude() >= -8.2449)) {
                        enviarNotificacao();
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

    public void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    private void enviarNotificacao() {
        Intent i = new Intent(this, Promocoes.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext());
        notification.setContentTitle("SportCenter");
        notification.setContentText("Olá! Vemos que está próximo da nossa loja, visite-nos!");
        notification.setAutoCancel(true);
        notification.setContentIntent(pi);
        notification.setSmallIcon(R.drawable.ic_stat_name);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, notification.build());
    }

    @OnClick(R.id.a)
    public void Catalogos() {
        Intent intent = new Intent(getApplicationContext(), EcraCatalogos.class);
        startActivity(intent);
    }

    @OnClick(R.id.topWishs)
    public void Top() {
        Intent intent = new Intent(getApplicationContext(), TopDash.class);
        startActivity(intent);
    }

    @OnClick(R.id.contacto)
    public void Contacto() {
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.historico)
    public void historico() {
        Intent intent = new Intent(getApplicationContext(), Historico.class);
        startActivity(intent);
    }

    @OnClick(R.id.promocoes)
    public void promocoes() {
        Intent intent = new Intent(getApplicationContext(), Promocoes.class);
        startActivity(intent);
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    private void insertAllProducts() throws Exception {
        Product p1 = new Product("Sim", 5.95f, 10.95f, "Tshirt RipCurl", "Feminino", 1, "M", "Azul", "...", "100% Algodão", "Sim");
        Product p2 = new Product("Não", 20.95f, 20.95f, "Camisola BillaBong", "Masculino", 2, "XL", "Preto", "...", "100% Algodão", "Sim");
        Product p3 = new Product("Não", 5.95f, 5.95f, "Chinelos Roxy", "Feminino", 3, "36", "Rosa", "...", "100% Algodão", "Sim");
        Product p4 = new Product("Não", 4.95f, 4.95f, "Chinelos QuickSilver", "Masculino", 4, "44", "Verde", "...", "100% Algodão", "Sim");
        Product p5 = new Product("Não", 12.95f, 12.95f, "Calças Nike", "Crianca", 5, "16", "Amarelo", "...", "100% Algodão", "Sim");
        Product p6 = new Product("Não", 34.95f, 34.95f, "Calções Adidas", "Masculino", 6, "XL", "Cinzento", "...", "100% Algodão", "Sim");
        Product p7 = new Product("Não", 28.95f, 28.95f, "Sweat Asics", "Feminino", 7, "S", "Branco", "...", "100% Algodão", "Sim");
        Product p8 = new Product("Sim", 12.95f, 24.95f, "Tshirt Puma", "Crianca", 8, "14", "Preto", "...", "100% Algodão", "Sim");
        Product p9 = new Product("Sim", 60.95f, 80.0f, "Sapatilhas New Balance", "Masculino", 9, "42", "Laranja", "...", "100% Algodão", "Sim");
        Product p10 = new Product("Sim", 8.95f, 10.0f, "Gorro Berg", "Feminino", 10, "L", "Verde", "...", "100% Algodão", "Sim");
        Product p11 = new Product("Sim", 7.50f, 12.0f, "Cachecol Adidas", "Crianca", 11, "S", "Azul", "...", "100% Algodão", "Sim");

        gravarFirebase(p1);
        gravarFirebase(p2);
        gravarFirebase(p3);
        gravarFirebase(p4);
        gravarFirebase(p5);
        gravarFirebase(p6);
        gravarFirebase(p7);
        gravarFirebase(p8);
        gravarFirebase(p9);
        gravarFirebase(p10);
        gravarFirebase(p11);
        inserirProduto(p1);
        inserirProduto(p2);
        inserirProduto(p3);
        inserirProduto(p4);
        inserirProduto(p5);
        inserirProduto(p6);
        inserirProduto(p7);
        inserirProduto(p8);
        inserirProduto(p9);
        inserirProduto(p10);
        inserirProduto(p11);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        values.put("disponivel", p.getDisponivel());
        values.put("promocao", p.getPromocao());
        values.put("precoPromocao", p.getPrecoPromocao());
        values.put("favorito", p.getFavourited());
        long rowId = db.insert("tblProduto", null, values);
        db.close();
        if (rowId < 0) {
            throw new Exception("Não foi possível inserir o produto na Base de Dados");
        }
    }

    public void gravarFirebase(Product product) {
        if (Utility.isNetworkAvailable(getApplicationContext())) {
            mFirebaseInstance = FirebaseDatabase.getInstance();
            mFirebaseDatabase = mFirebaseInstance.getReference("products");
            productID = String.valueOf(product.getReferencia());
            mFirebaseDatabase.child(productID).child("Nome").setValue(product.getNome());
            mFirebaseDatabase.child(productID).child("Tamanho").setValue(product.getTamanho());
            mFirebaseDatabase.child(productID).child("Promoção").setValue(product.getPromocao());
            mFirebaseDatabase.child(productID).child("Preço Normal").setValue(product.getPreco());
            mFirebaseDatabase.child(productID).child("Sexo").setValue(product.getSexo());
            mFirebaseDatabase.child(productID).child("Imagem").setValue(product.getImagem());
            mFirebaseDatabase.child(productID).child("Cor").setValue(product.getCor());
            mFirebaseDatabase.child(productID).child("Preço Promoção").setValue(product.getPrecoPromocao());
            mFirebaseDatabase.child(productID).child("Disponível").setValue(product.getDisponivel());
            mFirebaseDatabase.child(productID).child("Composição").setValue(product.getComposicao());
            mFirebaseDatabase.child(productID).child("Num Favoritos").setValue(product.getFavourited());
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
