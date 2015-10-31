package com.sandy.apps.dcs.component ;

import java.util.* ;
import java.awt.event.* ;
import java.io.* ;

import com.sandy.apps.dcs.component.model.* ;
import com.sandy.apps.dcs.component.view.* ;
import com.sandy.apps.dcs.cor.* ;
import com.sandy.apps.dcs.exception.* ;

public class DCDNANDGate extends DCDGate implements Serializable {

    private DCDNANDGateUI    ui ;

    private DCDNANDGateModel model ;

    private static int       id = 0 ;

    public DCDNANDGate( Chain c, DCDNANDGateModel model, DCDNANDGateUI ui ) {

        super( c, model, ui ) ;
        this.ui = ui ;
        this.model = model ;
    }

    public DCDNANDGate( Chain c ) {

        super( c, null, null ) ;
        super.numInputPorts = 2 ;
        super.numOutputPorts = 1 ;
        ui = new DCDNANDGateUI( this ) ;
        model = new DCDNANDGateModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public DCDNANDGate( int numInputPorts ) {

        super() ;
        super.numInputPorts = numInputPorts ;
        super.numOutputPorts = 1 ;
        ui = new DCDNANDGateUI( this ) ;
        model = new DCDNANDGateModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public DCDNANDGate( Chain c, int numInputPorts ) throws DCDGateException {

        super( c, null, null ) ;
        if( numInputPorts < 2 ) throw new DCDGateException(
                "Number of input gates in an AND gate can't be less than 2" ) ;

        super.numInputPorts = numInputPorts ;
        super.numOutputPorts = 1 ;
        ui = new DCDNANDGateUI( this ) ;
        model = new DCDNANDGateModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public String getType() {

        return "NAND" ;
    }
}