package com.example.cs205proj;

import android.content.Context;

// for global variables
public class GlobalContext {
    private static GlobalContext instance;
    private Context context;

    private GlobalContext() {}

    public static GlobalContext getInstance() {
        if (instance == null) {
            instance = new GlobalContext();
        }
        return instance;
    }

    public void setContext(Context ctx) {
        this.context = ctx;
    }

    public Context getContext() {
        return context;
    }

}