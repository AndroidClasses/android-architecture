/*
 * Copyright (C) 2015 The Android Open Source Project
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

package com.task.ui.mvp.tasks;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;

import com.common.ui.util.EspressoIdlingResource;
import com.task.domain.usecase.filter.TasksFilterType;
import com.task.ui.R;
import com.task.ui.mvp.TaskDrawerBaseActivity;
import com.task.ui.mvp.statistics.StatisticsActivity;

import javax.inject.Inject;

public class TasksActivity extends TaskDrawerBaseActivity {
    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    @Inject
    TasksPresenter presenter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.tasks_act;
    }

    @Override
    protected Fragment newFragmentInstance() {
        return TasksFragment.newInstance();
    }

    @Override
    protected void onFragmentAddAfter(Fragment fragment) {
        if (fragment instanceof TasksContract.View) {
            TasksContract.View view = (TasksContract.View) fragment;
            // Create the presenter
            DaggerTasksComponent.builder()
                    .tasksRepositoryComponent(getTasksRepositoryComponent())
                    .tasksPresenterModule(new TasksPresenterModule(view)).build()
                    .inject(this);
        } else {
            throw new IllegalStateException("Invalid TasksContract.View instance");
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Load previously saved state, if available.
        if (savedInstanceState != null) {
            TasksFilterType currentFiltering =
                    (TasksFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            presenter.setFiltering(currentFiltering);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_FILTERING_KEY, presenter.getFiltering());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void invokeNavigationItem(int itemId) {
        if (itemId == R.id.list_navigation_menu_item) {
            // Do nothing, we're already on that screen
        } else if (itemId == R.id.statistics_navigation_menu_item) {
            startActivity(StatisticsActivity.class);
        } else {
        }
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
