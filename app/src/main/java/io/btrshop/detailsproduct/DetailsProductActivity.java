package io.btrshop.detailsproduct;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import io.btrshop.R;
import io.btrshop.data.source.Injection;
import io.btrshop.products.ProductsPresenter;
import io.btrshop.util.ActivityUtils;

public class DetailsProductActivity extends AppCompatActivity {

    private DetailsProductPresenter mDetailPresenter;


    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.details_product_act);
        setupToolbar();


        DetailsProductFragment detailsProductFragment =
                (DetailsProductFragment) getSupportFragmentManager().findFragmentById(R.id.content_details_product);

        if (detailsProductFragment == null) {
            // Create the fragment
            detailsProductFragment = DetailsProductFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), detailsProductFragment, R.id.content_details_product);
        }

        // Create the presenter
        mDetailPresenter = new DetailsProductPresenter(
        Injection.provideUseCaseHandler(),
            detailsProductFragment
        );


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
