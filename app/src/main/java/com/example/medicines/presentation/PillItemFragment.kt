package com.example.medicines.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.medicines.R
import com.example.medicines.databinding.FragmentPillItemBinding
import com.example.medicines.domain.PillItem
import com.google.android.material.textfield.TextInputLayout

class PillItemFragment : Fragment() {
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private lateinit var viewModel: PillItemViewModel
    private lateinit var pillName: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var pillCount: TextInputLayout
    private lateinit var etCount: EditText
    private lateinit var descriptionOfTaking: TextInputLayout
    private lateinit var etDescription: EditText
    private lateinit var saveBtn: Button
    private lateinit var profilePillIcon: ImageView

    private var screenMode: String = MODE_UNKNOWN
    private var pillItemId: Int = PillItem.UNDEFINED_ID

    private lateinit var binding: FragmentPillItemBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("FragmentLifeCycle", "Cycle now is onAttach")
        if (context is OnEditingFinishedListener){
            onEditingFinishedListener = context
        }
        else throw RuntimeException("Activity must to implement OnEditingFinishedListener")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        parseParams()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments
        binding = FragmentPillItemBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[PillItemViewModel::class.java]
        initViews()
        addTextChangeListener()
        launchRightMode()
    }

    private fun addTextChangeListener() {
        binding.etName.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        binding.etCount.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun observeViewModel() {
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            pillCount.error = message
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            pillName.error = message
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }
    }

    interface OnEditingFinishedListener {
        fun onEditingFinished()
        
    }

    private fun launchEditMode() {
        viewModel.getPillItem(pillItemId)
        viewModel.pillItem.observe(viewLifecycleOwner) {
            etName.setText(it.title)
            etCount.setText(it.restOfPills.toString())
            etDescription.setText(it.descriptionOfTaking)
        }
        saveBtn.setOnClickListener {
            viewModel.editPillItem(
                etName.text?.toString(),
                etCount.text?.toString(),
                etDescription.text?.toString()
            )
        }
    }

    private fun launchAddMode() {
//        viewModel.getPillItem(pillItemId)
//        viewModel.pillItem.observe(viewLifecycleOwner) {
//            etName.setText(it.title)
//            etCount.setText(it.restOfPills.toString())
//            etDescription.setText(it.descriptionOfTaking)
//        }
        saveBtn.setOnClickListener {
            viewModel.addPillItem(
                etName.text?.toString(),
                etCount.text?.toString(),
                etDescription.text?.toString()
            )
        }
    }

    private fun parseParams() {

        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw  RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(PILL_ITEM_ID)) {
                throw RuntimeException("Param pill item Id is absent")
            }
            pillItemId = args.getInt(PILL_ITEM_ID, pillItemId)
        }
    }

    private fun initViews() {
        pillName = binding.pillName
        etName = binding.etName
        pillCount = binding.pillCount
        etCount = binding.etCount
        descriptionOfTaking = binding.descriptionOfTaking
        etDescription = binding.etDescription
        saveBtn = binding.saveBtn
        profilePillIcon = binding.profilePillIcon
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val PILL_ITEM_ID = "extra_pill_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): PillItemFragment {
            return PillItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(pillItemId: Int): PillItemFragment {
            return PillItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(PILL_ITEM_ID, pillItemId)
                }
            }
        }
    }
}