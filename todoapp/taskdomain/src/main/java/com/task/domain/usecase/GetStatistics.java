package com.task.domain.usecase;

import android.support.annotation.NonNull;

import com.clean.common.usecase.UseCase;
import com.repository.task.data.TasksDataSource;
import com.repository.task.data.TasksRepository;
import com.repository.task.model.Statistics;
import com.repository.task.model.Task;

import java.util.List;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Calculate statistics of active and completed Tasks {@link Task} in the {@link TasksRepository}.
 */
public class GetStatistics extends UseCase<UseCase.EmptyRequestValues, GetStatistics.ResponseValue> {

    private final TasksRepository repository;

    @Inject public GetStatistics(@NonNull TasksRepository tasksRepository) {
        repository = checkNotNull(tasksRepository, "tasksRepository cannot be null!");
    }

    @Override
    protected void executeUseCase(UseCase.EmptyRequestValues requestValues) {
        repository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {

                int activeTasks = 0;
                int completedTasks = 0;

                // We calculate number of active and completed tasks
                for (Task task : tasks) {
                    if (task.isCompleted()) {
                        completedTasks += 1;
                    } else {
                        activeTasks += 1;
                    }
                }

                ResponseValue responseValue = new ResponseValue(new Statistics(completedTasks, activeTasks));
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static class ResponseValue implements UseCase.ResponseValue {

        private final Statistics statistics;

        public ResponseValue(@NonNull Statistics statistics) {
            this.statistics = checkNotNull(statistics, "statistics cannot be null!");
        }

        public Statistics getStatistics() {
            return statistics;
        }
    }
}
