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

package com.task.domain.usecase;

import android.support.annotation.NonNull;

import com.clean.common.usecase.UseCase;
import com.repository.task.data.TasksDataSource;
import com.repository.task.data.TasksRepository;
import com.repository.task.model.Task;
import com.task.domain.usecase.filter.FilterFactory;
import com.task.domain.usecase.filter.TaskFilter;
import com.task.domain.usecase.filter.TasksFilterType;

import java.util.List;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Fetches the list of tasks.
 */
public class GetTasks extends UseCase<GetTasks.RequestValues, GetTasks.ResponseValue> {

    private final TasksRepository repository;

    private final FilterFactory filterFactory;

    @Inject
    public GetTasks(@NonNull TasksRepository tasksRepository) {
        this(tasksRepository, new FilterFactory());
    }

    public GetTasks(@NonNull TasksRepository tasksRepository, @NonNull FilterFactory filterFactory) {
        this.repository = checkNotNull(tasksRepository, "tasksRepository cannot be null!");
        this.filterFactory = checkNotNull(filterFactory, "filterFactory cannot be null!");
    }

    @Override
    protected void executeUseCase(final RequestValues values) {
        if (values.isForceUpdate()) {
            repository.refreshTasks();
        }

        repository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                TasksFilterType currentFiltering = values.getCurrentFiltering();
                TaskFilter taskFilter = filterFactory.create(currentFiltering);

                List<Task> tasksFiltered = taskFilter.filter(tasks);
                ResponseValue responseValue = new ResponseValue(tasksFiltered);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });

    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final TasksFilterType currentFiltering;
        private final boolean isForceUpdate;

        public RequestValues(boolean forceUpdate, @NonNull TasksFilterType currentFiltering) {
            isForceUpdate = forceUpdate;
            this.currentFiltering = checkNotNull(currentFiltering, "currentFiltering cannot be null!");
        }

        public boolean isForceUpdate() {
            return isForceUpdate;
        }

        public TasksFilterType getCurrentFiltering() {
            return currentFiltering;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<Task> tasks;

        public ResponseValue(@NonNull List<Task> tasks) {
            this.tasks = checkNotNull(tasks, "tasks cannot be null!");
        }

        public List<Task> getTasks() {
            return tasks;
        }
    }
}
