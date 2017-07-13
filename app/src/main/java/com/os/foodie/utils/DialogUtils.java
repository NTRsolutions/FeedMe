package com.os.foodie.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.TextView;

import com.os.foodie.R;

public class DialogUtils {

    /**
     * Show Alert Dialog
     *
     * @param context
     * @param titleId
     * @param messageId
     */
    public static void showAlert(Context context, int titleId, int messageId) {

        int AlertDialogStyle;

        if (context.getResources().getBoolean(R.bool.is_rtl)) {
            AlertDialogStyle = R.style.AlertDialogRTL;
        } else {
            AlertDialogStyle = R.style.AlertDialog;
        }

        Dialog dlg = new AlertDialog.Builder(context, AlertDialogStyle)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(titleId)
                .setPositiveButton(android.R.string.ok, null)
                .setMessage(messageId)
                .create();

        dlg.show();
    }

    /**
     * Show Alert Dialog Icon
     *
     * @param context
     * @param titleId
     * @param messageId
     */
    public static void showAlert(Context context, int iconId, int titleId, int messageId) {

        int AlertDialogStyle;

        if (context.getResources().getBoolean(R.bool.is_rtl)) {
            AlertDialogStyle = R.style.AlertDialogRTL;
        } else {
            AlertDialogStyle = R.style.AlertDialog;
        }

        Dialog dlg = new AlertDialog.Builder(context, AlertDialogStyle)
                .setIcon(iconId)
                .setTitle(titleId)
                .setPositiveButton(android.R.string.ok, null)
                .setMessage(messageId)
                .create();

        dlg.show();
    }

    /**
     * Show Alert Dialog
     *
     * @param context
     * @param titleId
     * @param message
     */
    public static void showAlert(Context context, int titleId, String message) {

        int AlertDialogStyle;

        if (context.getResources().getBoolean(R.bool.is_rtl)) {
            AlertDialogStyle = R.style.AlertDialogRTL;
        } else {
            AlertDialogStyle = R.style.AlertDialog;
        }

        Dialog dlg = new AlertDialog.Builder(context, AlertDialogStyle)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(titleId)
                .setPositiveButton(android.R.string.ok, null)
                .setMessage(message)
                .create();

        dlg.show();
    }

    /**
     * Show Alert Dialog Icon
     *
     * @param context
     * @param titleId
     * @param message
     */
    public static void showAlert(Context context, int iconId, int titleId, String message) {

        int AlertDialogStyle;

        if (context.getResources().getBoolean(R.bool.is_rtl)) {
            AlertDialogStyle = R.style.AlertDialogRTL;
        } else {
            AlertDialogStyle = R.style.AlertDialog;
        }

        Dialog dlg = new AlertDialog.Builder(context, AlertDialogStyle)
                .setIcon(iconId)
                .setTitle(titleId)
                .setPositiveButton(android.R.string.ok, null)
                .setMessage(message)
                .create();

        dlg.show();
    }

    /**
     * Show Alert Dialog
     *
     * @param context
     * @param titleId
     * @param message
     * @param positiveButtontxt
     * @param positiveListener
     */
    public static void showAlert(Context context, int titleId, String message,
                                 CharSequence positiveButtontxt, DialogInterface.OnClickListener positiveListener) {

        int AlertDialogStyle;

        if (context.getResources().getBoolean(R.bool.is_rtl)) {
            AlertDialogStyle = R.style.AlertDialogRTL;
        } else {
            AlertDialogStyle = R.style.AlertDialog;
        }

        Dialog dlg = new AlertDialog.Builder(context, AlertDialogStyle)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(titleId)
                .setPositiveButton(positiveButtontxt, positiveListener)
                .setMessage(message)
                .setCancelable(true)
                .create();

        dlg.show();
    }

    /**
     * Show Alert Dialog Icon
     *
     * @param context
     * @param titleId
     * @param message
     * @param positiveButtontxt
     * @param positiveListener
     */
    public static void showAlert(Context context, int iconId, int titleId, String message,
                                 CharSequence positiveButtontxt, DialogInterface.OnClickListener positiveListener) {

        int AlertDialogStyle;

        if (context.getResources().getBoolean(R.bool.is_rtl)) {
            AlertDialogStyle = R.style.AlertDialogRTL;
        } else {
            AlertDialogStyle = R.style.AlertDialog;
        }

        Dialog dlg = new AlertDialog.Builder(context, AlertDialogStyle)
                .setIcon(iconId)
                .setTitle(titleId)
                .setPositiveButton(positiveButtontxt, positiveListener)
                .setMessage(message)
                .setCancelable(true)
                .create();

        dlg.show();
    }

