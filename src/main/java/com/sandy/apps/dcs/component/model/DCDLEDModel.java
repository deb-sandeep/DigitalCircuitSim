
package com.sandy.apps.dcs.component.model ;

import java.io.Serializable ;
import java.util.BitSet ;
import java.util.Observable ;

import com.sandy.apps.dcs.component.DCDLED ;

public class DCDLEDModel extends DCDGateModel implements Serializable {

    private DCDLED gate ;

    private boolean state = false ;

    public DCDLEDModel(DCDLED gate) {

        super(gate) ;
        this.gate = gate ;
    }

    // The UI needs to be updated if the model changes.
    public void update(Observable arg0, Object arg1) {

    }

    public void populateTruthTable() {

    }

    public void fire(BitSet inputChargeStatus) {

        gate.setState(inputChargeStatus.get(0)) ;
    }

    public void populatePortCollections() {

        inputPortCollection.addPort(new Port(inputPortCollection, 0)) ;
    }

    public void setState(boolean b) {

        this.state = b ;
    }

    public boolean getState() {

        return (this.state) ;
    }

    public void toggleState() {

        state = (state) ? false : true ;
    }

    // This method is redundant .. an LED can't fire .. can it !!
    public void fire() {

        outputPortCollection.getPort(0).activateConnector(state, 1) ;
    }
}