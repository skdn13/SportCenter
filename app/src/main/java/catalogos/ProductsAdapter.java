package catalogos;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pt.ipp.estg.sportcenter.R;


public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private Context mContext;
    private List<Product> mProducts;

    public ProductsAdapter(Context context, List<Product> products) {
        this.mProducts = products;
        this.mContext = context;
    }

    public Context getmContext() {
        return mContext;
    }

    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(ProductsAdapter.ViewHolder viewHolder, int position) {
        Product product = this.mProducts.get(position);
        TextView textView = viewHolder.nameTextView, precoView = viewHolder.preco;
        textView.setText(product.getNome());
        precoView.setText(String.valueOf(product.getPreco()));

        Button button = viewHolder.messageButton;
        final TextView textView1 = viewHolder.nameTextView;
        button.setText(product.isDisponivel().equals("Sim") ? "Detalhes" : "Indisponível");
        button.setEnabled(product.isDisponivel().equals("Sim"));
        if (product.isDisponivel().equals("Sim")) {
            viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(getmContext(), R.drawable.ic_action2_name));
        } else {
            viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(getmContext(), R.drawable.ic_action3_name));
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, DetalhesProduto.class);
                myIntent.putExtra("text", textView1.getText().toString());
                mContext.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView, preco;
        public Button messageButton;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView =  itemView.findViewById(R.id.textView);
            messageButton =  itemView.findViewById(R.id.button);
            imageView = itemView.findViewById(R.id.imageView);
            preco =  itemView.findViewById(R.id.preco);
        }
    }
}
