package com.sandy.apps.dcs.testing ;

import java.awt.Graphics ;
import java.awt.geom.Point2D ;

public interface Component {

    public void paint( Graphics g ) ;

    public void setName( String name ) ;

    public String getName() ;

    public boolean handleMouseClick( Point2D p ) ;

    public void setSelected( Graphics g, boolean b ) ;

    public void remove( Graphics g ) ;

    public Point2D closestEnd( Point2D p ) ;
}