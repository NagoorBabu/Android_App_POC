package com.mfcwl.app.solidprinciples.solidprincipleinkotlin.interfaceseggregationprinciple.notfollowingprinciple

interface Food {
    fun name(): String
    fun type(): Type
    fun boil(): String
    fun freeze(): String
}