package sophia.com.ecommerce2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import sophia.com.ecommerce2.R;
import sophia.com.ecommerce2.data.Item;

/**
 * Created by archimede on 14/06/17.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>  {
    private List<Item> mDataSet;
    private Context context;
    private OnAdapterItemClickListener listener;
    private NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.ITALY);

    public ProductListAdapter(Context context, List<Item> mDataSet) {
        this.context = context;
        this.mDataSet = mDataSet;
        this.listener = (OnAdapterItemClickListener)context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_row_adapter, parent, false);
        TextView nameView = (TextView) v.findViewById(R.id.title_item);
        TextView descriptionView = (TextView) v.findViewById(R.id.description_item);
        TextView priceView = (TextView) v.findViewById(R.id.price);
        ImageView imageView = (ImageView) v.findViewById(R.id.image_item);
        ImageButton addToCart = (ImageButton) v.findViewById(R.id.addToCart);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OnClickListner item", v.getTag().toString());

                if(listener != null){
                    listener.OnItemClick((int)v.getTag());

                }
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OnClickListner item", v.getTag().toString());

                if(listener != null){
                    listener.OnItemAddToCart((int)v.getTag());
                }
            }
        });

        ViewHolder vh = new ViewHolder(v, nameView, descriptionView, priceView, imageView);
        vh.addToCart = addToCart;
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mDataSet.get(position).getName());
        holder.description.setText(mDataSet.get(position).getDescription());
        holder.price.setText(nf.format(mDataSet.get(position).getPrice()));
        //setimage
        try{
            Picasso.with(context).load(mDataSet.get(position).getPhotoAtIndex(0)).into(holder.imageView);
        }catch (ArrayIndexOutOfBoundsException ex) {

        }
        holder.itemView.setTag(position);
        holder.addToCart.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView description;
        public TextView price;
        public ImageView imageView;
        public ImageButton addToCart;

        public ViewHolder(View v, TextView name, TextView description, TextView price, ImageView imageView) {
            super(v);
            this.name = name;
            this.description = description;
            this.price = price;
            this.imageView = imageView;

        }


    }
}
