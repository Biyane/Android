package com.bignerdranch.android.criminalintent

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*


class CrimeListFragment : Fragment() {

    interface Callbacks {
        fun onCrimeSelected(crimeId: UUID)
    }

    private var callbacks: Callbacks? = null
    private lateinit var crimeRecyclerView: RecyclerView
    private lateinit var addButton: Button
    private val adapter: CrimeAdapter = CrimeAdapter()
    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this)[CrimeListViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_crime_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addButton = view.findViewById(R.id.add_button)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view)
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter
        crimeListViewModel.crimeListLiveData.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it.toMutableList())
            }
            if (adapter.itemCount == 0) {
                addButton.visibility = View.VISIBLE
            } else {
                addButton.visibility = View.GONE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        addButton.setOnClickListener {
            addCrime()
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun addCrime() {
        val crime = Crime()
        crimeListViewModel.addCrime(crime)
        callbacks?.onCrimeSelected(crime.id)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_crime -> {
                addCrime()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private abstract class BaseViewHolder<Crime>(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        abstract fun bind(crime: Crime)
    }

    private inner class CrimeHolder(view: View) : BaseViewHolder<Crime>(view) {
        private lateinit var crime: Crime
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        init {
            itemView.setOnClickListener(this)
        }

        override fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = crime.title
            val formattedDate = SimpleDateFormat("EEEE, LLLL, dd, yyyy", Locale.getDefault())
            val date = formattedDate.format(crime.date).toString()
            dateTextView.text = date
            solvedImageView.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        override fun onClick(p0: View?) {
            callbacks?.onCrimeSelected(crimeId = crime.id)
        }
    }

    private inner class CrimeHolder2(view: View) : BaseViewHolder<Crime>(view) {
        private lateinit var crime: Crime
        private val requireButton: Button = itemView.findViewById(R.id.require_police)

        init {
            itemView.setOnClickListener(this)

        }

        override fun bind(crime: Crime) {
            this.crime = crime
            requireButton.text = crime.title
        }

        override fun onClick(p0: View?) {
            Toast.makeText(
                context,
                "${crime.title} pressed!!",
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

    private inner class CrimeAdapter :
        ListAdapter<Crime, BaseViewHolder<Crime>>(DiffCallback()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Crime> {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            val view2 = layoutInflater.inflate(R.layout.list_item_crime_2, parent, false)
            return when (viewType) {
                0 -> CrimeHolder(view)
                else -> CrimeHolder2(view2)
            }
        }

        override fun onBindViewHolder(holder: BaseViewHolder<Crime>, position: Int) {
            val crime = currentList[position]
            holder.bind(crime)
        }

        override fun getItemCount(): Int = currentList.size


        override fun submitList(list: MutableList<Crime>?) {
            super.submitList(list)
        }
//        override fun getItemViewType(position: Int): Int {
//            val crime = crimes[position]
//            return when (crime.requirePolice) {
//                0 -> 0
//                else -> 1
//            }
//        }
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Crime>() {
    override fun areItemsTheSame(oldItem: Crime, newItem: Crime): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Crime, newItem: Crime): Boolean {
        return oldItem == newItem
    }
}