package tech.jayamakmur.jmdlibrary.util

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.ViewGroup

open class MyDialog(private val context: Context, private val cancelable: Boolean = true) {
    private val dialog = Dialog(context)

    fun showDialog( view:View,width: Double = .9, height: Int = ViewGroup.LayoutParams.WRAP_CONTENT) {
        dialog.window!!.setBackgroundDrawable(context.getDrawable(android.R.color.transparent))
        dialog.setContentView(view)
        dialog.window!!.setLayout((context.resources.displayMetrics.widthPixels * width).toInt(), height)
        dialog.setCancelable(cancelable)
        dialog.show()
    }

    fun dismissDialog() = dialog.dismiss()

}