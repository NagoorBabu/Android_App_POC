package com.mfcwl.app.solidprinciples.solidprincipleinkotlin.liskovsubstitutionprinciple.notfollowingprinciple

class Penguin : Bird() {
    override fun makeSound() {
    }

    override fun fly() { /*Penguins cant fly..*/
    }
}