
package com.sandy.apps.dcs.component.view ;

import java.util.* ;
import java.awt.* ;
import java.awt.event.* ;
import java.io.* ;

import javax.swing.* ;

import com.sandy.apps.dcs.common.* ;
import com.sandy.apps.dcs.component.DCDComponent ;
import com.sandy.apps.dcs.component.model.DCDComponentModel ;

// This is the abstract base class of all the displayable components.
// This stores only the displayable data and incorporates the drawing
// logic. This is being contained by value inside the DCDComponent class.
public abstract class DCDComponentUI extends Observable implements Observer,
        Serializable {

    public final static int SELECTED = 0 ;

    public final static int DESELECTED = 1 ;

    public final static int NORMAL = 2 ;

    public final static int DELETED = 3 ;

    // The DCDComponent which this UI represents
    private DCDComponent component ;

    protected JPopupMenu popupMenu = null ;

    protected JMenuItem setNameMenuItem = new JMenuItem("Name") ;

    protected JMenuItem deleteMenuItem = new JMenuItem("Delete") ;

    DCDComponentUI() {

    }

    DCDComponentUI(DCDComponent component) {

        this.component = component ;
        popupMenu = new JPopupMenu() ;
        deleteMenuItem.setActionCommand("DELETE") ;
        setNameMenuItem.setActionCommand("NAME") ;
        popupMenu.add(setNameMenuItem) ;
        popupMenu.add(deleteMenuItem) ;

        setNameMenuItem.addActionListener(component) ;
        deleteMenuItem.addActionListener(DCDUtility.getInstance()
                .getDCDController()) ;
    }

    // This incorporates the rendering logic of the component.
    // All the subclasses of this class must implement the paint method.
    public abstract void paint(Graphics g, int mode) ;

    public abstract boolean isSelected(MouseEvent e) ;

    // The UI classes need to handle the mouse clicks registered by the
    // Controller. This ineffect needs to update the model.
    public abstract void handleMouseDragEvent(MouseEvent e) ;

    // This is called when the associated component is selected and the
    // mouse is released. This action means that either the actor has
    // finished his intended work or else has changed his mind.
    public abstract void handleMouseReleaseEvent(MouseEvent e) ;

    // This function will return a String which will be unique for
    // for each instance of the Component visible on the Screen.
    // This will be a function of the location of the Component
    // on the screen.
    public abstract String getHashCode() ;

    public void showPopupMenu(Point p, Component component) {

        // Point offset=DCDUtility.getInstance().getDCDCanvas().getLocation();
        // p.translate((int)offset.getX(),(int)offset.getY());
        popupMenu.show(component, p.x, p.y) ;
    }

    public DCDComponent getComponent() {

        return this.component ;
    }

    public void setComponent(DCDComponent component) {

        this.component = component ;
    }
}
