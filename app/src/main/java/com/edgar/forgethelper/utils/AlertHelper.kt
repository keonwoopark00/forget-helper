package com.edgar.forgethelper.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

fun makeAlert(
    context: Context,
    title: String,
    message: String,
    positiveListener: DialogInterface.OnClickListener,
) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton("Yes", positiveListener)
    builder.setNegativeButton("Cancel") { di: DialogInterface, _: Int ->
        di.dismiss()
    }
    builder.create().show()
}