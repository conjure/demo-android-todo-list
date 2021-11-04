package uk.co.conjure.custom_views_demo.terms_and_conditions

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import uk.co.conjure.custom_views_demo.databinding.ActivityTermsAndConditionsBinding
import uk.co.conjure.custom_views_demo.todo_list.MainActivity
import uk.co.conjure.custom_views_demo.todo_list.TodoListViewModel

@AndroidEntryPoint
class TermsAndConditionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTermsAndConditionsBinding
    private val viewModel by viewModels<TermsAndConditionsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTermsAndConditionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cbAccepted.setOnCheckedChangeListener { _, checked ->
            viewModel.setAcceptedConditions(checked)
        }
        binding.btnContinue.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        viewModel.getAcceptedConditions().observe(this) {
            binding.btnContinue.isEnabled = it
        }
    }
}