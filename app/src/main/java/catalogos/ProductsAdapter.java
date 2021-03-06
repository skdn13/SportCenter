package catalogos;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        ButterKnife.bind(this, contactView);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(final ProductsAdapter.ViewHolder viewHolder, int position) {
        Product product = this.mProducts.get(position);
        viewHolder.nameTextView.setText(product.getNome());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, DetalhesProduto.class);
                myIntent.putExtra("text", viewHolder.nameTextView.getText().toString());
                mContext.startActivity(myIntent);
            }
        });
        if (product.getPromocao().equals("Sim")) {
            viewHolder.preco.setText(String.valueOf(product.getPrecoPromocao()) + "€");
        } else {
            viewHolder.preco.setText(String.valueOf(product.getPreco()) + "€");
        }
        if (product.getDisponivel().equals("Sim")) {
            viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(getmContext(), R.drawable.ic_action2_name));
        } else {
            viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(getmContext(), R.drawable.ic_action3_name));
        }
    }

    @Override
    public int getItemCount() {
        return this.mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView)
        TextView nameTextView;
        @BindView(R.id.preco)
        TextView preco;
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.item)
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
