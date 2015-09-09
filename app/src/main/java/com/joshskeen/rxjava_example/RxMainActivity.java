package com.joshskeen.rxjava_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.joshskeen.rxjava_example.model.GithubServiceManager;


public class RxMainActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button mLaunchRxButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mLaunchRxButton = (Button) findViewById(R.id.rx_button);
        mLaunchRxButton.setVisibility(View.GONE);
        new GithubServiceManager().rxFetchUserDetails().subscribe(githubUserDetails -> {
            mEditText.setText(githubUserDetails.toString());
        });
    }

}
