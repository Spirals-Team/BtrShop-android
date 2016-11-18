package io.btrshop.products;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.ButterKnife;
import io.btrshop.R;
import io.btrshop.detailsproduct.DetailsProductActivity;
import io.btrshop.detailsproduct.domain.model.Product;
import io.btrshop.scanner.ScannerActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by charlie on 20/10/16.
 */

public class ProductsFragment extends Fragment implements ProductsContract.View {

    private static final int ZXING_CAMERA_PERMISSION = 1;

    private ProductsContract.Presenter mPresenter;

    //handles the progressbar
    public ProgressBar mProgressBar;



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
        Intent detailProductIntent = new Intent(getContext(), DetailsProductActivity.class);
        detailProductIntent.putExtra("product", product);
        startActivity(detailProductIntent);
    }

    @Override
    public void showError() {

        ProductsActivity.dialog.dismiss();

        new MaterialDialog.Builder(getContext())
                .title("No Product !")
                .content("There is no product with this ean ! ")
                .positiveText("Ok")
                .show();

    }

    @Override
    public void setPresenter(ProductsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

}
