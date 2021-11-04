package uk.co.conjure.custom_views_demo.todo_add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.co.conjure.custom_views_demo.persistence.TodoStore
import javax.inject.Inject
import androidx.lifecycle.ViewModel as AndroidViewModel

@HiltViewModel
class AddViewModel @Inject constructor(
    private val todoStore: TodoStore
) : AndroidViewModel() {
    private val nameLiveData = MutableLiveData("")
    private val priorityLiveData = MutableLiveData(false)
    private val completeLiveData = MutableLiveData(false)

    fun getName() = nameLiveData as LiveData<String>
    fun getPriority() = priorityLiveData as LiveData<Boolean>
    fun getComplete() = completeLiveData as LiveData<Boolean>

    fun updateName(name: String) = nameLiveData.postValue(name)
    fun updatePriority(priority: Boolean) = priorityLiveData.postValue(priority)

    private fun getPriorityInt() = if (priorityLiveData.value == true) 1 else 0

    fun onAddClicked() {
        todoStore.addTodoItem(nameLiveData.value ?: "", getPriorityInt())
        completeLiveData.postValue(true)
    }
}