package sophia.com.ecommerce2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sophia.com.ecommerce2.data.Item;
import sophia.com.ecommerce2.network.EcommerceService;

public class ProductViewActivity extends AppCompatActivity {
    private Item itemSelected;
    private EcommerceOpenHelper mDB;
    private NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.ITALIAN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        mDB = new EcommerceOpenHelper(this);

        int id =  getIntent().getIntExtra("itemidselected",-1);

        itemSelected = mDB.queryItem(id);

        TextView title = (TextView)findViewById(R.id.title_item);
        TextView description = (TextView)findViewById(R.id.description_item);
        TextView price = (TextView)findViewById(R.id.price);
        ImageView imagePath = (ImageView)findViewById(R.id.image_item);


        title.setText(itemSelected.getName());
        description.setText(itemSelected.getDescription());
        price.setText(nf.format(itemSelected.getPrice()));
        try{
            Picasso.with(this).load(itemSelected.getPhotoAtIndex(0)).into(imagePath);
        }catch (ArrayIndexOutOfBoundsException ex) {

        }
    }

    public class ItemTask extends AsyncTask<Void,Void,Item>{
        @Override
        protected Item doInBackground(Void... params){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://proud-dawn-7260.getsandbox.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            EcommerceService service = retrofit.create(EcommerceService.class);
            Call<Item> listCall = service.getItemUrl(7);

            try {
                Response<Item> listResponse = listCall.execute();
                if (listResponse.isSuccessful()){
                    return listResponse.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Item item) {
            if (item==null){return;}
            TextView title = (TextView)findViewById(R.id.title_item);
            TextView description = (TextView)findViewById(R.id.description_item);
            TextView price = (TextView)findViewById(R.id.price);
            ImageView imagePath = (ImageView)findViewById(R.id.image_item);


            title.setText(item.getName());
            description.setText(item.getDescription());
            price.setText(nf.format(item.getPrice()));
            Picasso.with(ProductViewActivity.this).load(item.getPhotoItem()).into(imagePath);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.show_cart:

                Intent i = new Intent(this, ShoppingCartActivity.class);
                startActivity(i);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    *
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://proud-dawn-7260.getsandbox.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EcommerceService service = retrofit.create(EcommerceService.class);
        Call<Item> listCall = service.getItemUrl(7);

        try {
            Response<Item> listResponse = listCall.execute();
            if (listResponse.isSuccessful()){
                return listResponse.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    * */

}
