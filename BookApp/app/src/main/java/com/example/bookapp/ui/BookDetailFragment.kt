package com.example.bookapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bookapp.R
import com.example.bookapp.databinding.FragmentBookDetailBinding
import com.example.bookapp.model.BookDetailViewModel

class BookDetailFragment : Fragment() {

    private val viewModel: BookDetailViewModel by viewModels()
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
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.bookDetailFragment = this@BookDetailFragment
    }

    fun addButtonClicked() {
        findNavController().navigate(R.id.action_bookDetailFragment_to_bookListFragment)
    }
}

