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

package com.task.ui.mvp.addedittask;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;

import com.common.ui.util.EspressoIdlingResource;
import com.task.ui.Constants;
import com.task.ui.R;
import com.task.ui.mvp.TaskBaseActivity;

import javax.inject.Inject;

/**
 * Displays an add or edit task screen.
 */
public class AddEditTaskActivity extends TaskBaseActivity {
    @Inject
    AddEditTaskPresenter presenter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.addtask_act;
    }

    @Override
    protected int getActivityTitleResId() {
        if (getIntent().hasExtra(Constants.ARGUMENT_EDIT_TASK_ID)) {
            return R.string.edit_task;
        } else {
            return R.string.add_task;
        }
    }

    @Override
    protected Fragment newFragmentInstance() {
        return AddEditTaskFragment.newInstance();
    }

    @Override
    protected void onFragmentAddAfter(Fragment fragment) {
        if (fragment instanceof AddEditTaskFragment) {
            AddEditTaskFragment addEditTaskFragment = (AddEditTaskFragment) fragment;

            String taskId = null;

            if (getIntent().hasExtra(Constants.ARGUMENT_EDIT_TASK_ID)) {
                taskId = getIntent().getStringExtra(Constants.ARGUMENT_EDIT_TASK_ID);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.ARGUMENT_EDIT_TASK_ID, taskId);
                addEditTaskFragment.setArguments(bundle);
            }

            // Create the presenter
            DaggerAddEditTaskComponent.builder()
                    .addEditTaskPresenterModule(
                            new AddEditTaskPresenterModule(addEditTaskFragment, taskId))
                    .tasksRepositoryComponent(getTasksRepositoryComponent()).build()
                    .inject(this);
        } else {
            throw new IllegalStateException("Invalid AddEditTaskFragment instance");
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
