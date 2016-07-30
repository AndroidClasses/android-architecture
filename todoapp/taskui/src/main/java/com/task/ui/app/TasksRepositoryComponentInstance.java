package com.task.ui.app;

import android.content.Context;

import com.common.ui.app.ApplicationModule;

/**
 * Created by yangfeng on 16/7/30.
 */
public final class TasksRepositoryComponentInstance {
    private TasksRepositoryComponentInstance() {
        // not construct outside
    }

    public static TasksRepositoryComponent instanceComponent(Context context) {
        return DaggerTasksRepositoryComponent.builder()
                .applicationModule(new ApplicationModule(context.getApplicationContext()))
                .tasksRepositoryModule(new TasksRepositoryModule()).build();
    }
}
