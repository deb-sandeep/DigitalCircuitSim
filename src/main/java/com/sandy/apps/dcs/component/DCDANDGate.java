package com.sandy.apps.dcs.component ;

import java.io.Serializable ;

import com.sandy.apps.dcs.component.model.DCDANDGateModel ;
import com.sandy.apps.dcs.component.model.DCDGateModel ;
import com.sandy.apps.dcs.component.view.DCDANDGateUI ;
import com.sandy.apps.dcs.component.view.DCDGateUI ;
import com.sandy.apps.dcs.util.Chain ;
import com.sandy.apps.dcs.util.DCDGateException ;

public class DCDANDGate extends DCDGate implements Serializable {

    private DCDANDGateUI    ui ;

    private DCDANDGateModel model ;

    private static int      id = 0 ;

    // I don't know when this constructor can be used.
    public DCDANDGate( Chain c, DCDANDGateModel model, DCDANDGateUI ui ) {

        super( c, model, ui ) ;
        this.ui = ui ;
        this.model = model ;
    }

    // I am atpresent using this constructor.
    // Using this constructor creates a 2 input and gate.
    // For creating multiple input AND gates use the constructor below.
    public DCDANDGate( Chain c ) {

        super( c, null, null ) ;
        super.numInputPorts = 2 ;
        super.numOutputPorts = 1 ;
        ui = new DCDANDGateUI( this ) ;
        model = new DCDANDGateModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public DCDANDGate( Chain c, int numInputPorts ) throws DCDGateException {

        super( c, null, null ) ;
        if( numInputPorts < 2 ) throw new DCDGateException(
                "Number of input gates in an AND gate can't be less than 2" ) ;

        super.numInputPorts = numInputPorts ;
        super.numOutputPorts = 1 ;
        ui = new DCDANDGateUI( this ) ;
        model = new DCDANDGateModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public DCDANDGate( int numInputPorts ) {

        super() ;
        super.numInputPorts = numInputPorts ;
        super.numOutputPorts = 1 ;
        ui = new DCDANDGateUI( this ) ;
        model = new DCDANDGateModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public String getType() {

        return "AND" ;
    }

    public void setModel( DCDGateModel model ) {

        if( model instanceof DCDANDGateModel ) {
            super.setModel( model ) ;
            this.model = (DCDANDGateModel) model ;
        }
    }

    public void setUI( DCDGateUI ui ) {

        if( ui instanceof DCDANDGateUI ) {
            super.setUI( ui ) ;
            this.ui = (DCDANDGateUI) ui ;
        }
    }
}