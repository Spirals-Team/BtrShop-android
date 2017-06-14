package io.btrshop.achats;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.btrshop.BtrShopApplication;
import io.btrshop.R;
import io.btrshop.detailsproduct.domain.model.Product;
import io.btrshop.products.ProductsActivity;

/**
 * Created by martin on 19/05/2017.
 */

public class AchatsActivity extends AppCompatActivity implements AchatsContract.View,
        AchatsAdapter.AchatsAdapterListener {

    public static ArrayList<Product> listProduct = new ArrayList<>();
    protected static ListView list = null;
    protected static AchatsAdapter adapter = null;
    protected static double total = 0;
    protected static String price_currency = "€";

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.list_navigation_menu_item:
                                Intent intent = new Intent(AchatsActivity.this, ProductsActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intent);
                                break;
                            case R.id.list_navigation_menu_products:
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achats_act);

        setupToolbar();

        // Injection
        ButterKnife.bind(this);
        DaggerAchatsComponent.builder()
                .apiComponent(((BtrShopApplication) getApplicationContext()).getApiComponent())
                .achatsModule(new AchatsModule(this))
                .build().inject(this);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Navigation drawer.
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        TextView tv = (TextView) findViewById(R.id.prixArticles);
        tv.setText(String.valueOf(total));
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter = new AchatsAdapter(this,listProduct);
        adapter.addListener(this);
        list = (ListView) findViewById(R.id.list_articles);
        list.setAdapter(adapter);

        gestionList();
        adapter.notifyDataSetChanged();
    }

    private void gestionList(){
        Intent intent = getIntent();
        if(intent != null){
            String string = intent.getExtras().getString("from");
            if (string.equals("DetailsProductActivity")) {
                Product product = (Product) intent.getSerializableExtra("product");
                getIntent().removeExtra("product");
                if(product != null && product.getQuantity() > 0){
                    int indice = -1;
                    for(Product p : listProduct){
                        if (p.getName().equals(product.getName())) indice = listProduct.indexOf(p);
                        price_currency = p.getOffers()[0].getPriceCurrency();
                    }
                    if(indice >= 0){
                        total = total - listProduct.get(indice).getQuantity() * listProduct.get(indice).getOffers()[0].getPrice();
                        product.setQuantity(product.getQuantity() + listProduct.get(indice).getQuantity());
                        listProduct.set(indice,product);
                        total = total + product.getQuantity() * product.getOffers()[0].getPrice() ;
                    }
                    else{
                        listProduct.add(product);
                        total = total +  product.getQuantity() * product.getOffers()[0].getPrice() ;
                    }

                    adapter.notifyDataSetChanged();
                }
            }
        }

        TextView tv = (TextView) findViewById(R.id.prixArticles);
        BigDecimal bd = new BigDecimal(total);
        bd = bd.setScale(2, BigDecimal.ROUND_UP);
        tv.setText(String.valueOf(bd.toString()) + " " + price_currency);
    }

    @Override
    public void onClickSup(final Product item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        DialogInterface.OnClickListener onClickYes = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listProduct.remove(item);
                total = total - item.getQuantity() * item.getOffers()[0].getPrice();
                BigDecimal bd = new BigDecimal(total);
                bd = bd.setScale(2, BigDecimal.ROUND_UP);
                TextView tv = (TextView) findViewById(R.id.prixArticles);
                tv.setText(String.valueOf(bd.toString()) + " " + price_currency);
                adapter.notifyDataSetChanged();
            }
        };

        builder.setMessage(R.string.delete_article);
        builder.setPositiveButton(R.string.ok_button,onClickYes);
        builder.setNegativeButton(R.string.cancel_button,null);
        builder.show();
    }
}
