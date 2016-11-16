package com.lm.example.accountmanagersample;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.loopj.android.http.SyncHttpClient;

import java.util.Random;

/**
 * Created by 10528 on 2016/9/19.
 */
public class MyAuthenticator extends AbstractAccountAuthenticator{
    private Context mContext;

    public MyAuthenticator(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        Intent intent=new Intent(mContext,AuthenticatorActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,response);
        Bundle bundle=new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT,intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        //在这里可以连接服务器进行身份认证
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        //可以请求服务器获取token,这里为了简单直接返回
        Bundle bundle;
        if(!authTokenType.equals(Constants.AUTH_TOKEN_TYPE)){
            bundle=new Bundle();
            //没有error_code的情况,不会抛出异常
            bundle.putInt(AccountManager.KEY_ERROR_CODE,1);
            bundle.putString(AccountManager.KEY_ERROR_MESSAGE,"invalid authToken");
            return bundle;
        }

        AccountManager am=AccountManager.get(mContext);
        String psw=am.getPassword(account);
        if(!TextUtils.isEmpty(psw)){
            //链接服务器获取token
            Random random=new Random();
            bundle=new Bundle();
            bundle.putString(AccountManager.KEY_AUTHTOKEN,random.nextLong()+"");
            //不返回name和type会报错“the type and name should not be empty”
            bundle.putString(AccountManager.KEY_ACCOUNT_TYPE,account.type);
            bundle.putString(AccountManager.KEY_ACCOUNT_NAME,account.name);
            return bundle;
        }


        bundle=new Bundle();
        Intent intent=new Intent(mContext,AuthenticatorActivity.class);
        bundle.putParcelable(AccountManager.KEY_INTENT,intent);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE,account.type);
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME,account.name);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,response);
        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        return null;
    }
}
