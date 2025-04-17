package logic.search

interface SearchStrategy {
    fun validateTheInputInExistData(input: String, searchList: List<String>?): String?
}