package uk.co.conjure.custom_views_demo.todo_add

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import uk.co.conjure.custom_views_demo.databinding.ActivityAddBinding

@AndroidEntryPoint
class AddTodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private val viewModel by viewModels<AddViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getName().observe(this) {
            if (binding.etName.text.toString() != it) binding.etName.setText(it)
        }
        viewModel.getPriority().observe(this) {
            if (binding.swPriority.isChecked != it) binding.swPriority.isChecked =  it
        }
        viewModel.getComplete().observe(this) { if (it) finish() }
        binding.etName.addTextChangedListener { viewModel.updateName(it.toString()) }
        binding.swPriority.setOnCheckedChangeListener { _, isChecked -> viewModel.updatePriority(isChecked) }
        binding.btnAdd.setOnClickListener { viewModel.onAddClicked() }
        binding.etName.requestFocus()
    }
}