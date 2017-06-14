package io.btrshop.detailsproduct;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.btrshop.R;
import io.btrshop.achats.AchatsActivity;
import io.btrshop.detailsproduct.domain.model.Product;

public class DetailsProductActivity extends AppCompatActivity implements DetailsProductContract.View{

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

    @BindView(R.id.add_to_basket) ImageButton basket;

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
    @BindView(R.id.products_fragment_logo)
    ImageView logo;
    private Bitmap bmp;

    @Inject
    DetailsProductPresenter mPresenter;

    boolean achats = false;


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

        ButterKnife.bind(this);

        DaggerDetailsProductComponent.builder()
                .detailsProductModule(new DetailsProductModule(this))
                .build().inject(this);

        Intent i = getIntent();
        achats = i.getBooleanExtra("scanned",false);

        // Retrieve product
        Product p = (Product) getIntent().getSerializableExtra("product");
        mPresenter.showDetailedProduct(p);

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

    @Override
    public void showProduct(final Product product) {
        if (product != null){

            show (brand, layoutBrand, product.getBrand());
            show (category, layoutCategory, product.getCategory());
            show (color, layoutColor, product.getColor());
            show (description, null, product.getDescription());

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
            show (dimensions, layoutDimensions, hwd);

            show (ean, layoutEan, product.getEan());
            if(product.getOffers() != null) {
                if (product.getOffers().length >= 1 && product.getOffers()[0] != null) {
                    offers.setText(product.getOffers()[0].toString());
                    layoutOffers.setVisibility(View.VISIBLE);
                }
            }
            show (model, layoutModel, product.getModel());
            show (name, null, product.getName());
            if(product.getWeight() != null)
                show (weight, layoutWeight, product.getWeight().toString());

            if(achats) basket.setVisibility(View.VISIBLE);
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

    private void show (TextView view, LinearLayout layout, String value){
        if (value != null && !value.trim().isEmpty() ){
            view.setText(value);
            if (layout != null) {
                layout.setVisibility(View.VISIBLE);
            }
        }
    }

    public void onClickAddToBasket (View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.quantity_product);
        final NumberPicker numberPicker = new NumberPicker(this);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(100);
        numberPicker.setWrapSelectorWheel(false);
        builder.setView(numberPicker);

        DialogInterface.OnClickListener onClickOK = new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which){
                int nbExemplaires = numberPicker.getValue();
                Product p = (Product) getIntent().getSerializableExtra("product");
                p.setQuantity(nbExemplaires);
                Intent intent = new Intent(DetailsProductActivity.this,AchatsActivity.class);
                intent.putExtra("product",p);
                intent.putExtra("from","DetailsProductActivity");
                startActivity(intent);
                finish();
            }
        };

        builder.setPositiveButton(R.string.ok_button,onClickOK);
        builder.setNegativeButton(R.string.cancel_button,null);
        builder.show();
    }
}
