package com.example.android.architecture.blueprints.todoapp;

import android.content.Context;

import com.clean.common.usecase.UseCaseScheduler;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the Context dependency to the
 * {@link
 * TasksRepositoryComponent}.
 */
@Module
public final class ApplicationModule {

    private final Context mContext;

    ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Provides
    UseCaseScheduler provideUseCaseScheduler(UseCaseThreadPoolScheduler scheduler) {
        return scheduler;
    }
}