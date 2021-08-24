package com.example.bookapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.bookapp.application.interactor.domain.GetBookUseCase
import com.example.bookapp.application.model.MainListDetailViewModel
import com.example.bookapp.application.model.MainListDetailViewModelFactory
import com.example.bookapp.databinding.FragmentMainBookListDetailBinding
import org.koin.android.ext.android.get



class MainBookListDetailFragment : Fragment() {

    private lateinit var binding: FragmentMainBookListDetailBinding
    private val args: MainBookListDetailFragmentArgs by navArgs()
    private val bookViewModel: MainListDetailViewModel by viewModels {
        MainListDetailViewModelFactory(
            get<GetBookUseCase>()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bookViewModel.getByBookId(args.bookId)
        binding = FragmentMainBookListDetailBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bookViewModel.book.observe(viewLifecycleOwner) {
            binding.book = it
        }
    }

}