package encomendas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import pt.ipp.estg.sportcenter.R;


public class Screen2 extends AppCompatActivity {
    /* (non-Javadoc)
    * @see android.app.Activity#onCreate(android.os.Bundle) */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen2);
        TextView showCartContent = findViewById(R.id.showcart);
        final Controller ct = new Controller();
        final int CartSize = ct.getCart().getCartsize();
        String show = "";
        for (int i = 0; i < CartSize; i++) {
            String pName = ct.getCart().getProducts(i).getProductName();
            int pPrice = ct.getCart().getProducts(i).getProductPrice();
            String pDisc = ct.getCart().getProducts(i).getProductDesc();
            show += "Product Name:" + pName + " " + "Price : " + pPrice + "" + "Discription : " + pDisc + "" + "-----------------------------------";
        }
        showCartContent.setText(show);
    }
}
