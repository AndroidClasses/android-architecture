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

package com.task.ui.mvp.taskdetail;

import android.support.annotation.Nullable;

import com.common.ui.app.UseCaseHandler;
import com.repository.task.model.Task;
import com.task.domain.usecase.ActivateTask;
import com.task.domain.usecase.CompleteTask;
import com.task.domain.usecase.DeleteTask;
import com.task.domain.usecase.GetTask;
import com.task.ui.mvp.TaskBasePresenter;

import javax.inject.Inject;

/**
 * Listens to user actions from the UI ({@link TaskDetailFragment}), retrieves the data and updates
 * the UI as required.
 * <p>
 * By marking the constructor with {@code @Inject}, Dagger injects the dependencies required to
 * create an instance of the TaskDetailPresenter (if it fails, it emits a compiler error). It uses
 * {@link TaskDetailPresenterModule} to do so.
 * <p>
 * Dagger generated code doesn't require public access to the constructor or class, and
 * therefore, to ensure the developer doesn't instantiate the class manually and bypasses Dagger,
 * it's good practice minimise the visibility of the class/constructor as much as possible.
 */
final public class TaskDetailPresenter extends TaskBasePresenter implements TaskDetailContract.Presenter {

    private final TaskDetailContract.View mTaskDetailView;
    private final GetTask mGetTask;
    private final CompleteTask mCompleteTask;
    private final ActivateTask mActivateTask;
    private final DeleteTask mDeleteTask;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Nullable String mTaskId;
    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Inject
    public TaskDetailPresenter(UseCaseHandler useCaseHandler, @Nullable String taskId,
                        TaskDetailContract.View taskDetailView,
                        GetTask getTask, CompleteTask completeTask,
                        ActivateTask activateTask, DeleteTask deleteTask) {
        super(useCaseHandler);
        mTaskDetailView = taskDetailView;
        mTaskId = taskId;
        mGetTask = getTask;
        mCompleteTask = completeTask;
        mActivateTask = activateTask;
        mDeleteTask = deleteTask;
    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setupListeners() {
        mTaskDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        openTask();
    }

    private void openTask() {
        if (null == mTaskId || mTaskId.isEmpty()) {
            mTaskDetailView.showMissingTask();
            return;
        }

        mTaskDetailView.setLoadingIndicator(true);
        execute(mGetTask, mTaskId);
    }

    @Override
    public void editTask() {
        if (null == mTaskId || mTaskId.isEmpty()) {
            mTaskDetailView.showMissingTask();
            return;
        }
        mTaskDetailView.showEditTask(mTaskId);
    }

    @Override
    public void deleteTask() {
        execute(mDeleteTask, mTaskId);
    }

    @Override
    protected void successDeleteTask() {
        mTaskDetailView.showTaskDeleted();
    }

    @Override
    protected  void errorDeleteTask() {
        // Show error, log, etc.
    }

    @Override
    public void completeTask() {
        if (null == mTaskId || mTaskId.isEmpty()) {
            mTaskDetailView.showMissingTask();
            return;
        }

        execute(mCompleteTask, mTaskId);
    }

    protected void successCompleteTask() {
        mTaskDetailView.showTaskMarkedComplete();
    }

    protected void errorCompleteTask() {
        // Show error, log, etc.
    }

    @Override
    public void activateTask() {
        if (null == mTaskId || mTaskId.isEmpty()) {
            mTaskDetailView.showMissingTask();
            return;
        }

        execute(mActivateTask, mTaskId);
    }

    private void showTask(Task task) {
        String title = task.getTitle();
        String description = task.getDescription();

        if (title != null && title.isEmpty()) {
            mTaskDetailView.hideTitle();
        } else {
            mTaskDetailView.showTitle(title);
        }

        if (description != null && description.isEmpty()) {
            mTaskDetailView.hideDescription();
        } else {
            mTaskDetailView.showDescription(description);
        }
        mTaskDetailView.showCompletionStatus(task.isCompleted());
    }

    @Override
    protected void successGetTask(Task task) {
        // The view may not be able to handle UI updates anymore
        if (!mTaskDetailView.isActive()) {
            return;
        }
        mTaskDetailView.setLoadingIndicator(false);
        showTask(task);
    }

    @Override
    protected void errorGetTask() {
        // The view may not be able to handle UI updates anymore
        if (!mTaskDetailView.isActive()) {
            return;
        }
        mTaskDetailView.showMissingTask();
    }

    @Override
    protected void successActivateTask() {
        mTaskDetailView.showTaskMarkedActive();
    }

    @Override
    protected void errorActivateTask() {
        // Show error, log, etc.
    }
}
