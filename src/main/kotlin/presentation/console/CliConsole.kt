package presentation.console

import presentation.utils.readValidInt

class CliConsole : ConsoleIO {
    override fun println(message: String) = kotlin.io.println(message)
    override fun readInt(prompt: String): Int = readValidInt(prompt)
}