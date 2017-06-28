package io.btrshop.purchases;

import java.util.List;

import javax.inject.Inject;

import io.btrshop.data.source.api.ProductsService;
import io.btrshop.detailsproduct.domain.model.Product;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by martin on 26/06/2017.
 */

public class PurchasesPresenter implements PurchasesContract.Presenter{

    PurchasesContract.View mPurchasesView;
    public Retrofit retrofit;

    @Inject
    public PurchasesPresenter(Retrofit retrofit, PurchasesContract.View mPView){
        this.retrofit = retrofit;
        mPurchasesView = checkNotNull(mPView, "puchasesView cannot be null!");
    }

    @Override
    public void sendPurchases(){
        mPurchasesView.sendPurchases();
    }

    @Override
    public void postPurchases(String ean, List<String> listEansProducts) {
        checkNotNull(ean, "ean cannot be null!");
        checkNotNull(listEansProducts, "listProducts cannot be null");

        retrofit.create(ProductsService.class).postRecommendation(ean, listEansProducts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Product>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPurchasesView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Product product) {
                    }

                });
    }

    @Override
    public void start() {

    }
}
