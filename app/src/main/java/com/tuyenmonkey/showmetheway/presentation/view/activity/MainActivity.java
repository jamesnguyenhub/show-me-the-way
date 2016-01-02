package com.tuyenmonkey.showmetheway.presentation.view.activity;

import android.os.Bundle;

import com.tuyenmonkey.showmetheway.R;
import com.tuyenmonkey.showmetheway.presentation.view.fragment.MapFragment;
import com.tuyenmonkey.showmetheway.presentation.view.fragment.SearchFragment;

public class MainActivity extends BaseActivity {

    private static final String TAg = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initializeActivity(savedInstanceState);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            addFragment(R.id.fl_search_portion, SearchFragment.newInstance());
            addFragment(R.id.fl_map_portion, MapFragment.newInstance());
        }
    }
}
