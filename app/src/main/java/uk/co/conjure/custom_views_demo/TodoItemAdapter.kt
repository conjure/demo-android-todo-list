package uk.co.conjure.custom_views_demo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uk.co.conjure.custom_views_demo.databinding.ItemTodoBinding

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
        todoItems.clear()
        todoItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount() = todoItems.size

    class TodoItemViewHolder(
        private val binding: ItemTodoBinding,
        private val onCheckedChangeListener: OnItemCheckedListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todoItem: TodoItem) {
            binding.root.text = todoItem.name
            binding.root.setOnCheckedChangeListener { _, _ -> }
            binding.root.isChecked = todoItem.done
            binding.root.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked != todoItem.done)
                    onCheckedChangeListener.onCheckChanged(todoItem.id, isChecked)
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                onCheckedChangeListener: OnItemCheckedListener
            ): TodoItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTodoBinding.inflate(layoutInflater, parent, false)
                return TodoItemViewHolder(binding, onCheckedChangeListener)
            }
        }
    }

    class OnItemCheckedListener(val onCheckChanged: (id: Int, checked: Boolean) -> Unit)
}