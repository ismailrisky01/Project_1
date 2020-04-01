package tech.jayamakmur.jmdlibrary.lib

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.my_picker_container.view.*
import kotlinx.android.synthetic.main.my_list_picker_item.view.*
import tech.jayamakmur.jmdlibrary.*
import tech.jayamakmur.jmdlibrary.util.MyDialog
import tech.jayamakmur.jmdlibrary.util.MyRecyclerAdapter
import tech.jayamakmur.jmdlibrary.util.getColorStateListState
import tech.jayamakmur.jmdlibrary.util.getDrawableState

@SuppressLint("InflateParams")
class MyListPicker<T>(private val context: Context) : MyDialog(context) {
    private var selectedPosition = MutableLiveData<Int>()

    private var onPositiveCallback: (index: Int, value: T) -> Unit = { _, _ -> }
    private var onNegativeCallback: () -> Unit = {}
    private lateinit var itemList: List<T>

    private var title: String = "Title Placeholder"
    private var desc: String = "Desc Placeholder"

    private fun initUI(view: View) {
        view.ID_MyImagePicker_Title.text = title
        view.ID_MyImagePicker_Desc.text = desc
    }

    private fun initListener(view: View) {
        view.ID_MyImagePicker_Confirm.setOnClickListener {
            dismissDialog()
            selectedPosition.value?.let { onPositiveCallback.invoke(it, itemList[it]) }
        }
        view.ID_MyImagePicker_Cancel.setOnClickListener {
            dismissDialog()
            onNegativeCallback.invoke()
        }
    }

    private fun initList(parent: View){
        parent.ID_MyImagePicker_RecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = MyRecyclerAdapter(
            R.layout.my_list_picker_item,
            itemList
        ).apply {
            onBindItemView { view, position, value ->
                view.ID_MyListPicker_ID.text = position.plus(1).toString()
                view.ID_MyListPicker_Name.text = value.toString()
                selectedPosition.observeForever {
                    val state = position == it
                    view.ID_MyListPicker_Background.background = context.getDrawableState(state,
                        R.color.colorPrimaryDark,
                        R.color.colorAccent
                    )
                    view.ID_MyListPicker_ID.setTextColor(context.getColorStateListState(!state,
                        R.color.colorPrimaryDark,
                        R.color.colorAccent
                    ))
                    view.ID_MyListPicker_Name.setTextColor(context.getColorStateListState(!state,
                        R.color.colorPrimaryDark,
                        R.color.colorAccent
                    ))
                }
            }
            onItemClick { selectedPosition.value = it }
        }.create()
        parent.ID_MyImagePicker_RecyclerView.adapter = adapter
    }

    fun show() = apply {
        selectedPosition.value = 0
        val view = LayoutInflater.from(context).inflate(R.layout.my_picker_container, null, true)
        initUI(view)
        initList(view)
        initListener(view)
        showDialog(view)
    }

    fun onPositiveListener(listener: (index: Int, value: T) -> Unit) = apply {
        onPositiveCallback = listener
    }

    fun onNegativeListener(listener: () -> Unit) = apply {
        onNegativeCallback = listener
    }

    fun setList(list: List<T>) = apply {
        itemList = list
    }
}