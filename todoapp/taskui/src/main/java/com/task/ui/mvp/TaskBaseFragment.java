package com.task.ui.mvp;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;

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

}
