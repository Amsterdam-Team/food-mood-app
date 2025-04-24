package presentation.utils

fun <T> tryToExecute(action: () -> T, onSuccess: (result: T) -> Unit) {
    println(loadingMessage())

    try {
        action().also { onSuccess(it) }
    } catch (exception: Exception) {
        println(getErrorMessageByException(exception))
        throw exception
    }
}

fun readValidInt(prompt: String): Int {
    while (true) {
        println(prompt)
        val input = readlnOrNull()?.toIntOrNull()
        if (input != null) return input
        println("Invalid input. Please enter a valid number.")
    }
}