package com.nrlm.agey.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

fun Context.tost(msg:String){
     Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}

fun ProgressBar.show(msg:String){
    visibility =View.VISIBLE
}

fun ProgressBar.hide(){
    visibility =View.GONE
}