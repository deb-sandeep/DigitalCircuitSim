package com.sandy.apps.dcs.testing ;

import javax.swing.* ;
import java.awt.geom.* ;
import java.awt.* ;
import java.util.* ;

public class Connector implements Component {

    private GeneralPath path ;

    private String      name = "" ;

    public Connector() {

        path = new GeneralPath() ;
    }

    public Connector( Point2D p1, Point2D p2 ) {

        this() ;
        addPath( new Line2D.Float( p1, p2 ) ) ;
    }

    public void addPath( Shape s ) {

        if( s instanceof Line2D.Float ) {
            path.append( s, false ) ;
        }
    }

    public boolean isPointNear( Point2D p ) {

        Enumeration e = elements() ;
        while( e.hasMoreElements() ) {
            Line2D.Float line = (Line2D.Float) e.nextElement() ;
            if( line.ptLineDist( p ) < 2 ) return ( true ) ;
        }
        return ( false ) ;
    }

    public void paint( Graphics g ) {

        Graphics2D g2D = (Graphics2D) g ;
        g2D.draw( path ) ;
    }

    private Vector makeVector() {

        Vector lines = new Vector() ;
        float[] arr = new float[6] ;

        PathIterator iterator = path.getPathIterator( null ) ;
        while( !iterator.isDone() ) {
            iterator.currentSegment( arr ) ;
            float x1 = arr[0] ;
            float y1 = arr[1] ;
            iterator.next() ;
            iterator.currentSegment( arr ) ;
            float x2 = arr[0] ;
            float y2 = arr[1] ;
            lines.addElement( new Line2D.Float( x1, y1, x2, y2 ) ) ;
            iterator.next() ;
        }
        return ( lines ) ;

    }

    private Enumeration elements() {

        return ( makeVector().elements() ) ;
    }

    private Line2D.Float getFirstLine() {

        return ( (Line2D.Float) makeVector().elementAt( 0 ) ) ;
    }

    private Line2D.Float getLastLine() {

        Vector vec = makeVector() ;
        return ( (Line2D.Float) vec.elementAt( vec.size() - 1 ) ) ;
    }

    public Point2D closestEnd( Point2D p ) {

        Point2D p1 = getFirstLine().getP1() ;
        Point2D p2 = getLastLine().getP2() ;
        double d1 = p1.distance( p ) ;
        double d2 = p2.distance( p ) ;
        Point2D rp = ( d1 < d2 ) ? p1 : p2 ;
        return rp ;
    }

    public String getName() {

        return this.name ;
    }

    public void setName( String name ) {

        this.name = name ;
    }

    public boolean handleMouseClick( Point2D p ) {

        if( isPointNear( p ) ) { return true ; }
        return ( false ) ;
    }

    public void setSelected( Graphics g, boolean selected ) {

        if( selected ) g.setColor( Color.red ) ;
        else g.setColor( Color.black ) ;
        paint( g ) ;
    }

    public void remove( Graphics g ) {

        g.setColor( Color.white ) ;
        paint( g ) ;
    }
}