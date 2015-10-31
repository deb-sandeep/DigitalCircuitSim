package com.sandy.apps.dcs.testing ;

import javax.swing.* ;
import java.awt.event.* ;

public class JxFrame extends JFrame {

    public JxFrame( String str ) {

        super( str ) ;
        addWindowListener( new WindowAdapter() {

            public void windowClosing( WindowEvent e ) {

                System.exit( 0 ) ;
            }
        } ) ;
        setBounds( 100, 100, 400, 400 ) ;
    }

    public JxFrame() {

        this( "Walrus .." ) ;
    }
}
