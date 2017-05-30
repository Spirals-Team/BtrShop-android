package io.btrshop.products.domain.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView titre ;
        public ImageView image ;
        public Product product;

        public MyViewHolder(View view) {
            super(view);
            titre = (TextView) view.findViewById(R.id.title_product);
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product product = itemsList.get(position);
        holder.product = product;
        //holder.image.set(String.valueOf(item.numero));
        holder.titre.setText(String.valueOf(product.getName()));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

}