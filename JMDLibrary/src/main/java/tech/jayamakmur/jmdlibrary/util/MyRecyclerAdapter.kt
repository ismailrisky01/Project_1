package tech.jayamakmur.jmdlibrary.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyRecyclerAdapter<T>(private val layout: Int, private val list: List<T>) {
    private var onBindItemView: (View, Int, T) -> Unit = { _, _, _ -> }
    private var onItemClick: (Int) -> Unit = {}

    fun onBindItemView(listener: (View, Int, T) -> Unit) = apply {
        onBindItemView = listener
    }

    fun onItemClick(listener: (Int) -> Unit) = apply {
        onItemClick = listener
    }

    fun create() = InternalAdapter(list, layout, onBindItemView, onItemClick)

    class InternalAdapter<T>(
        var list: List<T>,
        private val layout: Int,
        private val onBindItemView: (View, Int, T) -> Unit,
        private val onItemClick: (Int) -> Unit
    ) : RecyclerView.Adapter<InternalAdapter.ViewHolder>() {

        override fun getItemCount() = list.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                LayoutInflater.from(parent.context).inflate(layout, parent, false)
            )

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            onBindItemView.invoke(holder.view, position, list[position])
            holder.itemView.setOnClickListener { onItemClick.invoke(position) }
        }

        class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    }
}