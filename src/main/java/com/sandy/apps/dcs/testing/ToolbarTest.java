package com.sandy.apps.dcs.testing ;

import javax.swing.* ;
import java.awt.* ;
import java.awt.geom.* ;
import java.util.* ;
import java.awt.event.* ;
import javax.swing.event.* ;

public class ToolbarTest implements ChangeListener, ItemListener {

    private JxFrame frame ;

    // private MyCanvas canvas;

    ToolbarTest() {

        frame = new JxFrame() ;
        setUpGUI() ;
        frame.setVisible( true ) ;
    }

    public void itemStateChanged( ItemEvent e ) {

    }

    public void stateChanged( ChangeEvent e ) {

    }

    private void setUpGUI() {

        JToolBar toolbar = new JToolBar() ;

        ButtonGroup bg = new ButtonGroup() ;

        JToggleButton button = new JToggleButton( new ImageIcon(
                "images/and.gif" ) ) ;
        button.setMaximumSize( new Dimension( 50, 40 ) ) ;
        toolbar.add( button ) ;
        // button.addChangeListener(this);
        button.addItemListener( this ) ;
        button.setActionCommand( "Button1" ) ;

        JToggleButton button1 = new JToggleButton( new ImageIcon(
                "images/nand.gif" ) ) ;
        button1.setMaximumSize( new Dimension( 50, 40 ) ) ;
        toolbar.add( button1 ) ;
        toolbar.setFloatable( false ) ;
        toolbar.setBorder( BorderFactory.createRaisedBevelBorder() ) ;
        button1.addItemListener( this ) ;
        button1.setActionCommand( "Button2" ) ;

        bg.add( button ) ;
        bg.add( button1 ) ;

        frame.getContentPane().add( toolbar, "North" ) ;
    }

    private void test() {

    }

    public static void main( String[] args ) {

        new ToolbarTest().test() ;
    }

}