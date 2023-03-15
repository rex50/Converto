package com.rex50.converto.data.datasources.remote.mappers

import com.rex50.converto.data.models.CurrenciesRateResponse
import com.rex50.converto.utils.extensions.orZero
import org.json.JSONObject

class CurrenciesResponseMapper {

    fun jsonToCurrenciesRateResponse(jsonObject: JSONObject): CurrenciesRateResponse {
        jsonObject.apply {
            val base = optString("base")
            val license = optString("license")
            val disclaimer = optString("disclaimer")
            val timeStamp = optInt("timestamp")
            val rates = hashMapOf<String, Double>()
            optJSONObject("rates")?.let {
                it.keys().forEach { curr ->
                    rates[curr] = it.optDouble(curr).orZero()
                }
            }
            return CurrenciesRateResponse(
                base,
                disclaimer,
                license,
                rates,
                timeStamp
            )
        }
    }

}