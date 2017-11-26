package com.ybao.ui.guide.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.ybao.demo.R

/**
 * Created by Y-bao on 2017/11/22 0022.
 */
var toast: Toast? = null

private fun createToast(context: Context): Toast {
    toast?.cancel()
    toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
    return toast!!
}

fun View.showToastMsg(msg: String) {
    this.context.showToastMsg(msg)
}

fun Context.showToastMsg(msg: String) {
    var toast = createToast(this)
    toast.setText(msg)
    toast.duration = Toast.LENGTH_SHORT
    toast.show()
}


fun View.createTagView(msg: String, d: Int): View {
    return this.context.createTagView(msg, d)
}

fun Context.createTagView(msg: String, d: Int): View {
    val layoutId = when (d) {
        1 ->
            R.layout.view_tag_left
        2 ->
            R.layout.view_tag_top
        3 ->
            R.layout.view_tag_right
        4 ->
            R.layout.view_tag_bottom
        else ->
            R.layout.view_tag_top
    }
    val tagView = LayoutInflater.from(this).inflate(layoutId, null, false)
    val txtTag = tagView.findViewById<TextView>(R.id.txt_tag)
    txtTag.text = msg
    return tagView
}