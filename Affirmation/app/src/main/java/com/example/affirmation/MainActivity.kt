package com.example.affirmation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.affirmation.adapter.ItemAdapter
import com.example.affirmation.data.Datasource
import com.example.affirmation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myDataset = Datasource().loadAffirmations()
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = ItemAdapter(this, myDataset)
        recyclerView.setHasFixedSize(true)
        binding.changeView.setOnClickListener { onButtonClick() }
    }

    private fun onButtonClick() {
        recyclerView.layoutManager = when(recyclerView.layoutManager){
            is GridLayoutManager -> LinearLayoutManager(
                this,
                RecyclerView.VERTICAL,
                false
            )
            else -> GridLayoutManager(this, 2)
        }
    }
}