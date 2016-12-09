package io.btrshop.products;

import java.util.List;
import java.util.Map;

import io.btrshop.BasePresenter;
import io.btrshop.BaseView;
import io.btrshop.detailsproduct.domain.model.Product;
import io.btrshop.products.domain.model.BeaconObject;

/**
 * Created by charlie on 20/10/16.
 */

public interface ProductsContract {

    interface View extends BaseView<Presenter> {

        void showScan();

        void showProduct(Product product);

        void showError();

    }

    interface Presenter extends BasePresenter {

        void scanProduct();

        void getProduct(String ean, List<BeaconObject> listBeacon);

    }
}
