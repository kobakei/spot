package io.github.kobakei.spotsample;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by keisuke on 16/04/01.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
