
package com.sandy.apps.dcs.component.model ;

import java.util.* ;
import java.io.* ;

import com.sandy.apps.dcs.component.* ;

public class DCDNOTGateModel extends DCDGateModel implements Serializable {

    private DCDNOTGate gate ;

    public DCDNOTGateModel(DCDNOTGate gate) {

        super(gate) ;
        this.gate = gate ;
    }

    // The UI needs to be updated if the model changes.
    public void update(Observable arg0, Object arg1) {

    }

    // The truth table of an NOT gate is as follows:
    // A | Output
    // 0 | 1
    // 1 | 0
    public void populateTruthTable() {

        BitSet inputCase1 = new BitSet(1) ;
        BitSet outputCase1 = new BitSet(1) ;
        outputCase1.set(0) ;
        truthTable.put(inputCase1, outputCase1) ;

        BitSet inputCase2 = new BitSet(1) ;
        BitSet outputCase2 = new BitSet(1) ;
        inputCase2.set(0) ;
        truthTable.put(inputCase2, outputCase2) ;
    }

    public void populatePortCollections() {

        inputPortCollection.addPort(new Port(inputPortCollection, 0)) ;
        outputPortCollection.addPort(new Port(outputPortCollection, 0)) ;
    }
}