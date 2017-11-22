package com.ybao.guide

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.ybao.guide.utils.showToastMsg
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.dip


class LoginActivity : AppCompatActivity() {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val guide = Guide.Builder(this)
                .setBackgroundColor(Color.parseColor("#aa000000"))
                .addIndicator(email, Indicator.RECTANGLE)
                .addAttachedView(
                        AttachedView(createTagView("矩形高亮", 2))
                                .setGravityX(AttachedView.GRAVITY_TO_TARGET, Gravity.LEFT)
                                .setGravityY(AttachedView.GRAVITY_TO_TARGET, Gravity.TOP)
                                .setX(AttachedView.TYPE_PS, -1f)
                )
                .setTag("email")
                .complete()
                .addIndicator(password, Indicator.ROUND_RECTANGLE)
                .setCorners(dip(10))
                .addAttachedView(
                        AttachedView(createTagView("圆角矩形高亮", 4))
                                .setGravityX(AttachedView.GRAVITY_TO_TARGET, Gravity.RIGHT)
                                .setGravityY(AttachedView.GRAVITY_TO_TARGET, Gravity.BOTTOM)
                                .setX(AttachedView.TYPE_PS, -1f)
                )
                .setTag("password")
                .complete()
                .addIndicator(img_tag, Indicator.VIEW_ONESEIF)
                .addAttachedView(
                        AttachedView(createTagView("内容高亮", 3))
                                .setGravityX(AttachedView.GRAVITY_TO_TARGET, Gravity.RIGHT)
                                .setGravityY(AttachedView.GRAVITY_TO_TARGET, Gravity.BOTTOM)
                                .setX(AttachedView.TYPE_PS, -2f)
                                .setY(AttachedView.TYPE_PS, -1.5f)
                )
                .setTag("img_tag")
                .complete()
                .addIndicator(btn_3, Indicator.CIRCLE)
                .addAttachedView(
                        AttachedView(createTagView("圆形高亮", 1))
                                .setGravityX(AttachedView.GRAVITY_TO_TARGET, Gravity.LEFT)
                                .setGravityY(AttachedView.GRAVITY_TO_TARGET, Gravity.CENTER_VERTICAL)
                )
                .setTag("btn_3")
                .complete()
                .addIndicator(btn_5, Indicator.OVAL)
                .setPadding(dip(10))
                .addAttachedView(
                        AttachedView(createTagView("椭圆形高亮", 4))
                                .setGravityX(AttachedView.GRAVITY_TO_TARGET, Gravity.CENTER_HORIZONTAL)
                                .setGravityY(AttachedView.GRAVITY_TO_TARGET, Gravity.BOTTOM)
                )
                .setTag("btn_5")
                .complete()
                .addIndicator(button, Indicator.VIEW_ONESEIF)
                .setTag("button")
                .complete()
                .create()

        guide.setOnClickIndicatorListener { idt ->
            showToastMsg("Click " + idt?.tag)
            true
        }
        guide.show()
        button.setOnClickListener { guide.show() }
    }

    fun createTagView(msg: String, d: Int): View {
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
}
