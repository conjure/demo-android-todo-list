package uk.co.conjure.custom_views_demo.todo_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import uk.co.conjure.custom_views_demo.R
import uk.co.conjure.custom_views_demo.custom_views.StrikeoutTextView
import uk.co.conjure.custom_views_demo.TodoItem

class TodoItemAdapter(private val onCheckedChangeListener: OnItemCheckedListener) :
    RecyclerView.Adapter<TodoItemAdapter.TodoItemViewHolder>() {

    private val todoItems = mutableListOf<TodoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        return TodoItemViewHolder.from(parent, onCheckedChangeListener)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.bind(todoItems[position])
    }

    fun onNewList(items: List<TodoItem>) {
        val diffCallback = ListDiffCallback(todoItems, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        todoItems.clear()
        todoItems.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = todoItems.size

    class TodoItemViewHolder(
        private val strikeoutTextView: StrikeoutTextView,
        private val onCheckedChangeListener: OnItemCheckedListener
    ) :
        RecyclerView.ViewHolder(strikeoutTextView) {
        fun bind(todoItem: TodoItem) {
            strikeoutTextView.text = todoItem.name
            strikeoutTextView.setOnCheckedChangeListener(null)
            strikeoutTextView.setChecked(todoItem.done, false)
            strikeoutTextView.setOnCheckedChangeListener { isChecked ->
                onCheckedChangeListener.onCheckChanged(todoItem.id, isChecked)
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                onCheckedChangeListener: OnItemCheckedListener
            ): TodoItemViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_strikeout_todo, parent, false) as StrikeoutTextView
                return TodoItemViewHolder(view, onCheckedChangeListener)
            }
        }
    }

    class OnItemCheckedListener(val onCheckChanged: (id: Int, checked: Boolean) -> Unit)

    private class ListDiffCallback(
        val oldList: List<TodoItem>,
        val newList: List<TodoItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }
    }
}