/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.task.ui.mvp.statistics;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.task.ui.R;
import com.task.ui.mvp.BaseTaskActivity;
import com.task.ui.mvp.tasks.TasksActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Show statistics for tasks.
 */
public class StatisticsActivity extends BaseTaskActivity {

    private DrawerLayout mDrawerLayout;

    @Inject
    StatisticsPresenter mStatiticsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_act);
    }

    @Override
    protected void onFragmentAddBefore() {
        // Set up the toolbar.
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.statistics_title);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = ButterKnife.findById(this, R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
    }

    protected void onFragmentAddAfter(Fragment fragment) {
        if (fragment instanceof StatisticsContract.View) {
            StatisticsContract.View view = (StatisticsContract.View) fragment;
            DaggerStatisticsComponent.builder()
                    .statisticsPresenterModule(new StatisticsPresenterModule(view))
                    .tasksRepositoryComponent(getTasksRepositoryComponent())
                    .build().inject(this);
        } else {
            throw new IllegalStateException("Invalid StatisticsContract.View instance");
        }
    }

    protected Fragment newFragmentInstance() {
        return StatisticsFragment.newInstance();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int i = menuItem.getItemId();
                        if (i == R.id.list_navigation_menu_item) {
                            startActivity(TasksActivity.class);
                        } else if (i == R.id.statistics_navigation_menu_item) {
                            // Do nothing, we're already on that screen
                        } else {
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
}
