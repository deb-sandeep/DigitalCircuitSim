
package com.sandy.apps.dcs.component.view ;

import java.awt.Graphics ;
import java.awt.Graphics2D ;
import java.awt.event.MouseEvent ;
import java.awt.geom.Point2D ;
import java.io.Serializable ;
import java.util.Observable ;

import com.sandy.apps.dcs.component.DCDTag ;
import com.sandy.apps.dcs.component.model.DCDTagModel ;
import com.sandy.apps.dcs.component.model.Port ;

public class DCDTagUI extends DCDComponentUI implements Serializable {

    private Point2D location ;

    private DCDTag tag ;

    public DCDTagUI() {

    }

    public DCDTagUI(DCDTag tag, Point2D location) {

        super(tag) ;
        this.tag = tag ;
        this.location = location ;
    }

    public String getHashCode() {

        return (null) ;
    }

    // The UI needs to be updated if the model changes.
    public void update(Observable arg0, Object arg1) {

    }

    public void paint(Graphics g, int mode) {

        paint((Graphics2D) g) ;
    }

    public void paint(Graphics2D g) {

        int x = (int) location.getX() ;
        int y = (int) location.getY() ;
        g.drawLine(x, y, x, y - 5) ;
        g.drawOval(x - 7, y - 19, 14, 14) ;
        Port port = ((DCDTagModel) tag.getModel()).getPort() ;
        g.drawString(String.valueOf(port.getID()), x - 1, y - 8) ;
    }

    public void handleMouseDragEvent(MouseEvent e) {

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

        return (false) ;
    }

    public String getSavingInformation() {

        String type = tag.getType() ;
        int x = 0 ;
        int y = (int) location.getY() + 15 ;
        if (type.startsWith(DCDTag.INPUT))
            x = (int) location.getX() + 40 ;
        else
            x = (int) location.getX() ;

        StringBuffer buffer = new StringBuffer() ;
        buffer.append("\t\t<Location>\n") ;
        buffer.append("\t\t\t<Point x=\"" + (int) x + "\" y=\"" + (int) y
                + "\"  />\n") ;
        buffer.append("\t\t</Location>\n") ;
        return buffer.toString() ;
    }

    public Point2D getLocation() {

        return this.location ;
    }

    public void setLocation(Point2D location) {

        this.location = location ;
    }
}
