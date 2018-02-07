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


public class EncomendaAdapter extends RecyclerView.Adapter<encomendas.EncomendaAdapter.ViewHolder> {
    private Context mContext;
    private List<Encomenda> mItens;

    public EncomendaAdapter(Context context, List<Encomenda> itens) {
        this.mItens = itens;
        this.mContext = context;
    }

    @Override
    public encomendas.EncomendaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_layout, parent, false);
        ButterKnife.bind(this, contactView);
        return new encomendas.EncomendaAdapter.ViewHolder(contactView);
    }


    @Override
    public void onBindViewHolder(encomendas.EncomendaAdapter.ViewHolder viewHolder, final int position) {
        final Encomenda product = this.mItens.get(position);
        viewHolder.numeroEncomenda.setText("Ref: " + String.valueOf(product.getNumero()));
        viewHolder.preco.setText("Total: " + String.valueOf(product.getTotal()));
    }

    @Override
    public int getItemCount() {
        return this.mItens.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView)
        TextView numeroEncomenda;
        @BindView(R.id.preco)
        TextView preco;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}