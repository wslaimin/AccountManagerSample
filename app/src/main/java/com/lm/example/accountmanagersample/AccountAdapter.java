package com.lm.example.accountmanagersample;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.database.DatabaseUtils;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by LM on 2016/9/25.
 */

public class AccountAdapter extends ArrayAdapter<Account>{
    private List<Account> mData;
    private LayoutInflater mInflater;
    private AccountManager mManager;

    public AccountAdapter(Context context, int resource, List<Account> objects) {
        super(context, resource, objects);
        mInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mManager=AccountManager.get(context);
        mData=objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.item_account,parent,false);
        }
        TextView textName=(TextView)convertView.findViewById(R.id.text_name);
        ImageButton btnDelete=(ImageButton)convertView.findViewById(R.id.button_delete);

        textName.setText(mData.get(position).name);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT<22) {
                    mManager.removeAccount(mData.get(position), null, null);
                }else{
                    mManager.removeAccountExplicitly(mData.get(position));
                }
                mData.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public List<Account> getData(){
        return mData;
    }
}
