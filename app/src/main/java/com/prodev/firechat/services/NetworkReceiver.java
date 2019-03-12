package com.prodev.firechat.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetworkReceiver extends BroadcastReceiver {
    public static final String TAG = NetworkReceiver.class.getSimpleName();
    private Context mContext;

    public NetworkReceiver() {
    }

    public NetworkReceiver(Context context) {
        this.mContext = context;
        mCallback = (NetworkCallback) mContext;
    }

    public interface NetworkCallback {
        void onNetworkConnected();

        void onNetworkDisconnected();
    }

    NetworkCallback mCallback;

    @Override
    public void onReceive(Context context, Intent intent) {
//        boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
//        String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
//        boolean isFailover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);
        NetworkInfo currentNetworkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
        NetworkInfo otherNetworkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);
        if (currentNetworkInfo.isConnected()) {
            Log.d(TAG, "onReceive: " + "Connected......");
            mCallback.onNetworkConnected();
        } else {
            Log.d(TAG, "onReceive: " + "Lost......");
            //sent to view to inflate network state
            mCallback.onNetworkDisconnected();
        }
    }
}
