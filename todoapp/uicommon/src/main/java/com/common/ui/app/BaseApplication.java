package com.common.ui.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by yangfeng on 16-7-29.
 */
public class BaseApplication extends Application {
    // Prevent need in a singleton (global) reference to the application object.
    @NonNull
    public static BaseApplication get(@NonNull Context context) {
        return (BaseApplication) context.getApplicationContext();
    }
}
