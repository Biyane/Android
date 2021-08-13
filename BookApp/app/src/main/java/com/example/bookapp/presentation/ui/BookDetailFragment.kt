package com.example.bookapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bookapp.BookApplication
import com.example.bookapp.application.interactor.network.GetBookListListUseCase
import com.example.bookapp.application.model.BookViewModel
import com.example.bookapp.application.model.BookViewModelFactory
import com.example.bookapp.databinding.FragmentBookDetailBinding
import org.koin.android.ext.android.get

class BookDetailFragment : Fragment() {

    private val bookViewModel: BookViewModel by activityViewModels {
        BookViewModelFactory(
            (activity?.application as BookApplication).repository,
            get<GetBookListListUseCase>()
        )
    }
    private lateinit var binding: FragmentBookDetailBinding
    private val args: BookDetailFragmentArgs by navArgs()
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
            lifecycleOwner = this@BookDetailFragment.viewLifecycleOwner
            viewModel = bookViewModel
            bookDetailFragment = this@BookDetailFragment
            book = bookViewModel.books.value?.let {
                it.first { book ->
                    book.title == args.bookTitle
                }
            }
        }

    }

    fun addButtonClicked() {
        binding.book?.map()?.let { bookViewModel.insertBook(it) }
        val action = BookDetailFragmentDirections.actionBookDetailFragmentToMainBookListFragment()
        findNavController().navigate(action)
    }

}

