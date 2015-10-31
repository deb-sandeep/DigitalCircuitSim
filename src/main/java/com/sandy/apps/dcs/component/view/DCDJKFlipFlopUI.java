
package com.sandy.apps.dcs.component.view ;

import java.awt.Font ;
import java.awt.Graphics2D ;
import java.awt.geom.Point2D ;
import java.io.Serializable ;
import java.util.Observable ;

import com.sandy.apps.dcs.component.DCDJKFlipFlop ;

public class DCDJKFlipFlopUI extends DCDFlipFlopUI implements Serializable {

    private DCDJKFlipFlop jkFlipFlop ;

    public DCDJKFlipFlopUI(DCDJKFlipFlop gate) {

        super(gate) ;
        this.jkFlipFlop = gate ;
    }

    // Here we polpulate the port locations relative to the
    // tlx and tly.
    protected void populatePortLocations() {

        // For a JKFlipFlop .. there can be no more than 3 Inputs
        // J,K and CP. Hence we can directly code it here instead
        // of asking the super class and all bull shit.
        inputPortLocation = new Point2D[3] ;
        inputPortLocation[0] = new Point2D.Float(0, 15) ;
        inputPortLocation[1] = new Point2D.Float(0, 35) ;
        inputPortLocation[2] = new Point2D.Float(0, 55) ;

        outputPortLocation = new Point2D[2] ;
        outputPortLocation[0] = new Point2D.Float(width, 15) ;
        outputPortLocation[1] = new Point2D.Float(width, 55) ;
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

        // These draw the input ports.
        g.drawLine(tlx, tly + 15, tlx + 4, tly + 15) ;
        g.drawLine(tlx, tly + 16, tlx + 4, tly + 16) ;

        g.drawLine(tlx, tly + 35, tlx + 4, tly + 35) ;
        g.drawLine(tlx, tly + 36, tlx + 4, tly + 36) ;
        g.drawLine(tlx + 6, tly + 33, tlx + 10, tly + 35) ;
        g.drawLine(tlx + 6, tly + 38, tlx + 10, tly + 36) ;

        g.drawLine(tlx, tly + 55, tlx + 4, tly + 55) ;
        g.drawLine(tlx, tly + 56, tlx + 4, tly + 56) ;

        // This is drawing the box.
        g.drawLine(tlx + 5, tly + 5, tlx + 5, tly + 65) ;
        g.drawLine(tlx + 35, tly + 5, tlx + 35, tly + 65) ;
        g.drawLine(tlx + 6, tly + 5, tlx + 34, tly + 5) ;
        g.drawLine(tlx + 6, tly + 65, tlx + 34, tly + 65) ;

        // These draw the output ports.
        g.drawLine(tlx + 36, tly + 15, tlx + 40, tly + 15) ;
        g.drawLine(tlx + 36, tly + 16, tlx + 40, tly + 16) ;

        g.drawLine(tlx + 36, tly + 55, tlx + 40, tly + 55) ;
        g.drawLine(tlx + 36, tly + 56, tlx + 40, tly + 56) ;

        g.setFont(new Font("Courier", Font.PLAIN, 12)) ;
        g.drawString("J", tlx + 11, tly + 18) ;
        g.drawString("CP", tlx + 11, tly + 38) ;
        g.drawString("K", tlx + 11, tly + 58) ;

        g.drawString("Q", tlx + 25, tly + 18) ;
        g.drawString("Q'", tlx + 25, tly + 58) ;

        // Draw the output ports.
    }

}