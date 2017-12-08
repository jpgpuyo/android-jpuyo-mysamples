package com.jpuyo.mysamples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.jpuyo.mysamples.core.RootActivity;
import com.jpuyo.mysamples.samples.rxpolling.RxPollingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends RootActivity {

    private static final String TAG = HomeActivity.class.getName();

    @BindView(R.id.button_rxjava_polling)
    Button buttonRxJavaPolling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.home_title);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.button_rxjava_polling)
    public void onClickExecute() {
        Intent intent = new Intent(this, RxPollingActivity.class);
        startActivity(intent);
    }
}
