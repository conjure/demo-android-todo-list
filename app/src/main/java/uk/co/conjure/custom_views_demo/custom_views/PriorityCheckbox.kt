package uk.co.conjure.custom_views_demo.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import uk.co.conjure.custom_views_demo.R

open class PriorityCheckbox : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        readAttrs(attrs, 0)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        readAttrs(attrs, 0)
    }

    protected val checkbox: CheckBox
    protected val priorityIcon: ImageView

    var text: String
        get() {
            return checkbox.text.toString()
        }
        set(value) {
            checkbox.text = value
        }

    open var isChecked: Boolean
        get() {
            return checkbox.isChecked
        }
        set(value) {
            checkbox.isChecked = value
        }

    var isPriority: Boolean = true
        set(value) {
            priorityIcon.visibility = if (value) View.VISIBLE else View.GONE
            field = value
        }

    private fun readAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PriorityCheckbox,
            defStyleAttr, 0
        ).apply {
            try {
                text = this.getText(R.styleable.PriorityCheckbox_android_text)?.toString() ?: ""
                isPriority = this.getBoolean(R.styleable.PriorityCheckbox_priority, isPriority)
            } finally {
                recycle()
            }
        }
    }

    init {
        inflate(context, R.layout.compound_priority_checkbox, this)
        checkbox = findViewById(R.id.checkbox)
        priorityIcon = findViewById(R.id.iv_priority)
    }

    open fun setOnCheckedChangeListener(listener: CompoundButton.OnCheckedChangeListener?) {
        checkbox.setOnCheckedChangeListener(listener)
    }
}