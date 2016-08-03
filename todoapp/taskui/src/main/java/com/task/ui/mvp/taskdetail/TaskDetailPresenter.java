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

import android.support.annotation.NonNull;
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

    @NonNull
    private final TaskDetailContract.View taskDetailView;

    private final GetTask getTask;
    private final CompleteTask completeTask;
    private final ActivateTask activateTask;
    private final DeleteTask deleteTask;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Nullable String taskId;
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
        this.taskDetailView = taskDetailView;
        this.taskId = taskId;
        this.getTask = getTask;
        this.completeTask = completeTask;
        this.activateTask = activateTask;
        this.deleteTask = deleteTask;
    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setupListeners() {
        taskDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        openTask();
    }

    private void openTask() {
        if (null == taskId || taskId.isEmpty()) {
            taskDetailView.showMissingTask();
            return;
        }

        taskDetailView.setLoadingIndicator(true);
        execute(getTask, taskId);
    }

    @Override
    public void editTask() {
        if (null == taskId || taskId.isEmpty()) {
            taskDetailView.showMissingTask();
            return;
        }
        taskDetailView.showEditTask(taskId);
    }

    @Override
    public void deleteTask() {
        execute(deleteTask, taskId);
    }

    @Override
    protected void successDeleteTask() {
        taskDetailView.showTaskDeleted();
    }

    @Override
    protected  void errorDeleteTask() {
        // Show error, log, etc.
    }

    @Override
    public void completeTask() {
        if (null == taskId || taskId.isEmpty()) {
            taskDetailView.showMissingTask();
            return;
        }

        execute(completeTask, taskId);
    }

    protected void successCompleteTask() {
        taskDetailView.showTaskMarkedComplete();
    }

    protected void errorCompleteTask() {
        // Show error, log, etc.
    }

    @Override
    public void activateTask() {
        if (null == taskId || taskId.isEmpty()) {
            taskDetailView.showMissingTask();
            return;
        }

        execute(activateTask, taskId);
    }

    private void showTask(Task task) {
        String title = task.getTitle();
        String description = task.getDescription();

        if (title != null && title.isEmpty()) {
            taskDetailView.hideTitle();
        } else {
            taskDetailView.showTitle(title);
        }

        if (description != null && description.isEmpty()) {
            taskDetailView.hideDescription();
        } else {
            taskDetailView.showDescription(description);
        }
        taskDetailView.showCompletionStatus(task.isCompleted());
    }

    @Override
    protected void successGetTask(Task task) {
        // The view may not be able to handle UI updates anymore
        if (!taskDetailView.isActive()) {
            return;
        }
        taskDetailView.setLoadingIndicator(false);
        showTask(task);
    }

    @Override
    protected void errorGetTask() {
        // The view may not be able to handle UI updates anymore
        if (!taskDetailView.isActive()) {
            return;
        }
        taskDetailView.showMissingTask();
    }

    @Override
    protected void successActivateTask() {
        taskDetailView.showTaskMarkedActive();
    }

    @Override
    protected void errorActivateTask() {
        // Show error, log, etc.
    }
}
