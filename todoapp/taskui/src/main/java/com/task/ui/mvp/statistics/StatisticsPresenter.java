/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.task.ui.mvp.statistics;

import android.support.annotation.NonNull;

import com.common.ui.app.UseCaseHandler;
import com.task.domain.usecase.GetStatistics;
import com.task.ui.mvp.TaskBasePresenter;

import javax.inject.Inject;

/**
 * Listens to user actions from the UI ({@link StatisticsFragment}), retrieves the data and updates
 * the UI as required.
 * <p />
 * By marking the constructor with {@code @Inject}, Dagger injects the dependencies required to
 * create an instance of the StatisticsPresenter (if it fails, it emits a compiler error). It uses
 * {@link StatisticsPresenterModule} to do so.
 * <p />
 * Dagger generated code doesn't require public access to the constructor or class, and
 * therefore, to ensure the developer doesn't instantiate the class manually and bypasses Dagger,
 * it's good practice minimise the visibility of the class/constructor as much as possible.
 **/
final public class StatisticsPresenter extends TaskBasePresenter implements StatisticsContract.Presenter {

    @NonNull
    private final StatisticsContract.View statisticsView;

    private final GetStatistics getStatistics;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Inject
    public StatisticsPresenter(UseCaseHandler useCaseHandler, StatisticsContract.View statisticsView,
                        GetStatistics getStatistics) {
        super(useCaseHandler);
        this.statisticsView = statisticsView;
        this.getStatistics = getStatistics;
    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setupListeners() {
        statisticsView.setPresenter(this);
    }

    @Override
    public void start() {
        loadStatistics();
    }

    private void loadStatistics() {
        statisticsView.setProgressIndicator(true);

        execute(getStatistics);
    }

    @Override
    protected void successGetStatistics(int activeTasks, int completedTasks) {
        // The view may not be able to handle UI updates anymore
        if (!statisticsView.isActive()) {
            return;
        }
        statisticsView.setProgressIndicator(false);

        statisticsView.showStatistics(activeTasks, completedTasks);
    }

    @Override
    protected void errorGetStatistics() {
        // The view may not be able to handle UI updates anymore
        if (!statisticsView.isActive()) {
            return;
        }
        statisticsView.showLoadingStatisticsError();
    }
}
