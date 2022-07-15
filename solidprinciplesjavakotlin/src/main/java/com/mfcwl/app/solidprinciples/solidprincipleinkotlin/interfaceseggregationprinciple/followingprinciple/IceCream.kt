package com.mfcwl.app.solidprinciples.solidprincipleinkotlin.interfaceseggregationprinciple.followingprinciple


class IceCream : ColdFood {
    override fun name(): String {
        return "Vanilla"
    }

    override fun type(): Type {
        return Type.DESSERT
    }

    override fun freeze(): String {
        return "Freezing"
    }
}