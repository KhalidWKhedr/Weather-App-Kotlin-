package com.example.se_weatherapp
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testing_project.GeoLocationJson
import com.example.testing_project.WeatherJson
import org.json.JSONObject
import java.util.*

open class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv_cloudiness = findViewById<TextView>(R.id.tv_cloudiness)
        val tv_wind = findViewById<TextView>(R.id.tv_wind)
        val tv_humidity = findViewById<TextView>(R.id.tv_humidity)
        val tv_location = findViewById<TextView>(R.id.tv_location)
        val tv_description = findViewById<TextView>(R.id.tv_description)
        val tv_temperature = findViewById<TextView>(R.id.tv_temperature)
        val tv_maxtemp = findViewById<TextView>(R.id.tv_maxtemp)
        val tv_mintemp = findViewById<TextView>(R.id.tv_mintemp)
        val tv_units = findViewById<TextView>(R.id.tv_units)
        val tv_clouds = findViewById<TextView>(R.id.tv_clouds)
        val iv_cloudiness = findViewById<ImageView>(R.id.iv_cloudiness)
        val iv_windiness = findViewById<ImageView>(R.id.iv_windiness)
        val iv_humidty = findViewById<ImageView>(R.id.iv_humidity)
        val iv_uparrow = findViewById<ImageView>(R.id.iv_arrowup)
        val iv_arrowdown = findViewById<ImageView>(R.id.iv_arrowdown)
        val btn_retry = findViewById<Button>(R.id.btn_retry)
        val tv_FailConnect = findViewById<TextView>(R.id.tv_FailConnect)
        tv_FailConnect.visibility = View.INVISIBLE
        btn_retry.visibility = View.INVISIBLE

        fun invisible() {
            tv_wind.visibility = View.GONE
            tv_cloudiness.visibility = View.GONE
            tv_wind.visibility = View.GONE
            tv_humidity.visibility = View.GONE
            tv_location.visibility = View.GONE
            tv_description.visibility = View.GONE
            tv_temperature.visibility = View.GONE
            tv_maxtemp.visibility = View.GONE
            tv_mintemp.visibility = View.GONE
            tv_units.visibility = View.GONE
            tv_clouds.visibility = View.GONE
            iv_cloudiness.visibility = View.GONE
            iv_windiness.visibility = View.GONE
            iv_humidty.visibility = View.GONE
            iv_uparrow.visibility = View.GONE
            iv_arrowdown.visibility = View.GONE
            btn_retry.visibility = View.VISIBLE
            tv_FailConnect.visibility = View.VISIBLE
            println(tv_clouds.text)
        }

        fun visible(){
            iv_cloudiness.visibility = View.VISIBLE
            iv_windiness.visibility = View.VISIBLE
            iv_humidty.visibility = View.VISIBLE
            iv_uparrow.visibility = View.VISIBLE
            iv_arrowdown.visibility = View.VISIBLE
            tv_wind.visibility = View.VISIBLE
            tv_cloudiness.visibility = View.VISIBLE
            tv_wind.visibility = View.VISIBLE
            tv_humidity.visibility = View.VISIBLE
            tv_location.visibility = View.VISIBLE
            tv_description.visibility = View.VISIBLE
            tv_temperature.visibility = View.VISIBLE
            tv_maxtemp.visibility = View.VISIBLE
            tv_mintemp.visibility = View.VISIBLE
            tv_units.visibility = View.VISIBLE
            tv_clouds.visibility = View.VISIBLE
            btn_retry.visibility = View.INVISIBLE
            btn_retry.visibility = View.INVISIBLE
        }

        fun getCountryCode(countryName: String): String? {
            val countrycode =
                Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }
            return countrycode
        }

        fun extractWeather(extractWeatherJson: WeatherJson) {
            val msg_cloudiness = extractWeatherJson.cloudiness.toString() + "%"
            val msg_windiness = (extractWeatherJson.wind).toString() + "m/s"
            val msg_humidty= extractWeatherJson.weather_humidity.toString() + "%"
            tv_cloudiness.text = msg_cloudiness
            tv_wind.text = msg_windiness
            tv_humidity.text = msg_humidty
            tv_description.text = extractWeatherJson.description.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
            tv_temperature.text = extractWeatherJson.weather_temp.toString().substringBefore(".")
            tv_maxtemp.text = extractWeatherJson.weather_temp_max.toString().substringBefore(".")
            tv_mintemp.text = extractWeatherJson.weather_temp_min.toString().substringBefore(".")


        }

        fun ExtractGeo(extractGeoJson: GeoLocationJson) {
            val call_WeatherView = UrlViewModel()
            var WeatherData: String
            val weather_link = "https://api.openweathermap.org/data/2.5/weather?" +
                    "lat=${extractGeoJson.latitude}&lon=${extractGeoJson.longitude}" +
                    "&appid=09d35ee05631d058923d59e33c32dac7&units=metric"


            val countryName = extractGeoJson.CountryName
            val countrycode = getCountryCode(countryName)

            val msg_location = extractGeoJson.Cityname + "," + countrycode
            tv_location.text = msg_location



            call_WeatherView.urlLiveData.observe(this)
            { urlText ->
                WeatherData = urlText.toString()
                val extractWeatherJson = WeatherJson(JSONObject(WeatherData))
                extractWeather(extractWeatherJson)
            }
            call_WeatherView.fetchurl(weather_link)
        }

        fun getGeoLocation(urlGeo: String) {
            val call_GeoView = UrlViewModel()
            var GeoData: String
            call_GeoView.urlLiveData.observe(this)
            { urlText ->
                GeoData = urlText.toString()
                val extractGeoJson = GeoLocationJson(JSONObject(GeoData))
                ExtractGeo(extractGeoJson)
            }
            call_GeoView.fetchurl(urlGeo)
        }

        fun getExternalIp(): String {
            val call_IpView = UrlViewModel()
            var external_ip = ""
            call_IpView.urlLiveData.observe(this)
            { urlText ->
                external_ip = urlText.toString()
            }
            call_IpView.fetchurl("https://checkip.amazonaws.com")
            return external_ip
        }

        fun checkConnection() {
            val call_CheckConnection = CheckConnection()
            val connection = call_CheckConnection.getConnection()
            if (connection != null) {
                visible()
                getExternalIp()
                val urlGeo = "http://ip-api.com/json/${getExternalIp()}"
                (getGeoLocation(urlGeo))
            } else {
                invisible()
                btn_retry.setOnClickListener {
                    checkConnection()
                }

            }
        }
        checkConnection()
    }
}
