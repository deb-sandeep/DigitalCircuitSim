package com.sandy.apps.dcs.component.factory ;

import java.awt.geom.Point2D ;

import com.sandy.apps.dcs.component.DCDANDGate ;
import com.sandy.apps.dcs.component.DCDClock ;
import com.sandy.apps.dcs.component.DCDComponent ;
import com.sandy.apps.dcs.component.DCDConnector ;
import com.sandy.apps.dcs.component.DCDDFlipFlop ;
import com.sandy.apps.dcs.component.DCDGate ;
import com.sandy.apps.dcs.component.DCDJKFlipFlop ;
import com.sandy.apps.dcs.component.DCDLED ;
import com.sandy.apps.dcs.component.DCDNANDGate ;
import com.sandy.apps.dcs.component.DCDNOTGate ;
import com.sandy.apps.dcs.component.DCDORGate ;
import com.sandy.apps.dcs.component.DCDPulseInputGate ;
import com.sandy.apps.dcs.component.DCDRSFlipFlop ;
import com.sandy.apps.dcs.component.DCDSevenSegmentDisplay ;
import com.sandy.apps.dcs.component.DCDSteadyInputGate ;
import com.sandy.apps.dcs.component.DCDTFlipFlop ;
import com.sandy.apps.dcs.component.DCDXORGate ;
import com.sandy.apps.dcs.util.DCDUtility ;

/**
 * This factory is used to produce the appropriate Component. This is a
 * singleton class .
 */
public class ComponentFactory {

    private static ComponentFactory instance ;

    private ComponentFactory() {

    }

    public static ComponentFactory getInstance() {

        if( instance == null ) instance = new ComponentFactory() ;
        return ( instance ) ;
    }

    // NOTE : THESE CREATOR METHODS CREATE A NEW DCDCOMPONENT AND TAKE CARE
    // TO ADD THE SAME IN THE CHAIN OF RESPONSIBILITY.

    // This methods queries the DCDUtility for the current status
    // of the toggle buttons and returns an appropriate DCDComponent.
    public static DCDComponent getDCDComponent( String componentIdentifier ) {

        return ( null ) ;

    }

    public DCDConnector getDCDConnector() {

        DCDConnector connector = new DCDConnector( DCDUtility.getInstance()
                .getChainHead() ) ;
        DCDUtility.getInstance().addLinkToChain( connector ) ;
        return ( connector ) ;

    }

    public DCDConnector getDCDConnector( Point2D startPoint ) {

        DCDConnector connector = new DCDConnector( DCDUtility.getInstance()
                .getChainHead() ) ;
        DCDUtility.getInstance().addLinkToChain( connector ) ;
        connector.setStartPoint( startPoint ) ;
        return ( connector ) ;

    }

    // This factory method is used if a branch connector needs to be
    // created.
    public DCDConnector getDCDConnector( DCDConnector parent ) {

        DCDConnector connector = new DCDConnector( DCDUtility.getInstance()
                .getChainHead(), parent ) ;
        DCDUtility.getInstance().addLinkToChain( connector ) ;
        return ( connector ) ;

    }

    private DCDANDGate getDCDANDGate( int numInputGates ) {

        DCDANDGate gate = null ;
        try {
            gate = new DCDANDGate( DCDUtility.getInstance().getChainHead(),
                    numInputGates ) ;
        }
        catch( Exception e ) {

        }
        DCDUtility.getInstance().addLinkToChain( gate ) ;
        return ( gate ) ;

    }

    private DCDNANDGate getDCDNANDGate( int numInputGates ) {

        DCDNANDGate gate = null ;
        try {
            gate = new DCDNANDGate( DCDUtility.getInstance().getChainHead(),
                    numInputGates ) ;
        }
        catch( Exception e ) {

        }
        DCDUtility.getInstance().addLinkToChain( gate ) ;
        return ( gate ) ;
    }

    private DCDNOTGate getDCDNOTGate() {

        DCDNOTGate gate = new DCDNOTGate( DCDUtility.getInstance()
                .getChainHead() ) ;
        DCDUtility.getInstance().addLinkToChain( gate ) ;
        return ( gate ) ;
    }

    private DCDSteadyInputGate getDCDSteadyInputGate() {

        DCDSteadyInputGate gate = new DCDSteadyInputGate( DCDUtility
                .getInstance().getChainHead() ) ;
        DCDUtility.getInstance().addLinkToChain( gate ) ;
        return ( gate ) ;
    }

    private DCDPulseInputGate getDCDPulseInputGate() {

        DCDPulseInputGate gate = new DCDPulseInputGate( DCDUtility
                .getInstance().getChainHead() ) ;
        DCDUtility.getInstance().addLinkToChain( gate ) ;
        return ( gate ) ;
    }

    private DCDClock getDCDClock() {

        DCDClock gate = new DCDClock( DCDUtility.getInstance().getChainHead() ) ;
        DCDUtility.getInstance().addLinkToChain( gate ) ;
        return ( gate ) ;
    }

    private DCDORGate getDCDORGate( int numInputGates ) {

        DCDORGate gate = null ;
        try {
            gate = new DCDORGate( DCDUtility.getInstance().getChainHead(),
                    numInputGates ) ;
        }
        catch( Exception e ) {
        }

        DCDUtility.getInstance().addLinkToChain( gate ) ;
        return ( gate ) ;
    }

