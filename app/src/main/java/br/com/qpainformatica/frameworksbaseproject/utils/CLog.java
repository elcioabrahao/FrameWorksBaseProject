package br.com.qpainformatica.frameworksbaseproject.utils;

import android.util.Log;

import br.com.qpainformatica.frameworksbaseproject.BuildConfig;

/**
 * Created by elcio on 14/05/15.
 */
public class CLog {
    private static final boolean DEBUG = BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug");
    private static String TAG = "ACTOMOBILE";

    public static void d(Object o) {
        if(DEBUG) {
            Log.d(TAG, o.toString());
        }
    }

    public static void v(Object o) {
        if(DEBUG) {
            Log.v(TAG, o.toString());
        }
    }

    public static void i(Object o) {
        if(DEBUG) {
            Log.i(TAG, o.toString());
        }
    }

    public static void e(Object o) {
        if(DEBUG) {
            Log.e(TAG, o.toString());
        }
    }

    public static void w(Object o) {
        if(DEBUG) {
            Log.w(TAG, o.toString());
        }
    }

    public static void d(Object ot, Object o) {
        if(DEBUG) {
            Log.d(ot.toString(), o.toString());
        }
    }

    public static void v(Object ot, Object o) {
        if(DEBUG) {
            Log.v(ot.toString(), o.toString());
        }
    }

    public static void i(Object ot, Object o) {
        if(DEBUG) {
            Log.i(ot.toString(), o.toString());
        }
    }

    public static void e(Object ot, Object o) {
        if(DEBUG) {
            Log.e(ot.toString(), o.toString());
        }
    }

    public static void w(Object ot, Object o) {
        if(DEBUG) {
            Log.w(ot.toString(), o.toString());
        }
    }


}