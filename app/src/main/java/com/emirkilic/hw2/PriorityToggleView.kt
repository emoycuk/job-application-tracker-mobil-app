package com.emirkilic.hw2

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.emirkilic.hw2.R

class PriorityToggleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val tvPriority: TextView
    private var current: String = "NORMAL"

    private var listener: ((String) -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_priority_toggle, this, true)
        tvPriority = findViewById(R.id.tvPriority)

        orientation = HORIZONTAL
        setPriority("NORMAL")

        tvPriority.setOnClickListener {
            toggle()
            listener?.invoke(current)
        }
    }

    fun setPriority(value: String) {
        current = value.uppercase()
        render()
    }

    fun getPriority(): String = current

    fun setOnPriorityChangedListener(l: (String) -> Unit) {
        listener = l
    }

    private fun toggle() {
        current = if (current == "HIGH") "NORMAL" else "HIGH"
        render()
    }

    private fun render() {
        tvPriority.text = current
        if (current == "HIGH") {
            tvPriority.setBackgroundColor(Color.parseColor("#D32F2F")) // kırmızı
            tvPriority.setTextColor(Color.WHITE)
        } else {
            tvPriority.setBackgroundColor(Color.parseColor("#455A64")) // gri
            tvPriority.setTextColor(Color.WHITE)
        }
    }
}