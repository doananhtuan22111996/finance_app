package com.example

// Raw Model
abstract class BaseRaw<BM : BaseModel> {
    abstract fun raw2Model(): BM
}

class UserRaw(private val firstName: String, private val lastName: String) : BaseRaw<UserModel>() {
    override fun raw2Model(): UserModel {
        return UserModel(fullName = "$firstName $lastName")
    }
}

// Domain Model
abstract class BaseModel

class UserModel(val fullName: String) : BaseModel()

fun main() {
    val userRaw = UserRaw(firstName = "Doan", lastName = "Tuan")
    val userModel = userRaw.raw2Model()
    println("Full name = ${userModel.fullName}")
}