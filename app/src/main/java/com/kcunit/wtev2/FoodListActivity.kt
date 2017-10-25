package com.kcunit.wtev2

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList

class FoodListActivity : AppCompatActivity() {

    /**
     * 菜品数据
     */
    private val foodDatas=ArrayList<FoodBriefItem>()

    private lateinit var thisAdapter:BaseAdapterEnhanced<FoodBriefItem>

    private lateinit var thisList:ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)

        //增加ActionBar回退按钮
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        foodDatas.add(FoodBriefItem("麻婆豆腐",15,12,4.1f,1))
        //初始化adapter
        thisAdapter=object :BaseAdapterEnhanced<FoodBriefItem>(foodDatas,this,R.layout.food_list_item){
            override fun bindData(viewHolder: ViewHolder, data: FoodBriefItem) {
                viewHolder.setText(R.id.foodListItemName, data.FoodName)
                viewHolder.setText(R.id.foodListItemPrice, "${data.FoodPriceMin}-${data.FoodPriceMax}元")
                viewHolder.setRate(R.id.foodListItemRate,data.FoodRate)
            }
        }

        thisList=findViewById(R.id.foodListListView) as ListView
        thisList.adapter=thisAdapter

        bindListData()
    }

    /**
     * 重写activity菜单创建事件(该函数在activity加载时调用一次)
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.food_list_menus,menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * 菜单按钮选中事件
     * @param item 菜单按钮
     * @return
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            //添加按钮
            R.id.foodListActionBar_menu_add -> {
                Toast.makeText(this,"添加按钮已点击",Toast.LENGTH_SHORT).show()
                val intent = Intent(this,FoodDetailActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 回退导航按钮点击事件
     */
    override fun onSupportNavigateUp(): Boolean {
        Toast.makeText(this,"回退按钮已点击",Toast.LENGTH_SHORT).show()
        //bindListData()
        return super.onSupportNavigateUp()
    }

    /**
     * 绑定列表数据
     */
    private fun bindListData(){
        thisAdapter.addData(FoodBriefItem("酱肉丝",18,15,4.2f,2))
    }
}
