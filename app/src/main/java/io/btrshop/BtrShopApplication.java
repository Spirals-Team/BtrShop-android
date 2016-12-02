package io.btrshop;

import android.app.Application;
import android.content.Context;

import io.btrshop.data.source.api.ApiComponent;
import io.btrshop.data.source.api.ApiModule;
import io.btrshop.data.source.api.DaggerApiComponent;

/**
 * Created by denis on 18/11/16.
 */

public class BtrShopApplication extends Application {

    private static Context mContext;
    private ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mApiComponent = DaggerApiComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule(mContext.getResources().getString(R.string.base_url_api)))
                .build();
    }

    public static Context getContext(){
        return mContext;
    }

    public ApiComponent getApiComponent() {
        return mApiComponent;
    }
}
