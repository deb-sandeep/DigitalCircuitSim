package com.sandy.apps.dcs.component ;

import java.io.Serializable ;

import com.sandy.apps.dcs.component.model.DCDGateModel ;
import com.sandy.apps.dcs.component.model.DCDInputGateModel ;
import com.sandy.apps.dcs.component.model.Port ;
import com.sandy.apps.dcs.component.model.PortCollection ;
import com.sandy.apps.dcs.component.view.DCDGateUI ;
import com.sandy.apps.dcs.component.view.DCDInputGateUI ;
import com.sandy.apps.dcs.util.Chain ;

// This class doesnot implement the getType method hence abstract
public abstract class DCDInputGate extends DCDGate implements Serializable {

    private DCDInputGateUI    ui ;

    private DCDInputGateModel model ;

    private static int        id = 0 ;

    protected DCDInputGate() {

    }

    public DCDInputGate( Chain c, DCDInputGateModel model, DCDInputGateUI ui ) {

        super( c, model, ui ) ;
        this.ui = ui ;
        this.model = model ;
    }

    public abstract void fire() ;

    public Port getTaggablePort() {

        PortCollection portCollection = model.getOutputPortCollection() ;
        return portCollection.getPort( 0 ) ;
    }

    public void setModel( DCDGateModel model ) {

        super.setModel( model ) ;
        this.model = (DCDInputGateModel) model ;
    }

    public void setUI( DCDGateUI ui ) {

        super.setUI( ui ) ;
        this.ui = (DCDInputGateUI) ui ;
    }

}