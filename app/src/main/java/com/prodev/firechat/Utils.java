package com.prodev.firechat;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

public class Utils {
    private static Toast toast;

    public static void showToast(Context context, String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();

    }
    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment,int layoutHolder){
        fragmentManager.beginTransaction()
                .replace(layoutHolder, fragment)
                .commit();
    }

}
