package com.task.ui.mvp.addedittask;

import android.support.annotation.Nullable;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link AddEditTaskPresenter}.
 */
@Module
public class AddEditTaskPresenterModule {

    private final AddEditTaskContract.View view;

    private String taskId;

    public AddEditTaskPresenterModule(AddEditTaskContract.View view, @Nullable String taskId) {
        this.view = view;
        this.taskId = taskId;
    }

    @Provides
    AddEditTaskContract.View provideAddEditTaskContractView() {
        return view;
    }

    @Provides
    @Nullable
    String provideTaskId() {
        return taskId;
    }
}
