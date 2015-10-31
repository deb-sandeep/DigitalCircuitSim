package com.sandy.apps.dcs.testing ;

import javax.swing.* ;
import java.awt.* ;
import java.awt.geom.* ;
import java.util.* ;
import java.awt.event.* ;
import javax.swing.event.* ;

public class ScrollbarOnPanelTest {

    private JxFrame frame ;

    // private MyCanvas canvas;

    ScrollbarOnPanelTest() {

        frame = new JxFrame() ;
        setUpGUI() ;
        frame.setVisible( true ) ;
    }

    private void setUpGUI() {

        JPanel panel = new JPanel() ;
        panel.setSize( new Dimension( 500, 500 ) ) ;
        // panel.setMinimumSize(new Dimension(500,500));
        panel.setPreferredSize( new Dimension( 1500, 1500 ) ) ;

        JScrollPane pane = new JScrollPane( panel ) ;
        frame.getContentPane().add( pane ) ;

    }

    public static void main( String[] args ) {

        new ScrollbarOnPanelTest() ;
    }

}