package io.btrshop.products;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.estimote.sdk.SystemRequirementsChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.btrshop.BtrShopApplication;
import io.btrshop.R;
import io.btrshop.achats.AchatsActivity;
import io.btrshop.detailsproduct.DetailsProductActivity;
import io.btrshop.detailsproduct.domain.model.Product;
import io.btrshop.products.domain.adapter.ProductAdapterRecycler;
import io.btrshop.scanner.ScannerActivity;
import io.btrshop.util.EspressoIdlingResource;


public class ProductsActivity extends AppCompatActivity implements ProductsContract.View {

    // Constants
    private final static int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    private final static int REQUEST_PERMISSION_PHONE_STATE = 1;
    protected final static String TAG = "ProductsFragment";

    ProductsBeacon beacons;
    Timer timer;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Présenter
    @Inject
    ProductsPresenter mProductsPresenter;

    // UI
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swiperecycler;
    @BindView(R.id.no_recommandation)
    LinearLayout noRecommandationView;
    @BindView(R.id.recycler_product)
    RecyclerView mRecyclerView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.fab_scan_article)
    FloatingActionButton fab;
    static MaterialDialog dialog;



    private int targetSdkVersion;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articles_act);

        // Targer Version
        try {
            final PackageInfo info = getApplicationContext().getPackageManager().getPackageInfo(
                    getApplicationContext().getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // Injection
        ButterKnife.bind(this);
        DaggerProductsComponent.builder()
                .apiComponent(((BtrShopApplication) getApplicationContext()).getApiComponent())
                .productsModule(new ProductsModule(this))
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

        // Floaction action button
        fab.setImageResource(R.mipmap.ic_barcode);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductsPresenter.scanProduct();
            }
        });

        // Permissions and bluetooth
        verifyBluetooth();
        checkAndRequestPermissions();

        // Beacons
        beacons = new ProductsBeacon(this);
        beacons.scanBeacon();

        // Recycler View
        // Définir le recycler view et le layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        swiperecycler.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiperecycler.setRefreshing(true);
                mProductsPresenter.getRecommandation(beacons.getListBeacons());
            }
        });

        // Presenter check recommandation
        //showRecommandation(Product.getProductsItems());
        showNoRecommandation();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        beacons.connect();

        timer = new Timer();
        TimerRecommendation mt = new TimerRecommendation();
        timer.schedule(mt,0,20000);
    }

    @Override
    protected void onPause() {
        beacons.stop();
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkAndRequestPermissions() {
        /* Checking for permissions */
        List<String> permissionsNeeded = new ArrayList<>();
        final List<String> permissionsList = new ArrayList<>();
        if (!selfPermissionGranted(Manifest.permission.CAMERA)) {
            permissionsNeeded.add("Camera");
            permissionsList.add(Manifest.permission.CAMERA);
        }
        if (!selfPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            permissionsNeeded.add("Access Location");
            permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        /* Asking for permissions */
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }
    }

    public boolean selfPermissionGranted(String permission) {
        // For Android < Android M, self permissions are always granted.
        boolean result = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                // targetSdkVersion >= Android M, we can
                // use Context#checkSelfPermission
                result = getApplicationContext().checkSelfPermission(permission)
                        == PackageManager.PERMISSION_GRANTED;
            } else {
                // targetSdkVersion < Android M, we have to use PermissionChecker
                result = PermissionChecker.checkSelfPermission(getApplicationContext(), permission)
                        == PermissionChecker.PERMISSION_GRANTED;
            }
        }

        return result;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.list_navigation_menu_item:
                                break;
                            case R.id.list_navigation_menu_products:
                                //Intent intent = new Intent(ProductsActivity.this, AchatsActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                Intent intent = new Intent(ProductsActivity.this, AchatsActivity.class);
                                intent.putExtra("from","ProductsActivity");
                                startActivity(intent);
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
                        .title(getResources().getString(R.string.retrieve_product))
                        .content(getResources().getString(R.string.wait))
                        .progress(true, 0)
                        .show();
                mProductsPresenter.postProduct(result, beacons.getListBeacons());
            }
        }
    }

    // VIEWS

    @Override
    public void showScan() {
        Intent intent = new Intent(this, ScannerActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void showProduct(final Product product, final boolean scanned) {
        if(dialog != null)
            dialog.dismiss();
        Intent detailProductIntent = new Intent(this, DetailsProductActivity.class);
        detailProductIntent.putExtra("product", product);
        detailProductIntent.putExtra("scanned",scanned);
        startActivity(detailProductIntent);
    }

    @Override
    public void showError(String message) {

        dialog.dismiss();
        new MaterialDialog.Builder(this)
                .title("Error !")
                .content(message)
                .positiveText("Ok")
                .show();
    }

    @Override
    public void showRecommandation(List<Product> listProduct) {

        mRecyclerView.setVisibility(View.VISIBLE);
        noRecommandationView.setVisibility(View.GONE);
        // Adapter pour la liste d'items
        mAdapter = new ProductAdapterRecycler(listProduct, getApplicationContext(), mProductsPresenter);
        mRecyclerView.setAdapter(mAdapter);
        swiperecycler.setRefreshing(false);
    }

    @Override
    public void showNoRecommandation() {
        mRecyclerView.setVisibility(View.GONE);
        noRecommandationView.setVisibility(View.VISIBLE);
        swiperecycler.setRefreshing(false);
    }

    // MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_products, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_reload:
                mProductsPresenter.getRecommandation(beacons.getListBeacons());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void verifyBluetooth() {
        try {
            boolean bluetoothError = false;

            if (Build.VERSION.SDK_INT < 18) {
                throw new RuntimeException(getResources().getString(R.string.problem_with_bluetooth));
            }
            if (!getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
                throw new RuntimeException(getResources().getString(R.string.problem_with_bluetooth));
            } else {
                if (!((BluetoothManager) getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter().isEnabled()) {
                    bluetoothError = true;
                }
            }
            if (bluetoothError) {
                dialog = new MaterialDialog.Builder(ProductsActivity.this)
                        .title(getResources().getString(R.string.bluetooth_not_enable))
                        .content(getResources().getString(R.string.bluetooth_not_enable_content))
                        .positiveText("Ok")
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ActivityCompat.finishAffinity(ProductsActivity.this);
                                System.exit(0);
                            }
                        })
                        .show();
            }
        } catch (RuntimeException e) {
            dialog = new MaterialDialog.Builder(ProductsActivity.this)
                    .title(getResources().getString(R.string.bluetooth_not_enable))
                    .content(getResources().getString(R.string.bluetooth_not_enable_content))
                    .positiveText("Ok")
                    .onAny(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            ActivityCompat.finishAffinity(ProductsActivity.this);
                            System.exit(0);
                        }
                    })
                    .show();
        }

    }

    class TimerRecommendation extends TimerTask {
        public void run(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProductsPresenter.getRecommandation(beacons.getListBeacons());
                }
            });
        }
    }
}
