package com.mfcwl.app.solidprinciples.solidprincipleinjava.singleresponsibilityprinciple.problem.methodsproblem;

public class Comments {
    /*SRP can be applied on methods, classes or modules. I make emphasis on methods because is very common to develop methods that do too many things at once.
     I’ve seen some huge 500 lines methods that have too many responsibilities at once.
     For instance, is common to find methods that record on logs, do some calculations and finally they persist the result of those calculations in the database.
      The problem with these types of methods is that besides they are difficult to maintain and difficult to introduce changes, they also are difficult to test.

     SRP allows to write methods that have just one responsibility without caring about the size of it, just caring about the method having one reason to change.*/
}
