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

import android.support.v4.app.Fragment;

import com.task.ui.R;
import com.task.ui.mvp.TaskDrawerBaseActivity;
import com.task.ui.mvp.tasks.TasksActivity;

import javax.inject.Inject;

/**
 * Show statistics for tasks.
 */
public class StatisticsActivity extends TaskDrawerBaseActivity {
    @Inject
    StatisticsPresenter presenter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.statistics_act;
    }

    @Override
    protected Fragment newFragmentInstance() {
        return StatisticsFragment.newInstance();
    }

    @Override
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

    @Override
    protected void invokeNavigationItem(int itemId) {
        if (itemId == R.id.list_navigation_menu_item) {
            startActivity(TasksActivity.class);
        } else if (itemId == R.id.statistics_navigation_menu_item) {
            // Do nothing, we're already on that screen
        } else {
        }
    }

    @Override
    protected int getActivityTitleResId() {
        // override title
        return R.string.statistics_title;
    }
}
