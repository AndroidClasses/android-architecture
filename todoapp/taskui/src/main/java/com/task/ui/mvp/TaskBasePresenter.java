package com.task.ui.mvp;

import com.clean.common.usecase.UseCase;
import com.common.ui.app.UseCaseHandler;
import com.repository.task.model.Statistics;
import com.repository.task.model.Task;
import com.task.domain.usecase.ActivateTask;
import com.task.domain.usecase.ClearCompleteTasks;
import com.task.domain.usecase.CompleteTask;
import com.task.domain.usecase.DeleteTask;
import com.task.domain.usecase.GetStatistics;
import com.task.domain.usecase.GetTask;
import com.task.domain.usecase.GetTasks;
import com.task.domain.usecase.SaveTask;
import com.task.domain.usecase.filter.TasksFilterType;

import java.util.List;

/**
 * Created by yangfeng on 16-8-2.
 *
 * 1. bridge execution of use cases to handler
 * 2. respond to the callback of execution result of use cases,
 * 3. implement empty response, which is override by sub class.
 */
public class TaskBasePresenter {
    private final UseCaseHandler useCaseHandler;
    public TaskBasePresenter(UseCaseHandler useCaseHandler) {
        this.useCaseHandler = useCaseHandler;
    }

    protected void execute(GetTasks mGetTasks, boolean forceUpdate, TasksFilterType mCurrentFiltering,
                           final boolean showLoadingUI) {
        GetTasks.RequestValues requestValue = new GetTasks.RequestValues(forceUpdate,
                mCurrentFiltering);

        useCaseHandler.execute(mGetTasks, requestValue,
                new UseCase.UseCaseCallback<GetTasks.ResponseValue>() {
                    @Override
                    public void onSuccess(GetTasks.ResponseValue response) {
                        List<Task> tasks = response.getTasks();
                        successGetTasks(tasks, showLoadingUI);
                    }

                    @Override
                    public void onError() {
                        errorGetTasks();
                    }
                });
    }

    protected void successGetTasks(List<Task> tasks, final boolean showLoadingUI) {
    }

    protected void errorGetTasks() {
    }

    protected void execute(CompleteTask mCompleteTask, String taskId) {
        useCaseHandler.execute(mCompleteTask, new CompleteTask.RequestValues(taskId),
                new UseCase.UseCaseCallback<CompleteTask.EmptyResponseValue>() {
                    @Override
                    public void onSuccess(CompleteTask.EmptyResponseValue response) {
                        successCompleteTask();
                    }

                    @Override
                    public void onError() {
                        errorCompleteTask();
                    }
                });
    }

    protected void successCompleteTask() {
    }

    protected void errorCompleteTask() {
    }

    protected void execute(ActivateTask mActivateTask, String taskId) {
        useCaseHandler.execute(mActivateTask, new ActivateTask.RequestValues(taskId),
                new UseCase.UseCaseCallback<ActivateTask.EmptyResponseValue>() {
                    @Override
                    public void onSuccess(ActivateTask.EmptyResponseValue response) {
                        successActivateTask();
                    }

                    @Override
                    public void onError() {
                        errorActivateTask();
                    }
                });
    }

    protected void successActivateTask() {
    }

    protected void errorActivateTask() {
    }

    protected void execute(ClearCompleteTasks mClearCompleteTasks) {
        useCaseHandler.execute(mClearCompleteTasks, ClearCompleteTasks.emptyRequestValues(),
                new UseCase.UseCaseCallback<ClearCompleteTasks.EmptyResponseValue>() {
                    @Override
                    public void onSuccess(ClearCompleteTasks.EmptyResponseValue response) {
                        successClearCompletedTasks();
                    }

                    @Override
                    public void onError() {
                        errorClearCompletedTasks();
                    }
                });
    }

    protected void errorClearCompletedTasks() {
    }

    protected void successClearCompletedTasks() {
    }

    protected void execute(GetTask mGetTask, String mTaskId) {
        useCaseHandler.execute(mGetTask, new GetTask.RequestValues(mTaskId),
                new UseCase.UseCaseCallback<GetTask.ResponseValue>() {
                    @Override
                    public void onSuccess(GetTask.ResponseValue response) {
                        successGetTask(response.getTask());
                    }

                    @Override
                    public void onError() {
                        errorGetTask();
                    }
                });
    }

    protected void successGetTask(Task task) {
    }

    protected void errorGetTask() {
    }

    protected void execute(SaveTask mSaveTask, Task newTask) {
        useCaseHandler.execute(mSaveTask, new SaveTask.RequestValues(newTask),
                new UseCase.UseCaseCallback<SaveTask.ResponseValue>() {
                    @Override
                    public void onSuccess(SaveTask.ResponseValue response) {
                        successSaveTask();
                    }

                    @Override
                    public void onError() {
                        errorSaveTask();
                    }
                });
    }

    protected void successSaveTask() {
    }

    protected void errorSaveTask() {
    }

    protected void execute(GetStatistics mGetStatistics) {
        useCaseHandler.execute(mGetStatistics, GetStatistics.emptyRequestValues(),
                new UseCase.UseCaseCallback<GetStatistics.ResponseValue>() {
                    @Override
                    public void onSuccess(GetStatistics.ResponseValue response) {
                        Statistics statistics = response.getStatistics();
                        successGetStatistics(statistics.getActiveTasks(), statistics.getCompletedTasks());
                    }

                    @Override
                    public void onError() {
                        errorGetStatistics();
                    }
                });
    }

    protected void successGetStatistics(int activeTasks, int completedTasks) {
    }

    protected void errorGetStatistics() {
    }

    protected void execute(DeleteTask mDeleteTask, String mTaskId) {
        useCaseHandler.execute(mDeleteTask, new DeleteTask.RequestValues(mTaskId),
                new UseCase.UseCaseCallback<DeleteTask.EmptyResponseValue>() {
                    @Override
                    public void onSuccess(DeleteTask.EmptyResponseValue response) {
                        successDeleteTask();
                    }

                    @Override
                    public void onError() {
                        errorDeleteTask();
                    }
                });
    }

    protected void successDeleteTask() {
    }


    protected  void errorDeleteTask() {
    }

}
