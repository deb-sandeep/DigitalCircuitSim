
package com.sandy.apps.dcs.component.view ;

import java.awt.* ;
import java.awt.geom.* ;
import java.awt.event.* ;
import java.util.* ;
import java.io.* ;

import com.sandy.apps.dcs.common.* ;
import com.sandy.apps.dcs.component.* ;

public class DCDNOTGateUI extends DCDGateUI implements Serializable {

    private DCDNOTGate andGate ;

    public DCDNOTGateUI(DCDNOTGate gate) {

        super(gate) ;
        this.andGate = gate ;
    }

    // Here we polpulate the port locations relative to the
    // tlx and tly.
    protected void populatePortLocations() {

        inputPortLocation = new Point2D[1] ;
        inputPortLocation[0] = new Point2D.Float(0, 15) ;

        outputPortLocation = new Point2D[1] ;
        outputPortLocation[0] = new Point2D.Float(40, 15) ;
    }

    // The UI needs to be updated if the model changes.
    public void update(Observable arg0, Object arg1) {

    }

    // This is the drawing function of the gate.
    // It should be kept in the mind that no two lines
    // or curves should intersect even in one point or else
    // while erasing the effect will be lost as I am drawing
    // again in XOR mode.
    public void paint(Graphics2D g) {

        // Draw port input port a.
        g.drawLine(tlx, tly + 15, tlx + 9, tly + 15) ;
        g.drawLine(tlx, tly + 16, tlx + 9, tly + 16) ;

        // Draw the body.
        g.drawLine(tlx + 10, tly + 5, tlx + 10, tly + 25) ;
        g.drawLine(tlx + 11, tly + 5, tlx + 25, tly + 15) ;
        g.drawLine(tlx + 11, tly + 25, tlx + 25, tly + 16) ;
        g.drawOval(tlx + 26, tly + 12, 6, 6) ;

        g.drawLine(tlx + 33, tly + 15, tlx + 40, tly + 15) ;
        g.drawLine(tlx + 33, tly + 16, tlx + 40, tly + 16) ;
    }
}