package com.example.bookapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookapp.data.network.BookDTO
import com.example.bookapp.databinding.ListItemBinding

class BookListAdapter : ListAdapter<BookDTO, BookListAdapter.BookViewHolder>(DiffCallBack) {

    class BookViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookDTO) {
            binding.book = book
            binding.executePendingBindings()
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<BookDTO>() {
        override fun areItemsTheSame(oldItem: BookDTO, newItem: BookDTO): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BookDTO, newItem: BookDTO): Boolean {
            return oldItem.authors == newItem.authors && oldItem.averageRating == newItem.averageRating
                    && oldItem.description == newItem.description && oldItem.title == newItem.title
                    && oldItem.imageLinks.thumbnail == newItem.imageLinks.thumbnail
                    && oldItem.imageLinks.smallThumbnail == oldItem.imageLinks.smallThumbnail
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book)
    }

}