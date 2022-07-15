package com.mfcwl.app.solidprinciples.solidprincipleinjava.interfacesegregationprinciple.solution.allinoneprinter;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.interfacesegregationprinciple.solution.interfacepackage.AllInOneFaxing;
import com.mfcwl.app.solidprinciples.solidprincipleinjava.interfacesegregationprinciple.solution.interfacepackage.AllInOnePrinting;
import com.mfcwl.app.solidprinciples.solidprincipleinjava.interfacesegregationprinciple.solution.interfacepackage.AllInOneScanning;

/*All in one printer*/
public class LX8578Printer implements AllInOnePrinting, AllInOneScanning, AllInOneFaxing {

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
