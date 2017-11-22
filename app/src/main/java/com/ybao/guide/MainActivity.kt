package com.ybao.guide

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.LinearLayout
import org.jetbrains.anko.button
import org.jetbrains.anko.dip
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.onClick

/**
 * Created by Y-bao on 2017/11/22 0022.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayout {
            orientation = LinearLayout.VERTICAL
            button("各种高亮样式展示") {
                onClick { startActivity(LoginActivity.newIntent(this@MainActivity)) }
            }.lparams {
                width = ViewGroup.LayoutParams.MATCH_PARENT
            }
            button("监听延时加载的控件") {
                onClick { startActivity(AnkoActivity.newIntent(this@MainActivity)) }
            }.lparams {
                topMargin = dip(10)
                width = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }
    }
}