
package com.sandy.apps.dcs.component.view ;

import java.awt.geom.Point2D ;
import java.io.Serializable ;
import java.util.Observable ;

import javax.swing.JMenuItem ;

import com.sandy.apps.dcs.component.DCDInputGate ;
import com.sandy.apps.dcs.util.DCDUtility ;

public abstract class DCDInputGateUI extends DCDGateUI implements Serializable {

    private DCDInputGate andGate ;

    private JMenuItem tag = new JMenuItem("Tag as IC Port") ;

    public DCDInputGateUI(DCDInputGate gate) {

        super(gate) ;
        this.andGate = gate ;
        popupMenu.add(tag) ;
        tag.setActionCommand("TAG_AS_IC_PORT") ;
        tag.addActionListener(DCDUtility.getInstance().getDCDController()) ;
    }

    // Here we polpulate the port locations relative to the
    // tlx and tly.
    protected void populatePortLocations() {

        outputPortLocation = new Point2D[1] ;
        outputPortLocation[0] = new Point2D.Float(40, 15) ;
    }

    // The UI needs to be updated if the model changes.
    public void update(Observable arg0, Object arg1) {

    }

}