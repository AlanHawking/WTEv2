package com.kcunit.wtev2

import java.net.URL
import java.util.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.io.PrintWriter


/**
 * Created by Administrator on 2017/10/25.
 * 服务器数据提供类
 */
class ServerDataProvider private constructor(hostInfo:String,private val requestMethod:RequestMethod){

    private val url=URL(hostInfo)
    private val gson= Gson()

    companion object {
        /**
         * 数据提供存储字典
         */
        private val dicProvider=Hashtable<String,ServerDataProvider>()

        /**
         * 获取数据提供实例
         */
        fun getProvider(serverInfo: String,requestMethod: RequestMethod):ServerDataProvider{
            var provider=dicProvider[serverInfo]

            if(provider==null){
                provider= ServerDataProvider(serverInfo,requestMethod)
                dicProvider.plus(Pair(serverInfo,provider))
            }

            return provider
        }

        enum class RequestMethod{GET,POST,PUT}
    }

    /**
     * 从远程服务器中获取指定类型的数据
     * @param params 传到服务器的参数
     * @return 返回数据
     */
    fun <T> getData(params:String):T {
        val jsonData=getJson(params)

        return gson.fromJson(jsonData,object:TypeToken<T>(){}.type)
    }

    /**
     * 获取指定类型的数据List
     * @param params 传到服务器的参数
     * @return 指定类型的数据List
     */
    fun <T> getList(params:String):List<T>{
        val jsonData=getJson(params)

        return gson.fromJson(jsonData,object:TypeToken<List<T>>(){}.type)
    }

    /**
     * 传入参数并获得返回Json字符串
     */
    private fun getJson(params:String):String{
        //开启链接并设置连接方式
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod=requestMethod.name.toUpperCase()

        //传入参数
        val pw = PrintWriter(conn.outputStream)
        pw.print(params)
        pw.flush()
        pw.close()

        //接受数据
        val inStream = conn.inputStream
        val data = ByteArray(1024)
        val outStream = ByteArrayOutputStream()
        var len = inStream.read(data)
        while (len != -1) {
            outStream.write(data, 0, len)
            len = inStream.read(data)
        }

        inStream.close()

        //json解析转化
        return outStream.toByteArray().toString()
    }
}