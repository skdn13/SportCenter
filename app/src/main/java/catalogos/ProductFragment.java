package catalogos;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import basesDeDados.BDProduto;
import pt.ipp.estg.sportcenter.R;

public class ProductFragment extends Fragment {
    private ArrayList<Product> products;
    private ProductsAdapter adapter;
    private ProductListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        products = new ArrayList<>();
        reloadProductList(products);
    }


    public void reloadProductList(ArrayList<Product> list) {
        BDProduto
                dbHelper = new BDProduto(getActivity());
        SQLiteDatabase db =
                dbHelper.getWritableDatabase();
        list.clear();
        Cursor c = db.rawQuery("SELECT	*	FROM	tblProduto", null);
        if (c != null && c.moveToFirst()) {
            do {
                Product p = new Product();
                p.setReferencia(c.getInt(0));
                p.setPreco(c.getFloat(1));
                p.setSexo(c.getString(2));
                p.setNome(c.getString(3));
                p.setTamanho(c.getString(4));
                p.setCor(c.getString(5));
                p.setComposicao(c.getString(6));
                p.setImagem(c.getString(7));
                p.setDisponivel(c.getString(8));
                p.setPromocao(c.getString(9));
                p.setPrecoPromocao(c.getFloat(10));
                list.add(p);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View
                mContentView = inflater.inflate(R.layout.recycler, container, false);
        adapter = new ProductsAdapter(getActivity(), products);
        adapter.notifyDataSetChanged();
        RecyclerView rvProducts = mContentView.findViewById(R.id.rvProducts1);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rvProducts.addItemDecoration(itemDecoration);
        rvProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvProducts.setAdapter(adapter);
        return mContentView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ProductListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement Product Listener");
        }
    }
}
