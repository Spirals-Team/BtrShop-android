package fr.lille1.univ.android.architecture.btrshop.products;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private TextView name;
    private TextView ean;
    private TextView description;
    private TextView category;
    private TextView poids;

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
    public void showProduct(Product product){
        Log.d("PROD_FRAG", "Affiche product");
        // Set product field
        name = (TextView) getActivity().findViewById(R.id.name);
        name.setVisibility(View.VISIBLE);
        ean = (TextView) getActivity().findViewById(R.id.ean);
        ean.setVisibility(View.VISIBLE);
        description = (TextView) getActivity().findViewById(R.id.description);
        description.setVisibility(View.VISIBLE);
        category = (TextView) getActivity().findViewById(R.id.category);
        category.setVisibility(View.VISIBLE);
        poids = (TextView) getActivity().findViewById(R.id.poids);
        poids.setVisibility(View.VISIBLE);
        if (product != null){
            name.setText(product.getName());
            description.setText(product.getDescription());
            category.setText(product.getCategory());
            ean.setText(product.getEan());
            poids.setText(product.getPoids());
        }
    }

    @Override
    public void showError() {
        new AlertDialog.Builder(getContext())
                .setTitle("No Product !")
                .setMessage("There is no product with this ean ! ")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void setPresenter(ProductsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }



}
