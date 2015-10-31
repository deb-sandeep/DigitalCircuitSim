
package com.sandy.apps.dcs.component.model ;

import java.io.Serializable ;
import java.util.BitSet ;

import com.sandy.apps.dcs.component.DCDJKFlipFlop ;

public class DCDJKFlipFlopModel extends DCDFlipFlopModel implements
        Serializable {

    private DCDJKFlipFlop gate ;

    public DCDJKFlipFlopModel(DCDJKFlipFlop gate) {

        super(gate) ;
        this.gate = gate ;
        BitSet initialChargeStatus = new BitSet(2) ;
        initialChargeStatus.clear(0) ;
        initialChargeStatus.set(1) ;
        outputPortCollection.setChargeStatus(initialChargeStatus) ;
    }

    // This is overloaded from the DCDGateModel class because
    // the output charge of a gate is not calculated from a truth table.
    public BitSet getOutputCharge(BitSet inputCharge) {

        // Input Charge will always be a 3 bit.
        // 1st bit signifies J
        // 2nd bit signifies CP
        // 3rd bit signifies K
        BitSet outputCharge = new BitSet(2) ;
        boolean j = inputCharge.get(0) ;
        boolean k = inputCharge.get(2) ;
        boolean q = outputPortCollection.getCharge(0) ;
        if (!j) {
            if (!k) {
                // J=0 K=0 => Q(t+1)=Q(t)
                if (q) {
                    outputCharge.set(0) ;
                    outputCharge.clear(1) ;
                }
                else {
                    outputCharge.clear(0) ;
                    outputCharge.set(1) ;
                }
            }
            else {
                // J=0 K=1 => Q(t+1)=0
                outputCharge.clear(0) ;
                outputCharge.set(1) ;
            }
        }
        else {
            if (k) {
                // J=1 K=1 => Q(t+1)=Q'(t)
                if (q) {
                    outputCharge.clear(0) ;
                    outputCharge.set(1) ;
                }
                else {
                    outputCharge.set(0) ;
                    outputCharge.clear(1) ;
                }
            }
            else {
                // J=1 K=0 => Q(t+1)=1
                outputCharge.set(0) ;
                outputCharge.clear(1) ;
            }
        }
        return (outputCharge) ;
    }

    public void populatePortCollections() {

        int numIPPorts = 3 ;
        for (int i = 0; i < numIPPorts; i++) {
            inputPortCollection.addPort(new Port(inputPortCollection, i)) ;
        }
        // Since the and gate will always have a single output.
        // We don't have to take this into consideration explicitlly.
        outputPortCollection.addPort(new Port(outputPortCollection, 0)) ;
        outputPortCollection.addPort(new Port(outputPortCollection, 1)) ;
    }

}