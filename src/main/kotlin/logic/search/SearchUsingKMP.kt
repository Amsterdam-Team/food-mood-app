package logic.search


class SearchUsingKMP : SearchStrategy {

    override fun validateTheInputInExistData(input: String, searchList:List<String>?): String? {
        return searchList?.firstOrNull { listItem ->
            kmpSearch(listItem, input.lowercase())
        }
    }

    private fun kmpSearch(text: String, pattern: String): Boolean {
        val lps = computeLPS(pattern)

        var textIndex = 0
        var pattenIndex = 0

        while (textIndex < text.length) {
            if (text[textIndex] == pattern[pattenIndex]) {
                textIndex++; pattenIndex++
            }

            if (pattenIndex == pattern.length) {
                return true
            } else if (textIndex < text.length && text[textIndex] != pattern[pattenIndex]) {
                if (pattenIndex != 0) {
                    pattenIndex = lps[pattenIndex - 1]
                } else {
                    textIndex++
                }
            }
        }
        return false
    }


    private fun computeLPS(pattern: String): IntArray {
        val lps = IntArray(pattern.length)
        var currentPrefixSuffixLength = 0
        var patternIndex = 1

        while (patternIndex < pattern.length) {
            if (pattern[patternIndex] == pattern[currentPrefixSuffixLength]) {
                currentPrefixSuffixLength++
                lps[patternIndex] = currentPrefixSuffixLength
                patternIndex++
            } else {
                if (currentPrefixSuffixLength != 0) {
                    currentPrefixSuffixLength = lps[currentPrefixSuffixLength - 1]
                } else {
                    lps[patternIndex] = 0
                    patternIndex++
                }
            }
        }
        return lps
    }
}