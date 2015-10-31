package com.sandy.apps.dcs.testing ;

import javax.swing.* ;
import java.awt.* ;
import java.awt.geom.* ;
import java.util.* ;
import java.awt.event.* ;

public class Tester implements MouseListener, MouseMotionListener {

    Vector          components = new Vector() ;

    private boolean dragging   = false ;

    private Point2D dragStartPoint ;

    private Point2D dragEndPoint ;

    Point2D         oldPoint ;

    Component       selectedComponent ;

    boolean         lineDrawn  = false ;

    private class MyCanvas extends Canvas {

        MyCanvas() {

            super() ;
        }

        public void paint( Graphics g ) {

            // Do the drawing here
            Enumeration e = components.elements() ;
            while( e.hasMoreElements() ) {
                Component component = (Component) e.nextElement() ;
                component.paint( g ) ;
            }
        }
    } ;

    private JxFrame  frame ;

    private MyCanvas canvas ;

    Tester() {

        frame = new JxFrame() ;
        canvas = new MyCanvas() ;
        canvas.setBackground( Color.white ) ;
        canvas.addMouseListener( this ) ;
        canvas.addMouseMotionListener( this ) ;
        frame.addKeyListener( new KeyAdapter() {

            public void keyPressed( KeyEvent e ) {

                if( e.getKeyCode() == KeyEvent.VK_DELETE ) {
                    if( selectedComponent != null ) {
                        selectedComponent.remove( canvas.getGraphics() ) ;
                        components.remove( selectedComponent ) ;
                        selectedComponent = null ;
                    }
                }
            }
        } ) ;
        setUpGUI() ;
        frame.setVisible( true ) ;
    }

    private void setUpGUI() {

        frame.getContentPane().add( canvas, "Center" ) ;
    }

    private void test() {

        Connector con = new Connector() ;
        con.addPath( new Line2D.Float( 100, 100, 150, 150 ) ) ;
        con.setName( "Connector1" ) ;
        addComponent( con ) ;

        con = new Connector() ;
        con.addPath( new Line2D.Float( 200, 200, 200, 150 ) ) ;
        con.addPath( new Line2D.Float( 200, 150, 250, 100 ) ) ;
        con.setName( "Connector2" ) ;
        addComponent( con ) ;
    }

    public void addComponent( Component c ) {

        components.addElement( c ) ;
    }

    public static void main( String[] args ) {

        new Tester().test() ;
    }

    /**
     * Invoked when the mouse has been clicked on a component.
     */
    public void handleSelection( MouseEvent e ) {

        Point p = e.getPoint() ;
        Enumeration en = components.elements() ;
        boolean flag = false ;
        while( en.hasMoreElements() && !flag ) {
            Component c = (Component) en.nextElement() ;
            flag = c.handleMouseClick( p ) ;
            if( flag ) {
                if( selectedComponent != null ) selectedComponent.setSelected(
                        canvas.getGraphics(), false ) ;
                c.setSelected( canvas.getGraphics(), true ) ;
                selectedComponent = c ;
                return ;
            }

        }
        if( flag == false ) {
            if( selectedComponent != null ) {
                selectedComponent.setSelected( canvas.getGraphics(), false ) ;
                selectedComponent = null ;
            }
        }

    } ;

    public void mouseClicked( MouseEvent e ) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    public void mousePressed( MouseEvent e ) {

        handleSelection( e ) ;
        dragging = true ;
        Point2D p = e.getPoint() ;
        if( selectedComponent != null ) dragStartPoint = selectedComponent
                .closestEnd( p ) ;
        else dragStartPoint = p ;
        oldPoint = dragStartPoint ;

    } ;

    /**
     * Invoked when a mouse button has been released on a component.
     */
    public void mouseReleased( MouseEvent e ) {

        dragging = false ;
        dragEndPoint = e.getPoint() ;
        if( lineDrawn ) {
            lineDrawn = false ;
            if( selectedComponent != null ) {
                ( (Connector) selectedComponent ).addPath( new Line2D.Float(
                        dragStartPoint, dragEndPoint ) ) ;
                selectedComponent.setSelected( canvas.getGraphics(), true ) ;
            }
            else {
                Component c = new Connector( dragStartPoint, dragEndPoint ) ;
                components.addElement( c ) ;
                selectedComponent = c ;
                c.setSelected( canvas.getGraphics(), true ) ;
            }
        }
    } ;

    /**
     * Invoked when the mouse enters a component.
     */
    public void mouseEntered( MouseEvent e ) {

    } ;

    public void mouseExited( MouseEvent e ) {

    } ;

    public void mouseMoved( MouseEvent e ) {

    } ;

    public void mouseDragged( MouseEvent e ) {

        lineDrawn = true ;
        Graphics2D g = (Graphics2D) canvas.getGraphics() ;
        g.setXORMode( Color.white ) ;
        g.draw( new Line2D.Double( dragStartPoint.getX(),
                dragStartPoint.getY(), oldPoint.getX(), oldPoint.getY() ) ) ;
        g.draw( new Line2D.Double( dragStartPoint.getX(),
                dragStartPoint.getY(), e.getX(), e.getY() ) ) ;
        oldPoint = e.getPoint() ;
    }
}