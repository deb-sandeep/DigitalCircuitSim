
package com.sandy.apps.dcs.component.view ;

import java.awt.* ;
import java.awt.geom.* ;
import java.awt.event.* ;
import java.util.* ;
import java.io.* ;

import com.sandy.apps.dcs.common.* ;
import com.sandy.apps.dcs.component.* ;
import com.sandy.apps.dcs.factory.* ;

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