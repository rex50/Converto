package com.rex50.converto.data.datasources.remote.mappers

import com.rex50.converto.data.models.CurrenciesRateResponse
import com.rex50.converto.data.models.Rate
import com.rex50.converto.utils.extensions.orZero
import org.joda.time.DateTime
import org.json.JSONObject

class CurrenciesResponseMapper {

    fun jsonToCurrenciesRateResponse(
        jsonObject: JSONObject,
        countriesMap: HashMap<String, String>? = null,
        lastUpdateTime: Long = 0
    ): CurrenciesRateResponse {
        jsonObject.apply {
            val base = optString("base")
            val license = optString("license")
            val disclaimer = optString("disclaimer")
            val timeStamp = optInt("timestamp")
            val rates = hashMapOf<String, Rate>()
            optJSONObject("rates")?.let {
                it.keys().forEach { curr ->
                    rates[curr] = Rate(
                        rate = it.optDouble(curr).orZero(),
                        currencyCode = curr,
                        country = countriesMap?.get(curr) ?: ""
                    )
                }
            }
            return CurrenciesRateResponse(
                base,
                disclaimer,
                license,
                rates,
                timeStamp,
                lastUpdateTime
            )
        }
    }

    fun jsonToCountriesResponse(jsonObject: JSONObject): HashMap<String, String> {
        val countriesMap = hashMapOf<String, String>()
        jsonObject.keys().forEach { currencyCode ->
            countriesMap[currencyCode] = jsonObject.optString(currencyCode)
        }
        return countriesMap
    }

}