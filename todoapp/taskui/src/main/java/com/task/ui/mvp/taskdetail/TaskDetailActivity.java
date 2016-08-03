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

package com.task.ui.mvp.taskdetail;

import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;

import com.common.ui.util.EspressoIdlingResource;
import com.task.ui.Constants;
import com.task.ui.R;
import com.task.ui.mvp.TaskBaseActivity;

import javax.inject.Inject;

/**
 * Displays task details screen.
 */
public class TaskDetailActivity extends TaskBaseActivity {

    @Inject
    TaskDetailPresenter presenter;

    @Override
    protected int getLayoutResourceId() {
            return R.layout.taskdetail_act;
    }

    @Override
    protected Fragment newFragmentInstance() {
        // Get the requested task id
        String taskId = getIntent().getStringExtra(Constants.EXTRA_TASK_ID);
        return TaskDetailFragment.newInstance(taskId);
    }

    @Override
    protected void onFragmentAddAfter(Fragment fragment) {
        if (fragment instanceof TaskDetailContract.View) {
            TaskDetailContract.View view = (TaskDetailContract.View) fragment;
            // Get the requested task id
            String taskId = getIntent().getStringExtra(Constants.EXTRA_TASK_ID);

            // Create the presenter
            DaggerTaskDetailComponent.builder()
                    .taskDetailPresenterModule(new TaskDetailPresenterModule(view, taskId))
                    .tasksRepositoryComponent(getTasksRepositoryComponent()).build()
                    .inject(this);
        } else {
            throw new IllegalStateException("Invalid TaskDetailContract.View instance");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
