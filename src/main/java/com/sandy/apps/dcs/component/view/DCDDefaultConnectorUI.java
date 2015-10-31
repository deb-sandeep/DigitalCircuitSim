
package com.sandy.apps.dcs.component.view ;

import java.awt.Color ;
import java.awt.Graphics ;
import java.awt.Graphics2D ;
import java.awt.Rectangle ;
import java.awt.event.MouseEvent ;
import java.awt.geom.Line2D ;
import java.awt.geom.Point2D ;
import java.io.Serializable ;
import java.util.Enumeration ;
import java.util.Observable ;
import java.util.Vector ;

import javax.swing.JPopupMenu ;

import com.sandy.apps.dcs.component.DCDConnector ;
import com.sandy.apps.dcs.component.factory.ComponentFactory ;
import com.sandy.apps.dcs.util.DCDUtility ;

public class DCDDefaultConnectorUI extends DCDComponentUI implements
        Serializable {

    private DCDConnector dcdConnector ;

    private Vector lines = new Vector() ;

    private boolean lineDrawn = false ;

    private Point2D startPoint ;

    private Point2D endPoint ;

    private final static int EXTENTION = 1 ;

    private final static int BRANCH = 2 ;

    private final static int NEW = 3 ;

    public DCDDefaultConnectorUI() {

    }

    public DCDDefaultConnectorUI(DCDConnector connector) {

        super(connector) ;
        this.dcdConnector = connector ;
    }

    // The UI needs to be updated if the model changes.
    public void update(Observable arg0, Object arg1) {

    }

    public void paint(Graphics g, int mode) {

        Graphics2D g2d = (Graphics2D) g ;
        switch (mode) {
        case DCDComponentUI.SELECTED:
            g.setColor(DCDUtility.getInstance().getSelectedLinkColor()) ;
            break ;
        case DCDComponentUI.DESELECTED:
        case DCDComponentUI.NORMAL:
            g.setColor(DCDUtility.getInstance().getLinkColor()) ;
            break ;
        case DCDComponentUI.DELETED:
            g.setColor(DCDUtility.getInstance().getBackgroundColor()) ;
            break ;
        }

        try {
            int size = lines.size() ;
            for (int i = 0; i < size; i++) {
                g2d.draw((Line2D) lines.elementAt(i)) ;
            }
            if (dcdConnector.isBranch()) {
                Point2D start = null ;
                if (lines.size() == 0) {
                    start = startPoint ;
                }
                else {
                    start = getFirstPoint() ;
                }
                g.fillOval((int) start.getX(), (int) start.getY(), 5, 5) ;
            }
        }
        catch (NullPointerException e) {

        }
    }

    // The intention of dragging the mouse over this connector
    // might be different depending upon the point from which the
    // dragging is started.
    //
    // If the dragging is started from near its ends then the
    // intention might be to extend this connector
    //
    // Else if the dragging is started from somewhere in the middle
    // of the connector the intention might be to create a branch
    // connector of this connector.
    //
    //
    // These actions will only be possible if and only if the
    // Connector toggle button is in a pressed state.
    //
    // CONTROL WILL COME HERE IF AND ONLY IF THE CONNECTOR BUTTON IS PRESSED
    // AND THIS CONNECTOR IS SELECTED ON THE SCREEN.
    public void handleMouseDragEvent(MouseEvent e) {

        // Get the intention of the mosue click.
        int intention = getIntentionOfMouseClick((Point2D) e.getPoint()) ;

        if (intention == EXTENTION) {
            if (lines.size() == 0) {
                if (startPoint == null) {
                    if (dcdConnector.isBranch()) {
                        DCDConnector parent = dcdConnector.getParent() ;
                        startPoint = ((DCDDefaultConnectorUI) parent.getUI())
                                .getClosestPoint((Point2D) e.getPoint()) ;
                        endPoint = startPoint ;
                    }
                    else {
                        startPoint = (Point2D) e.getPoint() ;
                        endPoint = startPoint ;
                    }
                }
            }
            else {
                if (startPoint == null) {
                    startPoint = getClosestEndPoint((Point2D) e.getPoint()) ;
                    endPoint = startPoint ;
                }
            }

            Graphics2D g = DCDUtility.getInstance().getGraphics() ;
            g.setXORMode(DCDUtility.getInstance().getBackgroundColor()) ;
            if (dcdConnector.isSelected())
                g.setColor(DCDUtility.getInstance().getSelectedLinkColor()) ;
            else
                g.setColor(Color.black) ;
            g.draw(new Line2D.Float(startPoint, endPoint)) ;
            endPoint = (Point2D) e.getPoint() ;
            g.draw(new Line2D.Float(startPoint, endPoint)) ;
            if (dcdConnector.isBranch()) {
                if (lines.size() == 0) {
                    Point2D start = startPoint ;
                    g.fillOval((int) start.getX(), (int) start.getY(), 5, 5) ;
                }
            }

            if (!lineDrawn)
                lineDrawn = true ;
        }
        else if (intention == BRANCH) {
            DCDConnector newConnector = ComponentFactory.getInstance()
                    .getDCDConnector(dcdConnector) ;
            DCDUtility.getInstance().setSelectedComponent(newConnector) ;
            newConnector.setBranch(true) ;
            dcdConnector.addBranch(newConnector) ;
            return ;
        }
        // This means there are no lines in this connector.

    }

    // This function causes the connector to blink sometimes
    // on the screen. This is useful because when the connector
    // blinks the user knows that the connector has been
    // succesfully absorbed by the port.
    public void blink() {

        DCDUtility utility = DCDUtility.getInstance() ;
        Graphics2D g = utility.getGraphics() ;
        this.paint(g, DCDComponentUI.DELETED) ;
        utility.sleep(100) ;
        this.paint(g, DCDComponentUI.SELECTED) ;
        utility.sleep(300) ;
        this.paint(g, DCDComponentUI.DELETED) ;
        utility.sleep(100) ;
        this.paint(g, DCDComponentUI.SELECTED) ;
    }

    public void handleMouseReleaseEvent(MouseEvent e) {

        if (lineDrawn) {
            // Here we have to draw a proper line.
            if (lines.size() != 0) {
                if (startPoint.equals(getFirstPoint()))
                    lines.insertElementAt(
                            new Line2D.Float(endPoint, startPoint), 0) ;
                else
                    lines.addElement(new Line2D.Float(startPoint, endPoint)) ;
            }
            else {
                lines.addElement(new Line2D.Float(startPoint, endPoint)) ;
            }
            Graphics2D g = DCDUtility.getInstance().getGraphics() ;
            g.setColor(Color.red) ;
            g.draw(new Line2D.Float(startPoint, this.endPoint)) ;

            startPoint = null ;
            endPoint = null ;
            lineDrawn = false ;
        }
    }

    public boolean isSelected(MouseEvent e) {

        return (isCloseEnough((Point2D) e.getPoint())) ;
    }

    public String getSavingInformation() {

        StringBuffer sBuf = new StringBuffer() ;
        sBuf.append("\t\t\t<DCDConnectorUI>\n") ;
        sBuf.append(getPointList()) ;
        sBuf.append("\t\t\t</DCDConnectorUI>\n") ;
        return (sBuf.toString()) ;
    }

    private String getPointList() {

        StringBuffer sBuf = new StringBuffer() ;
        Line2D line = null ;
        for (Enumeration e = lines.elements(); e.hasMoreElements();) {
            line = (Line2D) e.nextElement() ;
            sBuf.append("\t\t\t\t<Point x=\"" + line.getX1() + "\" y=\""
                    + line.getY1() + "\"  />\n") ;
        }
        if (line != null)
            sBuf.append("\t\t\t\t<Point x=\"" + line.getX2() + "\" y=\""
                    + line.getY2() + "\"  />\n") ;
        return (sBuf.toString()) ;

    }

    public void addLine(Line2D line) {

        lines.addElement(line) ;
    }

    public String getHashCode() {

        Line2D firstLine = (Line2D) lines.elementAt(0) ;
        String code = "[" + firstLine.getX1() + firstLine.getY1()
                + firstLine.getX2() + firstLine.getY2() + "]" ;
        return (code) ;
    }

    // ////////////////////
    //
    // Private Functions
    //
    // ////////////////////
    // This function is used by the function which tries to get
    // the closest component near the point of mouse click.
    private boolean isCloseEnough(Point2D p) {

        int size = lines.size() ;
        for (int i = 0; i < size; i++) {
            Line2D line = (Line2D) lines.elementAt(i) ;
            Rectangle boundingRectangle = line.getBounds() ;
            boundingRectangle.setRect(boundingRectangle.getX() - 4,
                    boundingRectangle.getY() - 4,
                    boundingRectangle.getWidth() + 8, boundingRectangle
                            .getHeight() + 8) ;
            if (line.ptLineDist(p) < 4 && boundingRectangle.contains(p))
                return (true) ;
        }
        return (false) ;
    }

    private Point2D getClosestEndPoint(Point2D p) {

        Point2D firstPoint = getFirstPoint() ;
        Point2D lastPoint = getLastPoint() ;
        double d1 = firstPoint.distance(p) ;
        double d2 = lastPoint.distance(p) ;

        return (d1 < d2) ? firstPoint : lastPoint ;
    }

    private Point2D getFirstPoint() {

        return (Point2D) (((Line2D) lines.firstElement()).getP1()).clone() ;
    }

    private Point2D getLastPoint() {

        return (Point2D) ((Line2D) lines.lastElement()).getP2().clone() ;
    }

    private int getIntentionOfMouseClick(Point2D p) {

        // This means that line is being currently drawn and this is
        // and intermediatery drag
        if (lineDrawn)
            return (EXTENTION) ;

        // This means that there are no points and this is just the start
        if (lines.size() == 0)
            return (EXTENTION) ;

        // This means that the point is far off and the intention
        // is to draw a new connector.
        if (!isCloseEnough(p))
            return NEW ;

        // If the control comes here that means either the intention
        // is to extend the connector or to draw a new branch connector.
        Point2D closest = getClosestEndPoint(p) ;
        if (p.distance(closest) < 5)
            return (EXTENTION) ;

        return (BRANCH) ;
    }

    public Point2D getClosestPoint(Point2D p) {

        Line2D line = null ;
        int size = lines.size() ;
        for (int i = 0; i < size; i++) {
            line = (Line2D) lines.elementAt(i) ;
            if (line.ptLineDist(p) < 3)
                break ;
        }

        if (line != null) {
            // This line is the closest to the point p.
            // So we have to find the desired point on this line.
            double distanceSq = line.ptLineDistSq(p) ;
            double x1 = line.getX1() ;
            double x2 = line.getX2() ;
            double y1 = line.getY1() ;
            double y2 = line.getY2() ;
            double x3 = p.getX() ;
            double y3 = p.getY() ;
            double x4 = x3
                    + Math.sqrt(distanceSq
                            * Math.pow((y2 - y1), 2)
                            / ((Math.pow((y2 - y1), 2)) + (Math.pow((x2 - x1),
                                    2)))) ;
            double y4 = y1 ;
            if ((int) (x2 - x1) == 0)
                y4 = y3 ;
            else
                y4 = (y2 - y1) * (x4 - x1) / (x2 - x1) + y1 ;

            return (new Point2D.Double((int) x4, (int) y4)) ;

        }
        return (null) ;

    }

    public Point2D getStartPoint() {

        return this.startPoint ;
    }

    public void setStartPoint(Point2D startPoint) {

        this.startPoint = startPoint ;
        this.endPoint = startPoint ;
    }

    public void setEndPoint(Point2D eP) {

        if (startPoint != null && endPoint != null) {
            Graphics2D g = DCDUtility.getInstance().getGraphics() ;
            g.setXORMode(DCDUtility.getInstance().getBackgroundColor()) ;
            g.setColor(DCDUtility.getInstance().getSelectedLinkColor()) ;
            g.draw(new Line2D.Float(startPoint, this.endPoint)) ;
            this.endPoint = eP ;
        }
        else {
            this.endPoint = eP ;
        }
    }

    public DCDConnector getDCDConnector() {

        return this.dcdConnector ;
    }

    public void setDCDConnector(DCDConnector dcdConnector) {

        this.dcdConnector = dcdConnector ;
        super.setComponent(dcdConnector) ;
        deleteMenuItem.setActionCommand("DELETE") ;
        popupMenu = new JPopupMenu() ;
        popupMenu.add(setNameMenuItem) ;
        popupMenu.add(deleteMenuItem) ;

        setNameMenuItem.addActionListener(dcdConnector) ;
        deleteMenuItem.addActionListener(DCDUtility.getInstance()
                .getDCDController()) ;
    }
}
