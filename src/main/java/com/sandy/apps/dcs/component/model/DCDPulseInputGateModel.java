
package com.sandy.apps.dcs.component.model ;

import com.sandy.apps.dcs.component.DCDPulseInputGate ;
import com.sandy.apps.dcs.ui.DCDController ;
import com.sandy.apps.dcs.util.DCDUtility ;

public class DCDPulseInputGateModel extends DCDInputGateModel {

    private DCDPulseInputGate gate ;

    public DCDPulseInputGateModel(DCDPulseInputGate gate) {

        super(gate) ;
        this.gate = gate ;
    }

    public void fire(boolean state) {

        DCDUtility utility = DCDUtility.getInstance() ;
        DCDController controller = utility.getDCDController() ;
        if (gate.getName().startsWith("D@"))
            utility.setMessageString("<---" + state + "--->") ;
        controller.fireAllInputGates() ;
        outputPortCollection.getPort(0).activateConnector(state,
                DCDInputGateModel.STEADY) ;
        if (state)
            utility.getDCDController().transferChargeToBufferInDCDCircuit() ;
        if (gate.getName().startsWith("D@"))
            utility.setMessageString("<------------------->") ;
    }

}