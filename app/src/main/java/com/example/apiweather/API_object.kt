package com.example.apiweather

object API_object {
    data class Weather(
        val main: String,
        val description: String,
        val icon: String
    )
    data class Main(
        val temp: String,
        val pressure: String,
        val humidity: String
    )
    data class Sys(
        val sunrise: String,
        val sunset: String
    )
    data class Model(
        val weather: List<Weather>,
        val main: Main,
        val sys:Sys,
        val dt:String,
        val name: String,
        val timezone: String,
        val cod: String
    )
}
