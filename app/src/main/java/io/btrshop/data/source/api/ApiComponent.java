package io.btrshop.data.source.api;

import javax.inject.Singleton;

import dagger.Component;
import io.btrshop.ApplicationModule;
import retrofit2.Retrofit;

/**
 * Created by charlie on 24/11/16.
 */

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApiComponent {
    // downstream components need these exposed with the return type
    // method name does not really matter
    Retrofit retrofit();
}