package uk.co.conjure.custom_views_demo.persistence

import android.content.Context
import android.content.SharedPreferences
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
    private val sharedPrefs = context.getSharedPreferences("todo-store", Context.MODE_PRIVATE)
    private val sharedPrefsListener = SharedPreferences.OnSharedPreferenceChangeListener { _, _ ->
        todoItemsLiveData.postValue(readTodoItems(sharedPrefs))
    }

    init {
        sharedPrefs.registerOnSharedPreferenceChangeListener(sharedPrefsListener)
        todoItemsLiveData.postValue(readTodoItems(sharedPrefs))
    }

    override fun getTodoItemsLiveData(): LiveData<List<TodoItem>> = todoItemsLiveData

    override fun addTodoItem(name: String, priority: Int) {
        val currentItems = readTodoItems(sharedPrefs)
        val lastId = currentItems.maxOfOrNull { it.id } ?: 0
        val newItem = TodoItem(lastId + 1, name, priority, false)
        writeTodoItems(currentItems.plus(newItem))
    }

    override fun updateTodoItemChecked(id: Int, checked: Boolean) {
        val items = readTodoItems(sharedPrefs).toMutableList()
        val indexOfItem = items.indexOfFirst { it.id == id }
        if (indexOfItem == -1) return
        items[indexOfItem] = items[indexOfItem].copy(done = checked)
        writeTodoItems(items)
    }

    override fun clearTodoItems() {
        writeTodoItems(emptyList())
    }

    private fun readTodoItems(sharedPreferences: SharedPreferences): List<TodoItem> {
        return sharedPreferences.getString(TODO_LIST_PREF, "")
            .let { gson.fromJson(it, todoListItemType) }
    }

    private fun writeTodoItems(todoItems: List<TodoItem>) {
        gson.toJson(todoItems).let { sharedPrefs.edit().putString(TODO_LIST_PREF, it).apply() }
    }

    companion object {
        const val TODO_LIST_PREF = "TODO_LIST_PREF"
    }

}