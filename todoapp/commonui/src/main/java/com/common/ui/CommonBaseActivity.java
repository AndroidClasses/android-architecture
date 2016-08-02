package com.common.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.common.ui.util.ActivityUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yangfeng on 16-7-29.
 *
 * 1. bind view with butterknife while content change and try to unbind while destroy.
 * 2. init toolbar and customize actionbar
 * 3. find existing fragment or instance from sub class, then add it
 * 4. bridge to start activity with class name
 */
abstract public class CommonBaseActivity extends AppCompatActivity {
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
    }

    protected abstract int getLayoutResourceId();

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        tryUnbind();
        unbinder = ButterKnife.bind(this);

        // Set up the toolbar.
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        int titleId = getActivityTitleResId();
        if (titleId != 0) {
            actionBar.setTitle(titleId);
        }
        customizeActionBar(actionBar);

        onFragmentAddBefore();
        int fragmentContainId = getFragmentHolderResId();
        if (fragmentContainId != 0) {
            Fragment fragment = addFragmentToActivity(fragmentContainId);
            onFragmentAddAfter(fragment);
        }
    }

    protected int getFragmentHolderResId() {
        return 0;
    }

    protected int getActivityTitleResId() {
        return 0;
    }

    protected void customizeActionBar(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    protected void onFragmentAddBefore() {
    }

    protected abstract Fragment newFragmentInstance();
    protected abstract void onFragmentAddAfter(Fragment fragment);

    private Fragment addFragmentToActivity(int fragmentId) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(fragmentId);
        if (fragment == null) {
            fragment = newFragmentInstance();
        }

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, fragmentId);
        return fragment;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tryUnbind();
    }

    private void tryUnbind() {
        if (null != unbinder) {
            unbinder.unbind();
        }
    }

    // helper method for sub classes to target activity via its class
    protected void startActivity(Class<?> cls) {
        ActivityUtils.startActivity(this, cls);
    }
}
