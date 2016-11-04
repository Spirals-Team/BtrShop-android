package fr.lille1.univ.android.architecture.btrshop.detailsproduct;

import fr.lille1.univ.android.architecture.btrshop.BasePresenter;
import fr.lille1.univ.android.architecture.btrshop.BaseView;
import fr.lille1.univ.android.architecture.btrshop.products.ProductsContract;
import fr.lille1.univ.android.architecture.btrshop.detailsproduct.domain.model.Product;

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
