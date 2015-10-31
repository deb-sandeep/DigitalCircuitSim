
package com.sandy.apps.dcs.component.view ;

import java.awt.* ;
import java.awt.geom.* ;
import java.awt.event.* ;
import java.util.* ;
import java.io.* ;

import com.sandy.apps.dcs.common.* ;
import com.sandy.apps.dcs.component.* ;

public class DCDPulseInputGateUI extends DCDInputGateUI implements Serializable {

    private DCDPulseInputGate gate ;

    public DCDPulseInputGateUI(DCDPulseInputGate gate) {

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
            g.drawOval(tlx + 18, tly + 1, 4, 4) ;
            g.drawLine(tlx + 10, tly + 11, tlx + 30, tly + 11) ;
        }
        else {
            g.drawOval(tlx + 18, tly + 1, 4, 4) ;
            g.drawLine(tlx + 10, tly + 6, tlx + 30, tly + 6) ;
        }
    }

}