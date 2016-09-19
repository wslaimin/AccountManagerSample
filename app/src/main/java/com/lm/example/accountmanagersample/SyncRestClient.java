package com.lm.example.accountmanagersample;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

/**
 * Created by 10528 on 2016/9/19.
 */
public class SyncRestClient {
    private static SyncHttpClient sClient=new SyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        sClient.get(url,params,responseHandler);
    }

    public static void post(String url,RequestParams params,AsyncHttpResponseHandler responseHandler){
        sClient.post(url,params,responseHandler);
    }
}
