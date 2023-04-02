package com.rex50.converto.data.datasources.local.datastore

import androidx.datastore.core.Serializer
import com.rex50.converto.data.models.UserLastSessionData
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object UserLastSessionDataSerializer : Serializer<UserLastSessionData> {
    override val defaultValue: UserLastSessionData
        get() = UserLastSessionData()

    override suspend fun readFrom(input: InputStream): UserLastSessionData {
        return try {
            Json.decodeFromString(
                deserializer = UserLastSessionData.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserLastSessionData, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = UserLastSessionData.serializer(), value = t
            ).encodeToByteArray()
        )
    }
}