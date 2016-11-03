package fr.lille1.univ.android.architecture.btrshop.products;

import fr.lille1.univ.android.architecture.btrshop.BasePresenter;
import fr.lille1.univ.android.architecture.btrshop.BaseView;
import fr.lille1.univ.android.architecture.btrshop.products.domain.model.Product;

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
