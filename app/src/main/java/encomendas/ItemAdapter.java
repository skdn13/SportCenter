package encomendas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import pt.ipp.estg.sportcenter.R;


public class ItemAdapter extends RecyclerView.Adapter<encomendas.ItemAdapter.ViewHolder> {
    private Context mContext;
    private List<Item> mItens;

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
        return new encomendas.ItemAdapter.ViewHolder(contactView);
    }


    @Override
    public void onBindViewHolder(encomendas.ItemAdapter.ViewHolder viewHolder, int position) {
        Item product = this.mItens.get(position);
        TextView textView = viewHolder.nameTextView, precoView = viewHolder.preco;
        textView.setText(product.getNome());
        precoView.setText(String.valueOf(product.getPreco()));
        Button button = viewHolder.messageButton;
        final TextView textView1 = viewHolder.nameTextView;
        button.setText("Apagar");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mItens.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView, preco;
        public Button messageButton;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textView);
            messageButton = itemView.findViewById(R.id.button);
            preco = itemView.findViewById(R.id.preco);
        }
    }
}

