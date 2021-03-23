//package com.example.bitcoin.utils.widget.baseadapter
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.annotation.LayoutRes
//import androidx.databinding.DataBindingUtil
//import androidx.databinding.ViewDataBinding
//import androidx.recyclerview.widget.RecyclerView
//import com.example.bitcoin.R
//import com.example.androidnc.utils.printLog
//
//
//data class Student(
//    val id: Int = 0,
//    val name: String = ""
//
//)
//
//class BaseAdapter<M>(
//    context: Context,
//    @LayoutRes
//    private val layoutRes: Int,
//    private val onItemClick: ((M) -> Unit)? = null,
//) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), TriggerLoadMore {
//
//
//    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
//    private val mList = mutableListOf<M>()
//    private var stateLoadMore: Boolean = false
//    private lateinit var recyclerView: RecyclerView
//
//    override fun showItemLoadMore() {
//        printLog("showLoadMore")
//        stateLoadMore = true
//        recyclerView.post {
//            notifyItemInserted(itemCount)
//            recyclerView.smoothScrollToPosition(itemCount)
//        }
//    }
//
//    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
//        super.onAttachedToRecyclerView(recyclerView)
//        this.recyclerView = recyclerView
//    }
//
//    fun updateList(list: MutableList<M>, isRefresh: Boolean = false) {
//        stateLoadMore = false
//        notifyItemRemoved(itemCount)
//        (recyclerView as? LoadMoreRecyclerView)?.setLoaded()
//        if (isRefresh) {
//            mList.clear()
//        }
//        val size = mList.size
//        mList.addAll(list)
//        val sizeAfter = itemCount - 1
//        notifyItemRangeInserted(size, sizeAfter)
//    }
//
//    private fun getItem(position: Int): M {
//        return mList[position]
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
////        if (viewType == R.layout.item_load_more) {
////            val loadMoreView = layoutInflater.inflate(R.layout.item_load_more, parent, false)
////            return LoadMoreVH(loadMoreView)
////        }
//        val view: ViewDataBinding =
//            DataBindingUtil.inflate(layoutInflater, layoutRes, parent, false)
//        return BaseViewHolder(view, onItemClick)
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        (holder as? BaseViewHolder<M>)?.bindData(getItem(position))
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return if (stateLoadMore && position == itemCount - 1) {
//            R.layout.item_load_more
//        } else {
//            layoutRes
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return if (stateLoadMore) mList.size + 1 else mList.size
//    }
//
//}
//
//
//open class BaseViewHolder<M>(
//    private val rootView: ViewDataBinding,
//    private val onItemClick: ((M) -> Unit)?
//) :
//    RecyclerView.ViewHolder(rootView.root) {
//
//    fun bindData(model: M) {
//        rootView.root.setOnClickListener {
//            onItemClick?.invoke(model)
//        }
//        rootView.setVariable(BR.model, model)
//    }
//}
//
//
//class LoadMoreVH(itemView: View) : RecyclerView.ViewHolder(itemView) {}
//
//interface TriggerLoadMore {
//    fun showItemLoadMore()
//}
//
//interface PageIndicator {
//    fun triggerLoadMore()
//    fun triggerRefresh()
//}
