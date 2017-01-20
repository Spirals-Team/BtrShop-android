package io.btrshop.products;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.btrshop.data.source.api.ProductsService;
import io.btrshop.detailsproduct.domain.model.Product;
import io.btrshop.products.domain.model.BeaconJson;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

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
    public void presentProduct(Product product) {
        if(product != null)
            mProductsView.showProduct(product);
        else
            mProductsView.showError("This product is null!");
    }

    @Override

    public void scanProduct() {
        mProductsView.showScan();
    }

    @Override
    public void postProduct(String ean, List<BeaconJson> listBeacon) {

        checkNotNull(ean, "ean cannot be null!");


        if(listBeacon != null) {

            retrofit.create(ProductsService.class).postProduct(ean, listBeacon)
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
    public void getRecommandation(List<BeaconJson> listBeacon) {

        if(listBeacon != null){
            List<String> listUUID = new ArrayList<>();
            for (BeaconJson beacon : listBeacon ){
                listUUID.add(beacon.getUuid());
            }

            retrofit.create(ProductsService.class).getRecommandation(listUUID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(new Observer<List<Product>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            mProductsView.showNoRecommandation();
                        }
                        @Override
                        public void onNext(List<Product> products) {
                            if(products.isEmpty())
                                mProductsView.showNoRecommandation();
                            else
                                mProductsView.showRecommandation(products);
                        }
                    });
        }else {
            mProductsView.showNoRecommandation();
        }

    }

}
