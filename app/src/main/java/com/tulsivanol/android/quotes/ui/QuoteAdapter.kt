package com.tulsivanol.android.quotes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tulsivanol.android.quotes.data.Quote
import com.tulsivanol.android.quotes.databinding.QuoteItemBinding

class QuoteAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    private var quotes: List<Quote> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val quoteItemBinding =
            QuoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuoteViewHolder(quoteItemBinding)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = quotes[position]
        holder.bind(quote)
    }

    override fun getItemCount(): Int = quotes.size

    fun setQuotes(quotes: List<Quote>) {
        this.quotes = quotes
        notifyDataSetChanged()
    }

    inner class QuoteViewHolder(val quoteItemBinding: QuoteItemBinding) :
        RecyclerView.ViewHolder(quoteItemBinding.root) {
        fun bind(quote: Quote) {
            quoteItemBinding.quoteText.text = quote.text
            quoteItemBinding.quoteAuthor.text = quote.author
            quoteItemBinding.quoteDate.text = quote.date

            quoteItemBinding.root.setOnClickListener {
                clickListener.onClick(quote)
            }
        }
    }
}

interface ClickListener {
    fun onClick(quote: Quote)
}