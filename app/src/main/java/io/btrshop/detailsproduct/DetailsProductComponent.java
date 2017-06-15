package io.btrshop.detailsproduct;

import dagger.Component;
import io.btrshop.util.CustomScope;

/**
 * Created by charlie on 24/11/16.
 */
@CustomScope
@Component(modules = DetailsProductModule.class)
public interface DetailsProductComponent {
    void inject(DetailsProductActivity activity);
}