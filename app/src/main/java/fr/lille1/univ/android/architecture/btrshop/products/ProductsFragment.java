package fr.lille1.univ.android.architecture.btrshop.products;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.InputStream;
import java.net.URL;

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


    //handles the differents linearlayouts
    private TextView brand;
    private TextView category;
    private TextView color;
    private TextView description;
    private TextView dimensions;
    private TextView ean;
    private TextView offers;
    private TextView model;
    private TextView name;
    private TextView weight;

    //handles the cover image
    private ImageView logo;
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

        brand = (TextView) getActivity().findViewById(R.id.products_fragment_brand);
        category = (TextView) getActivity().findViewById(R.id.products_fragment_category);
        color = (TextView) getActivity().findViewById(R.id.products_fragment_color);
        description = (TextView) getActivity().findViewById(R.id.products_fragment_description);
        dimensions = (TextView) getActivity().findViewById(R.id.products_fragment_dimensions);
        ean = (TextView) getActivity().findViewById(R.id.products_fragment_ean);
        offers = (TextView) getActivity().findViewById(R.id.products_fragment_offers);
        model = (TextView) getActivity().findViewById(R.id.products_fragment_model);
        name = (TextView) getActivity().findViewById(R.id.products_fragment_name);
        weight = (TextView) getActivity().findViewById(R.id.products_fragment_weight);


        //cover image
        logo = (ImageView) getActivity().findViewById(R.id.products_fragment_logo);


        if (product != null){

            if (product.getBrand() != null){
                brand.setText(product.getBrand());
                brand.setVisibility(View.VISIBLE);
            }
            if (product.getCategory() != null){
                category.setText(product.getCategory());
                category.setVisibility(View.VISIBLE);
            }
            if (product.getColor() != null){
                color.setText(product.getColor());
                color.setVisibility(View.VISIBLE);
            }
            if (product.getDescription() != null){
                description.setText(product.getDescription());
                description.setVisibility(View.VISIBLE);
            }
            // height x width x depth
            String hwd = "";
            if (product.getHeight() != null ){
                hwd += "height: " + product.getHeight();
            }
            if (product.getWidth() != null){
                hwd += " width: " + product.getWidth();
            }
            if (product.getDepth() != null){
                hwd += " depth: " + product.getDepth();
            }
            if (product.getHeight() != null || product.getWidth() != null || product.getDepth() != null ){
                dimensions.setText(hwd);
                dimensions.setVisibility(View.VISIBLE);
            }
            if (product.getEan() != null){
                ean.setText(product.getEan());
                ean.setVisibility(View.VISIBLE);
            }
            if (product.getOffers().length >= 1 && product.getOffers()[0] != null){
                    brand.setText(product.getOffers()[0].toString());
                    brand.setVisibility(View.VISIBLE);
            }
            if (product.getModel() != null){
                model.setText(product.getModel());
                model.setVisibility(View.VISIBLE);
            }
            if (product.getName() != null){
                name.setText(product.getName());
                name.setVisibility(View.VISIBLE);
            }
            if (product.getModel() != null){
                model.setText(product.getModel());
                model.setVisibility(View.VISIBLE);
            }
            if (product.getWeight() != null){
                weight.setText(product.getWeight().getValue() + " " + product.getWeight().getUnitText());
                weight.setVisibility(View.VISIBLE);
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
