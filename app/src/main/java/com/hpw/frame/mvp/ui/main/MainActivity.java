package com.hpw.frame.mvp.ui.main;

import android.os.Bundle;

import com.hpw.frame.R;
import com.hpw.frame.mvp.ui.BaseActivity;

public class MainActivity extends BaseActivity implements MainContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
