package com.ybao.guide

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.ybao.guide.utils.createTagView
import org.jetbrains.anko.button
import org.jetbrains.anko.dip
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.scrollView

/**
 * Created by Y-bao on 2017/11/22 0022.
 */
class FollowingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var btn5: Button? = null
        scrollView {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                button("1").lparams(150, 150)
                button("2").lparams(150, 150)
                button("3").lparams(150, 150)
                button("4").lparams(150, 150)
                btn5 = button("5").lparams(150, 150)
                button("6").lparams(150, 150)
                button("7").lparams(150, 150)
                button("8").lparams(150, 150)
                button("9").lparams(150, 150)
                button("10").lparams(150, 150)
            }.lparams {
                width = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }
        Guide.Builder(this)
                .setBackgroundColor(Color.parseColor("#aa000000"))
                .addIndicator(btn5).setMode(Indicator.VIEW_ONESEIF)
                .setCorners(dip(8))
                .addAttachedView(
                        AttachedView(createTagView("我会跟着动", 2))
                                .setGravityX(AttachedView.GRAVITY_TO_TARGET, Gravity.CENTER_HORIZONTAL)
                                .setGravityY(AttachedView.GRAVITY_TO_TARGET, Gravity.CENTER_VERTICAL)
                )
                .complete()
                .create().setTouchPenetrationMode(Guide.TOUCH_PENETRATION_ALL).show()
    }
}