package com.example.apiweather

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val wikiApiServe by lazy {
        WeatherApiService.create()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //iconView.setImageResource(R.drawable.ic_wi_day_cloudy)
        imageViewThermometer.setImageResource(R.drawable.ic_wi_thermometer)
        imageViewDegrees.setImageResource(R.drawable.ic_wi_degrees)
        imageViewBarometer.setImageResource(R.drawable.ic_wi_barometer)

        citySearch.setOnClickListener {
            if (inputCity.text.toString().isNotEmpty()){
                beginSearch(inputCity.text.toString())
                val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(citySearch.windowToken, 0)
            }
        }
    }


    private fun beginSearch(searchString: String) {
        disposable = wikiApiServe.hitCountCheck(
            searchString,
            "4d9c1e57310a8c80e27c9d8b1e80c77b",
            "metric",
            "pl"
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                val data = "${changeDate(
                    (result.dt.toLong() + result.timezone.toLong()) * 1000,
                    "dd.MM.yyyy"
                )}"
                val time = "${changeDate(
                    (result.dt.toLong() + result.timezone.toLong()) * 1000,
                    "mm:ss"
                )}"
                val sunriseTime =
                    "Sunrise: ${changeTime((result.sys.sunrise.toLong() + result.timezone.toLong()) * 1000L)}"
                val sunsetTime =
                    "Sunset: ${changeTime((result.sys.sunset.toLong() + result.timezone.toLong()) * 1000L)}"
                if (result.cod == "200") {
                    textViewThemperature.text = result.main.temp
                    textViewBarometer.text = result.main.pressure
                    textViewDescription.text = result.weather[0].description
                    textViewTime.text = data + time
                    if (result.weather[0].main == "Clear") {
                        iconView.setBackgroundResource(R.drawable.ic_wi_day_cloudy)
                    } else if (result.weather[0].main == "Rainy") {
                        iconView.setBackgroundResource(R.drawable.ic_wi_day_cloudy)
                    } else if (result.weather[0].main == "Windy") {
                        iconView.setBackgroundResource(R.drawable.ic_wi_day_cloudy)
                    } else if (result.weather[0].main == "Clouds") {
                        iconView.setBackgroundResource(R.drawable.ic_wi_day_cloudy)
                    }
                    textViewSunrise.text = sunriseTime
                    textViewSunset.text = sunsetTime
                }
            }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    fun changeDate(miliSeconds: Long, dateFormat: String?): String? {
        val formatter = SimpleDateFormat(dateFormat)
        val calendar = Calendar.getInstance()

        calendar.timeInMillis = miliSeconds

        return formatter.format(calendar.getTime())
    }

    fun changeTime(miliSeconds: Long): String? {
        val time = String.format (
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(miliSeconds) % 24,
            TimeUnit.MILLISECONDS.toMinutes(miliSeconds) % 60
        )
        return time
    }
}


