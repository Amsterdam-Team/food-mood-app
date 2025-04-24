package presentation.console

interface ConsoleIO {
    fun println(text: String)
    fun readInt(prompt: String): Int
}