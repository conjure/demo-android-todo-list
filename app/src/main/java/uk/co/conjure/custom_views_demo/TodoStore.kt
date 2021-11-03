package uk.co.conjure.custom_views_demo

import androidx.lifecycle.LiveData

interface TodoStore {
    fun getTodoItemsLiveData(): LiveData<List<TodoItem>>
    fun addTodoItem(name: String, priority: Int)
    fun updateTodoItemChecked(id: Int, checked: Boolean)
    fun clearTodoItems()
}