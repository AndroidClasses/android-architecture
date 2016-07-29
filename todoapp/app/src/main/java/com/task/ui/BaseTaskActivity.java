package com.task.ui;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.common.ui.app.BaseApplication;
import com.common.ui.util.ActivityUtils;
import com.example.android.architecture.blueprints.todoapp.TasksRepositoryComponent;
import com.example.android.architecture.blueprints.todoapp.ToDoApplication;
import com.example.android.architecture.blueprints.todoapp.R;

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
        BaseApplication app = BaseApplication.get(this);
        if (app instanceof ToDoApplication) {
            ToDoApplication todoApp = (ToDoApplication)app;
            return todoApp.getTasksRepositoryComponent();
        }
        throw new IllegalStateException("It requires ToDoApplication context instance.");
    }

    protected void startActivity(Class<?> cls) {
        ActivityUtils.startActivity(this, cls);
    }
}
