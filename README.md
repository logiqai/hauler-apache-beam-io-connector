# LOGIQ IO Connector for Apache Beam
> General Purpose Transforms to write events as a batch request to the LOGIQ Stack

---

# Usage
- ### Write
    1. Import the library to your project.
    2. Apply a function to transform your Events to LogiqEvent(s)
    3. Use the LogiqIO.Write class as a transform to start exporting events to the LOGIQ Stack.

   ```java
    package logiqio

    import org.apache.beam.sdk.Pipeline
    import org.apache.beam.sdk.transforms.Create
    import kotlin.test.Test

    class LibraryTest {

        @Test fun validateResponse() {
          val pipeline = Pipeline.create()

          // Create a sample list of type LogiqEvent
          val ele = List(50) {
            LogiqEvent("ns$it", "$it Events occurred", it, "host-$it", "process-$it", "app-$it", "cos$it")
          }

          pipeline
            .apply("Create elements", Create.of(ele)) // Use the LogiqEvent lists and add it to the pipeline
            .apply(LogiqIO.Write(endpoint = "endpoint_here", ingestToken = "ingest_token_here")) // Use LogiqIO.Write with with the logiq stack endpoints and ingestToken 

          pipeline.run().waitUntilFinish()
        }
    }
   ```