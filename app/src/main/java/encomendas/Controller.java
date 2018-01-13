package encomendas;

import java.util.ArrayList;


public class Controller {
    private ArrayList<ModelProducts> myproducts = new ArrayList<>();
    private ModelCart myCart = new ModelCart();

    public Controller() {

    }

    public ModelProducts getProducts(int pPosition) {
        return myproducts.get(pPosition);
    }

    public void setProducts(ModelProducts products) {
        myproducts.add(products);
    }

    public ModelCart getCart() {
        return myCart;
    }

    public int getProductArraylistsize() {
        return myproducts.size();
    }
}
