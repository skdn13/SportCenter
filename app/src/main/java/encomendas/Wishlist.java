package encomendas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import basesDeDados.BDWishs;
import butterknife.BindView;
import butterknife.ButterKnife;
import pt.ipp.estg.sportcenter.R;
import pt.ipp.estg.sportcenter.Utility;


public class Wishlist extends AppCompatActivity {
    private ArrayList<Item> itens;
    private WishAdapter adapter;
    private SharedPreferences preferences;
    private ItemTouchHelper.SimpleCallback simpleItemTouchCallback;
    @BindView(R.id.nomeWish)
    TextView wish;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private String itemID;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish);
        ButterKnife.bind(this);
        this.wish.setText("Lista de desejos");
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("SportCenter");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView rvProducts = findViewById(R.id.rvItens);
        itens = new ArrayList<>();
        reloadWishList(itens);
        adapter = new WishAdapter(this, itens);
        swipe();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvProducts);
        adapter.notifyDataSetChanged();
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        if (itens.isEmpty()) {
            wish.setText("Sem artigos desejados!");
        }
    }

    public void swipe() {
        this.simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                BDWishs dbHelper = new BDWishs(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("tblWish", "id=?", new String[]{Integer.toString(adapter.getID(viewHolder.getAdapterPosition()))});
                db.close();
                preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int counter = preferences.getInt("wishs", 0);
                counter--;
                SharedPreferences.Editor edit = preferences.edit();
                edit.putInt("wishs", counter);
                edit.commit();
                adapter.remove(viewHolder.getAdapterPosition());
                adapter.notifyDataSetChanged();
            }
        };
    }

    public void apagarFirebase(Item item) {
        if (Utility.isNetworkAvailable(getApplicationContext())) {
            mFirebaseInstance = FirebaseDatabase.getInstance();
            String email = preferences.getString("email", "");
            String ref = "wishs-" + email;
            String refS = ref.replaceAll("@", "-");
            String refSS = refS.replaceAll("\\.", "-");
            mFirebaseDatabase = mFirebaseInstance.getReference(refSS);
            itemID = String.valueOf(item.getId());
            mFirebaseDatabase.child(itemID).removeValue();
        }
    }

    public void reloadWishList(ArrayList<Item> list) {
        BDWishs
                dbHelper = new BDWishs(this);
        SQLiteDatabase db =
                dbHelper.getWritableDatabase();
        list.clear();
        Cursor c = db.rawQuery("SELECT	*	FROM	tblWish", null);
        if (c != null && c.moveToFirst()) {
            do {
                Item p = new Item();
                p.setId(c.getInt(0));
                p.setNome(c.getString(1));
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
