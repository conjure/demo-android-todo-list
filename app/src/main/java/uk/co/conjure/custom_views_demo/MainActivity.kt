package uk.co.conjure.custom_views_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import uk.co.conjure.custom_views_demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemAdapter: TodoItemAdapter

    private lateinit var viewModel: TodoListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (application as App).viewModelFactory.todoListViewModel

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { viewModel.onAddTodo() }

        itemAdapter = TodoItemAdapter(
            TodoItemAdapter.OnItemCheckedListener { id: Int, checked: Boolean ->
                viewModel.onTodoCheckedChanged(id, checked)
            }
        )
        binding.rvTodo.adapter = itemAdapter
        binding.rvTodo.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        binding.fab.setOnClickListener { viewModel.onAddTodo() }
        viewModel.getTodoList().observe(this) { itemAdapter.onNewList(it) }
    }

}