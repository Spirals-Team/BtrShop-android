package io.btrshop.products;

import dagger.Module;
import dagger.Provides;
import io.btrshop.util.CustomScope;

/**
 * Created by charlie on 24/11/16.
 */

@Module
public class ProductsModule {
    private final ProductsContract.View mView;


    public ProductsModule(ProductsContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    ProductsContract.View providesProductsContractView() {
        return mView;
    }
}