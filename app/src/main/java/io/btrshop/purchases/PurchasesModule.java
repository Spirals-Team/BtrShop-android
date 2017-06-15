package io.btrshop.purchases;

import dagger.Module;
import dagger.Provides;
import io.btrshop.util.CustomScope;

/**
 * Created by martin on 19/05/2017.
 */

@Module
public class PurchasesModule {
    private final PurchasesContract.View mView;


    public PurchasesModule(PurchasesContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    PurchasesContract.View providesPurchasesContractView() {
        return mView;
    }
}
