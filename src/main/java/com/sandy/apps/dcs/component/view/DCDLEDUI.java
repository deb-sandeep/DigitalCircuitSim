
package com.sandy.apps.dcs.component.view ;

import java.awt.Color ;
import java.awt.Graphics2D ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.awt.geom.Point2D ;
import java.io.Serializable ;
import java.util.Observable ;

import javax.swing.JColorChooser ;
import javax.swing.JMenuItem ;

import com.sandy.apps.dcs.component.DCDLED ;
import com.sandy.apps.dcs.util.DCDUtility ;

public class DCDLEDUI extends DCDGateUI implements Serializable, ActionListener {

    private DCDLED gate ;

    private Color ledOnColor = Color.green ;

    private Color ledOffColor = Color.gray ;

    private JMenuItem tag = new JMenuItem("Tag as IC Port") ;

    public DCDLEDUI(DCDLED gate) {

        super(gate) ;
        this.gate = gate ;
        JMenuItem onColorMenuItem = new JMenuItem("On Color") ;
        popupMenu.add(onColorMenuItem) ;
        onColorMenuItem.setActionCommand("ON_COLOR") ;
        onColorMenuItem.addActionListener(this) ;

        JMenuItem offColorMenuItem = new JMenuItem("Off Color") ;
        offColorMenuItem.setActionCommand("OFF_COLOR") ;
        popupMenu.add(offColorMenuItem) ;
        offColorMenuItem.addActionListener(this) ;

        tag.setActionCommand("TAG_AS_IC_PORT") ;
        tag.addActionListener(DCDUtility.getInstance().getDCDController()) ;
        popupMenu.add(tag) ;
    }

    // This is the drawing function of the gate.
    // It should be kept in the mind that no two lines
    // or curves should intersect even in one point or else
    // while erasing the effect will be lost as I am drawing
    // again in XOR mode.
    public void paint(Graphics2D g) {

        // This draws the basic input gate structure.
        g.drawLine(tlx, tly + 14, tlx + 14, tly + 14) ;
        g.drawLine(tlx, tly + 15, tlx + 14, tly + 15) ;
        g.drawLine(tlx, tly + 16, tlx + 14, tly + 16) ;

        g.drawOval(tlx + 15, tly + 8, 15, 15) ;

        // If this gate is in the 1 state
        setState(gate.getState(), g) ;
    }

    public void setState(boolean state, Graphics2D g) {

        Color temp = g.getColor() ;
        if (gate.getState()) {
            g.setColor(ledOnColor) ;
            g.fillOval(tlx + 18, tly + 11, 10, 10) ;
        }
        else {
            g.setColor(ledOffColor) ;
            g.fillOval(tlx + 18, tly + 11, 10, 10) ;
        }
        g.setColor(temp) ;
    }

    // Here we polpulate the port locations relative to the
    // tlx and tly.
    protected void populatePortLocations() {

        inputPortLocation = new Point2D[1] ;
        inputPortLocation[0] = new Point2D.Float(0, 15) ;

    }

    // The UI needs to be updated if the model changes.
    public void update(Observable arg0, Object arg1) {

    }

    public void setState(boolean b) {

    }

    public void actionPerformed(ActionEvent e) {

        String actionCommand = ((JMenuItem) e.getSource()).getActionCommand() ;

        Color color = JColorChooser.showDialog(DCDUtility.getInstance()
                .getFrame(), "Choose a color for the LED", null) ;

        if (color != null) {
            if (actionCommand.equals("ON_COLOR"))
                ledOnColor = color ;
            else {
                ledOffColor = color ;
            }
            paint(DCDUtility.getInstance().getGraphics()) ;
        }
    }

    public Color getLedOffColor() {

        return this.ledOffColor ;
    }

    public void setLedOffColor(Color ledOffColor) {

        this.ledOffColor = ledOffColor ;
    }

    public Color getLedOnColor() {

        return this.ledOnColor ;
    }

    public void setLedOnColor(Color ledOnColor) {

        this.ledOnColor = ledOnColor ;
    }
}