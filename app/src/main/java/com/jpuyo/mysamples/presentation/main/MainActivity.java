package com.jpuyo.mysamples.presentation.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.jpuyo.mysamples.R;
import com.jpuyo.mysamples.domain.interactor.users.GetCurrentUserCase;
import com.jpuyo.mysamples.domain.interactor.users.model.User;
import com.jpuyo.mysamples.infrastructure.RootActivity;
import com.jpuyo.android.infrastructure.interactor.DefaultSubscriber;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends RootActivity {

    private static final String TAG = MainActivity.class.getName();

    @Inject
    GetCurrentUserCase getCurrentUserCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.execute)
    public void onClickExecute() {
        getCurrentUserCase.execute(new GetCurrentUserSubscriber());
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
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            String message = "onError";
            Log.d(TAG, message);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(User user) {
            super.onNext(user);
            String message = "onNext " + user.getName();
            Log.d(TAG, message);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getCurrentUserCase.unsubscribe();
        getCurrentUserCase = null;
    }
}
