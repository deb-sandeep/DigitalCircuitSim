package com.sandy.apps.dcs.component ;

import java.io.Serializable ;

import com.sandy.apps.dcs.component.model.DCDGateModel ;
import com.sandy.apps.dcs.component.model.DCDJKFlipFlopModel ;
import com.sandy.apps.dcs.component.view.DCDGateUI ;
import com.sandy.apps.dcs.component.view.DCDJKFlipFlopUI ;
import com.sandy.apps.dcs.util.Chain ;

public class DCDJKFlipFlop extends DCDFlipFlop implements Serializable {

    private DCDJKFlipFlopUI    ui ;

    private DCDJKFlipFlopModel model ;

    // I don't know when this constructor can be used.
    public DCDJKFlipFlop( Chain c, DCDJKFlipFlopModel model, DCDJKFlipFlopUI ui ) {

        super( c, model, ui ) ;
        this.ui = ui ;
        this.model = model ;
    }

    public DCDJKFlipFlop( Chain c ) {

        super( c, null, null ) ;
        numInputPorts = 3 ;
        numOutputPorts = 2 ;
        setModel( new DCDJKFlipFlopModel( this ) ) ;
        setUI( new DCDJKFlipFlopUI( this ) ) ;
    }

    public DCDJKFlipFlop() {

        super() ;
        ui = new DCDJKFlipFlopUI( this ) ;
        model = new DCDJKFlipFlopModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public String getType() {

        return "JKFLIPFLOP" ;
    }

    public void setModel( DCDGateModel model ) {

        if( model instanceof DCDJKFlipFlopModel ) {
            super.setModel( model ) ;
            this.model = (DCDJKFlipFlopModel) model ;
        }
    }

    public void setUI( DCDGateUI ui ) {

        if( ui instanceof DCDJKFlipFlopUI ) {
            super.setUI( ui ) ;
            this.ui = (DCDJKFlipFlopUI) ui ;
        }
    }
}