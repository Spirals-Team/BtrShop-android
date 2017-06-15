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

    interface View {

        void showScan();

        void showProduct(Product product, boolean scanned);

        void showError(String message);

        void showRecommendations(List<Product> listProduct);

        void showNoRecommendation();
    }

    interface Presenter{

        void presentProduct(Product product);

        void scanProduct();

        void postProduct(String ean, List<BeaconJson> listBeacon);

        void getRecommendations(List<BeaconJson> listBeacon);

    }
}
