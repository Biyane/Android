 package com.example.bookapp.presentation.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.bookapp.BookApplication
import com.example.bookapp.R
import com.example.bookapp.presentation.adapter.BookListAdapter
import com.example.bookapp.databinding.FragmentBookListBinding
import com.example.bookapp.application.model.BookViewModel
import com.example.bookapp.application.model.BookViewModelFactory

class BookListFragment : Fragment() {

    private val bookViewModel: BookViewModel by activityViewModels {
        BookViewModelFactory(
            (activity?.application as BookApplication).repository
        )
    }
    private lateinit var binding: FragmentBookListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = FragmentBookListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = BookListAdapter()
        binding.apply {
            viewModel = bookViewModel
            lifecycleOwner = this@BookListFragment
            recyclerViewBook.adapter = adapter
        }
        bookViewModel.bookList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

}