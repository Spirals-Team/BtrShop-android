package fr.lille1.univ.android.architecture.btrshop.data.source;

/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.support.annotation.NonNull;

import fr.lille1.univ.android.architecture.btrshop.products.domain.model.Product;
import fr.lille1.univ.android.architecture.btrshop.data.source.api.ApiService;
import fr.lille1.univ.android.architecture.btrshop.data.source.api.ProductsApiRestInterface;

import java.io.IOException;

import retrofit2.Call;

/**
 * Concrete implementation to load tasks from the data sources into a cache.
 * <p>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */
public class ProductsRepository implements ProductsDataSource {

    private static ProductsRepository INSTANCE = null;

    // Prevent direct instantiation.
    private ProductsRepository() {

    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @return the {@link ProductsRepository} instance
     */
    public static ProductsRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductsRepository();
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance()} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Product getProduct(@NonNull String ean) {

        ProductsApiRestInterface apiService =
                ApiService.getInstance().create(ProductsApiRestInterface.class);

        Call<Product> call = apiService.getProduct(ean);
        try {
            Product p = call.execute().body();
            if(p != null)
                return p;
            else {
                return null;
            }
        } catch (IOException e) {
            e.getStackTrace();
            return null;
        }

    }

}
