package com.jpuyo.mysamples.features.polling;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pedrovgs.lynx.LynxConfig;
import com.github.pedrovgs.lynx.LynxView;
import com.jpuyo.android.infrastructure.interactor.DefaultSubscriber;
import com.jpuyo.mysamples.R;
import com.jpuyo.mysamples.features.polling.interactor.GetCurrentUserRequest;
import com.jpuyo.mysamples.features.polling.interactor.GetCurrentUserUseCase;
import com.jpuyo.mysamples.features.polling.model.User;
import com.jpuyo.mysamples.logger.LogMessage;
import com.jpuyo.mysamples.core.RootActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends RootActivity {

    private static final String TAG = MainActivity.class.getName();

    @Inject
    GetCurrentUserUseCase getCurrentUserUseCase;

    @BindView(R.id.radioGroupRequestType)
    RadioGroup radioGroupRequestType;

    @BindView(R.id.requestDescription)
    TextView requestDescription;

    @BindView(R.id.execute)
    Button buttonExecute;

    @BindView(R.id.logView)
    LynxView lynxView;

    private GetCurrentUserRequest getCurrentUserRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.rxjava_polling_title);
        setSupportActionBar(toolbar);

        getCurrentUserRequest = createRequest();
        updateRequestDescription();
        configureLogView();
    }

    private GetCurrentUserRequest createRequest() {
        int request = radioGroupRequestType.getCheckedRadioButtonId();
        GetCurrentUserRequest getCurrentUserRequest = new GetCurrentUserRequest();
        if (request == R.id.requestTypePollingWithDefer) {
            getCurrentUserRequest.setType(GetCurrentUserRequest.POLLING_WITH_REPEAT_WHEN);
            getCurrentUserRequest.setDescription(getString(R.string.polling_with_repeat_when_description));
        } else if (request == R.id.requestTypePollingWithSyncOnSubscribe) {
            getCurrentUserRequest.setType(GetCurrentUserRequest.POLLING_WITH_SYNCONSUBSCRIBE);
            getCurrentUserRequest.setDescription(getString(R.string.polling_with_sync_subscribe_description));
        }
        return getCurrentUserRequest;
    }

    private void updateRequestDescription() {
        requestDescription.setText(getCurrentUserRequest.getDescription());
    }

    private void configureLogView() {
        LynxConfig lynxConfig = lynxView.getLynxConfig()
                .setFilter(getCurrentUserUseCase.getLogTag());
        lynxView.setLynxConfig(lynxConfig);
    }

    @OnClick({R.id.requestTypePollingWithDefer, R.id.requestTypePollingWithSyncOnSubscribe})
    public void onRequestTypeChanged() {
        getCurrentUserRequest = createRequest();
        updateRequestDescription();
    }

    @OnClick(R.id.execute)
    public void onClickExecute() {
        getCurrentUserUseCase.execute(getCurrentUserRequest,
                new GetCurrentUserSubscriber(),
                new LogSubscriber());
        buttonExecute.setClickable(false);
    }

    @OnClick(R.id.clearLog)
    public void onClickClearLog() {
        lynxView.clear();
    }

    private final class GetCurrentUserSubscriber extends DefaultSubscriber<User> {

        public GetCurrentUserSubscriber() {
        }

        @Override
        public void onCompleted() {
            super.onCompleted();
            String message = "Completed";
            Log.d(TAG, message);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            buttonExecute.setClickable(true);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            String message = "onError";
            Log.d(TAG, message);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            buttonExecute.setClickable(true);
        }

        @Override
        public void onNext(User user) {
            super.onNext(user);
            String message = "onNext " + user.getName();
            Log.d(TAG, message);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private class LogSubscriber extends DefaultSubscriber<LogMessage> {

        @Override
        public void onNext(LogMessage logMessage) {
            super.onNext(logMessage);
            Log.d(logMessage.getTag(), logMessage.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getCurrentUserUseCase.unsubscribe();
        getCurrentUserUseCase = null;
    }
}
