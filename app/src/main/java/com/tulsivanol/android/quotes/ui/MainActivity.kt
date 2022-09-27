package com.tulsivanol.android.quotes.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tulsivanol.android.quotes.R
import com.tulsivanol.android.quotes.data.Quote
import com.tulsivanol.android.quotes.data.QuotesRepositoryImpl
import com.tulsivanol.android.quotes.databinding.ActivityMainBinding
import com.tulsivanol.android.quotes.ui.viewmodel.QuoteViewModelFactory
import com.tulsivanol.android.quotes.ui.viewmodel.QuotesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var quotesViewModel: QuotesViewModel
    private lateinit var quoteAdapter: QuoteAdapter

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        quotesViewModel = ViewModelProvider(
            this,
            QuoteViewModelFactory(QuotesRepositoryImpl(application = application))
        ).get(QuotesViewModel::class.java)


        quotesViewModel.dataLoading.observe(this, Observer { value ->
            value?.let { show ->
                activityMainBinding.loadingSpinner.visibility =
                    if (show) View.VISIBLE else View.GONE
            }
        })

        quotesViewModel.getAllQuotes().observe(this, Observer<List<Quote>> {
            quoteAdapter.setQuotes(it)
        })

        quoteAdapter = QuoteAdapter(clickListener = object : ClickListener {
            override fun onClick(quote: Quote) {
                val intent = Intent(this@MainActivity, AddEditActivity::class.java)
                intent.apply {
                    putExtra(AddEditActivity.EXTRA_ID, quote.id)
                    putExtra(AddEditActivity.EXTRA_AUTHOR, quote.author)
                    putExtra(AddEditActivity.EXTRA_TEXT, quote.text)
                    putExtra(AddEditActivity.EXTRA_DATE, quote.date)
                }
                startActivityForResult(intent, EDIT_QUOTE_REQUEST_CODE)
            }
        })

        activityMainBinding.quotesRecyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = quoteAdapter
        }

        activityMainBinding.addQuoteFloatingButton.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            startActivityForResult(intent, ADD_QUOTE_REQUEST_CODE)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_QUOTE_REQUEST_CODE -> {
                    val intentData = data!!
                    val newNote = Quote(
                        text = intentData.getStringExtra(AddEditActivity.EXTRA_TEXT)!!,
                        author = intentData.getStringExtra(AddEditActivity.EXTRA_AUTHOR)!!,
                        date = intentData.getStringExtra(AddEditActivity.EXTRA_DATE)!!
                    )
                    quotesViewModel.insertQuote(newNote)
                    Toast.makeText(this, "Quote saved!", Toast.LENGTH_SHORT).show()
                }
                EDIT_QUOTE_REQUEST_CODE -> {
                    val intentData = data!!
                    val updateQuote = Quote(
                        id = intentData.getIntExtra(AddEditActivity.EXTRA_ID, -1),
                        text = intentData.getStringExtra(AddEditActivity.EXTRA_TEXT)!!,
                        author = intentData.getStringExtra(AddEditActivity.EXTRA_AUTHOR)!!,
                        date = intentData.getStringExtra(AddEditActivity.EXTRA_DATE)!!
                    )
                    quotesViewModel.updateQuote(updateQuote)
                    Toast.makeText(this, "Quote updated!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Not found!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        const val ADD_QUOTE_REQUEST_CODE = 1
        const val EDIT_QUOTE_REQUEST_CODE = 2
    }
}
