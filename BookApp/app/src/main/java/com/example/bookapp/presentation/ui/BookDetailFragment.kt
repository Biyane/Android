package com.example.bookapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bookapp.BookApplication
import com.example.bookapp.R
import com.example.bookapp.databinding.FragmentBookDetailBinding
import com.example.bookapp.domain.model.BookViewModel
import com.example.bookapp.domain.model.BookViewModelFactory

class BookDetailFragment : Fragment() {

    private val bookViewModel: BookViewModel by activityViewModels {
        BookViewModelFactory(
            (activity?.application as BookApplication).repository
        )
    }
    private lateinit var binding: FragmentBookDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = this@BookDetailFragment
            viewModel = bookViewModel
            bookDetailFragment = this@BookDetailFragment
        }
    }

    fun addButtonClicked() {
//        bookViewModel.bookJson.value?.let { bookViewModel.insertBook(it) }
        findNavController().navigate(R.id.action_bookDetailFragment_to_bookListFragment)
    }

}

