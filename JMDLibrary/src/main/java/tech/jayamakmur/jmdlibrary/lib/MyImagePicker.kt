package tech.jayamakmur.jmdlibrary.lib

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.my_image_picker_item.view.*
import kotlinx.android.synthetic.main.my_picker_container.view.*
import org.jetbrains.anko.image
import tech.jayamakmur.jmdlibrary.*
import tech.jayamakmur.jmdlibrary.util.MyDialog
import tech.jayamakmur.jmdlibrary.util.MyRecyclerAdapter
import tech.jayamakmur.jmdlibrary.util.getColorStateList
import tech.jayamakmur.jmdlibrary.util.getDrawable

@SuppressLint("InflateParams")
class MyImagePicker<T>(private val context: Context) : MyDialog(context) {
    private var selectedPosition = MutableLiveData<Int>()

    private var onPositiveCallback: (index: Int, value: T) -> Unit = { _, _ -> }
    private var onNegativeCallback: () -> Unit = {}
    private lateinit var itemList: List<T>

    private var title: String = "Title Placeholder"
    private var desc: String = "Desc Placeholder"

    init {
        selectedPosition.value = 0
    }

    private var type = 0
    fun setType(type: Int) = apply {
        this.type = type
    }

    private fun initUI(view: View) {
        view.ID_MyImagePicker_Title.text = title
        view.ID_MyImagePicker_Desc.text = desc
    }

    fun setDefault(value: T) = apply {
        var index = 0
        itemList.forEach {
            if (value == it) selectedPosition.value = index
            index++
        }
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

    private fun initList(parent: View) {
        parent.ID_MyImagePicker_RecyclerView.layoutManager = GridLayoutManager(context, 3)
        val adapter = MyRecyclerAdapter(
            R.layout.my_image_picker_item,
            itemList
        ).apply {
            onBindItemView { view, position, value ->
                if (type == 0)
                    view.ID_MyImagePicker_Item.image = getDrawable(context, value as Int)
                else if (type == 1) {
                    view.ID_MyImagePicker_Item.visibility = View.GONE
                    view.ID_MyImagePicker_Container.setCardBackgroundColor(
                        getColorStateList(
                            context,
                            value as Int
                        )
                    )
                }
                view.ID_MyImagePicker_Item_Selected.image =
                    getDrawable(context, R.drawable.ic_check)
                selectedPosition.observeForever {
                    view.ID_MyImagePicker_Item_Selected.visibility = if (it == position) View.VISIBLE else View.GONE
                }
            }
            onItemClick { selectedPosition.value = it }
        }.create()
        parent.ID_MyImagePicker_RecyclerView.adapter = adapter
    }

    fun show() = apply {
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

    companion object{
        const val IMAGE = 0
        const val COLOR = 1
    }
}

