package com.example.bookapp.presentation.ui

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.bookapp.BookApplication
import com.example.bookapp.R
import com.example.bookapp.application.interactor.network.GetBookListListUseCase
import com.example.bookapp.application.model.BookViewModel
import com.example.bookapp.application.model.BookViewModelFactory
import com.example.bookapp.data.network.BookDTO
import com.example.bookapp.databinding.FragmentSearchBookListBinding
import com.example.bookapp.presentation.adapter.SearchBookListAdapter
import org.koin.android.ext.android.get

class SearchBookListFragment : Fragment() {

    private val bookViewModel: BookViewModel by activityViewModels {
        BookViewModelFactory(
            (activity?.application as BookApplication).repository,
            get<GetBookListListUseCase>()
        )
    }
    private var filteredBook = listOf<BookDTO>()
    private lateinit var binding: FragmentSearchBookListBinding
    private lateinit var adapter: SearchBookListAdapter
    private val args: SearchBookListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBookListBinding.inflate(layoutInflater)
        bookViewModel.getBookList(args.bookName)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = SearchBookListAdapter()
        binding.apply {
            recyclerViewBook.adapter = adapter
        }
        bookViewModel.books.observe(viewLifecycleOwner) {
            filteredBook = it
            adapter.submitList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu.findItem(R.id.search_bar)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchSeq ->
                    adapter.submitList(
                        filteredBook.filter { book ->
                            book.title.lowercase().contains(searchSeq.lowercase())
                        }
                    )
                }
                return false
            }
        })
    }

}