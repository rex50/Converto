package com.rex50.converto.data.datasources.remote.mappers

import com.rex50.converto.data.MockResponses
import junit.framework.TestCase.assertEquals
import org.json.JSONObject
import org.junit.Test

class CurrenciesResponseMapperTest {

    private val mapper = CurrenciesResponseMapper()

    @Test
    fun `maps from JSONObject to CurrenciesResponse`(){
        val currenciesResponse = mapper.jsonToCurrenciesRateResponse(
            JSONObject(MockResponses.successStringWith5Rates)
        )
        assertEquals(5, currenciesResponse.rates.size)
        assertEquals("USD", currenciesResponse.base)
    }

}