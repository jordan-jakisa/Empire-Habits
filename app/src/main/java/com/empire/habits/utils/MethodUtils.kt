package com.empire.habits.utils

import android.view.View

abstract class MethodUtils {
    fun isVisible(view: View): Boolean {
        return view.visibility == View.VISIBLE
    }

    fun isGone(view: View): Boolean {
        return view.visibility == View.GONE
    }

}