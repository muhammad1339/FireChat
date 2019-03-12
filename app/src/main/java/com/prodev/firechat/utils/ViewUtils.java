package com.prodev.firechat.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.prodev.firechat.R;

public class ViewUtils {
    private static Toast toast;

    public static void showToast(Context context, String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int layoutHolder) {
        fragmentManager.beginTransaction()
                .replace(layoutHolder, fragment)
                .commit();
    }

    public static void showOfflineState(Context context, TextView view) {
        view.setText(R.string.no_network);
        view.setBackground(context.getResources().getDrawable(R.color.colorGray));
        view.setVisibility(View.VISIBLE);
    }

    public static void showOnlineState(Context context, TextView view) {
        view.setText(R.string.back_online);
        view.setBackground(context.getResources().getDrawable(R.color.colorGreen));
        view.setVisibility(View.GONE);
        new Thread(() -> {
            try {
                Thread.sleep(1500);
                view.setVisibility(View.GONE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
