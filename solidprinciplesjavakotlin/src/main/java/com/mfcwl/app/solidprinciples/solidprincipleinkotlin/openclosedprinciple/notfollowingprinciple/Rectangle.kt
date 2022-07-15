package com.mfcwl.app.solidprinciples.solidprincipleinkotlin.openclosedprinciple.notfollowingprinciple

class Rectangle {
    private var length: Double = 0.0
    private var height: Double = 0.0

    fun getArea(): Double{
        return length*height
    }
}