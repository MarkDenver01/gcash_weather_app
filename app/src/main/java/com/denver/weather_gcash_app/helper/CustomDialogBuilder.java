package com.denver.weather_gcash_app.helper;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.denver.weather_gcash_app.R;
import com.denver.weather_gcash_app.domain.enums.AppStatus;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import kotlin.Unit;
import timber.log.Timber;

public class CustomDialogBuilder {
    private static AlertDialog mAlertDialogMessage;
    private static Disposable mDisposableAlertDialogMessage;

    public static void oneButtonDialogBox(Context context, AppStatus appStatus, String message, Runnable runnable) {
        if (mAlertDialogMessage != null && mAlertDialogMessage.isShowing()) {
            return;
        }
        LayoutInflater factory = LayoutInflater.from(context);
        final View alertDialogView = factory.inflate(R.layout.custom_alert_dialog, null);

        ImageView resource = alertDialogView.findViewById(R.id.image_alert);
        if (appStatus.equals(AppStatus.SUCCESS)) {
            resource.setImageDrawable(context.getDrawable(R.drawable.check_alert));
        } else if (appStatus.equals(AppStatus.NO_PERMISSION_ALLOWED)) {
            resource.setImageDrawable(context.getDrawable(R.drawable.no_location));
        } else if (appStatus.equals(AppStatus.LOCATION_REQUEST_WARNING)) {
            resource.setImageDrawable(context.getDrawable(R.drawable.warning));
        } else {
            resource.setImageDrawable(context.getDrawable(R.drawable.close));
        }
        TextView txtMessage = alertDialogView.findViewById(R.id.text_message);
        txtMessage.setText(message);
        Button btnClose = alertDialogView.findViewById(R.id.button_close);

        mAlertDialogMessage = new AlertDialog.Builder(context).create();
        mAlertDialogMessage.setView(alertDialogView);

        mDisposableAlertDialogMessage = (RxView.clicks(btnClose)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Unit>() {
                    @Override
                    public void accept(Unit unit) throws Exception {
                        if (runnable != null) {
                            runnable.run();
                        }
                        dispose(mDisposableAlertDialogMessage);
                        dispose(mAlertDialogMessage);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable);
                    }
                }));

        mAlertDialogMessage.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mAlertDialogMessage.setCancelable(false);
        mAlertDialogMessage.show();
    }

    public static void dispose(final Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public static void dispose(final AlertDialog alertDialog) {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}
