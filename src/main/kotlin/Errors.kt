package org.example

open class ValidationException(message:String): Exception()

class CountryNameNotException (message:String): ValidationException(message)