package io.btrshop.purchases;

import dagger.Component;
import io.btrshop.data.source.api.ApiComponent;
import io.btrshop.util.CustomScope;

/**
 * Created by martin on 19/05/2017.
 */

@CustomScope
@Component(dependencies = ApiComponent.class, modules = PurchasesModule.class)
public interface PurchasesComponent {

    void inject(PurchasesActivity activity);

}