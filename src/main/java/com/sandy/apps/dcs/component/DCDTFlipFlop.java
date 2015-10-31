package com.sandy.apps.dcs.component ;

import java.io.Serializable ;

import com.sandy.apps.dcs.component.model.DCDGateModel ;
import com.sandy.apps.dcs.component.model.DCDTFlipFlopModel ;
import com.sandy.apps.dcs.component.view.DCDGateUI ;
import com.sandy.apps.dcs.component.view.DCDTFlipFlopUI ;
import com.sandy.apps.dcs.util.Chain ;

public class DCDTFlipFlop extends DCDFlipFlop implements Serializable {

    private DCDTFlipFlopUI    ui ;

    private DCDTFlipFlopModel model ;

    // I don't know when this constructor can be used.
    public DCDTFlipFlop( Chain c, DCDTFlipFlopModel model, DCDTFlipFlopUI ui ) {

        super( c, model, ui ) ;
        this.ui = ui ;
        this.model = model ;
    }

    public DCDTFlipFlop( Chain c ) {

        super( c, null, null ) ;
        numInputPorts = 3 ;
        numOutputPorts = 2 ;
        setModel( new DCDTFlipFlopModel( this ) ) ;
        setUI( new DCDTFlipFlopUI( this ) ) ;
    }

    public DCDTFlipFlop() {

        super() ;
        ui = new DCDTFlipFlopUI( this ) ;
        model = new DCDTFlipFlopModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public String getType() {

        return "TFLIPFLOP" ;
    }

    public void setModel( DCDGateModel model ) {

        if( model instanceof DCDTFlipFlopModel ) {
            super.setModel( model ) ;
            this.model = (DCDTFlipFlopModel) model ;
        }
    }

    public void setUI( DCDGateUI ui ) {

        if( ui instanceof DCDTFlipFlopUI ) {
            super.setUI( ui ) ;
            this.ui = (DCDTFlipFlopUI) ui ;
        }
    }
}