package uk.co.conjure.custom_views_demo.todo_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.co.conjure.custom_views_demo.TodoItem
import uk.co.conjure.custom_views_demo.persistence.TodoStore
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoStore: TodoStore
) : ViewModel() {

    fun getTodoList(): LiveData<List<TodoItem>> =
        Transformations.map(todoStore.getTodoItemsLiveData()) {
            it.sortedByDescending { item -> item.priority }
        }

    fun clearTodoItems() = todoStore.clearTodoItems()

    fun onTodoCheckedChanged(id: Int, checked: Boolean) =
        todoStore.updateTodoItemChecked(id, checked)
}