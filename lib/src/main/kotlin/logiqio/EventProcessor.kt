package logiqio

import org.apache.beam.sdk.transforms.DoFn
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class ProcessBatchedEvent(private val endpoint: String, private val ingestToken: String) : DoFn<String, LogiqError>() {
    @ProcessElement
    fun processElement(@Element element: String, receiver: OutputReceiver<LogiqError>) {
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(this.endpoint))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer $ingestToken")
            .POST(HttpRequest.BodyPublishers.ofString(element))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        receiver.output(LogiqError(response.statusCode(), response.body()))
    }
}