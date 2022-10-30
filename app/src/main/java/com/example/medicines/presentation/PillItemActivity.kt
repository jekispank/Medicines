package com.example.medicines.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.medicines.R
import com.example.medicines.databinding.ActivityPillItemBinding
import com.example.medicines.domain.PillItem

class PillItemActivity : AppCompatActivity(), PillItemFragment.OnEditingFinishedListener {

    private var screenMode = MODE_UNKNOWN
    private var pillItemId = PillItem.UNDEFINED_ID
    private lateinit var binding: ActivityPillItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPillItemBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        parseIntent()

        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    override fun onEditingFinished() {
        finish()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw  RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_PILL_ITEM_ID)) {
                throw RuntimeException("Param pill item Id is absent")
            }
            pillItemId = intent.getIntExtra(EXTRA_PILL_ITEM_ID, -1)
        }

        Log.d("VERY_IMPORTANT", "$pillItemId")
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> PillItemFragment.newInstanceEditItem(pillItemId)
            MODE_ADD -> PillItemFragment.newInstanceAddItem()
            else -> {
                throw  RuntimeException("Unknown screen mode $screenMode")
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.pill_item_container, fragment)
            .commit()
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_PILL_ITEM_ID = "extra_pill_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, PillItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, pillItemId: Int): Intent {
            val intent = Intent(context, PillItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_PILL_ITEM_ID, pillItemId)
            return intent
        }
    }
}
