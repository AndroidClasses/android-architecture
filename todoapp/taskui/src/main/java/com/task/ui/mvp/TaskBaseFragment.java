package com.task.ui.mvp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.task.ui.Constants;
import com.task.ui.mvp.addedittask.AddEditTaskActivity;

import butterknife.ButterKnife;

/**
 * Created by yangfeng on 16-8-2.
 */
public class TaskBaseFragment extends Fragment {
    protected void setFloatingActionButton(int buttonResId, int drawableResId, View.OnClickListener onClickListener) {
        FloatingActionButton fab = ButterKnife.findById(getActivity(), buttonResId);
        fab.setImageResource(drawableResId);
        fab.setOnClickListener(onClickListener);
    }

    protected void gotoAddEditActivity(String taskId, int code) {
        Intent intent = new Intent(getContext(), AddEditTaskActivity.class);
        if (!TextUtils.isEmpty(taskId)) {
            intent.putExtra(Constants.ARGUMENT_EDIT_TASK_ID, taskId);
        }
        startActivityForResult(intent, code);
    }
}
