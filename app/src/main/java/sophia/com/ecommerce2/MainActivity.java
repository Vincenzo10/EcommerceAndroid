package sophia.com.ecommerce2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sophia.com.ecommerce2.adapter.CategoryAdapter;
import sophia.com.ecommerce2.adapter.OnAdapterItemClickListener;
import sophia.com.ecommerce2.data.Category;
import sophia.com.ecommerce2.network.EcommerceService;

public class MainActivity extends AppCompatActivity implements OnAdapterItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private RecyclerView categoryRecyclerView;
    private TextView welcomeMessage;

    List<Category> categoryList = new ArrayList<>();

    private SharedPreferences preferences;
    private EcommerceOpenHelper mDB;
    private CategoryTask mTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDB = new EcommerceOpenHelper(this);

        preferences = getSharedPreferences("ecommerce",MODE_PRIVATE);

        TextView welcomeMessage = (TextView)findViewById(R.id.welcome_message);
        boolean b = preferences.getBoolean("firstUser",true);

        if (b){
            welcomeMessage.setVisibility(View.VISIBLE);
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstUser",true);
        editor.apply();

        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.recycler_view),
                "BENVENUTO!!!", Snackbar.LENGTH_LONG);

        mySnackbar.setDuration(3000);
        mySnackbar.show();

        categoryRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager((this));
        categoryRecyclerView.setLayoutManager((mLayoutManager));


        categoryList = mDB.getAllCategory();

        categoryRecyclerView.setHasFixedSize(true);

        Context context;
        CategoryAdapter categoryRecyclerViewAdapter = new CategoryAdapter(this, categoryList);
        categoryRecyclerView.setAdapter(categoryRecyclerViewAdapter);

        mTask = new CategoryTask();
        mTask.execute((Void)null);

    }


    @Override
    public void OnItemClick(int position) {
        Log.d("click", String.valueOf(position));
        String title = categoryList.get(position).getTitle();
        int id = categoryList.get(position).getmId();
        Intent i = new Intent(this, ProductListActivity.class);
        i.putExtra("CategoryId", id);
        startActivity(i);
    }

    @Override
    public void OnItemAddToCart(int position) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        preferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Snackbar snackbar = Snackbar.make(categoryRecyclerView, "property changed" + key, Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    public void buttonOnClick(View view) {
//        SharedPreferences preferences = getSharedPreferences("ecommerce", MODE_PRIVATE);
//        boolean b = preferences.getBoolean("firstUser",true);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean("firstUser", !b);
//        editor.apply();
        Intent i = new Intent(this, ShoppingCartActivity.class);
        startActivity(i);

    }

    public class CategoryTask extends AsyncTask<Void,Void,List<Category>>{

        @Override
        protected List<Category> doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://proud-dawn-7260.getsandbox.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            EcommerceService service = retrofit.create(EcommerceService.class);
            Call<List<Category>> listCall = service.ListCategory();

            try {
                Response<List<Category>> listResponse = listCall.execute();
                if (listResponse.isSuccessful()){
                    return listResponse.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Category> categories) {

            if(categories==null){return;}

            categoryList = categories;

            CategoryAdapter categoryRecyclerViewAdapter = new CategoryAdapter(MainActivity.this, categoryList);
            categoryRecyclerView.setAdapter(categoryRecyclerViewAdapter);

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
}
