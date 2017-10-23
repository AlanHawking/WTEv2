package com.kcunit.wtev2

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.TextView

/**
 * Created by Administrator on 2017/10/17.
 * 带标签的可编辑文本框
 */
class EditTextWithLabel : AppCompatEditText {
    private var labelText:String?=null

    private lateinit var mPaint:Paint

    //删除图标资源
    private var icDeleteResID=0
    private lateinit var icDelete:Drawable

    //删除图标属性
    private var deleteX=0
    private var deleteY=0
    private var deleteWidth=0
    private var deleteHeight=0

    //左侧图标资源
    private var icLeftClickResID=0
    private var icLeftUnclickResID=0
    private var icLeftClick:Drawable?=null
    private var icLeftUnclick:Drawable?=null

    //左侧图标属性
    private var leftX=0
    private var leftY=0
    private var leftWidth=0
    private var leftHeight=0

    //光标
    private var cursor=0

    //分割线颜色
    private var lineColorClick = 0
    private var lineColorUnclick = 0
    private var color=0

    //分割线位置
    private var linePosition=0

    //长按与点击时间
    private var lastPressDownTime=0L
    private var lastPressUpTime=0L
    private var ifDeleteTouched=false
    //长按时间判断标准(ms)
    private val longPressTime=200

    constructor(context: Context):super(context)

    constructor(context: Context?, attrs: AttributeSet?):super(context,attrs){
        initRes(context!!,attrs!!)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int):super(context,attrs,defStyleAttr){
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

        icLeftClick?.setBounds(leftX,leftY,leftWidth,leftHeight)

        icLeftUnclickResID=ta.getResourceId(R.styleable.EditTextWithLabel_ic_left_unclick,R.mipmap.lock)

        icLeftUnclick=ContextCompat.getDrawable(context,icLeftUnclickResID)
        icLeftUnclick?.setBounds(leftX,leftY,leftWidth,leftHeight)

        icDeleteResID =ta.getResourceId(R.styleable.EditTextWithLabel_ic_delete,R.mipmap.heart)
        icDelete=ContextCompat.getDrawable(context, icDeleteResID)

        deleteWidth=ta.getInteger(R.styleable.EditTextWithLabel_delete_width,60)
        deleteHeight=ta.getInteger(R.styleable.EditTextWithLabel_delete_height,60)
        deleteX=ta.getInteger(R.styleable.EditTextWithLabel_delete_x,0)
        deleteY=ta.getInteger(R.styleable.EditTextWithLabel_delete_y,0)

        icDelete.setBounds(deleteX,deleteY,deleteWidth,deleteHeight)

        setCompoundDrawables(icLeftUnclick,null,null,null)

        /*光标样式更改
        cursor=ta.getResourceId(R.styleable.EditTextWithLabel_cursor,R.mipmap.array_up)
        try {
            val field= TextView::class.java.getDeclaredField("mCursorDrawableRes")
            field.isAccessible=true
            field.set(this,cursor)
        }catch (e:Exception){
            e.printStackTrace()
        }
        */

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

    /**
     * 重写图形绘制事件 -- 绘制底线
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mPaint.color=color
        setTextColor(color)

        val x=this.scrollX.toFloat()
        val w=this.measuredWidth.toFloat()
        val h=(this.measuredHeight-linePosition).toFloat()

        canvas?.drawLine(0f,h,w+x,h,mPaint)
    }

    /**
     * 重写文本内容修改事件
     * 在focusChanged事件后调用
     */
    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        setIconVisibility(hasFocus() && text.isNullOrEmpty(),hasFocus())
    }

    /**
     * 重写焦点改变事件
     */
    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        setIconVisibility(focused && length()>0,focused)
    }

    /**
     * 重写触摸事件 -- 处理删除图标点击
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_UP -> {
                lastPressUpTime=event.eventTime

                if(ifDeleteTouched) {
                    val draw = icDelete

                    //判断手指抬起的位置是否在删除图标范围(坐标x值在图标范围内)内--在则删除文字
                    if (event.x < (width - paddingRight) && event.x >= (width - paddingRight - draw.bounds.width())) {
                        if (lastPressUpTime - lastPressDownTime >= longPressTime) {
                            //长按状态
                            setText("")
                        } else {
                            //点击状态
                            if(text.isNullOrEmpty()) {
                                setText(text.removeRange(text.length - 1, text.length))
                            }
                        }
                    }
                }

                ifDeleteTouched=false
            }
            MotionEvent.ACTION_DOWN -> {
                ifDeleteTouched=true
                lastPressDownTime=event.eventTime
            }
        }
        return super.onTouchEvent(event)
    }
    /**
     * 设置图标可见性
     * @param deleteVisible 删除图标是否可见
     * @param leftVisible 左侧图标是否可见
     */
    private fun setIconVisibility(deleteVisible:Boolean,leftVisible:Boolean){
        val icClick=if (leftVisible){icLeftClick}else{icLeftUnclick}
        val icRight=if(deleteVisible){icDelete}else{null}
        setCompoundDrawables(icClick,null,icRight,null)
        setTextColor(color)

        //调用重绘view树方法
        invalidate()
    }
}