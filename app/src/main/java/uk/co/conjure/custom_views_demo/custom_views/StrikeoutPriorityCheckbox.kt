package uk.co.conjure.custom_views_demo.custom_views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.CompoundButton
import androidx.core.view.doOnLayout
import uk.co.conjure.custom_views_demo.R

class StrikeoutPriorityCheckbox : PriorityCheckbox {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        readAttrs(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        readAttrs(attrs, defStyleAttr)
    }

    private var strikeoutAnimator: ValueAnimator? = null
    private var listener: CompoundButton.OnCheckedChangeListener? = null
    private var strikeoutScale: Float = 0f

    private var strikeoutColor: Int = Color.BLACK
    private var strikeoutWidth: Float = 1f
    private var strikeoutStart = 0f
    private var strikeoutEnd = 0f

    private val linePaint by lazy {
        Paint().apply {
            strokeWidth = strikeoutWidth
            color = strikeoutColor
        }
    }

    private fun readAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.StrikeoutPriorityCheckbox,
            defStyleAttr, 0
        ).apply {
            try {
                strikeoutWidth = this.getDimension(
                    R.styleable.StrikeoutPriorityCheckbox_strikeoutWidth,
                    strikeoutWidth
                )
                strikeoutColor = this.getColor(
                    R.styleable.StrikeoutPriorityCheckbox_strikeoutColor,
                    strikeoutColor
                )
            } finally {
                recycle()
            }
        }
    }

    init {
        //This flag is used by implementations of ViewGroup which we are inheriting
        // we must set it to false or onDraw will not be called
        setWillNotDraw(false)
        checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            animateStrikeout()
            listener?.onCheckedChanged(buttonView, isChecked)
        }
        checkbox.doOnLayout {
            strikeoutStart = checkbox.x
            strikeoutEnd = strikeoutStart + checkbox.width
        }
    }

    private fun animateStrikeout() {
        if (isChecked) animateStrikeout(true)
        else animateStrikeout(false)
        //We must call invalidate to make sure onDraw is called again
        invalidate()
    }

    private fun animateStrikeout(animateIn: Boolean) {
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
        val drawableWidth = strikeoutEnd - strikeoutStart
        val stopX = strikeoutStart + (drawableWidth * strikeoutScale)
        val midY = this.height / 2f
        canvas?.drawLine(strikeoutStart, midY, stopX, midY, linePaint)
    }

    override fun setOnCheckedChangeListener(listener: CompoundButton.OnCheckedChangeListener?) {
        this.listener = listener
    }
}