package uk.co.conjure.custom_views_demo.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.*
import uk.co.conjure.custom_views_demo.R

class PriorityCheckbox : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val checkbox: CheckBox
    private val priorityIcon: ImageView

    var text: String
        get() {
            return checkbox.text.toString()
        }
        set(value) {
            checkbox.text = value
        }

    var isChecked: Boolean
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

    init {
        inflate(context, R.layout.compound_priority_checkbox, this)
        checkbox = findViewById(R.id.checkbox)
        priorityIcon = findViewById(R.id.iv_priority)
    }

    fun setOnCheckedChangeListener(listener: CompoundButton.OnCheckedChangeListener?) {
        checkbox.setOnCheckedChangeListener(listener)
    }
}