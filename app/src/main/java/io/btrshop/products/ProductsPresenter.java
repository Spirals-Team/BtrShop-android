package io.btrshop.products;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.btrshop.data.source.api.ProductsService;
import io.btrshop.detailsproduct.domain.model.Product;

import io.btrshop.products.domain.model.BeaconObject;
import io.btrshop.products.domain.model.Position;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.btrshop.util.ActivityUtils.calculPosition;

/**
 * Created by charlie on 20/10/16.
 */
public class ProductsPresenter implements ProductsContract.Presenter {


    ProductsContract.View mProductsView;
    public Retrofit retrofit;

    @Inject
    public ProductsPresenter(Retrofit retrofit, ProductsContract.View mView) {
        this.retrofit = retrofit;
        this.mProductsView = mView;
    }

    @Override

    public void scanProduct() {
        mProductsView.showScan();
    }

    @Override
    public void postProduct(String ean, List<BeaconObject> listBeacon) {

        checkNotNull(ean, "ean cannot be null!");


        if(listBeacon == null || !listBeacon.isEmpty()) {

            // Triangulation avec la listBeacon
            Position p = calculPosition(listBeacon);
            Log.d("POSITION", "lat : " + p.getLat() +" / long : " + p.getLng());

            retrofit.create(ProductsService.class).postProduct(ean, p)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(new Observer<Product>() {

                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            mProductsView.showError(e.getMessage());
                        }

                        @Override
                        public void onNext(Product product) {
                            mProductsView.showProduct(product);
                        }

                    });
        }else{
            retrofit.create(ProductsService.class).getProduct(ean)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(new Observer<Product>() {

                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("ERROR", e.getMessage());
                            mProductsView.showError(e.getMessage());
                        }

                        @Override
                        public void onNext(Product product) {
                            mProductsView.showProduct(product);
                        }

                    });
        }
    }

    @Override
    public void getBeacons() {

        retrofit.create(ProductsService.class).getBeacons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<List<BeaconObject>>() {


                    @Override
                    public void onCompleted() {
                        Log.d("SUCCESS", "success get beacons");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ERROR", "fail get beacons"  + e.getMessage());
                    }

                    @Override
                    public void onNext(List<BeaconObject> beaconObjects) {
                        Log.d("NEXT", beaconObjects.size()+"");
                        Map<String, BeaconObject> map = new HashMap<String, BeaconObject>();
                        for (BeaconObject i : beaconObjects)
                            map.put(i.getData().getUuid() ,i);

                        mProductsView.setBeaconsList(map);
                    }
                });

    }

    @Override
    public void start() {

    }
}
