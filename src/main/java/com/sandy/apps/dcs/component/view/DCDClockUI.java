
package com.sandy.apps.dcs.component.view ;

import java.awt.Graphics2D ;
import java.io.Serializable ;
import java.util.Observable ;

import javax.swing.JMenuItem ;

import com.sandy.apps.dcs.component.DCDClock ;
import com.sandy.apps.dcs.util.DCDUtility ;

public class DCDClockUI extends DCDInputGateUI implements Serializable {

    private DCDClock gate ;

    private JMenuItem stop = new JMenuItem("Stop") ;

    private JMenuItem start = new JMenuItem("Start") ;

    private JMenuItem frequency = new JMenuItem("Frequency") ;

    public DCDClockUI(DCDClock gate) {

        super(gate) ;
        this.gate = gate ;
        stop.setActionCommand("STOP") ;
        stop.addActionListener(gate) ;
        start.setActionCommand("START") ;
        start.addActionListener(gate) ;
        frequency.setActionCommand("FREQUENCY") ;
        frequency.addActionListener(gate) ;
        popupMenu.add(frequency) ;
        popupMenu.add(stop) ;
    }

    public void stopPressed() {

        popupMenu.remove(stop) ;
        popupMenu.add(start) ;
    }

    public void startPressed() {

        popupMenu.remove(start) ;
        popupMenu.add(stop) ;
    }

    // This is the drawing function of the gate.
    // It should be kept in the mind that no two lines
    // or curves should intersect even in one point or else
    // while erasing the effect will be lost as I am drawing
    // again in XOR mode.
    public void paint(Graphics2D g) {

        // This draws the basic input gate structure.
        g.drawLine(tlx, tly + 14, tlx + 9, tly + 14) ;
        g.drawLine(tlx, tly + 15, tlx + 9, tly + 15) ;

        g.drawLine(tlx + 10, tly + 5, tlx + 10, tly + 25) ;
        g.drawLine(tlx + 30, tly + 5, tlx + 30, tly + 25) ;

        if (!gate.getState()) {
            g.drawLine(tlx + 11, tly + 10, tlx + 11, tly + 20) ;
            g.drawLine(tlx + 21, tly + 10, tlx + 21, tly + 20) ;
            g.drawLine(tlx + 12, tly + 10, tlx + 20, tly + 10) ;
            g.drawLine(tlx + 12, tly + 20, tlx + 20, tly + 20) ;
        }
        else {
            g.drawLine(tlx + 20, tly + 10, tlx + 20, tly + 20) ;
            g.drawLine(tlx + 29, tly + 10, tlx + 29, tly + 20) ;
            g.drawLine(tlx + 21, tly + 10, tlx + 28, tly + 10) ;
            g.drawLine(tlx + 21, tly + 20, tlx + 28, tly + 20) ;
        }
        g.drawLine(tlx + 31, tly + 14, tlx + 40, tly + 14) ;
        g.drawLine(tlx + 31, tly + 15, tlx + 40, tly + 15) ;
    }

    // This will be called when the model toggles the state.
    public void update(Observable arg0, Object arg1) {

        this.paint(DCDUtility.getInstance().getGraphics(),
                DCDComponentUI.DELETED) ;
        this.paint(DCDUtility.getInstance().getGraphics(),
                DCDComponentUI.NORMAL) ;
    }

}
