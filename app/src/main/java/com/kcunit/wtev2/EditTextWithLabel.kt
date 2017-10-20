package com.kcunit.wtev2

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import android.widget.TextView

/**
 * Created by Administrator on 2017/10/17.
 * 带标签的可编辑文本框
 */
class EditTextWithLabel : AppCompatEditText {
    private val labelText:String?

    private lateinit var mPaint:Paint

    //删除图标资源
    private var icDeleteResID:Int=0
    private lateinit var icDelete:Drawable

    //删除图标属性
    private var deleteX:Int=0
    private var deleteY:Int=0
    private var deleteWidth:Int=0
    private var deleteHeight:Int=0

    //左侧图标资源
    private var icLeftClickResID:Int=0
    private var icLeftUnclickResID:Int=0
    private lateinit var icLeftClick:Drawable
    private lateinit var icLeftUnclick:Drawable

    //左侧图标属性
    private var leftX:Int=0
    private var leftY:Int=0
    private var leftWidth:Int=0
    private var leftHeight:Int=0

    //光标
    private var cursor:Int=0

    //分割线颜色
    private var lineColorClick: Int = 0
    private var lineColorUnclick: Int = 0
    private var color:Int=0
    //分割线位置
    private var linePosition:Int=0

    constructor(context: Context, label:String?):super(context){
        this.labelText=label
    }

    constructor(context: Context?, attrs: AttributeSet?, label:String?):super(context,attrs){
        this.labelText=label
        initRes(context!!,attrs!!)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, label:String?):super(context,attrs,defStyleAttr){
        this.labelText=label
        initRes(context!!,attrs!!)
    }

    private fun initRes(context: Context,attrs: AttributeSet){
        val ta:TypedArray=context.obtainStyledAttributes(attrs,R.styleable.EditTextWithLabel)

        icLeftClickResID=ta.getResourceId(R.styleable.EditTextWithLabel_ic_left_click,R.mipmap.gear)
        icLeftClick=ContextCompat.getDrawable(context,icLeftClickResID)

        leftX=ta.getInteger(R.styleable.EditTextWithLabel_left_x,0)
        leftY=ta.getInteger(R.styleable.EditTextWithLabel_left_y,0)
        leftWidth=ta.getInteger(R.styleable.EditTextWithLabel_left_width,60)
        leftHeight=ta.getInteger(R.styleable.EditTextWithLabel_left_height,60)

        icLeftClick.setBounds(leftX,leftY,leftWidth,leftHeight)

        icLeftUnclickResID=ta.getResourceId(R.styleable.EditTextWithLabel_ic_left_unclick,R.mipmap.lock)

        icLeftUnclick=ContextCompat.getDrawable(context,icLeftUnclickResID)
        icLeftUnclick.setBounds(leftX,leftY,leftWidth,leftHeight)

        icDeleteResID =ta.getResourceId(R.styleable.EditTextWithLabel_ic_delete,R.mipmap.heart)
        icDelete=ContextCompat.getDrawable(context, icDeleteResID)

        deleteWidth=ta.getInteger(R.styleable.EditTextWithLabel_delete_width,60)
        deleteHeight=ta.getInteger(R.styleable.EditTextWithLabel_delete_height,60)
        deleteX=ta.getInteger(R.styleable.EditTextWithLabel_delete_x,0)
        deleteY=ta.getInteger(R.styleable.EditTextWithLabel_delete_y,0)

        icDelete.setBounds(deleteX,deleteY,deleteWidth,deleteHeight)

        setCompoundDrawables(icLeftUnclick,null,null,null)

        cursor=ta.getResourceId(R.styleable.EditTextWithLabel_cursor,R.mipmap.array_up)
        try {
            val field= TextView::class.java.getDeclaredField("mCursorDrawableRes")
            field.isAccessible=true
            field.set(this,cursor)
        }catch (e:Exception){
            e.printStackTrace()
        }

        mPaint=Paint()
        mPaint.strokeWidth=2f

        lineColorClick=ta.getColor(R.styleable.EditTextWithLabel_lineColor_click,R.color.lineColor_click)
        lineColorUnclick=ta.getColor(R.styleable.EditTextWithLabel_lineColor_unclick,R.color.lineColor_unclick)

        color=lineColorClick
        mPaint.color=lineColorUnclick

        setTextColor(color)

        linePosition = ta.getInteger(R.styleable.EditTextWithLabel_linePosition, 1)

        background=null

        ta.recycle()
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