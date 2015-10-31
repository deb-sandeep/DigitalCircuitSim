package com.sandy.apps.dcs.component ;

import java.io.Serializable ;

import com.sandy.apps.dcs.component.model.DCDNOTGateModel ;
import com.sandy.apps.dcs.component.view.DCDNOTGateUI ;
import com.sandy.apps.dcs.util.Chain ;

public class DCDNOTGate extends DCDGate implements Serializable {

    private DCDNOTGateUI    ui ;

    private DCDNOTGateModel model ;

    private static int      id = 0 ;

    public DCDNOTGate( Chain c, DCDNOTGateModel model, DCDNOTGateUI ui ) {

        super( c, model, ui ) ;
        this.ui = ui ;
        this.model = model ;
    }

    public DCDNOTGate( Chain c ) {

        super( c, null, null ) ;
        ui = new DCDNOTGateUI( this ) ;
        model = new DCDNOTGateModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public DCDNOTGate() {

        super() ;
        ui = new DCDNOTGateUI( this ) ;
        model = new DCDNOTGateModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public String getType() {

        return "NOT" ;
    }

}