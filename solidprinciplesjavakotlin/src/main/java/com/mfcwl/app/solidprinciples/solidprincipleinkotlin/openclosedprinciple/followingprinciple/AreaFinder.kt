package com.mfcwl.app.solidprinciples.solidprincipleinkotlin.openclosedprinciple.followingprinciple

class AreaFinder {

    fun getArea(circle: Circle): Double {
        return circle.getArea()
    }

    fun getArea(rectangle: Rectangle): Double {
        return rectangle.getArea()
    }

    fun getArea(shape: IArea): Double {

        return shape.getArea()
    }
}