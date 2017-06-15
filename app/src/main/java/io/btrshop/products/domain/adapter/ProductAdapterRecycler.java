package io.btrshop.products.domain.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import io.btrshop.R;
import io.btrshop.detailsproduct.domain.model.Product;
import io.btrshop.products.ProductsPresenter;

/**
 * Created by charlie on 20/01/17.
 */


public class ProductAdapterRecycler extends RecyclerView.Adapter<ProductAdapterRecycler.MyViewHolder> {

    private List<Product> itemsList;
    private Context context;
    private ProductsPresenter presenter;
    private Bitmap bmp;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView title;
        public TextView description;
        public TextView price;
        public ImageView image ;
        public Product product;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_product);
            description = (TextView) view.findViewById(R.id.description_product);
            price = (TextView) view.findViewById(R.id.price_product);
            image = (ImageView) view.findViewById(R.id.img_product);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("ADAPTER", "CLICK");
            presenter.presentProduct(product);
        }
    }

    public ProductAdapterRecycler(List<Product> itemsList, Context ctx, ProductsPresenter presenter) {
        this.itemsList = itemsList;
        this.context = ctx;
        this.presenter = presenter;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Product product = itemsList.get(position);
        holder.product = product;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    InputStream in = new URL(holder.product.getLogo()).openStream();
                    bmp = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    // log error
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (bmp != null)
                    holder.image.setImageBitmap(bmp);
                holder.image.setVisibility(View.VISIBLE);
            }

        }.execute();
        holder.title.setText(String.valueOf(product.getName()));
        holder.description.setText(String.valueOf(product.getDescription()));
        holder.price.setText(String.valueOf(product.getOffers()[0].getPrice()) + " " + String.valueOf(product.getOffers()[0].getPriceCurrency()));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

}