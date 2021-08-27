package uk.co.conjure.custom_views_demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class TodoListViewModel {
    private val todoList = MutableLiveData<List<TodoItem>>(emptyList())

    private var lastTodoIndex = 0

    fun getTodoList(): LiveData<List<TodoItem>> {
        return todoList
    }

    fun onAddTodo() {
        val list = (todoList.value?.toMutableList() ?: mutableListOf())
        val id = ++lastTodoIndex
        list.add(0, TodoItem(id, "Todo list item: $id", false))
        todoList.postValue(list)
    }

    fun onTodoCheckedChanged(id: Int, checked: Boolean) {
        val list: MutableList<TodoItem> = (todoList.value?.toMutableList() ?: mutableListOf())
        val index = list.indexOfFirst { it.id == id }
        if (index >= 0 && list[index].done != checked) {
            val oldItem = list.removeAt(index)
            list.add(index, oldItem.copy(done = checked))
            todoList.postValue(list)
        }
    }
}