package com.nrlm.agey.utils;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AppUtils {
    public static AppUtils utilsInstance;
    private static boolean wantToShow = true;

    public synchronized static AppUtils getInstance() {
        if (utilsInstance == null) {
            utilsInstance = new AppUtils();
        }
        return utilsInstance;
    }

    public void replaceFragment(FragmentManager fragmentManager, Fragment fragment, String tag, boolean addTobackStack, int container) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (addTobackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(container, fragment, tag);
        ft.commit();
    }

    public void showLog(String logMsg, Class application) {
        if (wantToShow) {
            Log.d(application.getName(), logMsg);
        }
    }
}
