package uk.co.conjure.custom_views_demo.todo_add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uk.co.conjure.custom_views_demo.databinding.ActivityAddBinding

class AddTodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etName.requestFocus()
    }
}