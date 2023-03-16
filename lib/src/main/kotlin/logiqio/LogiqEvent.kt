package logiqio

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LogiqEvent(
    @SerializedName("namespace")
    private var namespace: String? = "",

    @SerializedName("message")
    private var message: String? = "",

    @SerializedName("@timestamp")
    private var timestamp: Int? = 0,

    @SerializedName("host")
    private var host: String? = "",

    @SerializedName("proc_id")
    private var pid: String? = "",

    @SerializedName("app_name")
    private var appName: String? = "",

    @SerializedName("cluster_id")
    private var clusterId: String? = "",
) : Serializable {
    fun withNamespace(namespace: String?) : LogiqEvent {
        this.namespace = namespace
        return this
    }

    fun withMessage(message: String?): LogiqEvent {
        this.message = message
        return this
    }

    fun withTimestamp(timestamp: Int?) : LogiqEvent {
        this.timestamp = timestamp
        return this
    }

    fun withHost(host: String?) : LogiqEvent {
        this.host = host
        return this
    }

    fun withPid(pid: String?) : LogiqEvent {
       this.pid = pid
       return this
    }

    fun withAppName(appName: String?) : LogiqEvent {
        this.appName = appName
        return this
    }

    fun withClusterId(clusterId: String?) : LogiqEvent {
        this.clusterId = clusterId
        return this
    }
}

