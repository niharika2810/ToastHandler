package com.toastfix.toastcompatwrapper

import android.content.Context
import android.os.Build
import android.widget.Toast

/**
 * @author Niharika.Arora
 * ToastHandler class for showing toast
 */
class ToastHandler {

    companion object {

        @JvmStatic
        val INSTANCE: ToastHandler by lazy {
            ToastHandler()
        }
    }

    /**
     * Method to show Toast
     */
    fun showToast(context: Context, message: String, length: Int) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            ToastCompat.makeText(
                context,
                message,
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(context, message, length).show()
        }
    }

    /**
     * Method to return Toast instance
     */
    fun getToastInstance(context: Context, message: String, length: Int): Toast {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            return ToastCompat.makeText(
                context,
                message,
                Toast.LENGTH_SHORT
            )
        } else {
            return Toast.makeText(context, message, length)
        }
    }
}