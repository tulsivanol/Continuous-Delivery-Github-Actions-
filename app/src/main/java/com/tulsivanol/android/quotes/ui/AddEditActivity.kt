package com.tulsivanol.android.quotes.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tulsivanol.android.quotes.databinding.ActivityAddEditBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AddEditActivity : AppCompatActivity() {

    private lateinit var addEditBinding: ActivityAddEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addEditBinding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(addEditBinding.root)

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit quote"
            addEditBinding.editTextQuoteText.setText(intent.getStringExtra(EXTRA_TEXT))
            addEditBinding.editTextQuoteAuthor.setText(intent.getStringExtra(EXTRA_AUTHOR))
            addEditBinding.editTextQuoteDate.setText(intent.getStringExtra(EXTRA_DATE))
        } else {
            title = "Add new quote"
        }
        addEditBinding.buttonSaveQuote.setOnClickListener {
            saveQuote()
        }

        initUI()
    }

    private fun initUI() {
        val calendar = Calendar.getInstance();
        val date = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val dateFormat = "dd/MM/yyyy"
            val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.US)
            addEditBinding.editTextQuoteDate.setText(simpleDateFormat.format(calendar.time))
        }

        addEditBinding.editTextQuoteDate.setOnClickListener {
            DatePickerDialog(
                this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }


    private fun saveQuote() {
        if (addEditBinding.editTextQuoteText.text.toString().trim().isBlank() ||
            addEditBinding.editTextQuoteAuthor.text.toString().trim().isBlank() ||
            addEditBinding.editTextQuoteDate.text.toString().trim().isBlank()
        ) {
            Toast.makeText(
                this,
                "Quote is empty! Please fill the missing fields",
                Toast.LENGTH_SHORT
            )
                .show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_TEXT, addEditBinding.editTextQuoteText.text.toString())
            putExtra(EXTRA_AUTHOR, addEditBinding.editTextQuoteAuthor.text.toString())
            putExtra(EXTRA_DATE, addEditBinding.editTextQuoteDate.text.toString())
            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    @Throws(ParseException::class)
    fun formatDate(dateStr: String): String {
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = formatter.parse(dateStr)
        return date!!.toString()
    }


    companion object {
        const val EXTRA_ID = "QUOTE_ID"
        const val EXTRA_TEXT = "QUOTE_TEXT"
        const val EXTRA_AUTHOR = "QUOTE_AUTHOR"
        const val EXTRA_DATE = "QUOTE_DATE"
    }
}