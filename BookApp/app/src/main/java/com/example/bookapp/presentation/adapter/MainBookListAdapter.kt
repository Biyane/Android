package com.example.bookapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookapp.data.database.Book
import com.example.bookapp.databinding.FragmentMainBookListItemBinding

class MainBookListAdapter
    : ListAdapter<Book, MainBookListAdapter.MainBookViewHolder>(DiffCallback) {
    class MainBookViewHolder(
        private val binding: FragmentMainBookListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.book = book
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainBookViewHolder {
        return MainBookViewHolder(
            FragmentMainBookListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: MainBookViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.authors == newItem.authors
                    && oldItem.description == newItem.description
                    && oldItem.title == newItem.title
        }
    }
}