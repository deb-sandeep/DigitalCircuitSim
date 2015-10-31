
package com.sandy.apps.dcs.component.view ;

import java.io.Serializable ;
import java.util.Observable ;

import com.sandy.apps.dcs.component.DCDFlipFlop ;

public abstract class DCDFlipFlopUI extends DCDGateUI implements Serializable {

    private DCDFlipFlop flipFlop ;

    public DCDFlipFlopUI(DCDFlipFlop gate) {

        super(gate) ;
        width = 40 ;
        height = 70 ;
        this.flipFlop = gate ;
    }

    // The UI needs to be updated if the model changes.
    public void update(Observable arg0, Object arg1) {

    }

}