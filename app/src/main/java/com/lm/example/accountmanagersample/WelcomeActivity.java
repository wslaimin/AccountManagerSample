package com.lm.example.accountmanagersample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by LM on 2016/9/25.
 */

public class WelcomeActivity extends AppCompatActivity{
    private TextView mTextToken;
    private Button mBtnLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    private void init(){
        mTextToken=(TextView)findViewById(R.id.text_token);
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:登出
            }
        });
    }
}
