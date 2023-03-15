package logiqio

/**
 *  Checks if given string is null or if it is empty.
 *  If true, throws an [IllegalStateException] with your preferred message
 */
fun checkIfNullOrEmpty(test: String?, exceptionMessage: String?) {
    if (test == null) {
        throw IllegalStateException(exceptionMessage)
    }

    if (test == "") {
        throw IllegalStateException(exceptionMessage)
    }
}