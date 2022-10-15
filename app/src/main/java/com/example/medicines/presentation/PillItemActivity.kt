package com.example.medicines.presentation

import android.content.Context
import android.content.Intent
import android.content.Intent.parseIntent
import android.os.Bundle
import android.service.controls.templates.TemperatureControlTemplate.MODE_UNKNOWN
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.medicines.R
import com.example.medicines.databinding.ActivityPillItemBinding
import com.example.medicines.domain.PillItem
import com.google.android.material.textfield.TextInputLayout

class PillItemActivity : AppCompatActivity() {
    //
//    private lateinit var viewModel: PillItemViewModel
//
//    private lateinit var pillName: TextInputLayout
//    private lateinit var etName: EditText
//    private lateinit var pillCount: TextInputLayout
//    private lateinit var etCount: EditText
//    private lateinit var descriptionOfTaking: TextInputLayout
//    private lateinit var etDescription: EditText
//    private lateinit var saveBtn: Button
//    private lateinit var profilePillIcon: ImageView
//
    private var screenMode = MODE_UNKNOWN
    private var pillItemId = PillItem.UNDEFINED_ID
//
    private lateinit var binding: ActivityPillItemBinding

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPillItemBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        parseIntent()
        //        viewModel = ViewModelProvider(this)[PillItemViewModel::class.java]
//        initViews()
//        addTextChangeListener()
        launchRightMode()
//
    }
//
//    private fun addTextChangeListener() {
//        binding.etName.addTextChangedListener(object : TextWatcher {
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetErrorInputName()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//        })
//        binding.etCount.addTextChangedListener(object : TextWatcher {
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetErrorInputCount()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//        })
//    }
//
//    private fun observeViewModel(){
//        viewModel.errorInputCount.observe(this) {
//            val message = if (it) {
//                getString(R.string.error_input_count)
//            } else {
//                null
//            }
//            pillCount.error = message
//        }
//        viewModel.errorInputName.observe(this) {
//            val message = if (it) {
//                getString(R.string.error_input_name)
//            } else {
//                null
//            }
//            pillName.error = message
//        }
//        viewModel.shouldCloseScreen.observe(this) {
//            finish()
//        }
//    }
//
//
//    private fun launchEditMode() {
//        viewModel.getPillItem(pillItemId)
//        viewModel.pillItem.observe(this) {
//            etName.setText(it.title)
//            etCount.setText(it.restOfPills.toString())
//            etDescription.setText(it.descriptionOfTaking)
//        }
//        saveBtn.setOnClickListener {
//            viewModel.editPillItem(
//                etName.text?.toString(),
//                etCount.text?.toString(),
//                etDescription.text?.toString()
//            )
//        }
//    }
//
//    private fun launchAddMode() {
//        viewModel.getPillItem(pillItemId)
//        viewModel.pillItem.observe(this) {
//            etName.setText(it.title)
//            etCount.setText(it.restOfPills.toString())
//            etDescription.setText(it.descriptionOfTaking)
//        }
//        saveBtn.setOnClickListener {
//            viewModel.addPillItem(
//                etName.text?.toString(),
//                etCount.text?.toString(),
//                etDescription.text?.toString()
//            )
//        }
//
//    }
//
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
        }
//
//    private fun initViews() {
//        pillName = binding.pillName
//        etName = binding.etName
//        pillCount = binding.pillCount
//        etCount = binding.etCount
//        descriptionOfTaking = binding.descriptionOfTaking
//        etDescription = binding.etDescription
//        saveBtn = binding.saveBtn
//        profilePillIcon = binding.profilePillIcon
//    }
    private fun launchRightMode(){
        val fragment = when (screenMode) {
            MODE_EDIT -> PillItemFragment.newInstanceEditItem(pillItemId)
            MODE_ADD -> PillItemFragment.newInstanceAddItem()
            else -> {throw  RuntimeException("Unknown screen mode $screenMode")}
        }
    supportFragmentManager.beginTransaction()
        .add(R.id.pill_item_container, fragment)
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