    private DCDXORGate getDCDXORGate( int numInputGates ) {

        DCDXORGate gate = null ;
        try {
            gate = new DCDXORGate( DCDUtility.getInstance().getChainHead(),
                    numInputGates ) ;
        }
        catch( Exception e ) {
        }
        DCDUtility.getInstance().addLinkToChain( gate ) ;
        return ( gate ) ;
    }

    private DCDLED getDCDLED() {

        DCDLED gate = new DCDLED( DCDUtility.getInstance().getChainHead() ) ;
        DCDUtility.getInstance().addLinkToChain( gate ) ;
        return ( gate ) ;
    }

    private DCDJKFlipFlop getDCDJKFlipFlop() {

        DCDJKFlipFlop flipFlop = new DCDJKFlipFlop( DCDUtility.getInstance()
                .getChainHead() ) ;
        DCDUtility.getInstance().addLinkToChain( flipFlop ) ;
        return ( flipFlop ) ;
    }

    private DCDRSFlipFlop getDCDRSFlipFlop() {

        DCDRSFlipFlop flipFlop = new DCDRSFlipFlop( DCDUtility.getInstance()
                .getChainHead() ) ;
        DCDUtility.getInstance().addLinkToChain( flipFlop ) ;
        return ( flipFlop ) ;
    }

    private DCDTFlipFlop getDCDTFlipFlop() {

        DCDTFlipFlop flipFlop = new DCDTFlipFlop( DCDUtility.getInstance()
                .getChainHead() ) ;
        DCDUtility.getInstance().addLinkToChain( flipFlop ) ;
        return ( flipFlop ) ;
    }

    private DCDDFlipFlop getDCDDFlipFlop() {

        DCDDFlipFlop flipFlop = new DCDDFlipFlop( DCDUtility.getInstance()
                .getChainHead() ) ;
        DCDUtility.getInstance().addLinkToChain( flipFlop ) ;
        return ( flipFlop ) ;
    }

    private DCDSevenSegmentDisplay getSevenSegmentDisplay() {

        DCDSevenSegmentDisplay sseg = new DCDSevenSegmentDisplay( DCDUtility
                .getInstance().getChainHead() ) ;
        DCDUtility.getInstance().addLinkToChain( sseg ) ;
        return ( sseg ) ;
    }

    public DCDGate getDCDGate( String gateName ) {

        if( gateName.equals( "AND" ) ) return ( getDCDANDGate( 2 ) ) ;
        else if( gateName.equals( "NAND" ) ) {
            return ( getDCDNANDGate( 2 ) ) ;
        }
        else if( gateName.equals( "NOT" ) ) {
            return ( getDCDNOTGate() ) ;
        }
        else if( gateName.equals( "STEADY_INPUT" ) ) {
            return ( getDCDSteadyInputGate() ) ;
        }
        else if( gateName.equals( "PULSE_INPUT" ) ) {
            return ( getDCDPulseInputGate() ) ;
        }
        else if( gateName.equals( "CLOCK" ) ) {
            return ( getDCDClock() ) ;
        }
        else if( gateName.equals( "LED" ) ) {
            return ( getDCDLED() ) ;
        }
        else if( gateName.equals( "OR" ) ) {
            return ( getDCDORGate( 2 ) ) ;
        }
        else if( gateName.equals( "XOR" ) ) {
            return ( getDCDXORGate( 2 ) ) ;
        }
        else if( gateName.equals( "JKFLIPFLOP" ) ) {
            return ( getDCDJKFlipFlop() ) ;
        }
        else if( gateName.equals( "TFLIPFLOP" ) ) {
            return ( getDCDTFlipFlop() ) ;
        }
        else if( gateName.equals( "DFLIPFLOP" ) ) {
            return ( getDCDDFlipFlop() ) ;
        }
        else if( gateName.equals( "RSFLIPFLOP" ) ) {
            return ( getDCDRSFlipFlop() ) ;
        }
        else if( gateName.equals( "SEVENSEGMENTDISPLAY" ) ) { return ( getSevenSegmentDisplay() ) ; }
        return ( null ) ;
    }

    public DCDGate getDCDGate( String gateName, int numInputGates ) {

        if( gateName.equals( "AND" ) ) return ( getDCDANDGate( numInputGates ) ) ;
        else if( gateName.equals( "NAND" ) ) {
            return ( getDCDNANDGate( numInputGates ) ) ;
        }
        else if( gateName.equals( "NOT" ) ) {
            return ( getDCDNOTGate() ) ;
        }
        else if( gateName.equals( "STEADY_INPUT" ) ) {
            return ( getDCDSteadyInputGate() ) ;
        }
        else if( gateName.equals( "LED" ) ) {
            return ( getDCDLED() ) ;
        }
        else if( gateName.equals( "OR" ) ) {
            return ( getDCDORGate( numInputGates ) ) ;
        }
        else if( gateName.equals( "XOR" ) ) { return ( getDCDXORGate( numInputGates ) ) ; }
        return ( null ) ;
    }
}
