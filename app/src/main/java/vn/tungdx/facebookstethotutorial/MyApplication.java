package vn.tungdx.facebookstethotutorial;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

/**
 * Created by BoChip on 3/20/2016.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        initPicasso();
    }

    private void initPicasso() {
        OkHttpClient picassoClient = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();
        Picasso picasso = new Picasso.Builder(this).downloader(new OkHttp3Downloader(picassoClient)).build();
        Picasso.setSingletonInstance(picasso);
    }
}
