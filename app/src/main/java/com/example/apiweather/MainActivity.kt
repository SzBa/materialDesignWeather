package com.example.apiweather

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.internal.format
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val wikiApiServe by lazy {
        WeatherApiService.create()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                var data = changeDate(
                    (result.dt.toLong() + result.timezone.toLong()) * 1000L
                )
                var time = changeTime(
                    (result.dt.toLong() + result.timezone.toLong()) * 1000L
                )
                var temp:Float = result.main.temp.toFloat()
                var sunriseTime =
                    changeTime((result.sys.sunrise.toLong() + result.timezone.toLong()) * 1000L)
                    textViewSunrise.text = sunriseTime
                var sunsetTime =
                    changeTime((result.sys.sunset.toLong() + result.timezone.toLong()) * 1000L)
                    textViewSunset.text = sunsetTime
                if (result.cod == "200") {
                    imageViewThermometer.setImageResource(R.drawable.ic_wi_thermometer)
                    imageViewDegrees.setImageResource(R.drawable.ic_wi_degrees)
                    imageViewBarometer.setImageResource(R.drawable.ic_wi_barometer)
                    imageViewTime2.setImageResource(R.drawable.ic_wi_time_2)
                    imageViewSunrise.setImageResource(R.drawable.ic_wi_sunrise)
                    imageViewSunset.setImageResource(R.drawable.ic_wi_sunset)
                    iconView.setImageResource(R.drawable.ic_wi_day_sunny)

                    textViewThemperature.text = "%.1f".format(temp)
                    textViewBarometer.text = "${result.main.pressure} hPa"
                    textViewDescription.text = result.weather[0].description
                    textViewTime.text = time + data
                    when {
                        result.weather[0].main == "Clear" -> iconView.setImageResource(R.drawable.ic_wi_day_sunny)
                        result.weather[0].main == "Snow" -> iconView.setImageResource(R.drawable.ic_wi_snow)
                        result.weather[0].main == "Windy" -> iconView.setImageResource(R.drawable.ic_wi_windy)
                        result.weather[0].main == "Rain" -> iconView.setImageResource(R.drawable.ic_wi_day_rain)
                        result.weather[0].main == "Clouds" -> iconView.setImageResource(R.drawable.ic_wi_cloud)
                    }
                }
            }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    fun changeDate(miliSeconds: Long): String {
        val formatter = SimpleDateFormat(" dd MMM yyyy", Locale.US)
        val calendar = Calendar.getInstance()

        calendar.timeInMillis = miliSeconds
        return formatter.format(calendar.time)
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
