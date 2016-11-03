package fr.lille1.univ.android.architecture.btrshop.products;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.InputStream;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.lille1.univ.android.architecture.btrshop.R;
import fr.lille1.univ.android.architecture.btrshop.products.domain.model.Product;
import fr.lille1.univ.android.architecture.btrshop.scanner.ScannerActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by charlie on 20/10/16.
 */

public class ProductsFragment extends Fragment implements ProductsContract.View {

    private static final int ZXING_CAMERA_PERMISSION = 1;

    private ProductsContract.Presenter mPresenter;

    //handles the progressbar
    public ProgressBar mProgressBar;

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

    public ProductsFragment() {
        // Requires empty public constructor
    }

    public static ProductsFragment newInstance() {
        return new ProductsFragment();
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
        View root = inflater.inflate(R.layout.articles_frag, container, false);
        ButterKnife.bind(this,root);

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);

        // Set up floating action button
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_scan_article);

        fab.setImageResource(R.mipmap.ic_barcode);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.scanProduct();
            }
        });

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void showScan() {

        Intent intent = new Intent(getContext(), ScannerActivity.class);
        getActivity().startActivityForResult(intent, 1);


    }

    @Override
    public void showProduct(final Product product){

        ProductsActivity.dialog.dismiss();

        Log.d("PROD_FRAG", "Affiche product");
        // Set product field


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
    public void showError() {

        ProductsActivity.dialog.dismiss();

        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title("No Product !")
                .content("There is no product with this ean ! ")
                .positiveText("Ok")
                .show();

        /* new AlertDialog.Builder(getContext())
                .setTitle("No Product !")
                .setMessage()
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        name = (TextView) getActivity().findViewById(R.id.name);
                        name.setVisibility(View.INVISIBLE);
                        ean = (TextView) getActivity().findViewById(R.id.ean);
                        ean.setVisibility(View.INVISIBLE);
                        description = (TextView) getActivity().findViewById(R.id.description);
                        description.setVisibility(View.INVISIBLE);
                        category = (TextView) getActivity().findViewById(R.id.category);
                        category.setVisibility(View.INVISIBLE);
                        poids = (TextView) getActivity().findViewById(R.id.poids);
                        poids.setVisibility(View.INVISIBLE);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show(); */
    }

    @Override
    public void setPresenter(ProductsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }



}
