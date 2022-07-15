package com.mfcwl.app.solidprinciples.solidprincipleinjava.interfacesegregationprinciple.solution.economicprinter;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.interfacesegregationprinciple.problem.interfacepackage.AllInOneLXPrinter;
import com.mfcwl.app.solidprinciples.solidprincipleinjava.interfacesegregationprinciple.solution.interfacepackage.AllInOnePrinting;
import com.mfcwl.app.solidprinciples.solidprincipleinjava.interfacesegregationprinciple.solution.interfacepackage.AllInOneScanning;

/*Economic Printer*/
public class LX8590Printer implements AllInOnePrinting
        , AllInOneScanning {

    @Override
    public boolean print() {
        return false;
    }

    @Override
    public boolean scan() {
        return false;
    }

}
