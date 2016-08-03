package com.task.ui.mvp.statistics;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link StatisticsPresenter}.
 */
@Module
public class StatisticsPresenterModule {

    private final StatisticsContract.View view;

    public StatisticsPresenterModule(StatisticsContract.View view) {
        this.view = view;
    }

    @Provides
    StatisticsContract.View provideStatisticsContractView() {
        return view;
    }
}
