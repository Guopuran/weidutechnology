package com.wd.tech;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tech.wd.com.common.base.BaseActivity;

public class MainActivity extends BaseActivity {


    @Override
    protected void initData() {
        Intent intent=new Intent(MainActivity.this,AsdActivity.class);
        startActivity(intent);
    }

    @Override
    protected void success(Object object) {

    }

    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }
}
