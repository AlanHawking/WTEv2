package com.kcunit.wtev2

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.util.ArrayList

/**
 * Created by Administrator on 2017/9/21.
 */
abstract class BaseAdapterPlus<T>: BaseAdapter {
    private var mData: ArrayList<T>? =null
    private var mLayoutRes: Int=0
    private var mContext: Context

    constructor(context: Context,data:ArrayList<T>,resId:Int){
        this.mData=data
        this.mLayoutRes=resId
        this.mContext=context
    }

    override fun getCount(): Int {
        return mData?.size ?: 0
    }

    override fun getItem(position: Int): T? {
        if (this.count >= position + 1 && position >= 0){
            return this.mData?.get(position)
        }

        return null
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val vh:ViewHolder = ViewHolder.bind(this.mContext,convertView,parent,this.mLayoutRes,position)

        this.bindView(vh,getItem(position))

        return vh.getItemView()
    }

    abstract fun bindView(viewHolder:ViewHolder,obj:T?)

    fun AddData(data:T){
        this.mData?.add(data)
        notifyDataSetChanged()
    }

    fun RemoveDataAt(position:Int):Boolean{
        if (position<this.count-1){
            this.mData?.removeAt(position)
            notifyDataSetChanged()
            return true
        }
        return false
    }

    fun InsertData(position:Int,data:T){
        this.mData?.add(position,data)
        notifyDataSetChanged()
    }

    fun ReplaceData(position:Int,data:T):Boolean{
        if (this.RemoveDataAt(position)){
            if(this.count>position+1){
                this.InsertData(position,data)
            }else{
                this.AddData(data)
            }
            notifyDataSetChanged()
            return true
        }
        return false
    }

    fun ClearData(){
        this.mData?.clear()
        notifyDataSetChanged()
    }
}