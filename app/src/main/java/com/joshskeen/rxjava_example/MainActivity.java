package com.joshskeen.rxjava_example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.joshskeen.rxjava_example.event.UserDetailsLoadedCompleteEvent;
import com.joshskeen.rxjava_example.model.GithubServiceManager;

import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button mLaunchRxButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mLaunchRxButton = (Button) findViewById(R.id.rx_button);
        mLaunchRxButton.setOnClickListener(v ->
                startActivity(new Intent(this, RxMainActivity.class)));
        new GithubServiceManager().fetchUserDetails();
    }

    public void onEvent(UserDetailsLoadedCompleteEvent event) {
        mEditText.setText(event.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
