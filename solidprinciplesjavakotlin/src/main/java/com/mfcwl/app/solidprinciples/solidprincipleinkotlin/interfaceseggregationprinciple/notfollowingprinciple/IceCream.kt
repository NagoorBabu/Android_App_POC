package com.mfcwl.app.solidprinciples.solidprincipleinkotlin.interfaceseggregationprinciple.notfollowingprinciple

class IceCream : Food {
    override fun name(): String {
        return "Vanilla"
    }

    override fun type(): Type {
        return Type.DESSERT
    }

    override fun boil(): String {
        return "Not Needed"
    }

    override fun freeze(): String {
        return "Freezing"
    }
}