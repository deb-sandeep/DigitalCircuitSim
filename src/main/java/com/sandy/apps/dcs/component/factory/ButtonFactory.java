package com.sandy.apps.dcs.component.factory ;

import java.awt.Dimension ;

import javax.swing.JButton ;

import com.sandy.apps.dcs.util.DCDUtility ;

public class ButtonFactory {

    private static Dimension size = new Dimension( 30, 25 ) ;

    public static JButton getExitButton() {

        JButton button = new JButton( DCDUtility.loadIconResource( "exit" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "EXIT" ) ;
        button.addActionListener( DCDUtility.getInstance().getDCDController() ) ;
        return ( button ) ;
    }

    public static JButton getRunButton() {

        JButton button = new JButton( DCDUtility.loadIconResource( "Run" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "RUN" ) ;
        button.addActionListener( DCDUtility.getInstance().getDCDController() ) ;
        return ( button ) ;
    }

    public static JButton getMultiInputButton() {

        JButton button = new JButton(
                DCDUtility.loadIconResource( "multiinput" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "MULTI_INPUT" ) ;
        button.addActionListener( DCDUtility.getInstance().getDCDController() ) ;
        return ( button ) ;
    }

    public static JButton getRefreshButton() {

        JButton button = new JButton( DCDUtility.loadIconResource( "refresh" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "REFRESH" ) ;
        button.addActionListener( DCDUtility.getInstance().getDCDController() ) ;
        return ( button ) ;
    }

}