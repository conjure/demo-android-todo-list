package uk.co.conjure.custom_views_demo.persistence

import androidx.lifecycle.LiveData
import uk.co.conjure.custom_views_demo.TodoItem

interface TodoStore {
    fun getTodoItemsLiveData(): LiveData<List<TodoItem>>
    fun addTodoItem(name: String, priority: Int)
    fun updateTodoItemChecked(id: Int, checked: Boolean)
    fun clearTodoItems()
}