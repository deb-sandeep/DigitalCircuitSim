
package com.sandy.apps.dcs.component.view ;

import java.awt.Graphics2D ;
import java.awt.geom.Point2D ;
import java.io.Serializable ;
import java.util.Observable ;

import com.sandy.apps.dcs.component.DCDNANDGate ;

public class DCDNANDGateUI extends DCDGateUI implements Serializable {

    private DCDNANDGate andGate ;

    public DCDNANDGateUI(DCDNANDGate gate) {

        super(gate) ;
        this.andGate = gate ;
    }

    // Here we polpulate the port locations relative to the
    // tlx and tly.
    protected void populatePortLocations() {

        int numInputPorts = super.getDCDGate().getNumInputPorts() ;
        inputPortLocation = new Point2D[numInputPorts] ;
        for (int i = 0; i < numInputPorts; i++) {
            inputPortLocation[i] = new Point2D.Float(0, 10 + 10 * i) ;
        }

        outputPortLocation = new Point2D[1] ;
        outputPortLocation[0] = new Point2D.Float(width, height / 2) ;
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

        int numInputPorts = super.getDCDGate().getNumInputPorts() ;
        for (int i = 0; i < numInputPorts; i++) {
            g.drawLine(tlx, tly + 10 * (i + 1), tlx + 9, tly + 10 * (i + 1)) ;
            g.drawLine(tlx, tly + 10 * (i + 1) + 1, tlx + 9, tly + 10 * (i + 1)
                    + 1) ;
        }

        g.drawLine(tlx + 10, tly + 5, tlx + 10, tly + height - 5) ;
        g.drawLine(tlx + 11, tly + 5, tlx + 16, tly + 5) ;
        g.drawLine(tlx + 11, tly + height - 5, tlx + 15, tly + height - 5) ;

        g.drawArc(tlx + 7, tly + 5, 18, height - 10, -90, 180) ;
        g.drawOval(tlx + 26, tly + height / 2 - 3, 6, 6) ;

        g.drawLine(tlx + 33, tly + height / 2, tlx + 40, tly + height / 2) ;
        g.drawLine(tlx + 33, tly + height / 2 + 1, tlx + 40, tly + height / 2
                + 1) ;

    }
}