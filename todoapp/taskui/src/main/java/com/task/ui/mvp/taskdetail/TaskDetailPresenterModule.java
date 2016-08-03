package com.task.ui.mvp.taskdetail;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link TaskDetailPresenter}.
 */
@Module
public class TaskDetailPresenterModule {

    private final TaskDetailContract.View view;

    private final String taskId;

    public TaskDetailPresenterModule(TaskDetailContract.View view, String taskId) {
        this.view = view;
        this.taskId = taskId;
    }

    @Provides
    TaskDetailContract.View provideTaskDetailContractView() {
        return view;
    }

    @Provides
    String provideTaskId() {
        return taskId;
    }
}
