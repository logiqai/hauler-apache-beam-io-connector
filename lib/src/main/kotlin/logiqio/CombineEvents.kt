package logiqio

import com.google.gson.Gson
import org.apache.beam.sdk.transforms.Combine
import java.io.Serializable

class CombineEventsFn: Combine.CombineFn<LogiqEvent, CombineEventsFn.Result, String>() {
    class Result(val responses: MutableList<LogiqEvent> = mutableListOf()) : Serializable {
        override fun equals(other: Any?): Boolean {
            if (other == null) return false
            if (other !is Result) return false

            val r: Result = other

            return r.responses.containsAll(this.responses)
        }

        override fun hashCode(): Int {
            return responses.hashCode()
        }
    }

    override fun createAccumulator(): Result {
        return Result()
    }

    override fun extractOutput(accumulator: Result): String {
        return Gson().toJson(accumulator.responses)
    }

    override fun mergeAccumulators(accumulators: MutableIterable<Result>?): Result {
        val ret = Result()

        accumulators?.forEach {
            ret.responses.addAll(it.responses)
        }

        return ret
    }

    override fun addInput(mutableAccumulator: Result, input: LogiqEvent): Result {
        mutableAccumulator.responses.add(input)

        return mutableAccumulator
    }
}