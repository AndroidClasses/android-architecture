package com.task.ui.mvp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.common.ui.app.CommonApplication;
import com.common.ui.util.ActivityUtils;
import com.task.ui.R;
import com.task.ui.app.TaskRepositoryHolder;
import com.task.ui.app.TasksRepositoryComponent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yangfeng on 16-7-29.
 *
 * 1. bind view with butterknife while content change and try to unbind while destroy.
 * 2. bridge to get tasks global repository component instance.
 * 3. bridge to start activity with class name
 */
abstract public class BaseTaskActivity extends AppCompatActivity {
    private Unbinder unbinder;

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        tryUnbind();
        unbinder = ButterKnife.bind(this);

        onFragmentAddBefore();
        Fragment fragment = addFragmentToActivity(R.id.contentFrame);
        onFragmentAddAfter(fragment);
    }

    protected abstract void onFragmentAddAfter(Fragment fragment);
    protected abstract void onFragmentAddBefore();
    protected abstract Fragment newFragmentInstance();

    private Fragment addFragmentToActivity(int fragmentId) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(fragmentId);
        if (fragment == null) {
            fragment = newFragmentInstance();
        }

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, fragmentId);
        return fragment;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tryUnbind();
    }

    private void tryUnbind() {
        if (null != unbinder) {
            unbinder.unbind();
        }
    }

    protected TasksRepositoryComponent getTasksRepositoryComponent() {
        CommonApplication app = CommonApplication.get(this);
        if (app instanceof TaskRepositoryHolder) {
            TaskRepositoryHolder todoApp = (TaskRepositoryHolder) app;
            return todoApp.getTasksRepositoryComponent();
        }
        throw new IllegalStateException("It requires TaskRepositoryHolder context instance.");
    }

    protected void startActivity(Class<?> cls) {
        ActivityUtils.startActivity(this, cls);
    }
}
