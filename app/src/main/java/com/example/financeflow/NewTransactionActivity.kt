package com.example.financeflow

import android.R.attr.description
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.financeflow.databinding.ActivityNewtransactionBinding


class NewTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewtransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewtransactionBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finish()
                }
            })

        if (savedInstanceState != null) {
            savedInstanceState.let {

                binding.etValue.editText?.setText(
                    it.getString("KEY_VALUE").orEmpty()
                )

                binding.etDescription.editText?.setText(
                    it.getString("KEY_DESCRIPTION").orEmpty()
                )

                binding.etDate.editText?.setText(
                    it.getString("KEY_DATE").orEmpty()
                )
            }
        }

        binding.btnSave.setOnClickListener {

            val value = binding.etValue.editText?.text.toString().toDoubleOrNull() ?: 0.0
            val description = binding.etDescription.editText?.text.toString()
            val date = binding.etDate.editText?.text.toString()

            var hasError = false

            if (value <= 0) {
                binding.etValue.error = getString(R.string.Errors)
                hasError = true
            } else {
                binding.etValue.error = null
            }

            if (description.isBlank()) {
                binding.etDescription.error = getString(R.string.Errors)
                hasError = true
            } else {
                binding.etDescription.error = null
            }

            if (date.isBlank()) {
                binding.etDate.error = getString(R.string.Errors)
                hasError = true
            } else {
                binding.etDate.error = null
            }

            if (!hasError) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    } // onCreate

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("KEY_VALUE", binding.etValue.editText?.text.toString())
        outState.putString("KEY_DESCRIPTION", binding.etDescription.editText?.text.toString())
        outState.putString("KEY_DATE", binding.etDate.editText?.text.toString())
//        outState.putString("type", _type)
    }
} // NewTransactionActivity