package com.task.ui.mvp.tasks;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link TasksPresenter}.
 */
@Module
public class TasksPresenterModule {

    private final TasksContract.View view;

    public TasksPresenterModule(TasksContract.View view) {
        this.view = view;
    }

    @Provides
    TasksContract.View provideTasksContractView() {
        return view;
    }

//    @Provides
//    UseCaseScheduler provideUseCaseScheduler() {
//        return new UseCaseThreadPoolScheduler();
//    }
}
