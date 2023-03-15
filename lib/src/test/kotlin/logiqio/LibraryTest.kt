package logiqio

import org.apache.beam.sdk.Pipeline
import org.apache.beam.sdk.transforms.Create
import org.apache.beam.sdk.values.PDone
import kotlin.test.Test

class LibraryTest {

    @Test fun validateResponse() {
        val pipeline = Pipeline.create()

        val ele = List(50) {
            LogiqEvent("ns$it", "$it Events occurred", it, "host-$it", "process-$it", "app-$it", "cos$it")
        }

        pipeline
            .apply("Create elements", Create.of(ele))
            .apply(LogiqIO.Write(endpoint = "http://localhost:9999/v1/json_batch", ingestToken = "none"))

        pipeline.run().waitUntilFinish()
    }
}

