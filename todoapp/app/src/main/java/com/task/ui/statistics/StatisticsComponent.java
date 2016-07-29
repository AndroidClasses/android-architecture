package com.task.ui.statistics;

import com.example.android.architecture.blueprints.todoapp.ToDoApplication;
import com.example.android.architecture.blueprints.todoapp.TasksRepositoryComponent;
import com.common.ui.util.FragmentScoped;

import dagger.Component;

/**
 * This is a Dagger component. Refer to {@link ToDoApplication} for the list of Dagger components
 * used in this application.
 * <P>
 * Because this component depends on the {@link TasksRepositoryComponent}, which is a singleton, a
 * scope must be specified. All fragment components use a custom scope for this purpose.
 */
@FragmentScoped
@Component(dependencies = TasksRepositoryComponent.class, modules = StatisticsPresenterModule.class)
public interface StatisticsComponent {

    void inject(StatisticsActivity statisticsActivity);
}
