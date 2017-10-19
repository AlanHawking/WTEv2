package com.kcunit.wtev2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import android.widget.EditText

/**
 * Created by Administrator on 2017/10/17.
 * 带标签的可编辑文本框
 */
class EditTextWithLabel : AppCompatEditText {
    private val labelText:String?

    //删除图标资源
    private var ic_deleteResID:Int=0
    private lateinit var ic_delete:Drawable

    //删除图标属性
    private var delete_x:Int=0
    private var delete_y:Int=0
    private var delete_width:Int=0
    private var delete_height:Int=0

    //左侧图标资源
    private var ic_left_clickResID:Int=0
    private var ic_left_unclickResID:Int=0
    private lateinit var ic_left_click:Drawable
    private lateinit var ic_left_unclick:Drawable

    //左侧图标属性
    private var left_x:Int=0
    private var left_y:Int=0
    private var left_width:Int=0
    private var left_height:Int=0

    //光标
    private var cursor:Int=0

    //分割线颜色
    private var lineColor_click: Int = 0
    private var lineColor_unclick: Int = 0
    private var color:Int=0
    //分割线位置
    private var linePosition:Int=0

    constructor(context: Context, label:String?):super(context){
        this.labelText=label
    }

    constructor(context: Context?, attrs: AttributeSet?, label:String?):super(context,attrs){
        this.labelText=label
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, label:String?):super(context,attrs,defStyleAttr){
        this.labelText=label
    }

    override fun onDraw(canvas: Canvas?) {
        val paint=Paint()
        paint.textSize=20f
        paint.color= Color.GRAY
        val drawString=labelText?:""
        canvas?.drawText(drawString,10f,(height / 2 + 5).toFloat(),paint)

        super.onDraw(canvas)
    }
}