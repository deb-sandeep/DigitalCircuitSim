package com.sandy.apps.dcs.component ;

import com.sandy.apps.dcs.component.model.DCDGateModel ;
import com.sandy.apps.dcs.component.model.DCDInputGateModel ;
import com.sandy.apps.dcs.component.model.DCDPulseInputGateModel ;
import com.sandy.apps.dcs.component.view.DCDComponentUI ;
import com.sandy.apps.dcs.component.view.DCDGateUI ;
import com.sandy.apps.dcs.component.view.DCDPulseInputGateUI ;
import com.sandy.apps.dcs.util.Chain ;
import com.sandy.apps.dcs.util.DCDUtility ;

public class DCDPulseInputGate extends DCDInputGate implements Runnable {

    private DCDPulseInputGateUI    ui ;

    private DCDPulseInputGateModel model ;

    private static int             id    = 0 ;

    private boolean                state = false ;

    public DCDPulseInputGate( Chain c ) {

        super( c, null, null ) ;
        ui = new DCDPulseInputGateUI( this ) ;
        model = new DCDPulseInputGateModel( this ) ;
        setUI( ui ) ;
        setModel( model ) ;
    }

    public DCDPulseInputGate() {

        super() ;
        ui = new DCDPulseInputGateUI( this ) ;
        model = new DCDPulseInputGateModel( this ) ;
        setUI( ui ) ;
        setModel( model ) ;
    }

    public void run() {

        ui.paint( DCDUtility.getInstance().getGraphics(),
                DCDComponentUI.DELETED ) ;
        state = true ;
        ui.paint( DCDUtility.getInstance().getGraphics(),
                DCDComponentUI.SELECTED ) ;
        model.fire( true ) ;

        try {
            Thread.currentThread().sleep( 500 ) ;
        }
        catch( InterruptedException e ) {
        }

        ui.paint( DCDUtility.getInstance().getGraphics(),
                DCDComponentUI.DELETED ) ;
        state = false ;
        ui.paint( DCDUtility.getInstance().getGraphics(),
                DCDComponentUI.SELECTED ) ;
        model.fire( false ) ;

    }

    public void fire() {

        Thread t = new Thread( this ) ;
        t.start() ;
    }

    public void fireCurrentState() {

        model.getOutputPortCollection().getPort( 0 )
                .activateConnector( state, DCDInputGateModel.STEADY ) ;
    }

    public String getType() {

        return "PULSE_INPUT" ;
    }

    public void setModel( DCDGateModel model ) {

        if( model instanceof DCDPulseInputGateModel ) {
            super.setModel( model ) ;
            this.model = (DCDPulseInputGateModel) model ;
        }
    }

    public void setUI( DCDGateUI ui ) {

        if( ui instanceof DCDPulseInputGateUI ) {
            super.setUI( ui ) ;
            this.ui = (DCDPulseInputGateUI) ui ;
        }
    }

    public boolean getState() {

        return this.state ;
    }

    public void setState( boolean state ) {

        this.state = state ;
    }
}