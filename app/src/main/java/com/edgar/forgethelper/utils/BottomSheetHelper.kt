package com.edgar.forgethelper.utils

import androidx.fragment.app.FragmentManager
import com.edgar.forgethelper.ui.component.BottomSheet
import com.edgar.forgethelper.ui.component.BottomSheetItem

/**
 * BottomSheetHelper.kt
 * provides functions to display a BottomSheet
 */

fun displayBottomSheet(helper: BottomSheetHelper, block: (s: String) -> Unit) {
    // declare a BottomSheet
    val bs = BottomSheet(helper.title, helper.guide!!, helper.btnText)
    // put block for btn click listener
    bs.onButtonClick = {
        block(it)
        bs.dismiss()
    }
    // pop up the BottomSheet
    bs.show(helper.manager, helper.tag)
}

fun displayBottomSheetItem(helper: BottomSheetHelper, block: (n: String, d: String) -> Unit) {
    // declare a BottomSheetItem
    val bs = BottomSheetItem(helper.title, helper.btnText)
    // put block for btn click listener
    bs.onButtonClick = { name: String, desc: String ->
        block(name, desc)
        bs.dismiss()
    }
    // pop up the BottomSheet
    bs.show(helper.manager, helper.tag)
}

class BottomSheetHelper(
    var title: String,
    var btnText: String,
    var guide: String? = null,
    var tag: String,
    var manager: FragmentManager
)