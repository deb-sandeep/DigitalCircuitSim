
package com.sandy.apps.dcs.component.model ;

import java.io.Serializable ;
import java.util.BitSet ;
import java.util.Random ;

import com.sandy.apps.dcs.component.DCDRSFlipFlop ;

public class DCDRSFlipFlopModel extends DCDFlipFlopModel implements
        Serializable {

    private DCDRSFlipFlop gate ;

    public DCDRSFlipFlopModel(DCDRSFlipFlop gate) {

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
        boolean s = inputCharge.get(0) ;
        boolean r = inputCharge.get(2) ;
        boolean q = outputPortCollection.getCharge(0) ;
        if (!s) {
            if (!r) {
                // S=0 R=0 => Q(t+1)=Q(t)
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
            if (r) {
                // Pick up a random value.
                boolean randomBit = new Random().nextBoolean() ;
                if (randomBit) {
                    outputCharge.set(0) ;
                    outputCharge.clear(1) ;
                }
                else {
                    outputCharge.set(1) ;
                    outputCharge.clear(0) ;
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