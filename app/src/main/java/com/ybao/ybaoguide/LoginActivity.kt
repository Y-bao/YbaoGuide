package com.ybao.ybaoguide

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.ybao.guide.Guide
import com.ybao.guide.Indicator
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var guide = Guide.Builder(this)
                .setBackgroundColor(Color.parseColor("#99000000"))
                .addIndicator(email, Indicator.RECTANGLE)
                .complete()
                .addIndicator(password, Indicator.ROUND_RECTANGLE)
                .setCorners(20)
                .complete()
                .addIndicator(img_tag, Indicator.VIEW_ONESEIF)
                .setPadding(20)
                .complete()
                .addIndicator(btn_3, Indicator.CIRCLE)
                .complete()
                .addIndicator(btn_5, Indicator.OVAL)
                .setPadding(20)
                .complete()
                .addIndicator(button, Indicator.VIEW_ONESEIF)
                .complete()
                .create()
        guide.show()
        button.setOnClickListener { guide.show() }
    }

    fun createTagView(msg: String, d: Int): View {
        var tagView = LayoutInflater.from(this).inflate(R.layout.view_tag, null, false) as LinearLayout
        var txtTag = tagView.findViewById<TextView>(R.id.txt_tag)
        var vIn = tagView.findViewById<View>(R.id.v_in)
        when (d) {
            1 ->
                tagView.orientation = LinearLayout.HORIZONTAL
            in 1..3->
                tagView.orientation = LinearLayout.HORIZONTAL
        }
    }
}
