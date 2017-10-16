package com.kcunit.wtev2

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView

/**
 * Created by Administrator on 2017/9/21.
 * 通用ViewHolder
 */
class ViewHolder {
    private var mViews: SparseArray<View> = SparseArray()   //存储ListView 的 item中的View
    private var item:View                  //存放convertView
    private var position:Int = 0               //游标
    private var context: Context            //Context上下文

    //构造方法，完成相关初始化
    private constructor(context:Context, parent: ViewGroup,layoutRes:Int) {
        this.context = context
        val convertView:View = LayoutInflater.from(context).inflate(layoutRes, parent, false)
        convertView.tag = this
        item = convertView
    }

    /**
     * 静态方法
     */
    companion object {
        //绑定ViewHolder与item
        fun bind(context: Context, convertView: View?, parent: ViewGroup, layoutRes: Int, position: Int): ViewHolder {
            var holder: ViewHolder

            if(convertView != null){
                holder = convertView.tag as ViewHolder
                holder.item = convertView
            }else{
                holder = ViewHolder(context,parent,layoutRes)
            }

            holder.position = position

            return holder
        }
    }

    @SuppressWarnings("unchecked")
    fun <T:View>getView(id:Int): T {
        var t = mViews.get(id) as T

        t = item.findViewById(id)
        mViews.put(id, t)
        return t
    }

    /**
     * 获取当前条目
     */
    fun getItemView():View {
        return item
    }

    /**
     * 获取条目位置
     */
    fun getItemPosition():Int {
        return position
    }

    /**
     * 设置文字
     */
    fun setText(id:Int, text:CharSequence):ViewHolder {
        val view:View = getView(id)
        if (view is TextView) {
             view.text = text
        }
        return this
    }

    /**
     * 设置图片
     */
    fun setImageResource(id:Int, drawableRes:Int):ViewHolder {
        val view:View = getView(id);
        if (view is ImageView) {
            view.setImageResource(drawableRes)
        } else {
            view.setBackgroundResource(drawableRes)
        }

        return this
    }

    /**
     * 设置点击监听
     */
    fun setOnClickListener(id:Int,listener:View.OnClickListener ):ViewHolder {
        getView<View>(id).setOnClickListener(listener)
        return this
    }

    /**
     * 设置可见
     */
    fun setVisibility(id:Int, visible:Int):ViewHolder {
        getView<View>(id).visibility = visible
        return this
    }

    /**
     * 设置标签
     */
    fun setTag(id:Int,obj:Any):ViewHolder {
        getView<View>(id).tag = obj
        return this
    }

    /**
     * 设置评分
     */
    fun setRate(id:Int,rate:Float):ViewHolder{
        val ratingBar:RatingBar=getView(id)
        ratingBar.rating=rate
        return this
    }

    fun setRatingChangeListener(id:Int,listener:RatingBar.OnRatingBarChangeListener){
        getView<RatingBar>(id).onRatingBarChangeListener = listener
    }
    //其他方法可自行扩展
}