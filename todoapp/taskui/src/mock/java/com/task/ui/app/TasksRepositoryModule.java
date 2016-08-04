package com.task.ui.app;

import android.content.Context;

import com.repository.task.data.Local;
import com.repository.task.data.Remote;
import com.repository.task.data.TasksDataSource;
import com.repository.task.data.TasksRepository;
import com.repository.task.data.local.TasksLocalDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This is used by Dagger to inject the required arguments into the {@link TasksRepository}.
 */
@Module
public class TasksRepositoryModule {

    @Singleton
    @Provides
    @Local
    TasksDataSource provideTasksLocalDataSource(Context context) {
        return new TasksLocalDataSource(context);
    }

    @Singleton
    @Provides
    @Remote
    TasksDataSource provideTasksRemoteDataSource() {
        return new FakeTasksRemoteDataSource();
    }

}
