package org.example.logic.search


class SearchUsingKMP {

    fun validateTheInputInExistData(input:String,searchList:List<String>?): String{
        if (searchList != null) {
            for(listItem in searchList){
                if (kmpSearch(listItem,input)) return listItem
            }
        }
         return "Not Found"
    }

    fun kmpSearch(text: String, pattern: String): Boolean {
        val lps = computeLPS(pattern)

        var i = 0 // text index
        var j = 0 // pattern index

        while (i < text.length) {
            if (text[i] == pattern[j]) {
                i++; j++
            }

            if (j == pattern.length) {
               return true
            } else if (i < text.length && text[i] != pattern[j]) {
                if (j != 0) {
                    j = lps[j - 1]
                } else {
                    i++
                }
            }
        }
        return false
    }


    fun computeLPS(pattern: String): IntArray {
        val lps = IntArray(pattern.length)
        var length = 0
        var i = 1

        while (i < pattern.length) {
            if (pattern[i] == pattern[length]) {
                length++
                lps[i] = length
                i++
            } else {
                if (length != 0) {
                    length = lps[length - 1]
                } else {
                    lps[i] = 0
                    i++
                }
            }
        }
        return lps
    }

}