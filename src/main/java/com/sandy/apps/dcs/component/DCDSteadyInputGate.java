package com.sandy.apps.dcs.component ;

import java.io.Serializable ;

import com.sandy.apps.dcs.component.model.DCDGateModel ;
import com.sandy.apps.dcs.component.model.DCDSteadyInputGateModel ;
import com.sandy.apps.dcs.component.view.DCDComponentUI ;
import com.sandy.apps.dcs.component.view.DCDGateUI ;
import com.sandy.apps.dcs.component.view.DCDSteadyInputGateUI ;
import com.sandy.apps.dcs.util.Chain ;
import com.sandy.apps.dcs.util.DCDUtility ;

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