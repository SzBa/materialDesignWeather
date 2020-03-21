package com.example.apiweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView.setImageResource(R.drawable.ic_wi_day_cloudy)
        imageViewThermometer.setImageResource(R.drawable.ic_wi_thermometer)
        imageViewDegrees.setImageResource(R.drawable.ic_wi_degrees)
        imageViewBarometer.setImageResource(R.drawable.ic_wi_barometer)

        //fetchJson()

    }
}

/*
    fun fetchJson() {

        val view:TextView = findViewById(R.id.textDateView)

        var city:String = "Gliwice"

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

                val gson = GsonBuilder().create()

                val homeWeather = gson.fromJson(body, WeatherDataClass::class.java)
                println(homeWeather)

                val homeWeather2 = gson.fromJson(body, Main::class.java)
                println(homeWeather2)

                val homeWeather3 = gson.fromJson(body, Sys::class.java)
                println(homeWeather3)

                checkButton.setOnClickListener {
                    textTemperatureView.text = homeWeather.temp.toString()
                    textPressureView.text = homeWeather2.pressure.toString()
                    textSunriseView.text = homeWeather.sunrise.toString()
                    textSunsetView.text = homeWeather.sunset.toString()
                    textTimeView.text = homeWeather.timezone.toString()
                    textDescriptionView.text = homeWeather.description
                }
            }
        })
    }
}


// data -> nie ma

data class WeatherDataClass(
    val name: String, //nazwa miejscowosci, mozliwosc wpisania w pole
    val weather: List<Weather>, //potrzebne -> description, ikona
    val timezone: Int,
    val pressure: Int, //cisnienie
    val temp: Double, // temperatura
    val sunrise: Int, //wschod
    val sunset: Int, //zachod
    val description: String
)

data class Main(
    val pressure: Int, //cisnienie
    val temp: Double // temperatura
)

data class Sys(
    val sunrise: Int, //wschod
    val sunset: Int //zachod
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)


*/
