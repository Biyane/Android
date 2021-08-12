package com.example.bookapp.presentation.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.bookapp.BookApplication
import com.example.bookapp.R
import com.example.bookapp.application.interactor.network.GetBookListListUseCase
import com.example.bookapp.application.model.BookViewModel
import com.example.bookapp.application.model.BookViewModelFactory
import com.example.bookapp.databinding.FragmentBookListBinding
import com.example.bookapp.presentation.adapter.BookListAdapter
import org.koin.android.ext.android.get

class BookListFragment : Fragment() {

    private val bookViewModel: BookViewModel by activityViewModels {
        BookViewModelFactory(
            (activity?.application as BookApplication).repository,
            get<GetBookListListUseCase>()
        )
    }
    private lateinit var binding: FragmentBookListBinding
    private val args: BookListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookListBinding.inflate(layoutInflater)
        bookViewModel.getBookList(args.bookName)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = BookListAdapter()
        binding.apply {
            viewModel = bookViewModel
            lifecycleOwner = this@BookListFragment
            recyclerViewBook.adapter = adapter
        }
        bookViewModel.books.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }
}