package com.example.testing_project

import org.json.JSONObject

data class GeoLocationJson(
    val CountryName: String, val Cityname:String,
    val latitude:Double, val longitude: Double, val timeZone:String)
fun GeoLocationJson(GeoData: JSONObject): GeoLocationJson {
    return GeoLocationJson(
        CountryName = GeoData.opt("country") as String,
        Cityname = GeoData.opt("city") as String,
        latitude = GeoData.opt("lat") as Double,
        longitude = GeoData.opt("lon") as Double,
        timeZone = GeoData.opt("timezone") as String
    )
}