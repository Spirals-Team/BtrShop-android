package io.btrshop.purchases;

import java.util.List;

import io.btrshop.BasePresenter;
import io.btrshop.BaseView;
import io.btrshop.detailsproduct.domain.model.Product;

/**
 * Created by martin on 19/05/2017.
 */

public class PurchasesContract {
    interface View extends BaseView<Presenter> {
        void showError(String message);

        void sendPurchases();

        void addAssociatedProducts(List<Product> products);
    }

    interface Presenter extends BasePresenter {

        void sendPurchases();

        void postPurchases(String ean, List<String> listEansProducts);

        void getAssociatedProducts(List<Product> listProducts);
    }
}
