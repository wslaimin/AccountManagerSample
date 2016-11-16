package com.lm.example.accountmanagersample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by LM on 2016/10/30.
 */

public class AuthenticatorService extends Service{
    private MyAuthenticator mAuthenticator=new MyAuthenticator(this);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
