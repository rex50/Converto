package com.rex50.converto.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import org.junit.Before

abstract class PrefsMockHelper {

    abstract val context: Context
    private val prefs = mockk<SharedPreferences>()
    private val editor = mockk<SharedPreferences.Editor>()

    var storedLong: Pair<String, Long>? = null
    var storedString: Pair<String, String>? = null

    @Before
    fun `init prefs`() {
        every { context.getSharedPreferences("ConvertoPrefs", Context.MODE_PRIVATE) } returns prefs
        every { prefs.edit() } returns editor
    }

    fun mockkEveryPrefsGetStringReturns(value: String) {
        every { prefs.getString(any(), any()) } returns value
    }

    fun mockkEveryPrefsGetLongReturns(value: Long) {
        every { prefs.getLong(any(), any()) } returns value
    }

    fun mockkEveryPrefsPutString() {
        every { editor.putString(any(),any()) } answers {
            storedString = firstArg<String>() to secondArg()
            editor
        }
        every { editor.commit() } returns true
    }

    fun mockkEveryPrefsPutLong() {
        every { editor.putLong(any(),any()) } answers {
            storedLong = firstArg<String>() to secondArg()
            editor
        }
        every { editor.commit() } returns true
    }

}