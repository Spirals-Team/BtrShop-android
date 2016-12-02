package io.btrshop.products;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import javax.inject.Inject;

import io.btrshop.BtrShopApplication;
import io.btrshop.R;

import io.btrshop.detailsproduct.DetailsProductActivity;
import io.btrshop.detailsproduct.domain.model.Product;
import io.btrshop.scanner.ScannerActivity;
import io.btrshop.util.ActivityUtils;
import io.btrshop.util.EspressoIdlingResource;

import static com.google.common.base.Preconditions.checkNotNull;


public class ProductsActivity extends AppCompatActivity implements ProductsContract.View{

    private DrawerLayout mDrawerLayout;
    static MaterialDialog dialog;

    @Inject
    ProductsPresenter mProductsPresenter;
    private static final int ZXING_CAMERA_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articles_act);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Permission
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);


        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        FloatingActionButton fab =
                (FloatingActionButton) findViewById(R.id.fab_scan_article);
        fab.setImageResource(R.mipmap.ic_barcode);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductsPresenter.scanProduct();
            }
        });


        DaggerProductsComponent.builder()
                .apiComponent(((BtrShopApplication) getApplicationContext()).getApiComponent())
                .productsModule(new ProductsModule(this))
                .build().inject(this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.list_navigation_menu_item:
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                Log.d("SCAN", result);
                dialog = new MaterialDialog.Builder(this)
                        .title("Récupération produit")
                        .content("Veuillez patientez ...")
                        .progress(true, 0)
                        .show();
                mProductsPresenter.getProduct(result);
            }
        }
    }

    @Override
    public void showScan() {
        Intent intent = new Intent(this, ScannerActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void showProduct(final Product product){
        dialog.dismiss();
        Intent detailProductIntent = new Intent(this, DetailsProductActivity.class);
        detailProductIntent.putExtra("product", product);
        startActivity(detailProductIntent);
    }

    @Override
    public void showError() {

        dialog.dismiss();
        new MaterialDialog.Builder(this)
                .title("No Product !")
                .content("There is no product with this ean ! ")
                .positiveText("Ok")
                .show();
    }

}
