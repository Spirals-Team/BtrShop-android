package fr.lille1.univ.android.architecture.btrshop.products.domain.usecase;

import android.support.annotation.NonNull;

import fr.lille1.univ.android.architecture.btrshop.UseCase;
import fr.lille1.univ.android.architecture.btrshop.products.domain.model.Product;
import fr.lille1.univ.android.architecture.btrshop.data.source.ProductsRepository;


import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by charlie on 23/10/16.
 */
public class GetProduct extends UseCase<GetProduct.RequestValues, GetProduct.ResponseValue> {


    private ProductsRepository mProductsRepository;

    public GetProduct(@NonNull ProductsRepository articlesRepository) {
        mProductsRepository = checkNotNull(articlesRepository, "articlesRepository cannot be null!");
    }


    @Override
    protected void executeUseCase(final GetProduct.RequestValues values) {
        String ean = values.getProductEan();
        Product product = mProductsRepository.getProduct(ean);
        getUseCaseCallback().onSuccess(new ResponseValue(product));

    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final String mProductEan;

        public RequestValues(@NonNull String productEan) {
            mProductEan = checkNotNull(productEan, "productEan cannot be null!");
        }

        public String getProductEan() {
            return mProductEan;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private Product mProduct;

        public ResponseValue(@NonNull Product product) {
            mProduct = product;
        }

        public Product getProduct() {
            return mProduct;
        }
    }
}