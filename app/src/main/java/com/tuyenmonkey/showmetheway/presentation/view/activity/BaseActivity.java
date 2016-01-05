package com.tuyenmonkey.showmetheway.presentation.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.tuyenmonkey.showmetheway.app.App;
import com.tuyenmonkey.showmetheway.presentation.di.component.AppComponent;
import com.tuyenmonkey.showmetheway.presentation.di.module.ActivityModule;

/**
 * Created by tuyen on 1/2/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    protected AppComponent getApplicationComponent() {
        return ((App)getApplication()).getAppComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}

