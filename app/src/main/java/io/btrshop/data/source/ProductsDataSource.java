package io.btrshop.data.source;

import android.support.annotation.NonNull;

import io.btrshop.detailsproduct.domain.model.Product;

/**
 * Created by charlie on 28/10/16.
 *
 * Interface : Retrive the data
 */
public interface ProductsDataSource {

    Product getProduct(@NonNull String ean);

}
