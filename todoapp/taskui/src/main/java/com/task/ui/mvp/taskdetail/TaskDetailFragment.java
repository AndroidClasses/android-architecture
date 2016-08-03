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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.task.ui.Constants;
import com.task.ui.R;
import com.task.ui.mvp.TaskBaseFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Main UI for the task detail screen.
 */
public class TaskDetailFragment extends TaskBaseFragment implements TaskDetailContract.View {
    private static final int REQUEST_EDIT_TASK = 1;

    private TaskDetailContract.Presenter presenter;

    private TextView titleView;

    private TextView descriptionView;

    private CheckBox completeStatusView;

    public static TaskDetailFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString(Constants.EXTRA_TASK_ID, taskId);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.taskdetail_frag, container, false);
        titleView = (TextView) root.findViewById(R.id.task_detail_title);
        descriptionView = (TextView) root.findViewById(R.id.task_detail_description);
        completeStatusView = (CheckBox) root.findViewById(R.id.task_detail_complete);

        setFloatingActionButton(R.id.fab_edit_task, R.drawable.ic_edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.editTask();
            }
        });

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void setPresenter(@NonNull TaskDetailContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_delete) {
            presenter.deleteTask();
            return true;
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.taskdetail_fragment_menu, menu);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            titleView.setText("");
            descriptionView.setText(getString(R.string.loading));
        }
    }

    @Override
    public void hideDescription() {
        descriptionView.setVisibility(View.GONE);
    }

    @Override
    public void hideTitle() {
        titleView.setVisibility(View.GONE);
    }

    @Override
    public void showDescription(String description) {
        descriptionView.setVisibility(View.VISIBLE);
        descriptionView.setText(description);
    }

    @Override
    public void showCompletionStatus(final boolean complete) {
        completeStatusView.setChecked(complete);
        completeStatusView.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            presenter.completeTask();
                        } else {
                            presenter.activateTask();
                        }
                    }
                });
    }

    @Override
    public void showEditTask(String taskId) {
        gotoAddEditActivity(taskId, REQUEST_EDIT_TASK);
    }

    @Override
    public void showTaskDeleted() {
        getActivity().finish();
    }

    public void showTaskMarkedComplete() {
        Snackbar.make(getView(), getString(R.string.task_marked_complete), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showTaskMarkedActive() {
        Snackbar.make(getView(), getString(R.string.task_marked_active), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_EDIT_TASK) {
            // If the task was edited successfully, go back to the list.
            if (resultCode == Activity.RESULT_OK) {
                getActivity().finish();
            }
        }
    }

    @Override
    public void showTitle(String title) {
        titleView.setVisibility(View.VISIBLE);
        titleView.setText(title);
    }

    @Override
    public void showMissingTask() {
        titleView.setText("");
        descriptionView.setText(getString(R.string.no_data));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
