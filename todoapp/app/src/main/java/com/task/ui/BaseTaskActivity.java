package com.task.ui;

import android.support.v7.app.AppCompatActivity;

import com.common.ui.app.BaseApplication;
import com.example.android.architecture.blueprints.todoapp.TasksRepositoryComponent;
import com.example.android.architecture.blueprints.todoapp.ToDoApplication;

/**
 * Created by yangfeng on 16-7-29.
 */
public class BaseTaskActivity extends AppCompatActivity {
    protected TasksRepositoryComponent getTasksRepositoryComponent() {
        BaseApplication app = BaseApplication.get(this);
        if (app instanceof ToDoApplication) {
            ToDoApplication todoApp = (ToDoApplication)app;
            return todoApp.getTasksRepositoryComponent();
        }
        throw new IllegalStateException("It requires ToDoApplication context instance.");
    }

}
