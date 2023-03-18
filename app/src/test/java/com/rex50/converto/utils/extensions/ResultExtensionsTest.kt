package com.rex50.converto.utils.extensions

import com.google.gson.JsonElement
import com.rex50.converto.utils.Result
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
internal class ResultExtensionsTest {

    @Test
    fun `safely map for unsuccessful response`() {
        val response = mockk<Response<String>>()
        every { response.isSuccessful } returns false
        every { response.message() } returns ""

        val isFailureResult = response.mapSafelyToResult { it } is Result.Failure
        assertEquals(true, isFailureResult)
    }

    @Test
    fun `safely map for successful response`() {
        val response = mockk<Response<String>>()
        every { response.isSuccessful } returns true
        every { response.body() } returns "Response"
        every { response.message() } returns ""

        val isSuccessResult = response.mapSafelyToResult { it } is Result.Success
        assertEquals(true, isSuccessResult)
    }

    @Test
    fun `safely map for successful response and null body`() {
        val response = mockk<Response<String>>()
        every { response.isSuccessful } returns true
        every { response.body() } returns null
        every { response.message() } returns ""

        val isFailureResult = response.mapSafelyToResult { it } is Result.Failure
        assertEquals(true, isFailureResult)
    }

    @Test
    fun `safely map for successful response but exception while mapping`() {
        val response = mockk<Response<String>>()
        every { response.isSuccessful } returns true
        every { response.body() } returns "Response"
        every { response.message() } returns ""

        val isFailureResult = response.mapSafelyToResult {
            throw Exception("Some exception")
        } is Result.Failure
        assertEquals(true, isFailureResult)
    }



    @Test
    fun `safely map for unsuccessful result`() = runTest {
        val result = Result.Failure(Exception())
        val isFailureResult = result.mapSafelyIfSuccess { it } is Result.Failure
        assertEquals(true, isFailureResult)
    }

    @Test
    fun `safely map for successful result`() = runTest {
        val result = Result.Success("Data")
        val isSuccessResult = result.mapSafelyIfSuccess { it } is Result.Success
        assertEquals(true, isSuccessResult)
    }

    @Test
    fun `safely map for successful result but exception while mapping`() = runTest {
        val result = Result.Success("Data")
        val isFailureResult = result.mapSafelyIfSuccess {
            throw Exception("Some exception")
        } is Result.Failure
        assertEquals(true, isFailureResult)
    }


}