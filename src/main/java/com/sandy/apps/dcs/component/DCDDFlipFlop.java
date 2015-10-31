package com.sandy.apps.dcs.component ;

import java.io.Serializable ;

import com.sandy.apps.dcs.component.model.DCDDFlipFlopModel ;
import com.sandy.apps.dcs.component.model.DCDGateModel ;
import com.sandy.apps.dcs.component.view.DCDDFlipFlopUI ;
import com.sandy.apps.dcs.component.view.DCDGateUI ;
import com.sandy.apps.dcs.util.Chain ;

public class DCDDFlipFlop extends DCDFlipFlop implements Serializable {

    private DCDDFlipFlopUI    ui ;

    private DCDDFlipFlopModel model ;

    // I don't know when this constructor can be used.
    public DCDDFlipFlop( Chain c, DCDDFlipFlopModel model, DCDDFlipFlopUI ui ) {

        super( c, model, ui ) ;
        this.ui = ui ;
        this.model = model ;
    }

    public DCDDFlipFlop( Chain c ) {

        super( c, null, null ) ;
        numInputPorts = 3 ;
        numOutputPorts = 2 ;
        setModel( new DCDDFlipFlopModel( this ) ) ;
        setUI( new DCDDFlipFlopUI( this ) ) ;
    }

    public DCDDFlipFlop() {

        super() ;
        ui = new DCDDFlipFlopUI( this ) ;
        model = new DCDDFlipFlopModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public String getType() {

        return "DFLIPFLOP" ;
    }

    public void setModel( DCDGateModel model ) {

        if( model instanceof DCDDFlipFlopModel ) {
            super.setModel( model ) ;
            this.model = (DCDDFlipFlopModel) model ;
        }
    }

    public void setUI( DCDGateUI ui ) {

        if( ui instanceof DCDDFlipFlopUI ) {
            super.setUI( ui ) ;
            this.ui = (DCDDFlipFlopUI) ui ;
        }
    }
}