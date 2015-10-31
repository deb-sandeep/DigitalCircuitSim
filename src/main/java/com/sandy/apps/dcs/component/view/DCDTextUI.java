
package com.sandy.apps.dcs.component.view ;

import java.awt.* ;
import java.awt.event.* ;
import java.util.* ;
import java.awt.geom.* ;
import java.io.* ;

import javax.swing.* ;

import com.sandy.apps.dcs.common.* ;
import com.sandy.apps.dcs.component.DCDText ;
import com.sandy.apps.dcs.component.model.* ;
import com.sandy.apps.dcs.factory.* ;

public class DCDTextUI extends DCDComponentUI implements Serializable {

    private Point2D location ;

    private DCDText dcdText ;

    public DCDTextUI() {

    }

    public DCDTextUI(DCDText tag, Point2D location) {

        super(tag) ;
        this.dcdText = tag ;
        this.location = location ;
    }

    public String getHashCode() {

        return (null) ;
    }

    // The UI needs to be updated if the model changes.
    public void update(Observable arg0, Object arg1) {

    }

    public void paint(Graphics g, int mode) {

        Graphics2D g2d = (Graphics2D) g ;
        if (mode == DCDComponentUI.DELETED) {
            g2d.setXORMode(DCDUtility.getInstance().getBackgroundColor()) ;
        }
        paint(g2d) ;

    }

    public void paint(Graphics2D g) {

        int x = (int) location.getX() ;
        int y = (int) location.getY() ;
        Color temp = g.getColor() ;
        g.setColor(DCDUtility.getInstance().getTextColor()) ;
        g.drawString(dcdText.getText(), x, y) ;
        g.setColor(temp) ;
    }

    public void handleMouseDragEvent(MouseEvent e) {

        Graphics2D g = DCDUtility.getInstance().getGraphics() ;
        paint(g, DCDComponentUI.DELETED) ;
        location = e.getPoint() ;
        paint(g, DCDComponentUI.SELECTED) ;
    }

    public void handleMouseReleaseEvent(MouseEvent e) {

    }

    public boolean isSelected(MouseEvent e) {

        return (isCloseEnough((Point2D) e.getPoint())) ;
    }

    // ////////////////////
    //
    // Private Functions
    //
    // ////////////////////
    // This function is used by the function which tries to get
    // the closest component near the point of mouse click.
    private boolean isCloseEnough(Point2D p) {

        Graphics g = (Graphics) DCDUtility.getInstance().getGraphics() ;
        FontMetrics fm = g.getFontMetrics(g.getFont()) ;
        int stringHeight = fm.getHeight() ;
        int stringWidth = fm.stringWidth(dcdText.getText()) ;
        Rectangle2D r = new Rectangle2D.Double(location.getX(), location.getY()
                - stringHeight, stringWidth, stringHeight) ;
        if (r.contains(p.getX(), p.getY()))
            return true ;
        return false ;
    }

    public String getSavingInformation() {

        StringBuffer buffer = new StringBuffer() ;
        buffer.append(" x=\"" + (int) location.getX() + "\" y=\""
                + (int) location.getY() + "\"") ;
        return buffer.toString() ;
    }

    public Point2D getLocation() {

        return this.location ;
    }

    public void setLocation(Point2D location) {

        this.location = location ;
    }
}
