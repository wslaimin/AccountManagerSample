package com.lm.example.accountmanagersample;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by LM on 2016/9/25.
 */

public class RegisterActivity extends AppCompatActivity{
    public static final int REQUEST_ACTIVITY_CODE=0;

    private EditText mEditName,mEditPsw;
    private Button mBtnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init(){
        mEditName=(EditText)findViewById(R.id.edit_name);
        mEditPsw=(EditText)findViewById(R.id.edit_psw);
        mBtnRegister=(Button)findViewById(R.id.button_register);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=mEditName.getText().toString();
                String psw=mEditPsw.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(v.getContext(),"账号不能为空",Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(psw)){
                    Toast.makeText(v.getContext(),"密码不能为空",Toast.LENGTH_LONG).show();
                    return;
                }

                //可以连接服务器进行注册，这里为了简单略过
                Account account=new Account(name,Constants.ACCOUNT_TYPE);
                AccountManager am=AccountManager.get(v.getContext());
                am.addAccountExplicitly(account,psw,null);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_OK);
    }
}
