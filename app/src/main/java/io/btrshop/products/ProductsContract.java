package io.btrshop.products;

import io.btrshop.BasePresenter;
import io.btrshop.BaseView;
import io.btrshop.detailsproduct.domain.model.Product;

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

        void getProduct(String ean);

    }
}
