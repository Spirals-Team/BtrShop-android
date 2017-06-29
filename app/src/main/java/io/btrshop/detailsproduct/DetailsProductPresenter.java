package io.btrshop.detailsproduct;

import javax.inject.Inject;

import io.btrshop.detailsproduct.domain.model.Product;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by charlie on 04/11/16.
 */

public class DetailsProductPresenter implements DetailsProductContract.Presenter  {

    DetailsProductContract.View mDetailsProductView;


    @Inject
    public DetailsProductPresenter( DetailsProductContract.View mDPView) {
        mDetailsProductView = checkNotNull(mDPView, "productsView cannot be null!");
    }


    @Override
    public void start() {

    }

    @Override
    public void showDetailedProduct(Product product) {
        mDetailsProductView.showProduct(product);
    }
}
