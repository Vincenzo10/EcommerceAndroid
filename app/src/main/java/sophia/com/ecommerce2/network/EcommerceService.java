package sophia.com.ecommerce2.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sophia.com.ecommerce2.data.Category;
import sophia.com.ecommerce2.data.Item;
import sophia.com.ecommerce2.data.User;
import sophia.com.ecommerce2.data.UserRequest;

/**
 * Created by archimede on 05/07/17.
 */

public interface EcommerceService {

    @Headers("Content-Type: application/json")
    @GET("product")
    Call<List<Item>> listProduct();

    @POST("login")
    Call<User> login(@Body UserRequest user);

    @Headers("Content-Type: application/json")
    @GET("categories")
    Call<List<Category>> ListCategory();

    @Headers("Content-Type: application/json")
    @GET("item/{id}")
    Call<Item> getItemUrl(@Path("id") int itemId);
}
