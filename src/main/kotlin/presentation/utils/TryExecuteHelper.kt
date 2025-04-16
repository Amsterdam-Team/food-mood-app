package presentation.utils

fun <T> tryToExecute(action: () -> T, onSuccess: (result: T) -> Unit) {
    println(loadingMessage())

    try {
        action().also { onSuccess(it) }
    } catch (exception: Exception) {
        println(getErrorMessageByException(exception))
    }
}