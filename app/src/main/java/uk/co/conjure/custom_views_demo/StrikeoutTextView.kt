package uk.co.conjure.custom_views_demo

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.Checkable
import androidx.appcompat.widget.AppCompatTextView

class StrikeoutTextView : AppCompatTextView, Checkable {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var strikeoutAnimator: ValueAnimator? = null
    private var isChecked = false
    private var checkedChangeListener: ((Boolean) -> Unit)? = null
    private var strikeoutScale: Float = 0f
    private val linePaint = Paint().apply {
        strokeWidth = 1.0f
        color = Color.BLACK
    }

    init {
        setOnClickListener { toggle() }
    }

    fun setChecked(checked: Boolean, animate: Boolean) {
        if (checked == isChecked) return
        isChecked = checked

        if (animate) {
            if (isChecked) animateStrikeThrough(true)
            else animateStrikeThrough(false)
        } else {
            strikeoutScale = if (isChecked) 1f else 0f
        }

        //We must call invalidate to make sure onDraw is called again
        invalidate()
        checkedChangeListener?.invoke(isChecked)
    }

    private fun animateStrikeThrough(animateIn: Boolean) {
        strikeoutAnimator?.cancel()
        strikeoutScale = if (animateIn) 0f else 1f
        val endScale = if (animateIn) 1f else 0f
        strikeoutAnimator = ValueAnimator.ofFloat(strikeoutScale, endScale).apply {
            duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
            addUpdateListener {
                strikeoutScale = it.animatedValue as Float
                //We must call invalidate to make sure onDraw is called again
                invalidate()
            }
        }

        strikeoutAnimator?.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas) // This will draw the text for us
        val startX = this.paddingLeft.toFloat()
        val endView = this.width.toFloat() - this.paddingRight.toFloat()
        val drawableWidth = endView - startX
        val stopX = startX + (drawableWidth * strikeoutScale)
        val midY = this.height / 2f
        canvas?.drawLine(startX, midY, stopX, midY, linePaint)
    }

    override fun setChecked(checked: Boolean) = setChecked(checked, true)

    override fun isChecked() = isChecked

    override fun toggle() = setChecked(!isChecked)

    fun setOnCheckedChangeListener(listener: ((Boolean) -> Unit)?) {
        checkedChangeListener = listener
    }
}