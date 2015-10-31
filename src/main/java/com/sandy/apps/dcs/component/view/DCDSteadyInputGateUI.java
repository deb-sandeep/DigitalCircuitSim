
package com.sandy.apps.dcs.component.view ;

import java.awt.Graphics2D ;
import java.io.Serializable ;

import com.sandy.apps.dcs.component.DCDSteadyInputGate ;

public class DCDSteadyInputGateUI extends DCDInputGateUI implements
        Serializable {

    private DCDSteadyInputGate gate ;

    public DCDSteadyInputGateUI(DCDSteadyInputGate gate) {

        super(gate) ;
        this.gate = gate ;
    }

    // This is the drawing function of the gate.
    // It should be kept in the mind that no two lines
    // or curves should intersect even in one point or else
    // while erasing the effect will be lost as I am drawing
    // again in XOR mode.
    public void paint(Graphics2D g) {

        // This draws the basic input gate structure.
        g.drawLine(tlx, tly + 15, tlx + 9, tly + 15) ;
        g.drawLine(tlx, tly + 16, tlx + 9, tly + 16) ;

        g.drawOval(tlx + 10, tly + 12, 6, 6) ;
        g.drawOval(tlx + 24, tly + 12, 6, 6) ;

        g.drawLine(tlx + 31, tly + 15, tlx + 40, tly + 15) ;
        g.drawLine(tlx + 31, tly + 16, tlx + 40, tly + 16) ;

        // If this gate is in the 1 state
        if (gate.getState()) {
            g.drawLine(tlx + 20, tly, tlx + 20, tly + 10) ;
            g.drawLine(tlx + 10, tly + 11, tlx + 30, tly + 11) ;
        }
        else {
            g.drawLine(tlx + 20, tly, tlx + 20, tly + 5) ;
            g.drawLine(tlx + 10, tly + 6, tlx + 30, tly + 6) ;
        }
    }

}