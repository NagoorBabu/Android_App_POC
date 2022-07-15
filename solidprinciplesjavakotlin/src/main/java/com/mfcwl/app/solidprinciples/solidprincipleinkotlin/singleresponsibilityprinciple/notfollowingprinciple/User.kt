package com.mfcwl.app.solidprinciples.solidprincipleinkotlin.singleresponsibilityprinciple.notfollowingprinciple

data class User(var name: String, var dob: String, var mobileNumber: String) {

    fun signIn(){

    }

    fun signOut(){

    }

}

/*A Class must do one concrete thing, In doing so, That class will have only one reason to change*/

