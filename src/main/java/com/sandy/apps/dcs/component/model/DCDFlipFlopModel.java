
package com.sandy.apps.dcs.component.model ;

import java.io.Serializable ;
import java.util.BitSet ;
import java.util.Observable ;

import com.sandy.apps.dcs.component.DCDFlipFlop ;
import com.sandy.apps.dcs.component.DCDGate ;
import com.sandy.apps.dcs.util.DCDUtility ;

public abstract class DCDFlipFlopModel extends DCDGateModel implements
        Serializable {

    // The gate to which this model belongs to.
    private DCDFlipFlop gate ;

    // This bitset will be the one with which the flip flop
    // will fire. This is so because in the event of firing
    // the state at time t(n) is lost because of the function
    // calls.
    // The 0th element will store the J charge and the 1st element
    // will store the K charge.
    private BitSet bufferCharge = new BitSet(2) ;

    // After each clock pulse all the unfired flip flops will be fired.
    private boolean fired = true ;

    protected DCDFlipFlopModel(DCDFlipFlop gate) {

        super(gate) ;
        this.gate = gate ;
    }

    // This function has been overloaded from the DCDGate class
    // because the FlipFlops don't fire as the normal gates do.
    // This is so because the firing is a function of the previous
    // state and moreover the firing takes place only when the
    // CP is 1.
    public void fire(BitSet inputChargeStatus) {

        // If the clock pulse is 0 the gate doesn't fire.
        if (inputChargeStatus.get(1) == false) {
            inputPortCollection.clearActivationStatus(1) ;
            fired = true ;
            return ;
        }
        else {
            // The ouputCharge will always be a 2 bit charge
            // which will represent the charge at the Q and Q' respectively.
            // This means that the chargestatus are always complimentary.
            BitSet inputCharge = new BitSet(3) ;
            if (bufferCharge.get(0))
                inputCharge.set(0) ;
            if (bufferCharge.get(1))
                inputCharge.set(2) ;
            inputCharge.set(1) ;
            BitSet outputCharge = getOutputCharge(inputCharge) ;
            inputPortCollection.clearActivationStatus(1) ;
            // transferChargeToBuffer();
            outputPortCollection.fire(outputCharge) ;
            fired = true ;

        }
    }

    public void setFiredStatus(boolean state) {

        this.fired = state ;
    }

    public boolean getFiredStatus() {

        return this.fired ;
    }

    public void fireWithExistingCharge() {

        DCDUtility.getInstance()
                .setMessageString("Firing with existing charge") ;
        boolean q = outputPortCollection.getCharge(0) ;
        BitSet outputCharge = new BitSet(2) ;
        if (q) {
            outputCharge.set(0) ;
            outputCharge.clear(1) ;
        }
        else {
            outputCharge.set(1) ;
            outputCharge.clear(0) ;
        }
        outputPortCollection.fire(outputCharge) ;
        fired = true ;

    }

    // The UI needs to be updated if the model changes.
    public void update(Observable arg0, Object arg1) {

    }

    public void initialize() {

        BitSet initialCharge = new BitSet() ;
        initialCharge.set(1) ;
        outputPortCollection.fire(initialCharge) ;
    }

    // This fuction will be called before the clocks are started
    // this will query the input port collection and transfer the
    // charge at the J and K port to the internal buffer.
    public void transferChargeToBuffer() {

        boolean j = inputPortCollection.getCharge(0) ;
        boolean k = inputPortCollection.getCharge(2) ;
        if (j)
            bufferCharge.set(0) ;
        else
            bufferCharge.clear(0) ;
        if (k)
            bufferCharge.set(1) ;
        else
            bufferCharge.clear(1) ;
    }

    // This function has not been implemented because FlipFlops
    // don't operate on a truth table.
    public void populateTruthTable() {

    }

    public DCDGate getDCDFlipFlop() {

        return this.gate ;
    }

    public void setDCDFlipFlop(DCDFlipFlop gate) {

        this.gate = gate ;
    }

    // TODO => Have to write the proper XML Here
    public String getSavingInformation() {

        StringBuffer sBuf = new StringBuffer() ;
        sBuf.append("\t\t\t<DCDGateModel>\n") ;
        sBuf.append("\t\t\t\t<InputPorts>\n") ;
        sBuf.append("\t\t\t\t\t" + inputPortCollection.getSavingInformation()
                + "\n") ;
        sBuf.append("\t\t\t\t</InputPorts>\n") ;
        sBuf.append("\t\t\t\t<OutputPorts>\n") ;
        sBuf.append("\t\t\t\t\t" + outputPortCollection.getSavingInformation()
                + "\n") ;
        sBuf.append("\t\t\t\t</OutputPorts>\n") ;
        sBuf.append("\t\t\t</DCDGateModel>\n") ;
        return (sBuf.toString()) ;
    }

}