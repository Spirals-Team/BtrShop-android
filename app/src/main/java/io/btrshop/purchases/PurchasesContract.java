package io.btrshop.purchases;

import java.util.List;

import io.btrshop.BasePresenter;
import io.btrshop.BaseView;

/**
 * Created by martin on 19/05/2017.
 */

public class PurchasesContract {
    interface View extends BaseView<Presenter> {
        void showError(String message);

        void sendPurchases();
    }

    interface Presenter extends BasePresenter {

        void sendPurchases();

        void postPurchases(String ean, List<String> listEansProducts);
    }
}
