package com.wd.tech;

import android.app.Application;

import tech.wd.com.common.util.ContextUtil;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //获取context
        ContextUtil.getContext=getApplicationContext();
    }
}
