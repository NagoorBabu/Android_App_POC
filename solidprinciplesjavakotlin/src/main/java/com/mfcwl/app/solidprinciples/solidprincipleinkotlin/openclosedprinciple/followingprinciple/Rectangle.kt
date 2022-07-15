package com.mfcwl.app.solidprinciples.solidprincipleinkotlin.openclosedprinciple.followingprinciple

class Rectangle: IArea {
    private var length: Double = 0.0
    private var height: Double = 0.0

    override fun getArea(): Double {
        return length*height
    }
}