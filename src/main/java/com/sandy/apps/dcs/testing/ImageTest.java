package com.sandy.apps.dcs.testing ;

import javax.swing.* ;
import java.awt.* ;
import java.awt.geom.* ;
import java.util.* ;
import java.awt.event.* ;
import javax.swing.event.* ;
import java.awt.image.* ;

public class ImageTest {

    private JxFrame  frame ;

    private MyCanvas canvas ;

    private Image    image = Toolkit.getDefaultToolkit().getImage(
                                   "images/and.gif" ) ;

    private class MyCanvas extends Canvas implements MouseMotionListener {

        int oldx = 100, oldy = 100 ;

        MyCanvas() {

            super() ;
            addMouseMotionListener( this ) ;
            setBackground( Color.white ) ;
        }

        public void paint( Graphics g ) {

            g.drawLine( 0, 0, 100, 100 ) ;
            g.drawLine( 50, 50, 200, 150 ) ;

        }

        public void mouseMoved( MouseEvent e ) {

        }

        public void mouseDragged( MouseEvent e ) {

            Graphics g = getGraphics() ;
            g.setXORMode( Color.white ) ;
            g.drawImage( image, oldx, oldy, frame ) ;
            g.drawImage( image, e.getX(), e.getY(), frame ) ;
            oldx = e.getX() ;
            oldy = e.getY() ;
        }
    } ;

    ImageTest() {

        frame = new JxFrame() ;
        setUpGUI() ;
        frame.setVisible( true ) ;
    }

    private void setUpGUI() {

        canvas = new MyCanvas() ;

        frame.getContentPane().add( canvas, "Center" ) ;
    }

    private void test() {

    }

    public static void main( String[] args ) {

        new ImageTest().test() ;
    }

}