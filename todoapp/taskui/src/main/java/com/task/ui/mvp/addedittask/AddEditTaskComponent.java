package com.task.ui.mvp.addedittask;

import com.common.ui.util.FragmentScoped;
import com.task.ui.app.TaskRepositoryHolder;
import com.task.ui.app.TasksRepositoryComponent;

import dagger.Component;

/**
 * This is a Dagger component. Refer to {@link TaskRepositoryHolder} for the list of Dagger components
 * used in this application.
 * <P>
 * Because this component depends on the {@link TasksRepositoryComponent}, which is a singleton, a
 * scope must be specified. All fragment components use a custom scope for this purpose.
 */
@FragmentScoped
@Component(dependencies = TasksRepositoryComponent.class, modules = AddEditTaskPresenterModule.class)
public interface AddEditTaskComponent {
    void inject(AddEditTaskActivity addEditTaskActivity);
}
