package encomendas;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import basesDeDados.BDItens;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.ipp.estg.sportcenter.R;


public class ItemAdapter extends RecyclerView.Adapter<encomendas.ItemAdapter.ViewHolder> {
    private Context mContext;
    private List<Item> mItens;
    private SharedPreferences preferences;
    private Item product;

    public ItemAdapter(Context context, List<Item> itens) {
        this.mItens = itens;
        this.mContext = context;
    }

    public Context getmContext() {
        return mContext;
    }

    @Override
    public encomendas.ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_layout, parent, false);
        ButterKnife.bind(this, contactView);
        return new encomendas.ItemAdapter.ViewHolder(contactView);
    }


    @Override
    public void onBindViewHolder(encomendas.ItemAdapter.ViewHolder viewHolder, final int position) {
        product = this.mItens.get(position);
        viewHolder.nome.setText(product.getNome());
        viewHolder.preco.setText(String.valueOf(product.getPreco()) + "€");
        viewHolder.apagar.setText("Apagar");
    }

    @Override
    public int getItemCount() {
        return this.mItens.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView)
        TextView nome;
        @BindView(R.id.preco)
        TextView preco;
        @BindView(R.id.button)
        Button apagar;

        @OnClick(R.id.button)
        public void apagar(Button button) {
            BDItens dbHelper = new BDItens(getmContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long rowId = db.delete("tblItem", "id=?", new String[]{Integer.toString(product.getId())});
            db.close();
            button.setText("Eliminado");
            Toast.makeText(mContext, "Item eliminado do carrinho", Toast.LENGTH_SHORT).show();
            preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            int counter = preferences.getInt("image_data", 0);
            counter--;
            SharedPreferences.Editor edit = preferences.edit();
            edit.putInt("image_data", counter);
            edit.commit();
            mItens.remove(product);
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

