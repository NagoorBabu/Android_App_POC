package com.mfcwl.app.solidprinciples.solidprincipleinjava.interfacesegregationprinciple.problem.allinoneprinter;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.interfacesegregationprinciple.problem.interfacepackage.AllInOneLXPrinter;

/*All in one printer*/
public class LX8578Printer implements AllInOneLXPrinter {

    @Override
    public boolean print() {
        return false;
    }

    @Override
    public boolean scan() {
        return false;
    }

    @Override
    public boolean fax() {
        return false;
    }
}
