package encomendas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pt.ipp.estg.sportcenter.R;


public class WishAdapter extends RecyclerView.Adapter<encomendas.WishAdapter.ViewHolder> {
    private Context mContext;
    private List<Item> mItens;

    public WishAdapter(Context context, List<Item> itens) {
        this.mItens = itens;
        this.mContext = context;
    }

    public Context getmContext() {
        return mContext;
    }

    @Override
    public encomendas.WishAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.wish_layout, parent, false);
        ButterKnife.bind(this, contactView);

        return new encomendas.WishAdapter.ViewHolder(contactView);
    }


    public void remove(int position) {
        mItens.remove(position);
    }

    @Override
    public void onBindViewHolder(encomendas.WishAdapter.ViewHolder viewHolder, final int position) {
        Item item = this.mItens.get(position);
        viewHolder.nome.setText(item.getNome());
    }


    public int getID(int pos) {
        return mItens.get(pos).getId();
    }

    @Override
    public int getItemCount() {
        return this.mItens.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView)
        TextView nome;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

