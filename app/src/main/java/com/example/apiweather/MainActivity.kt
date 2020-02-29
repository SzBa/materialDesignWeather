package com.example.apiweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException
import java.lang.Exception
import java.net.CacheResponse
import java.net.URL
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        JsonFun()

    }


    fun JsonFun() {

        val city = "Gliwice,pl"

        val API_KEY = "4d9c1e57310a8c80e27c9d8b1e80c77b"

        //https://api.openweathermap.org/data/2.5/weather?q=Gliwice,pl&APPID=4d9c1e57310a8c80e27c9d8b1e80c77b
        val url =
            "https://api.openweathermap.org/data/2.5/weather?q=$city&APPID=$API_KEY"



        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Nie udało sie")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                println(body)
            }

        })
    }

}
