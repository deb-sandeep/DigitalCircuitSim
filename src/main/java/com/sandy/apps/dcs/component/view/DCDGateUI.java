
package com.sandy.apps.dcs.component.view ;

import java.awt.Font ;
import java.awt.Graphics ;
import java.awt.Graphics2D ;
import java.awt.event.MouseEvent ;
import java.awt.geom.Point2D ;
import java.io.Serializable ;

import com.sandy.apps.dcs.component.DCDGate ;
import com.sandy.apps.dcs.component.DCDTag ;
import com.sandy.apps.dcs.component.model.PortInfo ;
import com.sandy.apps.dcs.util.DCDUtility ;

public abstract class DCDGateUI extends DCDComponentUI implements Serializable {

    // These are the coordinates of the image of the DCDComponent.
    // This will be updated constantly as the image is moved around
    // the screen

    // This is the top left x co-ordinate
    protected int tlx ;

    // This is the top left y co-ordinate
    protected int tly ;

    // This is the width of the gate on the screen.
    // Previously I had fixed it to 40 but when we start creating multiple
    // legged gates .. we have to configure it accordingly.
    protected int width = 40 ;

    // This stores the height of the gate on the screen.
    protected int height = 30 ;

    // The Gate which this UI represents
    private DCDGate gate ;

    // These are the locations of the input ports on the gate.
    protected Point2D[] inputPortLocation ;

    // These are the location of the output ports on the gate.
    protected Point2D[] outputPortLocation ;

    // These are the portLocations for the IC.
    protected Point2D[] portLocation = null ;

    public DCDGateUI(DCDGate gate) {

        super(gate) ;
        this.gate = gate ;
        // Set the width and the height of the gate.
        // This will be a function of the number of ports of the gate.
        height = Math.max((gate.getNumInputPorts() + 1) * 10, 30) ;
        width = 40 ;
        // This forces the sub classes to populate the port locations.
        populatePortLocations() ;
    }

    // Here we have to paint the gate differently depending upon the
    // scenario. There can only be two scenarios ..
    // Either the component is in normal mode or the component is
    // in the selected state.

    public void paint(Graphics g, int mode) {

        Graphics2D g2d = (Graphics2D) g ;
        DCDTag tag = gate.getDCDTag() ;
        DCDTagUI tagUI = null ;
        if (tag != null)
            tagUI = (DCDTagUI) tag.getUI() ;

        Font f = new Font("Sans", Font.PLAIN, 9) ;
        g2d.setFont(f) ;
        // To do something here.
        if (mode == DCDComponentUI.SELECTED) {
            g.setColor(DCDUtility.getInstance().getSelectedGateColor()) ;
            paint(g2d) ;
            if (tag != null)
                tagUI.paint(g2d) ;
            drawRect(g) ;

        }
        else if (mode == DCDComponentUI.NORMAL) {
            g.setColor(DCDUtility.getInstance().getNormalGateColor()) ;
            paint(g2d) ;
            if (tag != null)
                tagUI.paint(g2d) ;
        }
        else if (mode == DCDComponentUI.DELETED) {
            if (gate.isSelected())
                g.setColor(DCDUtility.getInstance().getSelectedGateColor()) ;
            else
                g.setColor(DCDUtility.getInstance().getNormalGateColor()) ;

            g.setXORMode(DCDUtility.getInstance().getBackgroundColor()) ;
            paint(g2d) ;
            if (tag != null)
                tagUI.paint(g2d) ;
            if (gate.isSelected())
                drawRect(g) ;
        }
        else if (mode == DCDComponentUI.DESELECTED) {
            g.setColor(DCDUtility.getInstance().getSelectedGateColor()) ;
            g.setXORMode(DCDUtility.getInstance().getBackgroundColor()) ;
            paint(g2d) ;
            if (tag != null)
                tagUI.paint(g2d) ;
            drawRect(g) ;
            paint(g, DCDComponentUI.NORMAL) ;
        }
    }

    private void drawRect(Graphics g) {

        g.drawLine(tlx - 1, tly - 1, tlx - 1, tly + height + 1) ;
        g.drawLine(tlx, tly + height + 1, tlx + width + 1, tly + height + 1) ;
        g.drawLine(tlx + width + 1, tly + height, tlx + width + 1, tly - 1) ;
        g.drawLine(tlx + width, tly - 1, tlx, tly - 1) ;
    }

