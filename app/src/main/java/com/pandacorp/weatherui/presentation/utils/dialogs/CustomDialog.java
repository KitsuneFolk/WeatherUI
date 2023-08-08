package com.pandacorp.weatherui.presentation.utils.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.CallSuper;
import androidx.preference.PreferenceManager;

public abstract class CustomDialog extends Dialog {
    protected SharedPreferences sp;
    protected OnValueAppliedListener onValueAppliedListener;

    public CustomDialog(Context context) {
        super(context);
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setOnValueAppliedListener(OnValueAppliedListener onValueAppliedListener) {
        this.onValueAppliedListener = onValueAppliedListener;
    }

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // remove the default background so dialog can be rounded
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); // remove the shadow
        }
    }

    public interface OnValueAppliedListener {
        void onApplied(String value);
    }

}