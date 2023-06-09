package com.jll.jlltestapp.utils

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDirections


fun View.show(){
        visibility=View.VISIBLE
    }

    fun View.hide(){
        visibility=View.INVISIBLE
    }
    fun View.gone(){
        visibility=View.GONE
    }
fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}
