package com.example.bookapp.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bookapp.BookApplication
import com.example.bookapp.R
import com.example.bookapp.application.model.MainListViewModel
import com.example.bookapp.application.model.MainListViewModelFactory
import com.example.bookapp.data.database.Book
import com.example.bookapp.databinding.FragmentMainBookListBinding
import com.example.bookapp.presentation.adapter.MainBookListAdapter

class MainBookListFragment : Fragment() {

    private lateinit var binding: FragmentMainBookListBinding
    private lateinit var adapter: MainBookListAdapter
    private var filteredList = listOf<Book>()
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
        adapter = MainBookListAdapter()
        binding.run {
            fragmentMainBookListRv.adapter = adapter
            fab.setOnClickListener {
                val action = MainBookListFragmentDirections.actionMainBookListFragmentToSearchBookFragment()
                findNavController().navigate(action)
            }
        }
        mainListViewModel.bookList.observe(viewLifecycleOwner) {
            kotlin.runCatching {
                filteredList = it
                adapter.submitList(it)
            }.also {
                if (it.isFailure) {
                    Log.e("MainBookListFragment", it.exceptionOrNull().toString())
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.search_bar)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchText ->
                    adapter.submitList(filteredList.filter {
                        it.title.lowercase().contains(searchText.lowercase())
                    })
                }
                return false
            }

        })
    }
}