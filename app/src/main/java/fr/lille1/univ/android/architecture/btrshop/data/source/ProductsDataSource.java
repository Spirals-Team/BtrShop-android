package fr.lille1.univ.android.architecture.btrshop.data.source;

import android.support.annotation.NonNull;

import fr.lille1.univ.android.architecture.btrshop.products.domain.model.Product;

/**
 * Created by charlie on 28/10/16.
 *
 * Interface : Retrive the data
 */
public interface ProductsDataSource {

    Product getProduct(@NonNull String ean);

}
