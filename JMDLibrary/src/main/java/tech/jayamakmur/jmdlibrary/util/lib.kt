package tech.jayamakmur.jmdlibrary.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log

fun logD(vararg value: Any?) {
    var msg = "| "
    value.forEach { it?.let { msg += "$it |" } }
    Log.d("Eirene", msg)
}

fun getColorStateList(context: Context, id: Int) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) context.resources.getColorStateList(id, null)
    else context.resources.getColorStateList(id)

fun getDrawable(context: Context, id: Int): Drawable =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) context.resources.getDrawable(id, null)
    else context.resources.getDrawable(id)

fun Context.getDrawableState(state: Boolean, on: Int, off: Int) = if (state) getDrawable(
    this,
    on
) else getDrawable(this, off)

fun Context.getColorStateListState(state: Boolean, on: Int, off: Int) = if (state) getColorStateList(
    this,
    on
) else getColorStateList(this, off)