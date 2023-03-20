package logiqio

import org.apache.beam.sdk.Pipeline
import org.apache.beam.sdk.transforms.Create
import org.apache.beam.sdk.metrics.Metrics
import org.apache.beam.sdk.transforms.DoFn
import org.apache.beam.sdk.transforms.ParDo
import kotlin.test.Test

class ApplyMetrics : DoFn<LogiqEvent, LogiqEvent>() {
    private var counter = Metrics.counter("Pipeline Metrics", "logiq_events_processed");

    @ProcessElement
    fun processElement() {
        counter.inc()
    }
}

class LibraryTest {
    @Test fun validateResponse() {
        val pipeline = Pipeline.create()

        val ele = List(50) {
            LogiqEvent("ns$it", "$it Events occurred", it, "host-$it", "process-$it", "app-$it", "cos$it")
        }

        pipeline
            .apply("Create elements", Create.of(ele))
            .apply(LogiqIO.Write(endpoint = "http://localhost:9999/v1/json_batch", ingestToken = "ingest_token_here"))

        pipeline.run().waitUntilFinish()
    }

    @Test fun metrics() {
        val pipeline = Pipeline.create()

        val elems = List(1029) {
            LogiqEvent("ns$it", "$it Events occurred", it, "host-$it", "process-$it", "app-$it", "cos$it")
        }

        pipeline
            .apply("Create", Create.of(elems))
            .apply(ParDo.of(ApplyMetrics()))

        val result = pipeline.run()
        val metrics = result.metrics()

        PushGateway("http://localhost:9091/metrics/job/test").write(metrics)
    }
}

