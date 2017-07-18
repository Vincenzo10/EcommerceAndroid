package sophia.com.ecommerce2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import sophia.com.ecommerce2.adapter.ProductListAdapter;
import sophia.com.ecommerce2.adapter.ShoppingCartAdapter;
import sophia.com.ecommerce2.data.Item;

/**
 * Created by archimede on 14/07/17.
 */

public class ShoppingCartActivity extends AppCompatActivity {
    private List<Item> itemList;
    private RecyclerView shoppingcartrecyclerview;
    private NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.ITALY);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        itemList = ShoppingCart.getInstance().getCart();

        shoppingcartrecyclerview = (RecyclerView) findViewById(R.id.shopping_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        shoppingcartrecyclerview.setLayoutManager(gridLayoutManager);

        TextView totalPrice = (TextView) findViewById(R.id.total_price);
        shoppingcartrecyclerview.setHasFixedSize(true);
        ShoppingCartAdapter shoppingCartAdapter = new ShoppingCartAdapter(this);
        shoppingcartrecyclerview.setAdapter(shoppingCartAdapter);

        totalPrice.setText(String.valueOf(ShoppingCart.getInstance().getTotalCart()));
    }


}
