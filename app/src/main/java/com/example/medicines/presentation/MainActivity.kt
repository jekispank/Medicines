package com.example.medicines.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.medicines.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var pillListAdapter: PillListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.piLLList.observe(this) {
            pillListAdapter.pillList = it
        }
    }

    fun setupRecyclerView() {
        val rvPillList = binding.rcPillList
        pillListAdapter = PillListAdapter()
        rvPillList.apply {
            adapter = pillListAdapter
            recycledViewPool.setMaxRecycledViews(
                PillListAdapter.VIEW_TYPE_ENABLED,
                PillListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                PillListAdapter.VIEW_TYPE_DISABLED,
                PillListAdapter.MAX_POOL_SIZE
            )
        }
        setupClickListener()
        setupLongClickListener()
        setupSwipeListener(rvPillList)
    }

    private fun setupSwipeListener(rvPillList: RecyclerView) {
        val callback =
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val item = pillListAdapter.pillList[viewHolder.adapterPosition]
                    viewModel.removePillItem(item)

                }
            }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvPillList)
    }

    private fun setupClickListener() {
        pillListAdapter.onPillItemClickListener = {
            Log.d("ClickListener", "Click-click on ${it.id}")
        }
    }

    private fun setupLongClickListener() {
        pillListAdapter.onPillItemLongClickListener = {
            viewModel.setCondition(it)
        }
    }
}

