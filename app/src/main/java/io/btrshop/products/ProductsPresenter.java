package io.btrshop.products;

import javax.inject.Inject;

import io.btrshop.data.source.api.ProductsService;
import io.btrshop.detailsproduct.domain.model.Product;

import retrofit2.Retrofit;
import rx.Observer;
import rx.*;
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
    public void scanProduct() {
        mProductsView.showScan();
    }

    @Override
    public void getProduct(String ean) {

        checkNotNull(ean, "ean cannot be null!");
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
                        mProductsView.showError();
                    }

                    @Override
                    public void onNext(Product product) {
                        mProductsView.showProduct(product);
                    }

                });

    }

    @Override
    public void start() {

    }
}
