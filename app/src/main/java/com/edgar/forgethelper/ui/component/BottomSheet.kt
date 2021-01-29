package com.edgar.forgethelper.ui.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edgar.forgethelper.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet(
    var title: String = "title",
    var guide: String = "guide",
    var btnText: String = "button",
    var onButtonClick: (String) -> Unit
) : BottomSheetDialogFragment() {
    private var _binding: BottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetBinding.inflate(inflater, container, false)

        // fill TextViews with right information
        binding.bsTvTitle.text = title
        binding.bsTvGuide.text = guide
        binding.bsBtn.text = btnText

        binding.bsBtn.setOnClickListener { onButtonClick(binding.bsEtName.text.toString()) }

        return binding.root
    }
}