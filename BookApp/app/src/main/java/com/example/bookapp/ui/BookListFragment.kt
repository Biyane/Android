package com.example.bookapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bookapp.BookApplication
import com.example.bookapp.R
import com.example.bookapp.databinding.FragmentBookListBinding
import com.example.bookapp.model.BookViewModel
import com.example.bookapp.model.BookViewModelFactory

class BookListFragment : Fragment() {

    private val viewModel: BookViewModel by viewModels {
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

}