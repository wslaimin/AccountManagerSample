package com.lm.example.accountmanagersample;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthenticatorActivity extends AccountAuthenticatorActivity {
    private EditText mEditName, mEditPsw;
    private TextView mTextRegister;
    private Button mBtnLogin;
    private AccountPopWindow mPop;
    private ImageButton mBtnAll;
    private AccountManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        init();
    }

    private void init() {
        mManager = AccountManager.get(this);

        mEditName = (EditText) findViewById(R.id.edit_name);
        mEditPsw = (EditText) findViewById(R.id.edit_psw);
        mTextRegister = (TextView) findViewById(R.id.text_register);
        mBtnLogin = (Button) findViewById(R.id.button_login);

        mTextRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthenticatorActivity.this, RegisterActivity.class);
                startActivityForResult(intent, RegisterActivity.REQUEST_ACTIVITY_CODE);
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录
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
                //可以请求服务器获取token
                Account account=new Account(name,Constants.ACCOUNT_TYPE);
                AccountManagerCallback<Bundle> callback=new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        try {
                            String token=future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
                            Intent intent=new Intent(AuthenticatorActivity.this,WelcomeActivity.class);
                            Account account1=new Account(future.getResult().getString(AccountManager.KEY_ACCOUNT_NAME),
                                    future.getResult().getString(AccountManager.KEY_ACCOUNT_TYPE));
                            intent.putExtra(WelcomeActivity.KEY_ACCOUNT,account1);
                            startActivity(intent);
                        } catch (OperationCanceledException | IOException | AuthenticatorException e) {
                            e.printStackTrace();
                        }
                    }
                };
                mManager.getAuthToken(account,Constants.AUTH_TOKEN_TYPE,null,AuthenticatorActivity.this,callback,null);
            }
        });


        mPop = new AccountPopWindow(this, new ArrayList<Account>());

        mBtnAll = (ImageButton) findViewById(R.id.button_all);
        mBtnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(AuthenticatorActivity.this, Manifest.permission.GET_ACCOUNTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(AuthenticatorActivity.this, new String[]{Manifest.permission.GET_ACCOUNTS},
                            Constants.ACCOUNT_PERMISSION_CODE);
                } else {
                    showPop();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode==Constants.ACCOUNT_PERMISSION_CODE){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                showPop();
            }
        }
    }

    private void showPop() {
        Account[] accounts = mManager.getAccountsByType(Constants.ACCOUNT_TYPE);
        mPop.updateData(Arrays.asList(accounts));
        mPop.showAsDropDown(mBtnAll);
    }

    class AccountPopWindow extends PopupWindow{
        private AccountAdapter mAdapter;

        AccountPopWindow(Context context,List<Account> accounts){
            super(context);
            //参数是MeasureSpec
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            ListView listView=(ListView) inflater.inflate(R.layout.pop_account,null);
            mAdapter=new AccountAdapter(AuthenticatorActivity.this,R.layout.item_account,accounts);
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Account account=((AccountAdapter) parent.getAdapter()).getItem(position);
                    if(account!=null) {
                        mEditName.setText(account.name);
                        mEditPsw.setText(mManager.getPassword(account));
                    }
                    dismiss();
                }
            });
            setContentView(listView);
            setTouchable(true);
            setOutsideTouchable(true);
            setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }

        void updateData(List<Account> accounts){
            mAdapter.getData().clear();
            mAdapter.getData().addAll(accounts);
        }

    }
}
