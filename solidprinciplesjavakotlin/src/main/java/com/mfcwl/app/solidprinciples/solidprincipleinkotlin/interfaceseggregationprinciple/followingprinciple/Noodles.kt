package com.mfcwl.app.solidprinciples.solidprincipleinkotlin.interfaceseggregationprinciple.followingprinciple


class Noodles : HotFood {
    override fun name(): String {
        return "Schezwan Chicken Noodles"
    }

    override fun type(): Type {
        return Type.FAST_FOOD
    }

    override fun boil(): String {
        return "Boiling"
    }
}