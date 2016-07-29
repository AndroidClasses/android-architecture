package com.common.ui.app;

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

    public ApplicationModule(Context context) {
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