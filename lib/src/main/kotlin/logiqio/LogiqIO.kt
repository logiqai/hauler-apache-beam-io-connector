package logiqio

import org.apache.beam.sdk.transforms.Combine
import org.apache.beam.sdk.transforms.PTransform
import org.apache.beam.sdk.transforms.ParDo
import org.apache.beam.sdk.values.PCollection

class LogiqIO {
    class Write(private var endpoint: String = "", private var ingestToken: String = ""): PTransform<PCollection<LogiqEvent>, PCollection<LogiqError>>() {
        override fun expand(input: PCollection<LogiqEvent>): PCollection<LogiqError> {
            checkIfNullOrEmpty(endpoint, "no endpoint provided")
            checkIfNullOrEmpty(ingestToken, "no ingest token specified")

            return input.apply(Combine.globally(CombineEventsFn())).apply(ParDo.of(ProcessBatchedEvent(endpoint, ingestToken)))
        }

        fun withEndpoint(endpoint: String) : Write {
            this.endpoint = endpoint
            return this
        }

        fun withToken(token: String): Write {
            this.ingestToken = token
            return this
        }
    }
}
