package com.lm.example.accountmanagersample;

import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AccountAuthenticatorActivity {
    private EditText mEditName,mEditPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){
        mEditName=(EditText)findViewById(R.id.edit_name);
        mEditPsw=(EditText)findViewById(R.id.edit_psw);
        TextView textView=(TextView)findViewById(R.id.text_register);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:註冊頁
            }
        });
        Button button=(Button)findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:登錄
            }
        });
    }
}
