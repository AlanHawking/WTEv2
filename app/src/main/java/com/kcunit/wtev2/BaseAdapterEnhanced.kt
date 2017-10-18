package com.kcunit.wtev2

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.util.*

/**
 * Created by Administrator on 2017/10/18.
 * replacement for BaseAdapterPlus(which has been deleted)
 * BaseAdapter扩展泛型类
 */
abstract class BaseAdapterEnhanced<T:ListItemBase>(private var dataList: ArrayList<T>, private var activityContext: Context, private var layoutItemResId: Int) : BaseAdapter() {
    /**
     * 获取itemId
     * @param position 列表中的指定位置参数
     * @return 位置
     */
    override fun getItemId(position: Int): Long = position.toLong()

    /**
     *重写方法获取指定位置的对应view
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder=ViewHolder.bind(activityContext,convertView,parent!!,layoutItemResId,position)

        this.bindData(holder,getItem(position))

        return holder.getItemView()
    }

    override fun getItem(position: Int): T = dataList[position]

    override fun getCount(): Int = dataList.size

    abstract fun bindData(viewHolder:ViewHolder,data:T)

    /**
     * 新增数据
     * @param data 要新增的数据
     * @return 是否新增成功
     */
    fun addData(data:T):Boolean{
        if(dataList.add(data)){
            notifyDataSetChanged()
            return true
        }

        return false
    }

    /**
     * 在指定位置插入
     * @param data 要插入的数据
     * @param position 要插入的位置
     * @return 当插入位置正确时执行插入并返回true,不正确则直接返回false
     */
    fun insertData(data: T,position: Int):Boolean{
        if(position>=0 && position<=dataList.size){
            dataList.add(position,data)

            notifyDataSetChanged()
            return true
        }

        return false
    }

    /**
     * 删除指定位置的数据
     * @param position 指定位置
     * @return 是否成功删除
     */
    fun deleteDataAtPosition(position: Int):Boolean{
        if(position>=0 && position<this.count){
            dataList.removeAt(position)
            notifyDataSetChanged()
            return true
        }

        return false
    }

    /**
     * 通过数据itemId获取
     * @param itemId
     * @return 指定数据itemId元素在集合中的索引
     */
    fun findDataPositionByItemId(itemId:Int):Int{
        return (0..(dataList.size-1)).firstOrNull { dataList[it].itemId==itemId }
                ?: -1
    }
}