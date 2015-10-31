package com.sandy.apps.dcs.component.factory ;

import java.awt.Dimension ;

import javax.swing.JToggleButton ;

import com.sandy.apps.dcs.util.DCDUtility ;

public class ToggleButtonFactory {

    private static Dimension size = new Dimension( 30, 25 ) ;

    public static JToggleButton getANDButton() {

        JToggleButton button = new JToggleButton(
                DCDUtility.loadIconResource( "and" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "AND" ) ;
        button.addItemListener( DCDUtility.getInstance() ) ;
        button.setToolTipText( "AND Gate" ) ;
        return ( button ) ;
    }

    public static JToggleButton getSelectorButton() {

        JToggleButton button = new JToggleButton(
                DCDUtility.loadIconResource( "selector" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "SELECTOR" ) ;
        button.addItemListener( DCDUtility.getInstance() ) ;
        button.setToolTipText( "SELECTOR" ) ;
        return ( button ) ;
    }

    public static JToggleButton getNANDButton() {

        JToggleButton button = new JToggleButton(
                DCDUtility.loadIconResource( "nand" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "NAND" ) ;
        button.addItemListener( DCDUtility.getInstance() ) ;
        button.setToolTipText( "nAND Gate" ) ;
        return ( button ) ;
    }

    public static JToggleButton getNOTButton() {

        JToggleButton button = new JToggleButton(
                DCDUtility.loadIconResource( "not" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "NOT" ) ;
        button.addItemListener( DCDUtility.getInstance() ) ;
        button.setToolTipText( "NOT Gate" ) ;
        return ( button ) ;
    }

    public static JToggleButton getConnectorButton() {

        JToggleButton button = new JToggleButton(
                DCDUtility.loadIconResource( "connector" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "CONNECTOR" ) ;
        button.addItemListener( DCDUtility.getInstance() ) ;
        button.setToolTipText( "CONNECTOR" ) ;
        return ( button ) ;
    }

    public static JToggleButton getSteadyInputButton() {

        JToggleButton button = new JToggleButton(
                DCDUtility.loadIconResource( "steadyIP" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "STEADY_INPUT" ) ;
        button.addItemListener( DCDUtility.getInstance() ) ;
        button.setToolTipText( "STEADY_INPUT" ) ;
        return ( button ) ;
    }

    public static JToggleButton getORButton() {

        JToggleButton button = new JToggleButton(
                DCDUtility.loadIconResource( "or" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "OR" ) ;
        button.addItemListener( DCDUtility.getInstance() ) ;
        button.setToolTipText( "OR Gate" ) ;
        return ( button ) ;
    }

    public static JToggleButton getXORButton() {

        JToggleButton button = new JToggleButton(
                DCDUtility.loadIconResource( "xor" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "XOR" ) ;
        button.addItemListener( DCDUtility.getInstance() ) ;
        button.setToolTipText( "XOR Gate" ) ;
        return ( button ) ;
    }

    public static JToggleButton getLEDButton() {

        JToggleButton button = new JToggleButton(
                DCDUtility.loadIconResource( "led" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "LED" ) ;
        button.addItemListener( DCDUtility.getInstance() ) ;
        button.setToolTipText( "LED" ) ;
        return ( button ) ;
    }

    public static JToggleButton getClockButton() {

        JToggleButton button = new JToggleButton(
                DCDUtility.loadIconResource( "clockInput" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "CLOCK" ) ;
        button.addItemListener( DCDUtility.getInstance() ) ;
        button.setToolTipText( "CLOCK" ) ;
        return ( button ) ;
    }

    public static JToggleButton getICButton() {

        JToggleButton button = new JToggleButton(
                DCDUtility.loadIconResource( "IC" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "IC" ) ;
        button.addItemListener( DCDUtility.getInstance() ) ;
        button.setToolTipText( "IC" ) ;
        return ( button ) ;
    }

    public static JToggleButton getJKFlipFlopButton() {

        JToggleButton button = new JToggleButton(
                DCDUtility.loadIconResource( "JKFlipFlop" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "JKFLIPFLOP" ) ;
        button.addItemListener( DCDUtility.getInstance() ) ;
        button.setToolTipText( "JK FLIP-FLOP" ) ;
        return ( button ) ;
    }

    public static JToggleButton getRSFlipFlopButton() {

        JToggleButton button = new JToggleButton(
                DCDUtility.loadIconResource( "JKFlipFlop" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "RSFLIPFLOP" ) ;
        button.addItemListener( DCDUtility.getInstance() ) ;
        button.setToolTipText( "RS FLIP-FLOP" ) ;
        return ( button ) ;
    }

    public static JToggleButton getTFlipFlopButton() {

        JToggleButton button = new JToggleButton(
                DCDUtility.loadIconResource( "TFlipFlop" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "TFLIPFLOP" ) ;
        button.addItemListener( DCDUtility.getInstance() ) ;
        button.setToolTipText( "T FLIP-FLOP" ) ;
        return ( button ) ;
    }

    public static JToggleButton getDFlipFlopButton() {

        JToggleButton button = new JToggleButton(
                DCDUtility.loadIconResource( "DFlipFlop" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "DFLIPFLOP" ) ;
        button.addItemListener( DCDUtility.getInstance() ) ;
        button.setToolTipText( "D FLIP-FLOP" ) ;
        return ( button ) ;
    }

    public static JToggleButton getSevenSegmentDisplayButton() {

        JToggleButton button = new JToggleButton(
                DCDUtility.loadIconResource( "SevenSegmentDisplay" ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( "SEVENSEGMENTDISPLAY" ) ;
        button.addItemListener( DCDUtility.getInstance() ) ;
        button.setToolTipText( "SEVEN SEGMENT DISPLAY" ) ;
        return ( button ) ;
    }

    public static JToggleButton getToggleButton( String iconResource,
            String actionCommand, String tooltipText ) {

        JToggleButton button = new JToggleButton(
                DCDUtility.loadIconResource( iconResource ) ) ;
        button.setMaximumSize( size ) ;
        button.setActionCommand( actionCommand ) ;
        button.addItemListener( DCDUtility.getInstance() ) ;
        button.setToolTipText( tooltipText ) ;
        return ( button ) ;
    }
}