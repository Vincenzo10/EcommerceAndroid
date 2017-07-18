package sophia.com.ecommerce2;

import java.util.ArrayList;
import java.util.List;

import sophia.com.ecommerce2.data.Item;

/**
 * Created by archimede on 12/07/17.
 */

public class ShoppingCart {

    private List<Item> cart;

    private static final ShoppingCart ourInstance = new ShoppingCart();

    public static ShoppingCart getInstance() {
        return ourInstance;
    }

    private ShoppingCart() {
        this.cart = new ArrayList<>();
    }

    public List<Item> getCart() {
        return cart;
    }

    public void setCart(List<Item> cart) {
        this.cart = cart;
    }

    public void addProduct(Item item){
        cart.add(item);
    }

    public void removeProduct(Item item){
        cart.remove(item);
    }

    public void emptyCart(){
        cart.clear();
    }

    public double getTotalCart(){
        double totale = 0;

        for (Item item : cart) {
            totale += item.getPrice();
        }
        return totale;
    }

}
