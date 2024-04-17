package com.example.testing_project

import org.json.JSONObject

data class WeatherJson(
        val main:String, val weather_temp:Double?, val weather_feelslike_temp:Double?,
        val weather_temp_min:Double?,  val weather_temp_max:Double?,  val weather_humidity:Int?,
        val wind:Double?, val cloudiness:Int?, val description: String)

fun WeatherJson(WeatherData: JSONObject): WeatherJson {
        val weather_main = WeatherData.getJSONArray("weather").get(0) as JSONObject
        val main = weather_main.get("main")
        val description = weather_main.get("description")
        val get_all_temps = WeatherData.opt("main") as JSONObject
        val weather_temp = get_all_temps.get("temp")
        val weather_feelslike_temp = get_all_temps.get("feels_like")
        val weather_temp_min = get_all_temps.get("temp_min")
        val weather_temp_max = get_all_temps.get("temp_max")
        val weather_humidity = get_all_temps.get("humidity")
        val get_wind = WeatherData.opt("wind") as JSONObject
        val wind = get_wind.get("speed")
        val get_clouds = WeatherData.opt("clouds") as JSONObject
        val cloudiness = get_clouds.get("all")
        return WeatherJson(main = main as String, description = description as String,
                        weather_temp = weather_temp as Double,
                        weather_feelslike_temp = weather_feelslike_temp as Double,
                        weather_temp_min = weather_temp_min as Double,
                        weather_temp_max = weather_temp_max as Double,
                        weather_humidity = weather_humidity as Int,
                        wind = wind as Double, cloudiness = cloudiness as Int)
}
