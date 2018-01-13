package encomendas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import pt.ipp.estg.sportcenter.R;


public class CarrinhoCompras extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrinho);
        final LinearLayout layout =findViewById(R.id.linearMain);
        final Button btn =  findViewById(R.id.second);
        final Controller ct = new Controller();
        ModelProducts products;
        for (int i = 1; i <= 7; i++) {
            int Price = 15 + i;
            products = new ModelProducts("Product Item" + i, "Description" + i, Price);
            ct.setProducts(products);
        }
        int productsize = ct.getProductArraylistsize();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int j = 0; j < productsize; j++) {
            String pName = ct.getProducts(j).getProductName();
            int pPrice = ct.getProducts(j).getProductPrice();
            LinearLayout la = new LinearLayout(this);
            la.setOrientation(LinearLayout.HORIZONTAL);
            TextView tv = new TextView(this);
            tv.setText(" " + pName + " ");
            la.addView(tv);
            TextView tv1 = new TextView(this);
            tv1.setText("$" + pPrice + " ");
            la.addView(tv1);
            final Button btn1 = new Button(this);
            btn1.setId(j + 1);
            btn1.setText("Add to Cart");
            btn1.setLayoutParams(params);
            final int index = j;
            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.i("TAG", "index:" + index);
                    ModelProducts productsObject = ct.getProducts(index);
                    if (!ct.getCart().CheckProductInCart(productsObject)) {
                        btn1.setText("Item Added");
                        ct.getCart().setProducts(productsObject);
                        Toast.makeText(getApplicationContext(), "New CartSize:" + ct.getCart().getCartsize(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Products" + (index + 1) + "Already Added", Toast.LENGTH_LONG).show();
                    }
                }
            });
            la.addView(btn1);
            layout.addView(la);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getBaseContext(), Screen2.class);
                startActivity(in);
            }
        });
    }
}
