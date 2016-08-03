package com.task.ui;

import android.app.Activity;
import android.content.Context;

import com.common.ui.util.ActivityUtils;
import com.task.ui.mvp.addedittask.AddEditTaskActivity;
import com.task.ui.mvp.statistics.StatisticsActivity;
import com.task.ui.mvp.tasks.TasksActivity;

/**
 * Created by yangfeng on 16/8/3.
 */
public class TaskUiHelper {
    private TaskUiHelper() {
        // EMPTY
    }

    public static void gotoTasksActivity(Context context) {
        ActivityUtils.startActivity(context, TasksActivity.class);
    }

    public static void gotoStatisticsActivity(Context context) {
        ActivityUtils.startActivity(context, StatisticsActivity.class);
    }

    public static void gotoAddEditActivity(Activity context, int code) {
        ActivityUtils.startActivityForResult(context, AddEditTaskActivity.class, code);
    }
}
