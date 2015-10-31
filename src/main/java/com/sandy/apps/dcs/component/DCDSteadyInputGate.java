package com.sandy.apps.dcs.component ;

import java.util.* ;
import java.awt.event.* ;
import java.io.* ;

import com.sandy.apps.dcs.common.* ;
import com.sandy.apps.dcs.component.model.* ;
import com.sandy.apps.dcs.component.view.* ;
import com.sandy.apps.dcs.cor.* ;

public class DCDSteadyInputGate extends DCDInputGate implements Serializable {

    private DCDSteadyInputGateUI    ui ;

    private DCDSteadyInputGateModel model ;

    private static int              id = 0 ;

    public DCDSteadyInputGate( Chain c ) {

        super( c, null, null ) ;
        ui = new DCDSteadyInputGateUI( this ) ;
        model = new DCDSteadyInputGateModel( this ) ;
        setUI( ui ) ;
        setModel( model ) ;
    }

    public DCDSteadyInputGate() {

        super() ;
        ui = new DCDSteadyInputGateUI( this ) ;
        model = new DCDSteadyInputGateModel( this ) ;
        setUI( ui ) ;
        setModel( model ) ;
    }

    public boolean getState() {

        return ( model.getState() ) ;
    }

    public void setState( boolean b ) {

        model.setState( b ) ;
    }

    public void toggleState() {

        ui.paint( DCDUtility.getInstance().getGraphics(),
                DCDComponentUI.DELETED ) ;
        model.toggleState() ;
        ui.paint( DCDUtility.getInstance().getGraphics(),
                DCDComponentUI.SELECTED ) ;
        fire() ;
    }

    public void fire() {

        model.fire() ;
    }

    public String getType() {

        return "STEADY_INPUT" ;
    }

    public void setModel( DCDGateModel model ) {

        if( model instanceof DCDSteadyInputGateModel ) {
            super.setModel( model ) ;
            this.model = (DCDSteadyInputGateModel) model ;
        }
    }

    public void setUI( DCDGateUI ui ) {

        if( ui instanceof DCDSteadyInputGateUI ) {
            super.setUI( ui ) ;
            this.ui = (DCDSteadyInputGateUI) ui ;
        }
    }

}