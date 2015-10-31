package com.sandy.apps.dcs.component ;

import java.util.* ;
import java.awt.event.* ;
import java.awt.geom.* ;
import java.io.* ;

import com.sandy.apps.dcs.common.* ;
import com.sandy.apps.dcs.component.model.* ;
import com.sandy.apps.dcs.component.view.* ;
import com.sandy.apps.dcs.cor.* ;

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