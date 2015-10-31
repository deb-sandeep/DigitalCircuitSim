package com.sandy.apps.dcs.ui ;

import java.awt.Graphics ;

import com.sandy.apps.dcs.component.DCDClock ;
import com.sandy.apps.dcs.component.DCDFlipFlop ;
import com.sandy.apps.dcs.component.DCDIC ;
import com.sandy.apps.dcs.component.DCDInputGate ;
import com.sandy.apps.dcs.component.DCDPulseInputGate ;
import com.sandy.apps.dcs.util.Chain ;

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