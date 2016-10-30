package com.lm.example.accountmanagersample;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by LM on 2016/9/25.
 */

public class WelcomeActivity extends AppCompatActivity{
    public static final String KEY_ACCOUNT="KEY_ACCOUNT";
    private TextView mTextToken;
    private Button mBtnLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }

    private void init(){
        final Account account=getIntent().getParcelableExtra(KEY_ACCOUNT);
        final AccountManager am=AccountManager.get(this);
        mTextToken=(TextView)findViewById(R.id.text_token);
        mBtnLogout=(Button)findViewById(R.id.button_logout);
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:登出
                AccountManagerCallback<Bundle> callback=new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        try {
                            String token=future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
                            am.invalidateAuthToken(Constants.ACCOUNT_TYPE,token);
                            finish();
                        } catch (OperationCanceledException | IOException | AuthenticatorException e) {
                            e.printStackTrace();
                        }
                    }
                };
                am.getAuthToken(account,Constants.AUTH_TOKEN_TYPE,null,WelcomeActivity.this,callback,null);
            }
        });

        AccountManagerCallback<Bundle> callback=new AccountManagerCallback<Bundle>() {
            @Override
            public void run(AccountManagerFuture<Bundle> future) {
                try {
                    String token=future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
                    mTextToken.setText(token);
                } catch (OperationCanceledException | IOException | AuthenticatorException e) {
                    e.printStackTrace();
                }
            }
        };

        am.getAuthToken(account,Constants.AUTH_TOKEN_TYPE,null,this,callback,null);
    }
}
