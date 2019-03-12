package com.prodev.firechat.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class FunctionUtils {
    public static boolean isConnectedToInternet(Context context) {
        boolean netState = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            netState = true;
        }
        return netState;
    }

    public static boolean IS_ONLINE = false;

    public static boolean isOnline() {

        new Thread(() -> {
            try {
                int timeoutMs = 1500;
                Socket sock = new Socket();
                SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);
                sock.connect(sockaddr, timeoutMs);
                sock.close();
                IS_ONLINE = true;
            } catch (IOException e) {
                IS_ONLINE = false;
            }
        }).start();
        return IS_ONLINE;
    }

}
