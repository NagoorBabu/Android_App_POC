package com.mfcwl.app.solidprinciples.solidprincipleinjava.interfacesegregationprinciple.problem.economicprinter;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.interfacesegregationprinciple.problem.interfacepackage.AllInOneLXPrinter;

/*Economic Printer*/
public class LX8590Printer implements AllInOneLXPrinter {

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
        throw new UnsupportedOperationException("Faxing not supported.");
    }
}
