
package com.sandy.apps.dcs.component.model ;

import java.io.Serializable ;

import com.sandy.apps.dcs.component.DCDClock ;
import com.sandy.apps.dcs.ui.DCDController ;
import com.sandy.apps.dcs.util.DCDUtility ;

public class DCDClockModel extends DCDInputGateModel implements Serializable {

    private DCDClock gate ;

    private boolean state = false ;

    public DCDClockModel(DCDClock gate) {

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

        DCDUtility utility = DCDUtility.getInstance() ;
        DCDController controller = utility.getDCDController() ;
        if (gate.getName().startsWith("D@"))
            utility.setMessageString("<---" + state + "--->") ;
        controller.fireAllInputGates() ;
        this.setChanged() ;
        this.notifyObservers(new Boolean(state)) ;
        outputPortCollection.getPort(0).activateConnector(state,
                DCDInputGateModel.STEADY) ;
        // Firing all the unfired flip flops.

        if (state) {
            // utility.fireAllUnfiredFlipFlops();
            utility.getDCDController().transferChargeToBufferInDCDCircuit() ;
        }
        // Clear all the firing status and mark them as unfired.
        // utility.clearFiringStatusOfAllFlipFlops();
        state = !state ;
        if (gate.getName().startsWith("D@"))
            utility.setMessageString("<------------------->") ;
    }

}