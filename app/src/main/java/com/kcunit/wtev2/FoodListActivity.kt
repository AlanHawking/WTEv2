package com.kcunit.wtev2

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class FoodListActivity : AppCompatActivity() {

    /**
     * 菜品数据
     */
    private val foodDatas=ArrayList<FoodBriefItem>()

    private lateinit var thisAdapter:BaseAdapterPlus<FoodBriefItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)

        //增加ActionBar回退按钮
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //初始化adapter
        thisAdapter=object :BaseAdapterPlus<FoodBriefItem>(foodDatas,R.layout.activity_food_list){
            override fun bindView(viewHolder: ViewHolder, obj: FoodBriefItem?) {

            }
        }
    }

    /**
     * 重写activity菜单创建事件(该函数在activity加载时调用一次)
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.food_list_menus,menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * 菜单加载前事件(该函数在每次点击menu键时调用)
     */
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val x=1//未作任何修改，仅为展示两个菜单创建函数区别
        return super.onPrepareOptionsMenu(menu)
    }

    /**
     * 菜单按钮点击事件
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            //添加按钮
            R.id.foodListActionBar_menu_add -> {
                Toast.makeText(this,"添加按钮已点击",Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 回退导航按钮点击事件
     */
    override fun onSupportNavigateUp(): Boolean {
        Toast.makeText(this,"回退按钮已点击",Toast.LENGTH_LONG).show()
        return super.onSupportNavigateUp()
    }

    /**
     * 绑定列表数据
     */
    private fun bindListDatas(){

    }
}
