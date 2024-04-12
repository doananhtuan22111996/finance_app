package com.example

class Person(lastName: String, firstName: String) {
    val fullName = "$firstName $lastName"
}

fun main() {
    val person = Person(lastName = "Tuan", firstName = "Doan")
    // Set to View
    println("Full Name: ${person.fullName}")
}