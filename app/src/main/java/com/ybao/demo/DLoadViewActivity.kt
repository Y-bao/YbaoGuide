package com.ybao.demo

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import com.ybao.demo.helper.StandardAdapter
import com.ybao.guide.AttachedView
import com.ybao.guide.Guide
import com.ybao.guide.Indicator
import com.ybao.ui.guide.utils.createTagView
import com.ybao.ui.guide.utils.showToastMsg
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by Y-bao on 2017/11/22 0022.
 */

class DLoadViewActivity : AppCompatActivity() {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, DLoadViewActivity::class.java)
        }
    }

    private var h = Handler(Looper.getMainLooper())
    private var s = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var rv = recyclerView {
            lparams {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.MATCH_PARENT
            }
            layoutManager = GridLayoutManager(this@DLoadViewActivity, 2)
        }
        rv.adapter = adapter
        Guide.Builder(this)
                .setBackgroundColor(Color.parseColor("#aa000000"))
                .addIndicator(object : Indicator() {
                    var listener = ViewTreeObserver.OnGlobalLayoutListener {
                        setTargetView(rv.getChildAt(10))
                    }

                    override fun onAttached() {
                        super.onAttached()
                        rv.viewTreeObserver.addOnGlobalLayoutListener(listener)
                    }

                    override fun onDetached() {
                        super.onDetached()
                        rv.viewTreeObserver.removeOnGlobalLayoutListener(listener)
                    }
                }).setMode(Indicator.ROUND_RECTANGLE)
                .setCorners(dip(8))
                .addAttachedView(
                        AttachedView(createTagView("可监听延时加载的控件", 2))
                                .setGravityX(AttachedView.GRAVITY_TO_TARGET, Gravity.CENTER_HORIZONTAL)
                                .setGravityY(AttachedView.GRAVITY_TO_TARGET, Gravity.TOP)
                )
                .addAttachedView(
                        AttachedView(createTagView("无需考虑宽高计算", 4))
                                .setGravityX(AttachedView.GRAVITY_TO_TARGET, Gravity.CENTER_HORIZONTAL)
                                .setGravityY(AttachedView.GRAVITY_TO_TARGET, Gravity.BOTTOM)
                )
                .complete()
                .create()
                .setCancelable(false).show()
        run.run()
    }

    private var run = object : Runnable {
        override fun run() {
            if (s < 0) {
                var list = ArrayList<String>()
                for (i in 0..100) {
                    list.add("$i")
                }
                adapter.data = list
            } else {
                showToastMsg("加载数据倒计时:${s}s")
                h.postDelayed(this, 1000)
                s--
            }
        }
    }

    private var adapter = object : StandardAdapter<StandardAdapter.ItemViewHolder, String>() {
        override fun onBindViewHolder(holder: ItemViewHolder?, position: Int) {
            super.onBindViewHolder(holder, position)
            holder?.itemView?.find<TextView>(0x00000001)?.text = "item : " + getItemData(position)
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): StandardAdapter.ItemViewHolder {
            return StandardAdapter.ItemViewHolder(UI {
                linearLayout {
                    padding = dip(8)
                    gravity = Gravity.CENTER_VERTICAL
                    orientation = LinearLayout.HORIZONTAL
                    imageView {
                        lparams {
                            width = dip(50)
                            height = dip(50)
                        }
                        backgroundResource = R.mipmap.ic_launcher
                    }
                    textView {
                        id = 0x00000001
                        lparams {
                            leftMargin = dip(8)
                        }
                        singleLine = true
                        ellipsize = TextUtils.TruncateAt.END
                    }
                }
            }.view)
        }
    }
}