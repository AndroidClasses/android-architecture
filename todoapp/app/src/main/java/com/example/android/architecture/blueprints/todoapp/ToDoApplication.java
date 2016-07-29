package com.example.android.architecture.blueprints.todoapp;

import android.app.Application;

import com.common.ui.app.ApplicationModule;
import com.common.ui.app.BaseApplication;
import com.task.ui.mvp.addedittask.AddEditTaskComponent;
import com.task.ui.app.DaggerTasksRepositoryComponent;
import com.task.ui.app.TaskRepositoryHolder;
import com.task.ui.app.TasksRepositoryComponent;
import com.task.ui.app.TasksRepositoryModule;
import com.task.ui.mvp.statistics.StatisticsComponent;
import com.task.ui.mvp.taskdetail.TaskDetailComponent;
import com.task.ui.mvp.tasks.TasksComponent;

/**
 * Even though Dagger2 allows annotating a {@link dagger.Component} as a singleton, the code itself
 * must ensure only one instance of the class is created. Therefore, we create a custom
 * {@link Application} class to store a singleton reference to the {@link
 * TasksRepositoryComponent}.
 * <P>
 * The application is made of 5 Dagger components, as follows:<BR />
 * {@link TasksRepositoryComponent}: the data (it encapsulates a db and server data)<BR />
 * {@link TasksComponent}: showing the list of to do items, including marking them as
 * completed<BR />
 * {@link AddEditTaskComponent}: adding or editing a to do item<BR />
 * {@link TaskDetailComponent}: viewing details about a to do item, inlcuding marking it as
 * completed and deleting it<BR />
 * {@link StatisticsComponent}: viewing statistics about your to do items<BR />
 */
public class ToDoApplication extends BaseApplication implements TaskRepositoryHolder {

    private TasksRepositoryComponent mRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mRepositoryComponent = DaggerTasksRepositoryComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .tasksRepositoryModule(new TasksRepositoryModule()).build();
    }

    @Override
    public TasksRepositoryComponent getTasksRepositoryComponent() {
        return mRepositoryComponent;
    }
}
