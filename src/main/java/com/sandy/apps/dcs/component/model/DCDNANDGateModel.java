
package com.sandy.apps.dcs.component.model ;

import java.io.Serializable ;
import java.util.BitSet ;
import java.util.Observable ;

import com.sandy.apps.dcs.component.DCDNANDGate ;

public class DCDNANDGateModel extends DCDGateModel implements Serializable {

    private DCDNANDGate gate ;

    public DCDNANDGateModel(DCDNANDGate gate) {

        super(gate) ;
        this.gate = gate ;
    }

    // The UI needs to be updated if the model changes.
    public void update(Observable arg0, Object arg1) {

    }

    // The truth table of an two input NAND gate is as follows:
    // A B | Output
    // ------------------
    // 0 0 | 1
    // 0 1 | 1
    // 1 0 | 1
    // 1 1 | 0
    public void populateTruthTable() {

        int numInputPorts = super.getDCDGate().getNumInputPorts() ;
        int inputCases = (int) Math.pow(2, numInputPorts) ;
        for (int i = 0; i < inputCases; i++) {
            int num = i ;
            BitSet inputBitSet = new BitSet(numInputPorts) ;
            BitSet outputBitSet = new BitSet(1) ;

            boolean allTrue = true ;
            for (int j = 0; j < numInputPorts; j++) {
                int flag = num & 0x0001 ;
                if (flag == 1)
                    inputBitSet.set(j) ;
                else
                    allTrue = false ;
                num = num >> 1 ;
            }

            if (!allTrue)
                outputBitSet.set(0) ;

            truthTable.put(inputBitSet, outputBitSet) ;
        }
    }

    public void populatePortCollections() {

        int numIPPorts = super.getDCDGate().getNumInputPorts() ;
        for (int i = 0; i < numIPPorts; i++) {
            inputPortCollection.addPort(new Port(inputPortCollection, i)) ;
        }
        // Since the and gate will always have a single output.
        // We don't have to take this into consideration explicitlly.
        outputPortCollection.addPort(new Port(outputPortCollection, 0)) ;
    }
}