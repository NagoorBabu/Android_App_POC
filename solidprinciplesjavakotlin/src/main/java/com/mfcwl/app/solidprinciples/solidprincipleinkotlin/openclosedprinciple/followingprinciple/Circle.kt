package com.mfcwl.app.solidprinciples.solidprincipleinkotlin.openclosedprinciple.followingprinciple

class Circle : IArea {
    private var radius: Double = 0.0

    override fun getArea(): Double {
        return radius * radius * Math.PI
    }
}