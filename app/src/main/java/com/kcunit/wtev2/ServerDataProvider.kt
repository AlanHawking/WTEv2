package com.kcunit.wtev2

import java.net.URL
import java.util.*
import android.R.attr.data
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection


/**
 * Created by Administrator on 2017/10/25.
 * 服务器数据提供类
 */
class ServerDataProvider private constructor(val hostInfo:String){

    val url=URL(hostInfo)

    companion object {
        /**
         * 数据提供存储字典
         */
        private val dicProvider=Hashtable<String,ServerDataProvider>()

        /**
         * 获取数据提供实例
         */
        fun getProvider(serverInfo: String):ServerDataProvider{
            var provider=dicProvider[serverInfo]

            if(provider==null){
                provider= getProvider(serverInfo)
            }

            return provider
        }
    }

    fun getData(params:String):String {
        val conn = url.openConnection() as HttpURLConnection

        conn.outputStream.write(params.toByteArray())

        val inStream = conn.inputStream
        val data = ByteArray(1024)
        val outStream = ByteArrayOutputStream()
        var len = inStream.read(data)
        while (len != -1) {
            outStream.write(data, 0, len)
            len = inStream.read(data)
        }

        inStream.close()

        return outStream.toByteArray().toString()
    }
}