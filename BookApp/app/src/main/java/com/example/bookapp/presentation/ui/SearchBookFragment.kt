package com.example.bookapp.presentation.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bookapp.databinding.FragmentSearchBookBinding


class SearchBookFragment : Fragment() {

    private lateinit var binding: FragmentSearchBookBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {
        binding = FragmentSearchBookBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            searchBook = this@SearchBookFragment
        }
    }

    fun onSearchClick(){
        val bookText = binding.editTextFindBook.text.toString()
        if (!TextUtils.isEmpty(bookText)){
            val action = SearchBookFragmentDirections.actionSearchBookFragmentToSearchBookListFragment(bookText)
            findNavController().navigate(action)
        }
    }

}