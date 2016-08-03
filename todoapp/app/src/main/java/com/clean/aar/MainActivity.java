package com.clean.aar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.task.ui.Constants;
import com.task.ui.TaskUiHelper;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yangfeng on 16/8/2.
 */
public class MainActivity extends AppCompatActivity {
    private Unbinder unbinder;

    @OnClick(R.id.task_list)
    void onTaskListClicked(View view) {
        TaskUiHelper.gotoTasksActivity(this);
    }

    @OnClick(R.id.statistics)
    void onStatisticsClicked(View view) {
        TaskUiHelper.gotoStatisticsActivity(this);
    }

    @OnClick(R.id.add_task)
    void onAddTaskClicked(View view) {
        TaskUiHelper.gotoAddEditActivity(this, Constants.REQUEST_ADD_TASK);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_ADD_TASK && resultCode == RESULT_OK) {
            TaskUiHelper.gotoTasksActivity(this);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
