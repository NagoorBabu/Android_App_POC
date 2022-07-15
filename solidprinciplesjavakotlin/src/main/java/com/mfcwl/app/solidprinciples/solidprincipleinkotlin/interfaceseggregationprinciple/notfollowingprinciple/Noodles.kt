package com.mfcwl.app.solidprinciples.solidprincipleinkotlin.interfaceseggregationprinciple.notfollowingprinciple

class Noodles : Food {
    override fun name(): String {
        return "Schezwan Chicken Noodles"
    }

    override fun type(): Type {
        return Type.FAST_FOOD
    }

    override fun boil(): String {
        return "Boiling"
    }

    override fun freeze(): String {
        return "Not Needed"
    }
}