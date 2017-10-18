package com.kcunit.wtev2

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.util.*

/**
 * Created by Administrator on 2017/10/18.
 */
abstract class BaseAdapterEnhanced<T>(dataList: LinkedList<T>, activityContext: Context, layoutItemResId: Int) : BaseAdapter() {
    private var data: LinkedList<T> = dataList
    private var context:Context = activityContext
    private var layoutItemId:Int = layoutItemResId

    /**
     * 获取itemId
     * @param position 列表中的指定位置参数
     * @return 位置
     */
    override fun getItemId(position: Int): Long = position.toLong()

    /**
     *获取指定位置的对应view
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder=ViewHolder.bind(context,convertView,parent!!,layoutItemId,position)

        this.bindData(holder,getItem(position))

        return holder.getItemView()
    }

    override fun getItem(position: Int): T = data[position]

    override fun getCount(): Int = data.size

    abstract fun bindData(viewHolder:ViewHolder,data:T)
}