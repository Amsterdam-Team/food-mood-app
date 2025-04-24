package logic.helpers

import java.io.ByteArrayInputStream

fun simulateUserInput(input: String) {
    val inputStream = ByteArrayInputStream(input.toByteArray())
    System.setIn(inputStream)
}