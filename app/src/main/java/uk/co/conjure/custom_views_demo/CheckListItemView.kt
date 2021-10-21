package uk.co.conjure.custom_views_demo

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewPropertyAnimator
import android.widget.Checkable
import android.widget.FrameLayout
import android.widget.TextView

class CheckListItemView : FrameLayout, Checkable {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val name: TextView
    private val strikethrough: View

    var text: String
        get() {
            return name.text.toString()
        }
        set(value) {
            name.text = value
        }

    private var isChecked = false
    private var animation: ViewPropertyAnimator? = null
    private var checkedChangeListener: ((Boolean) -> Unit)? = null

    init {
        inflate(context, R.layout.item_todo, this)
        name = findViewById(R.id.tv_item_name)
        strikethrough = findViewById(R.id.strike_through)

        strikethrough.visibility = View.VISIBLE
        strikethrough.pivotX = 0f
        strikethrough.scaleX = 0f

        setOnClickListener { toggle() }
    }

    fun setChecked(checked: Boolean, animate: Boolean) {
        if (checked == isChecked) return
        isChecked = checked

        if (animate) {
            if (isChecked) animateStrikeThrough(true)
            else animateStrikeThrough(false)
        } else {
            strikethrough.scaleX = if (isChecked) 1f else 0f
        }

        checkedChangeListener?.invoke(isChecked)
    }

    override fun setChecked(checked: Boolean) {
        setChecked(checked, true)
    }

    private fun animateStrikeThrough(animateIn: Boolean) {
        animation?.cancel()
        strikethrough.scaleX = if (animateIn) 0f else 1f
        animation = strikethrough.animate()
            .scaleX(if (animateIn) 1f else 0f)
            .setDuration(resources.getInteger(android.R.integer.config_mediumAnimTime).toLong())
        animation?.start()
    }

    override fun isChecked() = isChecked

    override fun toggle() = setChecked(!isChecked)

    fun setOnCheckedChangeListener(listener: ((Boolean) -> Unit)?) {
        checkedChangeListener = listener
    }
}