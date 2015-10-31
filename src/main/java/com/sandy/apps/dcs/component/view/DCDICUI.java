
package com.sandy.apps.dcs.component.view ;

import java.awt.Graphics2D ;
import java.awt.event.MouseEvent ;
import java.awt.geom.Point2D ;
import java.io.Serializable ;
import java.util.Observable ;

import javax.swing.JMenuItem ;

import com.sandy.apps.dcs.component.DCDIC ;
import com.sandy.apps.dcs.component.model.PortInfo ;

public class DCDICUI extends DCDGateUI implements Serializable {

    private DCDIC ic ;

    private JMenuItem expand = new JMenuItem("Expand") ;

    private JMenuItem portInfo = new JMenuItem("Port Info") ;

    public DCDICUI(DCDIC gate) {

        super(gate) ;
        this.ic = gate ;
        expand.addActionListener(ic) ;
        expand.setActionCommand("EXPAND") ;
        popupMenu.add(expand) ;

        portInfo.addActionListener(ic) ;
        portInfo.setActionCommand("PORT_INFORMATION") ;
        popupMenu.add(portInfo) ;
    }

    // Here we polpulate the port locations relative to the
    // tlx and tly.
    protected void populatePortLocations() {

        int numPorts = super.getDCDGate().getNumInputPorts()
                + super.getDCDGate().getNumOutputPorts() ;
        numPorts = ((numPorts % 2) == 0) ? numPorts : numPorts + 1 ;
        height = (numPorts / 2 + 1) * 12 ;
        width = 70 ;

        this.portLocation = new Point2D[numPorts] ;
        for (int i = 0; i < numPorts; i++) {
            if (i < (numPorts / 2))
                this.portLocation[i] = new Point2D.Float(width, height
                        - (i + 1) * 12) ;
            else
                this.portLocation[i] = new Point2D.Float(0,
                        (i - (numPorts / 2) + 1) * 12) ;
        }

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

        // Draw the input gates.
        int numPorts = ic.getNumPorts() ;
        int numPby2 = 0 ;
        boolean lastPortUseable = ((numPorts % 2) == 0) ? true : false ;
        numPorts = ((numPorts % 2) == 0) ? numPorts : numPorts + 1 ;
        numPby2 = numPorts / 2 ;

        for (int i = 0; i < numPby2; i++) {
            g.drawLine(tlx, tly + 12 * (i + 1), tlx + 9, tly + 12 * (i + 1)) ;
            g.drawLine(tlx, tly + 1 + 12 * (i + 1), tlx + 9, tly + 1 + 12
                    * (i + 1)) ;
            if ((i == numPby2 - 1) && !lastPortUseable)
                g.drawString("X", tlx + 15, tly + 5 + 12 * (i + 1)) ;
            else
                g.drawString(Integer.toString(numPby2 + 1 + i), tlx + 15, tly
                        + 5 + 12 * (i + 1)) ;

            g.drawLine(tlx + 61, tly + 12 * (i + 1), tlx + 70, tly + 12
                    * (i + 1)) ;
            g.drawLine(tlx + 61, tly + 1 + 12 * (i + 1), tlx + 70, tly + 1 + 12
                    * (i + 1)) ;
            g.drawString(Integer.toString(numPby2 - i), tlx + 52, tly + 5 + 12
                    * (i + 1)) ;
        }

        g.drawLine(tlx + 10, tly + 2, tlx + 10, tly + height - 2) ;
        g.drawLine(tlx + 60, tly + 2, tlx + 60, tly + height - 2) ;
        g.drawLine(tlx + 11, tly + 2, tlx + 59, tly + 2) ;
        g.drawLine(tlx + 11, tly + height - 2, tlx + 59, tly + height - 2) ;
        g.drawArc(tlx + width / 2 - 6, tly + height - 8, 12, 12, 0, 180) ;
    }

    public PortInfo getPortInfo(MouseEvent e, PortInfo visitor) {

        Point2D p = new Point2D.Float(e.getX() - tlx, e.getY() - tly) ;
        PortInfo portInfo = getClosestPortInfo(p, this.portLocation, visitor) ;
        return (portInfo) ;
    }

}