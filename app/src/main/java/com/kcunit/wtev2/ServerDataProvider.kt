package com.kcunit.wtev2

import java.net.URL

/**
 * Created by Administrator on 2017/10/24.
 * 服务器数据获取类
 */
abstract class ServerDataProvider (val serverAddr: String) {
    private val url:URL = URL(serverAddr)

    fun <T> GetData(param:Any){

        //DataRecived<T>()
    }
    /**
     * 数据获取到后的回调方法
     */
    abstract fun <T> DataRecived(data:T, Result:RequestResult)

    companion object {
        /**
         * 请求结果
         */
        enum class RequestResult{Success,Failed,ParseFailed}
    }
}