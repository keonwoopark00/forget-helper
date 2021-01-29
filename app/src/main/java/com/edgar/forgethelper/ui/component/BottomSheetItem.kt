package com.edgar.forgethelper.ui.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edgar.forgethelper.databinding.BottomSheetItemBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetItem(
    var title: String = "title",
    var btnText: String = "button",
    var onButtonClick: (String, String) -> Unit
) : BottomSheetDialogFragment() {
    private var _binding: BottomSheetItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetItemBinding.inflate(inflater, container, false)

        // fill TextViews with right information
        binding.bsItemTitle.text = title
        binding.bsItemBtn.text = btnText

        binding.bsItemBtn.setOnClickListener {
            onButtonClick(
                binding.bsItemEtName.text.toString(),
                binding.bsItemEtDescription.text.toString()
            )
        }

        return binding.root
    }
}