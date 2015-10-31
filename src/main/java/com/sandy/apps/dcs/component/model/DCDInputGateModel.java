
package com.sandy.apps.dcs.component.model ;

import java.util.* ;
import java.io.* ;

import com.sandy.apps.dcs.component.* ;

public class DCDInputGateModel extends DCDGateModel implements Serializable {

    private DCDInputGate gate ;

    public static final int PULSE = 0 ;

    public static final int STEADY = 1 ;

    public DCDInputGateModel(DCDInputGate gate) {

        super(gate) ;
        this.gate = gate ;
    }

    // The UI needs to be updated if the model changes.
    public void update(Observable arg0, Object arg1) {

    }

    public void populateTruthTable() {

    }

    public void populatePortCollections() {

        outputPortCollection.addPort(new Port(outputPortCollection, 0)) ;
    }
}