package com.task.ui.mvp;

import com.common.ui.CommonBaseActivity;
import com.common.ui.app.CommonApplication;
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
    protected TasksRepositoryComponent getTasksRepositoryComponent() {
        CommonApplication app = CommonApplication.get(this);
        if (app instanceof TaskRepositoryHolder) {
            TaskRepositoryHolder repositoryHolder = (TaskRepositoryHolder) app;
            return repositoryHolder.getTasksRepositoryComponent();
        }
        throw new IllegalStateException("It requires TaskRepositoryHolder context instance.");
    }

    @Override
    protected int getFragmentHolderResId() {
        return R.id.contentFrame;
    }
}