    /**
     * Show Alert Dialog
     *
     * @param context
     * @param titleId
     * @param messageId
     * @param positiveButtontxt
     * @param positiveListener
     * @param negativeButtontxt
     * @param negativeListener
     */
    public static void showAlert(Context context, int titleId, int messageId,
                                 CharSequence positiveButtontxt, DialogInterface.OnClickListener positiveListener,
                                 CharSequence negativeButtontxt, DialogInterface.OnClickListener negativeListener) {

        int AlertDialogStyle;

        if (context.getResources().getBoolean(R.bool.is_rtl)) {
            AlertDialogStyle = R.style.AlertDialogRTL;
        } else {
            AlertDialogStyle = R.style.AlertDialog;
        }

        Dialog dlg = new AlertDialog.Builder(context, AlertDialogStyle)
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(titleId)
                .setPositiveButton(positiveButtontxt, positiveListener)
                .setNegativeButton(negativeButtontxt, negativeListener)
                .setMessage(messageId)
                .setCancelable(true)
                .create();

        dlg.show();
    }

    /**
     * Show Alert Dialog Icon
     *
     * @param context
     * @param titleId
     * @param messageId
     * @param positiveButtontxt
     * @param positiveListener
     * @param negativeButtontxt
     * @param negativeListener
     */
    public static void showAlert(Context context, int iconId, int titleId, int messageId,
                                 CharSequence positiveButtontxt, DialogInterface.OnClickListener positiveListener,
                                 CharSequence negativeButtontxt, DialogInterface.OnClickListener negativeListener) {

        int AlertDialogStyle;

        if (context.getResources().getBoolean(R.bool.is_rtl)) {
            AlertDialogStyle = R.style.AlertDialogRTL;
        } else {
            AlertDialogStyle = R.style.AlertDialog;
        }

        Dialog dlg = new AlertDialog.Builder(context, AlertDialogStyle)
                .setIcon(iconId)
                .setTitle(titleId)
                .setPositiveButton(positiveButtontxt, positiveListener)
                .setNegativeButton(negativeButtontxt, negativeListener)
                .setMessage(messageId)
                .setCancelable(true)
                .create();

        dlg.show();
    }

    /**
     * Show Alert Dialog
     *
     * @param context
     * @param titleId
     * @param messageId
     * @param positiveButtontxt
     * @param positiveListener
     * @param negativeButtontxt
     * @param negativeListener
     * @param neutralButtontxt
     * @param neutralListener
     */
    public static void showAlert(Context context, int titleId, int messageId,
                                 CharSequence positiveButtontxt, DialogInterface.OnClickListener positiveListener,
                                 CharSequence negativeButtontxt, DialogInterface.OnClickListener negativeListener,
                                 CharSequence neutralButtontxt, DialogInterface.OnClickListener neutralListener) {

        int AlertDialogStyle;

        if (context.getResources().getBoolean(R.bool.is_rtl)) {
            AlertDialogStyle = R.style.AlertDialogRTL;
        } else {
            AlertDialogStyle = R.style.AlertDialog;
        }

        Dialog dlg = new AlertDialog.Builder(context, AlertDialogStyle)
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(titleId)
                .setPositiveButton(positiveButtontxt, positiveListener)
                .setNegativeButton(negativeButtontxt, negativeListener)
                .setNeutralButton(neutralButtontxt, neutralListener)
                .setMessage(messageId)
                .setCancelable(true)
                .create();

        dlg.show();
    }

    /**
     * Show Alert Dialog Icon
     *
     * @param context
     * @param titleId
     * @param messageId
     * @param positiveButtontxt
     * @param positiveListener
     * @param negativeButtontxt
     * @param negativeListener
     * @param neutralButtontxt
     * @param neutralListener
     */
    public static void showAlert(Context context, int iconId, int titleId, int messageId,
                                 CharSequence positiveButtontxt, DialogInterface.OnClickListener positiveListener,
                                 CharSequence negativeButtontxt, DialogInterface.OnClickListener negativeListener,
                                 CharSequence neutralButtontxt, DialogInterface.OnClickListener neutralListener) {

        int AlertDialogStyle;

        if (context.getResources().getBoolean(R.bool.is_rtl)) {
            AlertDialogStyle = R.style.AlertDialogRTL;
        } else {
            AlertDialogStyle = R.style.AlertDialog;
        }

        Dialog dlg = new AlertDialog.Builder(context, AlertDialogStyle)
                .setIcon(iconId)
                .setTitle(titleId)
                .setPositiveButton(positiveButtontxt, positiveListener)
                .setNegativeButton(negativeButtontxt, negativeListener)
                .setNeutralButton(neutralButtontxt, neutralListener)
                .setMessage(messageId)
                .setCancelable(true)
                .create();

        dlg.show();
    }
}