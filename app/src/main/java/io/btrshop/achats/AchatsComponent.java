package io.btrshop.achats;

import dagger.Component;
import io.btrshop.data.source.api.ApiComponent;
import io.btrshop.util.CustomScope;

/**
 * Created by martin on 19/05/2017.
 */

@CustomScope
@Component(dependencies = ApiComponent.class, modules = AchatsModule.class)
public interface AchatsComponent {

    void inject(AchatsActivity activity);

}