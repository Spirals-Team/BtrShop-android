package io.btrshop.detailsproduct;

import dagger.Module;
import dagger.Provides;
import io.btrshop.util.CustomScope;

/**
 * Created by charlie on 24/11/16.
 */

@Module
public class DetailsProductModule {
    private final DetailsProductContract.View mView;


    public DetailsProductModule(DetailsProductContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    DetailsProductContract.View providesDetailsProductContractView() {
        return mView;
    }
}
