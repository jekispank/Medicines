package com.example.medicines.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.medicines.R
import com.example.medicines.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: PillListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.piLLList.observe(this) {
            adapter.pillList = it
        }
    }

    fun setupRecyclerView() {
        val rvPillList = binding.rcPillList
        adapter = PillListAdapter()
        rvPillList.apply {
            adapter = adapter
            recycledViewPool.setMaxRecycledViews(
                PillListAdapter.VIEW_TYPE_ENABLED,
                PillListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                PillListAdapter.VIEW_TYPE_DISABLED,
                PillListAdapter.MAX_POOL_SIZE
            )
        }
    }
}