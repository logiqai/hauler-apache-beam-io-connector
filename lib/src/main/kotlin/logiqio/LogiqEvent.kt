package logiqio

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LogiqEvent(
    @SerializedName("namespace")
    val namespace: String? = "",

    @SerializedName("message")
    val message: String? = "",

    @SerializedName("@timestamp")
    val timestamp: Int? = 0,

    @SerializedName("host")
    val host: String? = "",

    @SerializedName("proc_id")
    val pid: String? = "",

    @SerializedName("app_name")
    val appName: String? = "",

    @SerializedName("cluster_id")
    val clusterId: String? = "",
) : Serializable

