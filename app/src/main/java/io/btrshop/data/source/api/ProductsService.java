package io.btrshop.data.source.api;

import io.btrshop.detailsproduct.domain.model.Product;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by charlie on 28/10/16.
 *
 * Interface : all the apis for the products.
 *
 */
public interface ProductsService {

    /**
     *  Function that retrieve the call for the product with EAN.
     *
     * @param ean, of the product
     * @return the product
     */
    @GET("products/{ean}")
    Observable<Product> getProduct(@Path("ean")
                             String ean);

}
