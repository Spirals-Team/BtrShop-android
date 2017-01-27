package io.btrshop;

import android.app.Application;
import android.content.Context;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.EstimoteSDK;

import io.btrshop.data.source.api.ApiComponent;
import io.btrshop.data.source.api.ApiModule;
import io.btrshop.data.source.api.DaggerApiComponent;

/**
 * Created by denis on 18/11/16.
 */

public class BtrShopApplication extends Application {

    private static Context mContext;
    private ApiComponent mApiComponent;
    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mApiComponent = DaggerApiComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule(mContext.getResources().getString(R.string.base_url_api)))
                .build();

        // You can get them by adding your app on https://cloud.estimote.com/#/apps
        EstimoteSDK.initialize(getApplicationContext(), getResources().getString(R.string.app_api), getResources().getString(R.string.app_token));
    }

    public static Context getContext(){
        return mContext;
    }

    public ApiComponent getApiComponent() {
        return mApiComponent;
    }
}
