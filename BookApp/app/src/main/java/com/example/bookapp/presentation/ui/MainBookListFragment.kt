package com.example.bookapp.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bookapp.BookApplication
import com.example.bookapp.R
import com.example.bookapp.application.model.MainListViewModel
import com.example.bookapp.application.model.MainListViewModelFactory
import com.example.bookapp.databinding.FragmentMainBookListBinding
import com.example.bookapp.presentation.adapter.MainBookListAdapter

class MainBookListFragment : Fragment() {

    private lateinit var binding: FragmentMainBookListBinding
    private val mainListViewModel: MainListViewModel by viewModels {
        MainListViewModelFactory(
            (activity?.application as BookApplication).repository,
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
        val adapter = MainBookListAdapter()
        binding.run {
            lifecycleOwner = this@MainBookListFragment.viewLifecycleOwner
            fragmentMainBookListRv.adapter = adapter
            fab.setOnClickListener {
                val action = MainBookListFragmentDirections.actionMainBookListFragmentToSearchBookFragment()
                findNavController().navigate(action)
            }
        }
        Log.e("MainBookListFragment", "error")
//        mainListViewModel.bookList.observe(viewLifecycleOwner) {
//            adapter.submitList(it)
//        }
        Log.e("MainBookListFragment", "error")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }
}