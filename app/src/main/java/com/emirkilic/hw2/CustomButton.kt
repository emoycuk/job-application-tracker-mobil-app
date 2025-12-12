package com.emirkilic.hw2

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {
    private var customButtonListener: CustomButtonEventListener? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomButton)

        val customText = typedArray.getString(R.styleable.CustomButton_customText)
        val customColor = typedArray.getColor(R.styleable.CustomButton_customColor, Color.RED)
        val customSizePx = typedArray.getDimension(R.styleable.CustomButton_customSize, 20f)

        typedArray.recycle()

        // Atamaları yap
        text = customText ?: "Special Buton"
        setBackgroundColor(customColor)
        textSize = customSizePx / resources.displayMetrics.scaledDensity  // convert px metric to sp metric
        setTextColor(Color.WHITE)

        setOnClickListener {
            customButtonListener?.oncustomEvent(this)
        }
    }
    interface CustomButtonEventListener {
        fun oncustomEvent(view: View)
    }
    fun setOnCustomEventListener(listener: CustomButtonEventListener) {
        customButtonListener = listener
    }
}

/*
class CustomButton : AppCompatButton {

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val defaultText = "Special Buton"
        val defaultColor = Color.MAGENTA
        val defaultTextSizeSp = 20f

        if (attrs == null) {
            setBackgroundColor(defaultColor)
            setTextColor(Color.WHITE)
            textSize = defaultTextSizeSp
            text = defaultText
        } else {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomButton)
            val customText = typedArray.getString(R.styleable.CustomButton_customText) ?: defaultText
            val customColor = typedArray.getColor(R.styleable.CustomButton_customColor, defaultColor)
            val customTextSizePx = typedArray.getDimension(R.styleable.CustomButton_customSize, defaultTextSizeSp * resources.displayMetrics.scaledDensity)
            typedArray.recycle()

            setBackgroundColor(customColor)
            setTextColor(Color.WHITE)
            textSize = customTextSizePx / resources.displayMetrics.scaledDensity
            text = customText
        }
    }
}

*/



