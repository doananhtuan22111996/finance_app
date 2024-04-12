package com.example

class MyClass(private val id: String, val name: String) {
    override fun toString(): String = "Class $name, Id is $id"
}

fun main() {
    val a = MyClass(id = "abc", name = "Class A")
    println(a.toString())
}