package com.rex50.converto

import android.content.Context
import android.util.Log
import com.rex50.converto.data.MockResponses
import com.rex50.converto.data.datasources.remote.mappers.CurrenciesResponseMapper
import com.rex50.converto.data.models.CurrenciesRateResponse
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import net.danlew.android.joda.JodaTimeAndroid
import org.json.JSONObject

object TestUtils {

    /**
     * Joda date time needs to initialized before using DateTime
     */
    fun initJodaTimeAndroid(context: Context) {
        JodaTimeAndroid.init(context)
    }

    fun countRatesCount(jsonObject: JSONObject): Int {
        return jsonObject.optJSONObject("rates")?.keys().sizeOrZero()
    }

    fun createCurrenciesRateResponse(): CurrenciesRateResponse {
        return CurrenciesResponseMapper().jsonToCurrenciesRateResponse(
            JSONObject(MockResponses.successStringWith5Rates)
        )
    }

    fun mockLogs() {
        mockkStatic(Log::class)
        every { Log.e(any(), any(), any()) } returns 0
    }

    private fun <T> Iterator<T>?.sizeOrZero(): Int {
        var size = 0
        this?.forEach { _ -> size++ }
        return size
    }

}

suspend fun Job.awaitCompletion() {
    this.join()
}