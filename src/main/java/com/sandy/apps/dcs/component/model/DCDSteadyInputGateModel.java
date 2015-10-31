
package com.sandy.apps.dcs.component.model ;

import java.io.Serializable ;

import com.sandy.apps.dcs.component.DCDSteadyInputGate ;

public class DCDSteadyInputGateModel extends DCDInputGateModel implements
        Serializable {

    private DCDSteadyInputGate gate ;

    private boolean state = false ;

    public DCDSteadyInputGateModel(DCDSteadyInputGate gate) {

        super(gate) ;
        this.gate = gate ;
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

    public void fire() {

        outputPortCollection.getPort(0).activateConnector(state,
                DCDInputGateModel.STEADY) ;
    }

}