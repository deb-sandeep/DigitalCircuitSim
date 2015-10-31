package com.sandy.apps.dcs.testing ;

import java.awt.event.* ;
import java.awt.* ;
import javax.swing.* ;

public class RightClickTest {

    private JxFrame    frame ;

    private JPopupMenu popupMenu = new JPopupMenu( "Hehehe" ) ;

    // private MyCanvas canvas;

    RightClickTest() {

        frame = new JxFrame() ;
        popupMenu.add( "One" ) ;
        popupMenu.add( "Two" ) ;
        popupMenu.add( "Three" ) ;
        setUpGUI() ;
        frame.addMouseListener( new MouseAdapter() {

            public void mouseClicked( MouseEvent e ) {

                // Here to test which button was clicked.
                // System.out.println("[S][]:"+e.getModifiers());
                if( e.getModifiers() == ( MouseEvent.BUTTON1_MASK ) ) {
                    System.out.println( "[S][BUTTON 1]:" ) ;
                }
                else if( e.getModifiers() == ( MouseEvent.BUTTON2_MASK ) ) {
                    System.out.println( "[S][BUTTON 2]:" ) ;
                }
                else if( e.getModifiers() == ( MouseEvent.BUTTON3_MASK ) ) {
                    System.out.println( "[S][BUTTON 3]:" ) ;
                    popupMenu.show( frame, e.getPoint().x, e.getPoint().y ) ;
                }
            }
        } ) ;
        frame.setVisible( true ) ;
    }

    private void setUpGUI() {

        frame.getContentPane().setLayout( new FlowLayout() ) ;
    }

    private void test() {

    }

    public static void main( String[] args ) {

        new RightClickTest().test() ;
    }

}