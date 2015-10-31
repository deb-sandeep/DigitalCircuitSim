package com.sandy.apps.dcs.ui ;

import java.awt.* ;

import com.sandy.apps.dcs.component.* ;
import com.sandy.apps.dcs.cor.Chain ;

public interface CircuitController {

    public void addLinkToChain( Chain c ) ;

    public void addInputGate( DCDInputGate gate ) ;

    public void addPulseInputGate( DCDPulseInputGate gate ) ;

    public void addClock( DCDClock clock ) ;

    public void addFlipFlop( DCDFlipFlop flipFlop ) ;

    public void addIC( DCDIC ic ) ;

    public void initiatePainting( Graphics c ) ;

    public void setCircuitDescription( String str ) ;
}