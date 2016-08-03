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

package com.task.ui.mvp.tasks;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.common.ui.app.UseCaseHandler;
import com.repository.task.data.TasksDataSource;
import com.repository.task.model.Task;
import com.task.domain.usecase.ActivateTask;
import com.task.domain.usecase.ClearCompleteTasks;
import com.task.domain.usecase.CompleteTask;
import com.task.domain.usecase.GetTasks;
import com.task.domain.usecase.filter.TasksFilterType;
import com.task.ui.Constants;
import com.task.ui.mvp.TaskBasePresenter;

import java.util.List;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Listens to user actions from the UI ({@link TasksFragment}), retrieves the data and updates the
 * UI as required.
 * <p />
 * By marking the constructor with {@code @Inject}, Dagger injects the dependencies required to
 * create an instance of the TasksPresenter (if it fails, it emits a compiler error).  It uses
 * {@link TasksPresenterModule} to do so.
 * <p/>
 * Dagger generated code doesn't require public access to the constructor or class, and
 * therefore, to ensure the developer doesn't instantiate the class manually and bypasses Dagger,
 * it's good practice minimise the visibility of the class/constructor as much as possible.
 **/
final public class TasksPresenter extends TaskBasePresenter implements TasksContract.Presenter {

    @NonNull
    private final TasksContract.View tasksView;

    private final GetTasks getTasks;
    private final CompleteTask completeTask;
    private final ActivateTask activateTask;
    private final ClearCompleteTasks clearCompleteTasks;

    private TasksFilterType currentFiltering = TasksFilterType.ALL_TASKS;

    private boolean isFirstLoad = true;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Inject
    public TasksPresenter(UseCaseHandler handler, TasksContract.View tasksView,
                   GetTasks getTasks, CompleteTask completeTask,
                   ActivateTask activateTask, ClearCompleteTasks clearCompleteTasks) {
        super(handler);
        this.tasksView = tasksView;
        this.getTasks = getTasks;
        this.completeTask = completeTask;
        this.activateTask = activateTask;
        this.clearCompleteTasks = clearCompleteTasks;
    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setupListeners() {
        tasksView.setPresenter(this);
    }

    @Override
    public void start() {
        loadTasks(false);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        // If a task was successfully added, show snackbar
        if (Constants.REQUEST_ADD_TASK == requestCode
                && Activity.RESULT_OK == resultCode) {
            tasksView.showSuccessfullySavedMessage();
        }
    }

    @Override
    public void loadTasks(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadTasks(forceUpdate || isFirstLoad, true);
        isFirstLoad = false;
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link TasksDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadTasks(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            tasksView.setLoadingIndicator(true);
        }

        execute(getTasks, forceUpdate, currentFiltering, showLoadingUI);
    }

    private void processTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
            processEmptyTasks();
        } else {
            // Show the list of tasks
            tasksView.showTasks(tasks);
            // Set the filter label's text.
            showFilterLabel();
        }
    }

    private void showFilterLabel() {
        switch (currentFiltering) {
            case ACTIVE_TASKS:
                tasksView.showActiveFilterLabel();
                break;
            case COMPLETED_TASKS:
                tasksView.showCompletedFilterLabel();
                break;
            default:
                tasksView.showAllFilterLabel();
                break;
        }
    }

    private void processEmptyTasks() {
        switch (currentFiltering) {
            case ACTIVE_TASKS:
                tasksView.showNoActiveTasks();
                break;
            case COMPLETED_TASKS:
                tasksView.showNoCompletedTasks();
                break;
            default:
                tasksView.showNoTasks();
                break;
        }
    }

    @Override
    public void addNewTask() {
        tasksView.showAddTask();
    }

    @Override
    public void openTaskDetails(@NonNull Task requestedTask) {
        checkNotNull(requestedTask, "requestedTask cannot be null!");
        tasksView.showTaskDetailsUi(requestedTask.getId());
    }

    @Override
    public void completeTask(@NonNull Task completedTask) {
        checkNotNull(completedTask, "completedTask cannot be null!");
        execute(completeTask, completedTask.getId());
    }

    @Override
    public void activateTask(@NonNull Task activeTask) {
        checkNotNull(activeTask, "activeTask cannot be null!");
        execute(activateTask, activeTask.getId());
    }

    @Override
    public void clearCompletedTasks() {
        execute(clearCompleteTasks);
    }

    /**
     * Sets the current task filtering type.
     *
     * @param requestType Can be {@link TasksFilterType#ALL_TASKS},
     *                    {@link TasksFilterType#COMPLETED_TASKS}, or
     *                    {@link TasksFilterType#ACTIVE_TASKS}
     */
    @Override
    public void setFiltering(TasksFilterType requestType) {
        currentFiltering = requestType;
    }

    @Override
    public TasksFilterType getFiltering() {
        return currentFiltering;
    }

    @Override
    protected void successGetTasks(List<Task> tasks, final boolean showLoadingUI) {
        // The view may not be able to handle UI updates anymore
        if (!tasksView.isActive()) {
            return;
        }
        if (showLoadingUI) {
            tasksView.setLoadingIndicator(false);
        }

        processTasks(tasks);
    }

    @Override
    protected void errorGetTasks() {
        // The view may not be able to handle UI updates anymore
        if (!tasksView.isActive()) {
            return;
        }
        tasksView.showLoadingTasksError();
    }

    @Override
    protected void successCompleteTask() {
        tasksView.showTaskMarkedComplete();
        loadTasks(false, false);
    }

    @Override
    protected void errorCompleteTask() {
        tasksView.showLoadingTasksError();
    }

    @Override
    protected void successActivateTask() {
        tasksView.showTaskMarkedActive();
        loadTasks(false, false);
    }

    @Override
    protected void errorActivateTask() {
        tasksView.showLoadingTasksError();
    }

    @Override
    protected void errorClearCompletedTasks() {
        tasksView.showLoadingTasksError();
    }

    @Override
    protected void successClearCompletedTasks() {
        tasksView.showCompletedTasksCleared();
        loadTasks(false, false);
    }
}
