// Source file: d:/sandy/dcd/DCD/component/model/DCDComponentModel.java

package com.sandy.apps.dcs.component.model ;

import java.io.Serializable ;
import java.util.Observable ;
import java.util.Observer ;

import com.sandy.apps.dcs.component.DCDComponent ;

// This is the model of the compoent .. this is the one
// which is going to store the truth table and other non-displayable
// information.
public abstract class DCDComponentModel extends Observable implements Observer,
        Serializable {

    // The DCDComponent to which this model belongs to.
    private DCDComponent component ;

    public DCDComponentModel() {

        this.component = null ;
    }

    public DCDComponentModel(DCDComponent component) {

        this.component = component ;
    }

    public void setDCDComponent(DCDComponent component) {

        this.component = component ;
    }

}
