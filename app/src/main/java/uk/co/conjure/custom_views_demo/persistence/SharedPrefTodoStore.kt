package uk.co.conjure.custom_views_demo.persistence

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import uk.co.conjure.custom_views_demo.TodoItem
import javax.inject.Inject

class SharedPrefTodoStore @Inject constructor(
    @ApplicationContext private val context: Context
) : TodoStore {

    private val gson = Gson()
    private val todoListItemType = object : TypeToken<List<TodoItem>>() {}.type

    private val todoItemsLiveData = MutableLiveData<List<TodoItem>>()
    private val sharedPrefs = context
        .getSharedPreferences("todo-store", Context.MODE_PRIVATE)
        .apply {
            registerOnSharedPreferenceChangeListener { _, _ ->
                todoItemsLiveData.postValue(readTodoItems())
            }
        }

    override fun getTodoItemsLiveData(): LiveData<List<TodoItem>> = todoItemsLiveData

    override fun addTodoItem(name: String, priority: Int) {
        val currentItems = readTodoItems()
        val lastId = currentItems.maxOf { it.id }
        val newItem = TodoItem(lastId + 1, name, priority, false)
        writeTodoItems(currentItems.plus(newItem))
    }

    override fun updateTodoItemChecked(id: Int, checked: Boolean) {
        val items = readTodoItems().toMutableList()
        val indexOfItem = items.indexOfFirst { it.id == id }
        if (indexOfItem == -1) return
        items[indexOfItem] = items[indexOfItem].copy(done = checked)
        writeTodoItems(items)
    }

    override fun clearTodoItems() {
        writeTodoItems(emptyList())
    }

    private fun readTodoItems(): List<TodoItem> {
        return sharedPrefs.getString(TODO_LIST_PREF, "")
            .let { gson.fromJson(it, todoListItemType) }
    }

    private fun writeTodoItems(todoItems: List<TodoItem>) {
        gson.toJson(todoItems).let { sharedPrefs.edit().putString(TODO_LIST_PREF, it).apply() }
    }

    companion object {
        const val TODO_LIST_PREF = "TODO_LIST_PREF"
    }

}