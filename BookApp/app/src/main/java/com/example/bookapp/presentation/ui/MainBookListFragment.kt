package com.example.bookapp.presentation.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bookapp.BookApplication
import com.example.bookapp.R
import com.example.bookapp.application.interactor.network.GetBookListListUseCase
import com.example.bookapp.application.model.BookViewModel
import com.example.bookapp.application.model.BookViewModelFactory
import com.example.bookapp.databinding.FragmentMainBookListBinding
import org.koin.android.ext.android.get

class MainBookListFragment : Fragment() {

    private lateinit var binding: FragmentMainBookListBinding
    private val mainListViewModel: BookViewModel by viewModels {
        BookViewModelFactory(
            (activity?.application as BookApplication).repository,
            get<GetBookListListUseCase>()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBookListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }
}