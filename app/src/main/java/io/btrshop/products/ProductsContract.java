package io.btrshop.products;

import java.util.List;

import io.btrshop.BasePresenter;
import io.btrshop.BaseView;
import io.btrshop.detailsproduct.domain.model.Product;
import io.btrshop.products.domain.model.BeaconJson;

/**
 * Created by charlie on 20/10/16.
 */

public interface ProductsContract {

    interface View extends BaseView<Presenter> {

        void showScan();

        void showProduct(Product product);

        void showError(String message);
    }

    interface Presenter extends BasePresenter {

        void scanProduct();

        void postProduct(String ean, List<BeaconJson> listBeacon);

    }
}
