package encomendas;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pt.ipp.estg.sportcenter.R;


public class EncomendaAdapter extends RecyclerView.Adapter<encomendas.EncomendaAdapter.ViewHolder> {
    private Context mContext;
    private List<Encomenda> mItens;
    private SharedPreferences preferences;

    public EncomendaAdapter(Context context, List<Encomenda> itens) {
        this.mItens = itens;
        this.mContext = context;
    }

    public Context getmContext() {
        return mContext;
    }

    @Override
    public encomendas.EncomendaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_layout, parent, false);
        return new encomendas.EncomendaAdapter.ViewHolder(contactView);
    }


    @Override
    public void onBindViewHolder(encomendas.EncomendaAdapter.ViewHolder viewHolder, final int position) {
        final Encomenda product = this.mItens.get(position);
        TextView textView = viewHolder.numeroEncomenda, precoView = viewHolder.preco;
        textView.setText("Ref: " + String.valueOf(product.getNumero()));
        precoView.setText("Total: " + String.valueOf(product.getTotal()));
        final Button button = viewHolder.detalhes;
        button.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return this.mItens.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView numeroEncomenda, preco;
        public Button detalhes;

        public ViewHolder(View itemView) {
            super(itemView);
            numeroEncomenda = itemView.findViewById(R.id.textView);
            detalhes = itemView.findViewById(R.id.button);
            preco = itemView.findViewById(R.id.preco);
        }
    }
}