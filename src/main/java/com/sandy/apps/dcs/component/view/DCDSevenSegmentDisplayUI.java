
package com.sandy.apps.dcs.component.view ;

import java.awt.* ;
import java.awt.geom.* ;
import java.awt.event.* ;
import java.util.* ;
import java.io.* ;

import javax.swing.* ;

import com.sandy.apps.dcs.common.* ;
import com.sandy.apps.dcs.component.* ;

public class DCDSevenSegmentDisplayUI extends DCDGateUI implements Serializable {

    private DCDSevenSegmentDisplay gate ;

    private BitSet presentDisplay = new BitSet() ;

    private Segment[] segment = { new Segment(false, 20, 5),
            new Segment(true, 42, 9), new Segment(true, 42, 36),
            new Segment(false, 20, 58), new Segment(true, 15, 36),
            new Segment(true, 15, 9), new Segment(false, 20, 31) } ;

    private class Segment {

        boolean vertical = false ;

        int x = 0 ;

        int y = 0 ;

        Segment(boolean vertical, int tlx, int tly) {

            this.vertical = vertical ;
            this.y = tly ;
            this.x = tlx ;
        }

        void paint(Graphics2D g, boolean highlight) {

            Color col = g.getColor() ;

            if (vertical) {
                g.setColor(Color.gray) ;
                g.drawRect(tlx - 1 + x, tly - 1 + y, 4, 21) ;
                if (highlight) {
                    g.setColor(Color.red) ;
                    g.fillRect(tlx + x, tly + y, 3, 20) ;
                    g.setColor(col) ;
                }
                else {
                    g.setColor(DCDUtility.getInstance().getBackgroundColor()) ;
                    g.fillRect(tlx + x, tly + y, 3, 20) ;
                    g.setColor(col) ;
                }
            }
            else {
                g.setColor(Color.gray) ;
                g.drawRect(tlx - 1 + x, tly - 1 + y, 21, 4) ;

                if (highlight) {
                    g.setColor(Color.red) ;
                    g.fillRect(tlx + x, tly + y, 20, 3) ;
                    g.setColor(col) ;
                }
                else {
                    g.setColor(DCDUtility.getInstance().getBackgroundColor()) ;
                    g.fillRect(tlx + x, tly + y, 20, 3) ;
                    g.setColor(col) ;
                }
            }
        }
    } ;

    public DCDSevenSegmentDisplayUI(DCDSevenSegmentDisplay gate) {

        super(gate) ;
        this.gate = gate ;

    }

    // This is the drawing function of the gate.
    // It should be kept in the mind that no two lines
    // or curves should intersect even in one point or else
    // while erasing the effect will be lost as I am drawing
    // again in XOR mode.
    public void paint(Graphics2D g) {

        for (int i = 0; i < 4; i++) {
            g.drawLine(tlx, tly + 60 - 12 * i, tlx + 9, tly + 60 - 12 * i) ;
            g.drawLine(tlx, tly + 59 - 12 * i, tlx + 9, tly + 59 - 12 * i) ;
            // g.drawLine(tlx,tly+6+8*i,tlx+9,tly+6+8*i);
        }
        paintDigit(g) ;
    }

    private void paintDigit(Graphics2D g) {

        for (int i = 0; i < 7; i++) {
            if (presentDisplay.get(i))
                segment[i].paint(g, true) ;
            else
                segment[i].paint(g, false) ;

        }
    }

    // Here we polpulate the port locations relative to the
    // tlx and tly.
    protected void populatePortLocations() {

        inputPortLocation = new Point2D[4] ;
        for (int i = 0; i < 4; i++)
            inputPortLocation[i] = new Point2D.Float(0, 60 - 12 * i) ;
        width = 50 ;
        height = 70 ;

    }

    // The UI needs to be updated if the model changes.
    public void update(Observable arg0, Object arg1) {

    }

    public void display(BitSet inputCharge) {

        presentDisplay = inputCharge ;
        paintDigit(DCDUtility.getInstance().getGraphics()) ;
    }

}