package org.example.presentation.utils

// returns one random element or null if the list is empty
fun <T> List<T>.getRandomElementOrNull(): T? {
    return if (this.isEmpty()) null else this.random()
}

// returns a number of elements after shuffling the list
fun <T> List<T>.getRandomElements(count: Int): List<T> {
    return this.shuffled().take(count)
}
