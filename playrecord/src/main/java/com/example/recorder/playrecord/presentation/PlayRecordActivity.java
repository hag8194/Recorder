package com.example.recorder.playrecord.presentation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.recorder.base.RecorderApplication;
import com.example.recorder.base.presentation.base.BaseActivity;
import com.example.recorder.playrecord.R;
import com.example.recorder.playrecord.injection.DaggerPlayRecordComponent;

import javax.inject.Inject;


public class PlayRecordActivity extends BaseActivity {

    @Inject
    String testo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerPlayRecordComponent.builder().activity(this)
                .appComponent(((RecorderApplication) getApplication()).getAppComponent())
                .build().inject(this);

        TextView textView = findViewById(R.id.textView);
        textView.setText(testo == null ? "Nailo" : testo);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.play_record_activity;
    }

}
