package com.rex50.converto.data.datasources.local.datastore

import androidx.datastore.core.Serializer
import com.rex50.converto.data.models.OpenExchangeData
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object OpenExchangeDataSerializer : Serializer<OpenExchangeData> {
    override val defaultValue: OpenExchangeData
        get() = OpenExchangeData()

    override suspend fun readFrom(input: InputStream): OpenExchangeData {
        return try {
            Json.decodeFromString(
                deserializer = OpenExchangeData.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: OpenExchangeData, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = OpenExchangeData.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}