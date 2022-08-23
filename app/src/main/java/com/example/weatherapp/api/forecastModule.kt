package com.example.weatherapp.api

// To parse the JSON, install Klaxon and do:
//
//   val welcome10 = Welcome10.fromJson(jsonString)


import com.beust.klaxon.*

private fun <T> Klaxon.convert(k: kotlin.reflect.KClass<*>, fromJson: (JsonValue) -> T, toJson: (T) -> String, isUnion: Boolean = false) =
    this.converter(object: Converter {
        @Suppress("UNCHECKED_CAST")
        override fun toJson(value: Any)        = toJson(value as T)
        override fun fromJson(jv: JsonValue)   = fromJson(jv) as Any
        override fun canConvert(cls: Class<*>) = cls == k.java || (isUnion && cls.superclass == k.java)
    })

private val klaxon = Klaxon()
    .convert(Pod::class,         { Pod.fromValue(it.string!!) },         { "\"${it.value}\"" })
    .convert(Description::class, { Description.fromValue(it.string!!) }, { "\"${it.value}\"" })
    .convert(MainEnum::class,    { MainEnum.fromValue(it.string!!) },    { "\"${it.value}\"" })

data class FWeather (
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<ListElement>,
    val city: City
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<FWeather>(json)
    }
}

data class City (
    val id: Long,
    val name: String,
    val coord: CoordForecast,
    val country: String,
    val population: Long,
    val timezone: Long,
    val sunrise: Long,
    val sunset: Long
)

data class CoordForecast (
    val lat: Double,
    val lon: Double
)

data class ListElement (
    val dt: Long,
    val main: MainClass,
    val weather: List<WeatherForecast>,
    val clouds: CloudsForecast,
    val wind: WindForecast,
    val visibility: Long,
    val pop: Double,
    val sys: SysForecast,

    @Json(name = "dt_txt")
    val dtTxt: String,

    val rain: RainForecast? = null
)

data class CloudsForecast (
    val all: Long
)

data class MainClass (
    val temp: Double,

    @Json(name = "feels_like")
    val feelsLike: Double,

    @Json(name = "temp_min")
    val tempMin: Double,

    @Json(name = "temp_max")
    val tempMax: Double,

    val pressure: Long,

    @Json(name = "sea_level")
    val seaLevel: Long,

    @Json(name = "grnd_level")
    val grndLevel: Long,

    val humidity: Long,

    @Json(name = "temp_kf")
    val tempKf: Double
)

data class RainForecast (
    @Json(name = "3h")
    val the3H: Double
)

data class SysForecast (
    val pod: Pod
)

enum class Pod(val value: String) {
    D("d"),
    N("n");

    companion object {
        public fun fromValue(value: String): Pod = when (value) {
            "d"  -> D
            "n"  -> N
            else -> throw IllegalArgumentException()
        }
    }
}

data class WeatherForecast (
    val id: Long,
    val main: MainEnum,
    val description: Description,
    val icon: String
)

enum class Description(val value: String) {
    BrokenClouds("broken clouds"),
    FewClouds("few clouds"),
    LightRain("light rain"),
    OvercastClouds("overcast clouds"),
    ScatteredClouds("scattered clouds");

    companion object {
        public fun fromValue(value: String): Description = when (value) {
            "broken clouds"    -> BrokenClouds
            "few clouds"       -> FewClouds
            "light rain"       -> LightRain
            "overcast clouds"  -> OvercastClouds
            "scattered clouds" -> ScatteredClouds
            else               -> throw IllegalArgumentException()
        }
    }
}

enum class MainEnum(val value: String) {
    Clouds("Clouds"),
    Rain("Rain");

    companion object {
        public fun fromValue(value: String): MainEnum = when (value) {
            "Clouds" -> Clouds
            "Rain"   -> Rain
            else     -> throw IllegalArgumentException()
        }
    }
}

data class WindForecast (
    val speed: Double,
    val deg: Long,
    val gust: Double
)
