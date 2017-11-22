package com.ybao.guide

import android.content.Context
import android.content.Intent
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
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, FollowingActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var btn5: Button? = null
        scrollView {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER_HORIZONTAL
                var size=dip(150)
                button("1").lparams(size, size)
                button("2").lparams(size, size)
                button("3").lparams(size, size)
                button("4").lparams(size, size)
                btn5 = button("5").lparams(size, size)
                button("6").lparams(size, size)
                button("7").lparams(size, size)
                button("8").lparams(size, size)
                button("9").lparams(size, size)
                button("10").lparams(size, size)
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