    // For gates mouse drag event means that the component
    // is being dragged on the workspace.
    public void handleMouseDragEvent(MouseEvent e) {

        Graphics2D g = DCDUtility.getInstance().getGraphics() ;
        DCDTagUI tagUI = null ;
        if (gate.getDCDTag() != null)
            tagUI = (DCDTagUI) gate.getDCDTag().getUI() ;
        paint(g, DCDComponentUI.DELETED) ;
        if (tagUI != null)
            tagUI.setLocation(new Point2D.Float(e.getX(), e.getY())) ;
        tlx = e.getX() ;
        tly = e.getY() ;
        paint(g, DCDComponentUI.SELECTED) ;
    }

    public void handleMouseReleaseEvent(MouseEvent e) {

    }

    public void setInitialPosition(int x, int y) {

        this.tlx = x ;
        this.tly = y ;
    }

    public boolean isSelected(MouseEvent e) {

        int x = e.getPoint().x ;
        int y = e.getPoint().y ;
        if (x > tlx && x < tlx + width) {
            if (y > tly && y < tly + height) {
                return (true) ;
            }
        }
        return (false) ;
    }

    public abstract void paint(Graphics2D g) ;

    // This abstract method forces the sub classes to populate the
    // location of the gates in this class.
    protected abstract void populatePortLocations() ;

    // This should return the port information about the port nearest
    // to the mouse click.
    public PortInfo getPortInfo(MouseEvent e, PortInfo visitor) {

        Point2D p = new Point2D.Float(e.getX() - tlx, e.getY() - tly) ;
        if (p.getX() < (width / 2)) {
            // This means that the click is on the side of the output ports.
            return (getClosestInputPortInfo(p, visitor)) ;
        }
        else {
            // This means that the click is on the side of the input ports.
            return (getClosestOutputPortInfo(p, visitor)) ;
        }
    }

    private PortInfo getClosestInputPortInfo(Point2D p, PortInfo visitor) {

        PortInfo portInfo = getClosestPortInfo(p, inputPortLocation, visitor) ;
        if (portInfo != null)
            portInfo.setPortType(PortInfo.INPUT_PORT) ;
        return portInfo ;
    }

    private PortInfo getClosestOutputPortInfo(Point2D p, PortInfo visitor) {

        PortInfo portInfo = getClosestPortInfo(p, outputPortLocation, visitor) ;
        if (portInfo != null)
            portInfo.setPortType(PortInfo.OUTPUT_PORT) ;
        return portInfo ;
    }

    protected PortInfo getClosestPortInfo(Point2D p, Point2D[] locations,
            PortInfo visitor) {

        if (locations == null)
            return (null) ;

        int size = locations.length ;
        Point2D closestPoint = locations[0] ;
        int id = 0 ;
        double closestDistance = locations[0].distance(p) ;
        for (int i = 1; i < size; i++) {
            double distance = locations[i].distance(p) ;
            if (distance < closestDistance) {
                closestDistance = distance ;
                closestPoint = locations[i] ;
                id = i ;
            }
        }
        Point2D p1 = (Point2D) closestPoint.clone() ;
        p1.setLocation(closestPoint.getX() + tlx, closestPoint.getY() + tly) ;
        visitor.setPortLocation(p1) ;
        visitor.setPortID(id) ;
        return (visitor) ;
    }

    // Getter and Setter methods for the Height of the gate
    public int getHeight() {

        return this.height ;
    }

    public void setHeight(int height) {

        this.height = height ;
    }

    // Getter and Setter methods for the Width of the gate.
    public int getWidth() {

        return this.width ;
    }

    public void setWidth(int width) {

        this.width = width ;
    }

    public DCDGate getDCDGate() {

        return this.gate ;
    }

    public void setDCDGate(DCDGate gate) {

        this.gate = gate ;
    }

    public String getSavingInformation() {

        StringBuffer sBuf = new StringBuffer() ;
        sBuf.append("\t\t\t<DCDGateUI>\n") ;
        sBuf.append("\t\t\t\t<Point x=\"" + tlx + "\" y=\"" + tly + "\" />\n") ;
        sBuf.append("\t\t\t</DCDGateUI>\n") ;
        return (sBuf.toString()) ;
    }

    // This will return a String representation of the
    // (tlx+width)/2 ; (tly+height)/2
    public String getHashCode() {

        String code = String.valueOf(tlx + width / 2) + ";"
                + String.valueOf(tly + height / 2) ;
        return (code) ;

    }

    public Point2D getLocationOnScreen() {

        return (new Point2D.Float(tlx, tly)) ;
    }
}
