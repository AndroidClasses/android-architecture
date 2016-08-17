package com.task.ui.mvp;

import android.content.Context;

import com.common.ui.CommonBaseActivity;
import com.task.ui.R;
import com.task.ui.app.TaskRepositoryHolder;
import com.task.ui.app.TasksRepositoryComponent;

/**
 * Created by yangfeng on 16-7-29.
 *
 * 1. bridge to get tasks global repository component instance.
 * 2. provide container id for super class to inject fragment
 */
abstract public class TaskBaseActivity extends CommonBaseActivity {
    public static TaskRepositoryHolder getRepositoryHolder(Context context) {
        Context app = context.getApplicationContext();
        if (app instanceof TaskRepositoryHolder) {
            TaskRepositoryHolder repositoryHolder = (TaskRepositoryHolder) app;
            return repositoryHolder;
        }
        throw new IllegalStateException("It requires TaskRepositoryHolder context instance.");
    }
    protected TasksRepositoryComponent getTasksRepositoryComponent() {
        return getRepositoryHolder(this).getTasksRepositoryComponent();
    }

    @Override
    protected int getFragmentHolderResId() {
        return R.id.contentFrame;
    }
}
