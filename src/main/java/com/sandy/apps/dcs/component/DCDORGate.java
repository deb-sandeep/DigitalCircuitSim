package com.sandy.apps.dcs.component ;

import java.io.Serializable ;

import com.sandy.apps.dcs.component.model.DCDGateModel ;
import com.sandy.apps.dcs.component.model.DCDORGateModel ;
import com.sandy.apps.dcs.component.view.DCDGateUI ;
import com.sandy.apps.dcs.component.view.DCDORGateUI ;
import com.sandy.apps.dcs.util.Chain ;
import com.sandy.apps.dcs.util.DCDGateException ;

public class DCDORGate extends DCDGate implements Serializable {

    private DCDORGateUI    ui ;

    private DCDORGateModel model ;

    private static int     id = 0 ;

    public DCDORGate( Chain c, DCDORGateModel model, DCDORGateUI ui ) {

        super( c, model, ui ) ;
        this.ui = ui ;
        this.model = model ;
    }

    public DCDORGate( Chain c ) {

        super( c, null, null ) ;
        super.numInputPorts = 2 ;
        super.numOutputPorts = 1 ;
        ui = new DCDORGateUI( this ) ;
        model = new DCDORGateModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public DCDORGate( int numInputPorts ) {

        super() ;
        super.numInputPorts = numInputPorts ;
        super.numOutputPorts = 1 ;
        ui = new DCDORGateUI( this ) ;
        model = new DCDORGateModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public DCDORGate( Chain c, int numInputPorts ) throws DCDGateException {

        super( c, null, null ) ;
        if( numInputPorts < 2 ) throw new DCDGateException(
                "Number of input gates in an OR gate can't be less than 2" ) ;

        super.numInputPorts = numInputPorts ;
        super.numOutputPorts = 1 ;
        ui = new DCDORGateUI( this ) ;
        model = new DCDORGateModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public String getType() {

        return "OR" ;
    }

    public void setModel( DCDGateModel model ) {

        if( model instanceof DCDORGateModel ) {
            super.setModel( model ) ;
            this.model = (DCDORGateModel) model ;
        }
    }

    public void setUI( DCDGateUI ui ) {

        if( ui instanceof DCDORGateUI ) {
            super.setUI( ui ) ;
            this.ui = (DCDORGateUI) ui ;
        }
    }
}