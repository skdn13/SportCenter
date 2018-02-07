package encomendas;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pt.ipp.estg.sportcenter.R;


public class ItemAdapter extends RecyclerView.Adapter<encomendas.ItemAdapter.ViewHolder> {
    private Context mContext;
    private List<Item> mItens;
    private SharedPreferences preferences;

    public ItemAdapter(Context context, List<Item> itens) {
        this.mItens = itens;
        this.mContext = context;
    }

    public void remove(int position) {
        mItens.remove(position);
    }

    public int getID(int pos) {
        return mItens.get(pos).getId();
    }

    public float getPreco(int pos) {
        return mItens.get(pos).getPreco();
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
        Item product = this.mItens.get(position);
        viewHolder.nome.setText(product.getNome());
        viewHolder.preco.setText(String.valueOf(product.getPreco()) + "€");
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

