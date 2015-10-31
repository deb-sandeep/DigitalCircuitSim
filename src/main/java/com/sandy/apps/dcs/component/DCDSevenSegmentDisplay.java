package com.sandy.apps.dcs.component ;

import java.util.* ;
import java.awt.event.* ;
import java.awt.geom.* ;
import java.io.* ;

import com.sandy.apps.dcs.common.* ;
import com.sandy.apps.dcs.component.model.* ;
import com.sandy.apps.dcs.component.view.* ;
import com.sandy.apps.dcs.cor.* ;

public class DCDSevenSegmentDisplay extends DCDGate implements Serializable {

    private DCDSevenSegmentDisplayUI    ui ;

    private DCDSevenSegmentDisplayModel model ;

    private static int                  id = 0 ;

    public DCDSevenSegmentDisplay( Chain c, DCDSevenSegmentDisplayModel model,
            DCDSevenSegmentDisplayUI ui ) {

        super( c, model, ui ) ;
        this.ui = ui ;
        this.model = model ;
    }

    public DCDSevenSegmentDisplay( Chain c ) {

        super( c, null, null ) ;
        super.numInputPorts = 1 ;
        ui = new DCDSevenSegmentDisplayUI( this ) ;
        model = new DCDSevenSegmentDisplayModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public DCDSevenSegmentDisplay() {

        super() ;
        super.numInputPorts = 1 ;
        ui = new DCDSevenSegmentDisplayUI( this ) ;
        model = new DCDSevenSegmentDisplayModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public String getType() {

        return "SEVEN_SEGMENT_DISPLAY" ;
    }
}