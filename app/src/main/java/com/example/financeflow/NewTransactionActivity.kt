package com.example.financeflow

import android.R.attr.description
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.financeflow.databinding.ActivityNewtransactionBinding
import java.util.Calendar
import java.util.Locale


class NewTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewtransactionBinding

    private var type = "Income"

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

        binding.etDate.editText?.setOnClickListener {
            val calendar = Calendar.getInstance()

            val dialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                val date = String.format(
                    Locale.getDefault(),
                    "%02d/%02d/%04d",
                    dayOfMonth,
                    month + 1,
                    year
                )
                binding.etDate.editText?.setText(date)
            }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        } // binding.etDate.editText?.setOnClickListener

        binding.toggleType.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener

            when (checkedId) {
                R.id.btnIncome -> {
                    type = "Income"
                    // Income seleccionado
                    binding.btnIncome.backgroundTintList =
                        getColorStateList(R.color.green_typeIncomeSelected)
                    binding.btnIncome.strokeColor =
                        getColorStateList(R.color.green_typeStroke)
                    binding.btnIncome.setTextColor(getColor(R.color.green_typoText))
                    binding.btnIncome.strokeWidth = 2

                    // Expense deseleccionado
                    binding.btnExpense.backgroundTintList =
                        getColorStateList(R.color.gray_typoUnselected)
                    binding.btnExpense.strokeColor =
                        getColorStateList(R.color.gray_typoStrokeUnselected)
                    binding.btnExpense.setTextColor(getColor(R.color.gray_typoText))
                    binding.btnExpense.strokeWidth = 1
                }
                R.id.btnExpense -> {
                    type = "Expense"
                    // Income deseleccionado
                    binding.btnIncome.backgroundTintList =
                        getColorStateList(R.color.gray_typoUnselected)
                    binding.btnIncome.strokeColor =
                        getColorStateList(R.color.gray_typoStrokeUnselected)
                    binding.btnIncome.setTextColor(getColor(R.color.gray_typoText))
                    binding.btnIncome.strokeWidth = 1

                    // Expense seleccionado
                    binding.btnExpense.backgroundTintList =
                        getColorStateList(R.color.red_typeExpenseSelected)
                    binding.btnExpense.strokeColor =
                        getColorStateList(R.color.red_typeStroke)
                    binding.btnExpense.setTextColor(getColor(R.color.red_typoText))
                    binding.btnExpense.strokeWidth = 2
                }
            } // when
        } // binding.toggleType.addOnButtonCheckedListener

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
        } // binding.btnSave.setOnClickListener

    } // onCreate

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("KEY_VALUE", binding.etValue.editText?.text.toString())
        outState.putString("KEY_DESCRIPTION", binding.etDescription.editText?.text.toString())
        outState.putString("KEY_DATE", binding.etDate.editText?.text.toString())
//        outState.putString("type", _type)
    }
} // NewTransactionActivity