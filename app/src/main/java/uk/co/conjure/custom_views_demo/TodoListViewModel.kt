package uk.co.conjure.custom_views_demo

import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel as AndroidViewModel

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoStore: TodoStore
) : AndroidViewModel() {

    fun getTodoList(): LiveData<List<TodoItem>> = todoStore.getTodoItemsLiveData()

    fun clearTodoItems() = todoStore.clearTodoItems()

    fun onTodoCheckedChanged(id: Int, checked: Boolean) =
        todoStore.updateTodoItemChecked(id, checked)
}