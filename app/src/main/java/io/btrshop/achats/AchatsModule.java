package io.btrshop.achats;

import dagger.Module;
import dagger.Provides;
import io.btrshop.util.CustomScope;

/**
 * Created by martin on 19/05/2017.
 */

@Module
public class AchatsModule {
    private final AchatsContract.View mView;


    public AchatsModule(AchatsContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    AchatsContract.View providesAchatsContractView() {
        return mView;
    }
}
