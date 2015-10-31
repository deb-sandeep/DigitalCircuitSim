package com.sandy.apps.dcs.testing ;

import javax.swing.* ;
import java.awt.* ;
import java.awt.geom.* ;
import java.util.* ;
import java.awt.event.* ;

public class TransparencyTest {

    private JxFrame frame ;

    // private MyCanvas canvas;

    TransparencyTest() {

        frame = new JxFrame() ;
        setUpGUI() ;
        frame.setVisible( true ) ;
    }

    private void setUpGUI() {

        frame.getContentPane().setLayout( new FlowLayout() ) ;
        JButton button = new JButton( new ImageIcon( "images/nand.gif" ) ) ;
        // button.setBackground(Color.white);
        frame.getContentPane().add( button ) ;

    }

    private void test() {

    }

    public static void main( String[] args ) {

        new TransparencyTest().test() ;
    }

}