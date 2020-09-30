package com.toastfix.toastcompatwrapper

import android.content.Context
import android.content.ContextWrapper
import android.view.Display
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.WindowManager.BadTokenException

/**
 * @author Niharika.Arora
 */
class ToastContextWrapper(base: Context) : ContextWrapper(base) {
    override fun getApplicationContext(): Context {
        return ApplicationContextWrapper(
            baseContext.applicationContext
        )
    }

    private inner class ApplicationContextWrapper(base: Context) :
        ContextWrapper(base) {
        override fun getSystemService(name: String): Any {
            return if (WINDOW_SERVICE == name) {
                // noinspection ConstantConditions
                WindowManagerWrapper(
                    baseContext.getSystemService(
                        name
                    ) as WindowManager
                )
            } else super.getSystemService(name)
        }
    }

    private inner class WindowManagerWrapper(private val base: WindowManager) :
        WindowManager {
        override fun getDefaultDisplay(): Display {
            return base.defaultDisplay
        }

        override fun removeViewImmediate(view: View) {
            base.removeViewImmediate(view)
        }

        override fun addView(view: View, params: ViewGroup.LayoutParams) {
            try {
                base.addView(view, params)
            } catch (e: BadTokenException) {
                // ignore
            }
        }

        override fun updateViewLayout(view: View, params: ViewGroup.LayoutParams) {
            base.updateViewLayout(view, params)
        }

        override fun removeView(view: View) {
            base.removeView(view)
        }
    }
}