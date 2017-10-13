package com.kcunit.wtev2

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

    constructor(data:ArrayList<T>,resId:Int){
        this.mData=data
        this.mLayoutRes=resId
    }

    override fun getCount(): Int {
        return mData?.size ?: 0
    }

    override fun getItem(position: Int): T? {
        if (this.count >= position + 1){
            return this.mData?.get(position)
        }

        return null
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val vh:ViewHolder = ViewHolder.bind(parent.context,convertView,parent,this.mLayoutRes,position)

        this.bindView(vh,getItem(position))

        return convertView
    }

    abstract fun bindView(viewHolder:ViewHolder,obj:T?)

    fun AddData(data:T){
        this.mData?.add(data)
    }

    fun RemoveDataAt(position:Int):Boolean{
        if (position<this.count-1){
            this.mData?.removeAt(position)
            return true
        }
        return false
    }

    fun InsertData(position:Int,data:T){
        this.mData?.add(position,data)
    }

    fun ReplaceData(position:Int,data:T):Boolean{
        if (this.RemoveDataAt(position)){
            if(this.count>position+1){
                this.InsertData(position,data)
            }else{
                this.AddData(data)
            }
            return true
        }
        return false
    }

    fun ClearData(){
        this.mData?.clear()
    }
}