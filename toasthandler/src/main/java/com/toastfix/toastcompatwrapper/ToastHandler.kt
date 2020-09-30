package com.toastfix.toastcompatwrapper

import android.content.Context
import android.os.Build
import android.widget.Toast

/**
 * @author Niharika.Arora
 * ToastHandler Singleton for showing toast
 */
object ToastHandler {

    /**
     * Method to show Toast
     */
    @JvmStatic
    fun showToast(context: Context, message: String, length: Int) {
        getToastInstance(context, message, length).show()
    }

    /**
     * Method to return Toast instance
     */
    @JvmStatic
    fun getToastInstance(context: Context, message: String, length: Int): Toast {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            return ToastCompat.makeText(
                context,
                message,
                length
            )
        } else {
            return Toast.makeText(context, message, length)
        }
    }
}