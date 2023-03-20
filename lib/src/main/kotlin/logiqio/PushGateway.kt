package logiqio

import org.apache.beam.sdk.metrics.DistributionResult
import org.apache.beam.sdk.metrics.GaugeResult
import org.apache.beam.sdk.metrics.MetricResult
import org.apache.beam.sdk.metrics.MetricResults
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class PushGateway(private val endpoint: String) {
    private fun gather(metrics: MetricResults) :  String {
        val allMetrics = metrics.allMetrics()
        val metricLines = mutableListOf<String>();

        metricLines.addAll(transformCounters(allMetrics.counters))
        metricLines.addAll(transformDistributions(allMetrics.distributions))
        metricLines.addAll(transformGauges(allMetrics.gauges))

        // The prometheus text protocol requires the end of the line to be newline
        return metricLines.joinToString("\n") + "\n"
    }

    fun write(metrics: MetricResults) {
        val client = HttpClient.newBuilder().build();
        val requestBody = gather(metrics)

        val request = HttpRequest.newBuilder()
            .uri(URI.create(endpoint))
            .header("Content-Type", "text/plain")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        print(response.body())
    }

    private fun transformCounters(res: Iterable<MetricResult<Long>>) : List<String> {
        return res.map {
            "${it.name.name} ${it.committedOrNull}"
        }
    }

    private fun transformDistributions(res: Iterable<MetricResult<DistributionResult>>) : Collection<String> {
        return res.map {
            "${it.name.name} ${it.committed.count}"
        }
    }

    private fun transformGauges(res: Iterable<MetricResult<GaugeResult>>): Collection<String> {
        return res.map {
            "${it.name.name} ${it.committed.value}"
        }
    }
}