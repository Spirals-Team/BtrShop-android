package fr.lille1.univ.android.architecture.btrshop.data.source.api;

import fr.lille1.univ.android.architecture.btrshop.products.domain.model.Product;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by charlie on 28/10/16.
 *
 * Interface : all the apis for the products.
 *
 */
public interface ProductsApiRestInterface {

    /**
     *  Function that retrieve the call for the product with EAN.
     *
     * @param ean, of the product
     * @return the product
     */
    @GET("products/{ean}")
    Call<Product> getProduct(@Path("ean")
                             String ean);

}
