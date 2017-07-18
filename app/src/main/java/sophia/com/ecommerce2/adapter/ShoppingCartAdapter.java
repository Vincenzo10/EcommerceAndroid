package sophia.com.ecommerce2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import sophia.com.ecommerce2.R;
import sophia.com.ecommerce2.ShoppingCart;
import sophia.com.ecommerce2.data.Item;

/**
 * Created by archimede on 13/07/17.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
    private Context context;
    private OnAdapterItemClickListener listener;
    private NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.ITALY);

    public ShoppingCartAdapter(Context context) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_row_adapter,parent,false);
        TextView titleView = (TextView) v.findViewById(R.id.title);
        TextView priceView = (TextView) v.findViewById(R.id.price);
        ImageView imageView = (ImageView) v.findViewById(R.id.image_shopping);
        ViewHolder vh = new ViewHolder(v,titleView,priceView,imageView);

        return vh;
    }

    @Override
    public void onBindViewHolder(ShoppingCartAdapter.ViewHolder holder, int position) {
        holder.title.setText(ShoppingCart.getInstance().getCart().get(position).getName());
        holder.price.setText(nf.format(ShoppingCart.getInstance().getCart().get(position).getPrice()));
        //setimage
        try{
            Picasso.with(context).load(ShoppingCart.getInstance().getCart().get(position).getPhotoAtIndex(0)).into(holder.imageView);
        }catch (ArrayIndexOutOfBoundsException ex) {

        }
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return ShoppingCart.getInstance().getCart().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView price;
        public ImageView imageView;

        public ViewHolder(View itemView, TextView title, TextView price, ImageView imageView) {
            super(itemView);
            this.title = title;
            this.price = price;
            this.imageView = imageView;

        }
    }
}
