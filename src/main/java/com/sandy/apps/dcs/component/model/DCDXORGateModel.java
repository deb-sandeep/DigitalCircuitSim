
package com.sandy.apps.dcs.component.model ;

import java.util.* ;
import java.io.* ;

import com.sandy.apps.dcs.component.* ;

public class DCDXORGateModel extends DCDGateModel implements Serializable {

    private DCDXORGate gate ;

    public DCDXORGateModel(DCDXORGate gate) {

        super(gate) ;
        this.gate = gate ;
    }

    // The UI needs to be updated if the model changes.
    public void update(Observable arg0, Object arg1) {

    }

    // The truth table of an two input XOR gate is as follows:
    // A B | Output
    // 0 0 | 0
    // 0 1 | 0
    // 1 0 | 0
    // 1 1 | 1
    public void populateTruthTable() {

        int numInputPorts = super.getDCDGate().getNumInputPorts() ;
        int inputCases = (int) Math.pow(2, numInputPorts) ;
        for (int i = 0; i < inputCases; i++) {
            int num = i ;
            BitSet inputBitSet = new BitSet(numInputPorts) ;
            BitSet outputBitSet = new BitSet(1) ;

            boolean firstTrue = false ;
            boolean output1 = false ;
            for (int j = 0; j < numInputPorts; j++) {
                int flag = num & 0x0001 ;
                if (flag == 1) {
                    inputBitSet.set(j) ;
                    if (firstTrue == false) {
                        firstTrue = true ;
                        output1 = true ;
                    }
                    else
                        output1 = false ;
                }
                num = num >> 1 ;
            }

            if (output1)
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