package io.btrshop.products;

import dagger.Component;
import io.btrshop.data.source.api.ApiComponent;
import io.btrshop.util.CustomScope;

/**
 * Created by charlie on 24/11/16.
 */

@CustomScope
@Component(dependencies = ApiComponent.class, modules = ProductsModule.class)
public interface ProductsComponent {

    void inject(ProductsActivity activity);

}