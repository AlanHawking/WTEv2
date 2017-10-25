package com.kcunit.wtev2

/**
 * 食品简介项
 * Created by Administrator on 2017/10/11.
 */
data class FoodBriefItem (var FoodName:CharSequence, var FoodPriceMax:Int, var FoodPriceMin:Int, var FoodRate:Float, override var itemId: Int): ServerDataBase(),IListItemBase {
    override fun readJsonString(data: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}