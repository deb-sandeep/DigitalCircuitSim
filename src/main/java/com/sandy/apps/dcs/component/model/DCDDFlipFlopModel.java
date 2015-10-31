
package com.sandy.apps.dcs.component.model ;

import java.io.Serializable ;
import java.util.BitSet ;

import com.sandy.apps.dcs.component.DCDDFlipFlop ;

public class DCDDFlipFlopModel extends DCDFlipFlopModel implements Serializable {

    private DCDDFlipFlop gate ;

    public DCDDFlipFlopModel(DCDDFlipFlop gate) {

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
        boolean d = inputCharge.get(0) ;
        if (d) {
            // D=1 => Q(t+1)=1
            outputCharge.clear(1) ;
            outputCharge.set(0) ;
        }
        else {
            // D=0 => Q(t+1)=0
            outputCharge.clear(0) ;
            outputCharge.set(1) ;
        }
        return (outputCharge) ;
    }

    public void populatePortCollections() {

        int numIPPorts = 2 ;
        for (int i = 0; i < numIPPorts; i++) {
            inputPortCollection.addPort(new Port(inputPortCollection, i)) ;
        }
        // Since the and gate will always have a single output.
        // We don't have to take this into consideration explicitlly.
        outputPortCollection.addPort(new Port(outputPortCollection, 0)) ;
        outputPortCollection.addPort(new Port(outputPortCollection, 1)) ;
    }

}