package io.btrshop.detailsproduct;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.btrshop.R;
import io.btrshop.products.ProductsContract;
import io.btrshop.products.ProductsFragment;
import io.btrshop.detailsproduct.domain.model.Product;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by charlie on 04/11/16.
 */

public class DetailsProductFragment extends Fragment implements DetailsProductContract.View {

    //handles the progressbar
    public ProgressBar mProgressBar;

    private DetailsProductContract.Presenter mPresenter;

    //handles the differents elements
    @BindView(R.id.products_fragment_brand) TextView brand;
    @BindView(R.id.products_fragment_category) TextView category;
    @BindView(R.id.products_fragment_color) TextView color;
    @BindView(R.id.products_fragment_description) TextView description;
    @BindView(R.id.products_fragment_dimensions) TextView dimensions;
    @BindView(R.id.products_fragment_ean) TextView ean;
    @BindView(R.id.products_fragment_offers) TextView offers;
    @BindView(R.id.products_fragment_model) TextView model;
    @BindView(R.id.products_fragment_name) TextView name;
    @BindView(R.id.products_fragment_weight) TextView weight;

    //handles the differents layout
    @BindView(R.id.products_fragment_brand_layout) LinearLayout layoutBrand;
    @BindView(R.id.products_fragment_category_layout) LinearLayout layoutCategory;
    @BindView(R.id.products_fragment_color_layout) LinearLayout layoutColor;
    @BindView(R.id.products_fragment_dimensions_layout) LinearLayout layoutDimensions;
    @BindView(R.id.products_fragment_ean_layout) LinearLayout layoutEan;
    @BindView(R.id.products_fragment_offers_layout) LinearLayout layoutOffers;
    @BindView(R.id.products_fragment_model_layout) LinearLayout layoutModel;
    @BindView(R.id.products_fragment_weight_layout) LinearLayout layoutWeight;

    //handles the cover image
    @BindView(R.id.products_fragment_logo) ImageView logo;
    private Bitmap bmp;


    public DetailsProductFragment() {
        // Requires empty public constructor
    }

    public static DetailsProductFragment newInstance() {
        return new DetailsProductFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.details_product_frag, container, false);
        ButterKnife.bind(this,root);

        // Retrieve product
        Product p = (Product) getActivity().getIntent().getSerializableExtra("product");
        mPresenter.showDetailedProduct(p);

        setHasOptionsMenu(true);

        return root;
    }


    @Override
    public void showProduct(final Product product) {
        if (product != null){

            if (product.getBrand() != null){
                brand.setText(product.getBrand());
                layoutBrand.setVisibility(View.VISIBLE);
            }
            if (product.getCategory() != null){
                category.setText(product.getCategory());
                layoutCategory.setVisibility(View.VISIBLE);
            }
            if (product.getColor() != null){
                color.setText(product.getColor());
                layoutColor.setVisibility(View.VISIBLE);
            }
            if (product.getDescription() != null){
                description.setText(product.getDescription());
            }
            // height x width x depth
            String hwd = "";
            if (product.getHeight() != null && product.getHeight().getUnitText() != null){
                hwd += "height: " + product.getHeight().toString() + "\n";
            }
            if (product.getWidth() != null && product.getWidth().getUnitText() != null){
                hwd += "width: " + product.getWidth().toString() + "\n";
            }
            if (product.getDepth() != null && product.getDepth().getUnitText() != null){
                hwd += "depth: " + product.getDepth().toString();
            }
            if (product.getHeight() != null || product.getWidth() != null || product.getDepth() != null ){
                dimensions.setText(hwd);
                layoutDimensions.setVisibility(View.VISIBLE);
            }
            if (product.getEan() != null){
                ean.setText(product.getEan());
                layoutEan.setVisibility(View.VISIBLE);
            }
            if (product.getOffers().length >= 1 && product.getOffers()[0] != null){
                offers.setText(product.getOffers()[0].toString());
                layoutOffers.setVisibility(View.VISIBLE);
            }
            if (product.getModel() != null){
                model.setText(product.getModel());
                layoutModel.setVisibility(View.VISIBLE);
            }
            if (product.getName() != null){
                name.setText(product.getName());
            }
            if (product.getModel() != null){
                model.setText(product.getModel());
                layoutModel.setVisibility(View.VISIBLE);
            }
            if (product.getWeight() != null){
                weight.setText(product.getWeight().toString());
                layoutWeight.setVisibility(View.VISIBLE);
            }
        }


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    InputStream in = new URL(product.getLogo()).openStream();
                    bmp = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    // log error
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (bmp != null)
                    logo.setImageBitmap(bmp);
                logo.setVisibility(View.VISIBLE);
            }

        }.execute();


    }

    @Override
    public void setPresenter(DetailsProductContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }
}

