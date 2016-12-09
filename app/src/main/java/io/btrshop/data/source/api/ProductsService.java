package io.btrshop.data.source.api;

import java.util.List;

import io.btrshop.detailsproduct.domain.model.Product;
import io.btrshop.products.domain.model.BeaconObject;
import io.btrshop.products.domain.model.Position;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
     * Envoie de la position de l'article et recupération de celui ci
     *
     * @param ean
     * @param position
     * @return
     */
    @POST("products/{ean}")
    Observable<Product> postProduct(@Path("ean") String ean,
                                    @Body Position position);

    @GET("beacons/")
    Observable<List<BeaconObject>> getBeacons();



}
