# LOGIQ IO Connector for Apache Beam
> General Purpose Transforms to write events as a batch request to the LOGIQ Stack

---

# Usage
- ### Write
    1. Import the library to your project.
    2. Apply a function to transform your Events to LogiqEvent(s)
    3. Use the LogiqIO.Write class as a transform to start exporting events to a LOGIQ Stack endpoint.

## Sample Code to export a list of LogiqEvent(s) to a Logiq instance.
   ```kotlin
    package logiqio

    import org.apache.beam.sdk.Pipeline
    import org.apache.beam.sdk.transforms.Create
    import kotlin.test.Test

    class LogiqTest {

        @Test fun validateResponse() {
          val pipeline = Pipeline.create()

          // Create a sample list of type LogiqEvent
          val ele = List(50) {
            LogiqEvent(
              namespace = "ns$it", 
              message = "$it Events occurred",
              timestamp = it, 
              host = "host-$it",
              pid = "process-$it", 
              appName = "app-$it", 
              clusterId = "cos$it",
            )
          }

          pipeline
            .apply("Create elements", Create.of(ele)) // Use the LogiqEvent lists and add it to the pipeline
            .apply(LogiqIO.Write(endpoint = "endpoint_here", ingestToken = "ingest_token_here")) // Use LogiqIO.Write with the logiq stack endpoints and ingestToken 

          pipeline.run().waitUntilFinish()
        }
    }
   ```
  
   ## Exporting Apache Beam Metrics to a PushGateway
   In order to export metrics to a Prometheus PushGateway, you can refer to this [test](https://github.com/logiqai/logiq-io/blob/294c774740c966df70a2cc18f07b6b6c830b68c6/lib/src/test/kotlin/logiqio/LibraryTest.kt#L34)
