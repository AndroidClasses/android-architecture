package com.task.ui.app;

import com.common.ui.app.ApplicationModule;
import com.common.ui.app.UseCaseHandler;
import com.repository.task.data.TasksRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * This is a Dagger component. Refer to the implement class of {@link TaskRepositoryHolder} for the list of Dagger components
 * used in this application.
 * <P>
 * Even though Dagger allows annotating a {@link Component @Component} as a singleton, the code
 * itself must ensure only one instance of the class is created. This is done in {@link
 * TaskRepositoryHolder} implement class.
 */
@Singleton
@Component(modules = {TasksRepositoryModule.class, ApplicationModule.class})
public interface TasksRepositoryComponent {
    TasksRepository getTasksRepository();
    UseCaseHandler getUseCaseHandler();
}
