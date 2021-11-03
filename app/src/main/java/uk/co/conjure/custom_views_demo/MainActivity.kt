package uk.co.conjure.custom_views_demo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uk.co.conjure.custom_views_demo.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemAdapter: TodoItemAdapter

    private val viewModel by viewModels<TodoListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { onAddClicked() }

        itemAdapter = TodoItemAdapter(
            TodoItemAdapter.OnItemCheckedListener { id: Int, checked: Boolean ->
                viewModel.onTodoCheckedChanged(id, checked)
            }
        )
        binding.rvTodo.adapter = itemAdapter
        binding.rvTodo.layoutManager = LinearLayoutManager(this)

        viewModel.getTodoList().observe(this) { itemAdapter.onNewList(it) }
    }

    private fun onAddClicked() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear -> {
                viewModel.clearTodoItems()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}