package com.sandy.apps.dcs.component ;

import java.io.Serializable ;

import com.sandy.apps.dcs.component.model.DCDGateModel ;
import com.sandy.apps.dcs.component.model.DCDRSFlipFlopModel ;
import com.sandy.apps.dcs.component.view.DCDGateUI ;
import com.sandy.apps.dcs.component.view.DCDRSFlipFlopUI ;
import com.sandy.apps.dcs.cor.Chain ;

public class DCDRSFlipFlop extends DCDFlipFlop implements Serializable {

    private DCDRSFlipFlopUI    ui ;

    private DCDRSFlipFlopModel model ;

    // I don't know when this constructor can be used.
    public DCDRSFlipFlop( Chain c, DCDRSFlipFlopModel model, DCDRSFlipFlopUI ui ) {

        super( c, model, ui ) ;
        this.ui = ui ;
        this.model = model ;
    }

    public DCDRSFlipFlop( Chain c ) {

        super( c, null, null ) ;
        numInputPorts = 3 ;
        numOutputPorts = 2 ;
        setModel( new DCDRSFlipFlopModel( this ) ) ;
        setUI( new DCDRSFlipFlopUI( this ) ) ;
    }

    public DCDRSFlipFlop() {

        super() ;
        ui = new DCDRSFlipFlopUI( this ) ;
        model = new DCDRSFlipFlopModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public String getType() {

        return "RSFLIPFLOP" ;
    }

    public void setModel( DCDGateModel model ) {

        if( model instanceof DCDRSFlipFlopModel ) {
            super.setModel( model ) ;
            this.model = (DCDRSFlipFlopModel) model ;
        }
    }

    public void setUI( DCDGateUI ui ) {

        if( ui instanceof DCDRSFlipFlopUI ) {
            super.setUI( ui ) ;
            this.ui = (DCDRSFlipFlopUI) ui ;
        }
    }
}