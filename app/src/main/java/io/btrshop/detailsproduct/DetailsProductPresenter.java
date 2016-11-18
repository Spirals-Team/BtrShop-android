package io.btrshop.detailsproduct;

import android.support.annotation.NonNull;

import io.btrshop.UseCaseHandler;
import io.btrshop.detailsproduct.domain.model.Product;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by charlie on 04/11/16.
 */

public class DetailsProductPresenter implements DetailsProductContract.Presenter  {

    private DetailsProductContract.View mDetailsProductView;
    private UseCaseHandler mUseCaseHandler;

    public DetailsProductPresenter(@NonNull UseCaseHandler useCaseHandler,
                             @NonNull DetailsProductContract.View mDPView) {
        mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
        mDetailsProductView = checkNotNull(mDPView, "productsView cannot be null!");
        mDetailsProductView.setPresenter(this);

    }


    @Override
    public void start() {

    }

    @Override
    public void showDetailedProduct(Product product) {
        mDetailsProductView.showProduct(product);
    }
}
