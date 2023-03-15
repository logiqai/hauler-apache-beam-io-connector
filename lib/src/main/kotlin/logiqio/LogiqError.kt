package logiqio

import java.io.Serializable

data class LogiqError(
    val statusCode: Int,
    val response: String,
) : Serializable
