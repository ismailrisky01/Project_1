package tech.jayamakmur.trackingapp.util

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tech.jayamakmur.trackingapp.databinding.ViewLoadingBinding

open class MyDialog(private val context: Context) {
    private val dialog = Dialog(context)

    fun showLoading() = apply{
        val binding = ViewLoadingBinding.inflate(LayoutInflater.from(context))
        show(binding.root)
    }

    private fun show(view: View, width: Double = .9, height: Int = ViewGroup.LayoutParams.WRAP_CONTENT) {
        dialog.window!!.setBackgroundDrawable(context.getDrawable(android.R.color.transparent))
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.window!!.setLayout((context.resources.displayMetrics.widthPixels * width).toInt(), height)
        dialog.show()
    }

    fun dismiss() = dialog.dismiss()

}