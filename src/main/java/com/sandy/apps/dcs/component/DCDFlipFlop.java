package com.sandy.apps.dcs.component ;

import java.io.Serializable ;

import com.sandy.apps.dcs.component.model.DCDFlipFlopModel ;
import com.sandy.apps.dcs.component.model.DCDGateModel ;
import com.sandy.apps.dcs.component.view.DCDFlipFlopUI ;
import com.sandy.apps.dcs.component.view.DCDGateUI ;
import com.sandy.apps.dcs.cor.Chain ;

public abstract class DCDFlipFlop extends DCDGate implements Serializable {

    private DCDFlipFlopUI    ui ;

    private DCDFlipFlopModel model ;

    // I don't know when this constructor can be used.
    public DCDFlipFlop( Chain c, DCDFlipFlopModel model, DCDFlipFlopUI ui ) {

        super( c, model, ui ) ;
        this.ui = ui ;
        this.model = model ;
    }

    public DCDFlipFlop() {

        super() ;
    }

    // I am atpresent using this constructor.
    // Using this constructor creates a 2 input and gate.
    // For creating multiple input AND gates use the constructor below.
    public DCDFlipFlop( Chain c ) {

        super( c, null, null ) ;
        super.numInputPorts = 2 ;
        super.numOutputPorts = 1 ;
    }

    public void setModel( DCDGateModel model ) {

        if( model instanceof DCDFlipFlopModel ) {
            super.setModel( model ) ;
            this.model = (DCDFlipFlopModel) model ;
        }
    }

    public void initialize() {

        model.initialize() ;
    }

    public void setUI( DCDGateUI ui ) {

        if( ui instanceof DCDFlipFlopUI ) {
            super.setUI( ui ) ;
            this.ui = (DCDFlipFlopUI) ui ;
        }
    }

    public void transferChargeToBuffer() {

        model.transferChargeToBuffer() ;
    }

}