package com.sandy.apps.dcs.component ;

import java.util.* ;
import java.awt.event.* ;
import java.io.* ;

import com.sandy.apps.dcs.component.model.* ;
import com.sandy.apps.dcs.component.view.* ;
import com.sandy.apps.dcs.cor.* ;
import com.sandy.apps.dcs.exception.* ;

public class DCDXORGate extends DCDGate implements Serializable {

    private DCDXORGateUI    ui ;

    private DCDXORGateModel model ;

    private static int      id = 0 ;

    public DCDXORGate( Chain c, DCDXORGateModel model, DCDXORGateUI ui ) {

        super( c, model, ui ) ;
        this.ui = ui ;
        this.model = model ;
    }

    public DCDXORGate( Chain c ) {

        super( c, null, null ) ;
        super.numInputPorts = 2 ;
        super.numOutputPorts = 1 ;
        ui = new DCDXORGateUI( this ) ;
        model = new DCDXORGateModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public DCDXORGate( Chain c, int numInputPorts ) throws DCDGateException {

        super( c, null, null ) ;
        if( numInputPorts < 2 ) throw new DCDGateException(
                "Number of input gates in an AND gate can't be less than 2" ) ;

        super.numInputPorts = numInputPorts ;
        super.numOutputPorts = 1 ;
        ui = new DCDXORGateUI( this ) ;
        model = new DCDXORGateModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public DCDXORGate( int numInputPorts ) {

        super() ;
        super.numInputPorts = numInputPorts ;
        super.numOutputPorts = 1 ;
        ui = new DCDXORGateUI( this ) ;
        model = new DCDXORGateModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public String getType() {

        return "XOR" ;
    }

    public void setModel( DCDGateModel model ) {

        if( model instanceof DCDXORGateModel ) {
            super.setModel( model ) ;
            this.model = (DCDXORGateModel) model ;
        }
    }

    public void setUI( DCDGateUI ui ) {

        if( ui instanceof DCDXORGateUI ) {
            super.setUI( ui ) ;
            this.ui = (DCDXORGateUI) ui ;
        }
    }

}