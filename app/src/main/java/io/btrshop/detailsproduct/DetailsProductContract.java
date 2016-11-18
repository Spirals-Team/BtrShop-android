package io.btrshop.detailsproduct;

import io.btrshop.BasePresenter;
import io.btrshop.BaseView;
import io.btrshop.products.ProductsContract;
import io.btrshop.detailsproduct.domain.model.Product;

/**
 * Created by charlie on 04/11/16.
 */

public class DetailsProductContract {

    interface View extends BaseView<Presenter> {

        void showProduct(Product product);

    }

    interface Presenter extends BasePresenter {

        void showDetailedProduct(Product product);

    }
}
