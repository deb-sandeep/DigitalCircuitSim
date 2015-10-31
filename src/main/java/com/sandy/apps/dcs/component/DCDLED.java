package com.sandy.apps.dcs.component ;

import java.io.Serializable ;

import com.sandy.apps.dcs.component.model.DCDLEDModel ;
import com.sandy.apps.dcs.component.model.Port ;
import com.sandy.apps.dcs.component.view.DCDLEDUI ;
import com.sandy.apps.dcs.util.Chain ;
import com.sandy.apps.dcs.util.DCDUtility ;

public class DCDLED extends DCDGate implements Serializable {

    private DCDLEDUI    ui ;

    private DCDLEDModel model ;

    private static int  id = 0 ;

    public DCDLED( Chain c, DCDLEDModel model, DCDLEDUI ui ) {

        super( c, model, ui ) ;
        this.ui = ui ;
        this.model = model ;
    }

    public DCDLED( Chain c ) {

        super( c, null, null ) ;
        super.numInputPorts = 1 ;
        ui = new DCDLEDUI( this ) ;
        model = new DCDLEDModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public DCDLED() {

        super() ;
        super.numInputPorts = 1 ;
        ui = new DCDLEDUI( this ) ;
        model = new DCDLEDModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public boolean getState() {

        return ( model.getState() ) ;
    }

    public void setState( boolean b ) {

        model.setState( b ) ;
        ui.setState( b, DCDUtility.getInstance().getGraphics() ) ;
    }

    public String getType() {

        return "LED" ;
    }

    public Port getTaggablePort() {

        return model.getInputPortCollection().getPort( 0 ) ;
    }

}