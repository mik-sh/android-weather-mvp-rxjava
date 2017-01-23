package com.miksh.weather.weather_list;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.miksh.weather.R;

/**
 * Created by mik.sh on 23/01/2017.
 */

public class ErrorDialogFragment extends DialogFragment {

    public static ErrorDialogFragment newInstance() {
        return new ErrorDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.error_title)
                .setMessage(R.string.error_message)
                .setPositiveButton(R.string.ok_btn, (dialog, which) -> dialog.dismiss())
                .create();
    }
}
