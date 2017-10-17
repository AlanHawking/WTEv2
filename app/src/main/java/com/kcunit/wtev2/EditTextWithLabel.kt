package com.kcunit.wtev2

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText

/**
 * Created by Administrator on 2017/10/17.
 * 带标签的可编辑文本框
 */
class EditTextWithLabel : EditText {
    constructor(context: Context):super(context)

    constructor(context: Context?, attrs: AttributeSet?):super(context,attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int):super(context,attrs,defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int):super(context,attrs,defStyleAttr,defStyleRes)


}