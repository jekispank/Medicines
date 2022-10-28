package com.example.medicines.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.medicines.R
import com.example.medicines.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var pillListAdapter: PillListAdapter
    private var pillItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        pillItemContainer = binding.pillItemContainer
        setupRecyclerView()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.piLLList.observe(this) {
            pillListAdapter.submitList(it)
        }
        binding.buttonAddPillItem.setOnClickListener{
            if (isOnePaneMode()) {
                val intent = PillItemActivity.newIntentAddItem(this)
                startActivity(intent)
            }
            else launchFragment(PillItemFragment.newInstanceAddItem())
        }
    }

    private fun isOnePaneMode(): Boolean {
        return pillItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.pill_item_container, fragment)
            .addToBackStack(null)
            .commit()
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
                    val item = pillListAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.removePillItem(item)

                }
            }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvPillList)
    }

    private fun setupClickListener() {
        pillListAdapter.onPillItemClickListener = {
            if (isOnePaneMode()) {
                val intent = PillItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            }
            else launchFragment(PillItemFragment.newInstanceEditItem(it.id))
        }
    }

    private fun setupLongClickListener() {
        pillListAdapter.onPillItemLongClickListener = {
            viewModel.setCondition(it)
        }
    }
}

