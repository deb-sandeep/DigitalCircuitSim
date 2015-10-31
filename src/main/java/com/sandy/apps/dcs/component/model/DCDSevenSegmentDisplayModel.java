
package com.sandy.apps.dcs.component.model ;

import java.io.Serializable ;
import java.util.BitSet ;
import java.util.Observable ;

import com.sandy.apps.dcs.component.DCDSevenSegmentDisplay ;
import com.sandy.apps.dcs.component.view.DCDSevenSegmentDisplayUI ;

public class DCDSevenSegmentDisplayModel extends DCDGateModel implements
        Serializable {

    private DCDSevenSegmentDisplay gate ;

    private boolean state = false ;

    public DCDSevenSegmentDisplayModel(DCDSevenSegmentDisplay gate) {

        super(gate) ;
        this.gate = gate ;
    }

    // The UI needs to be updated if the model changes.
    public void update(Observable arg0, Object arg1) {

    }

    public void populateTruthTable() {

    }

    // Here the input charge will be of four bits ... we need to convert it to
    // seven bits in order to display. In the model also we will have to
    // optimize the display.. i.e if a segment is presently on.. then there is
    // no need to paint it again... this will reduce the flicker time..
    // also as now we won't be needing a binary to decimal converter IC, this
    // would
    // reduce the processing time further. hehehehh
    public void fire(BitSet inputChargeStatus) {

        // Firing .. have to convert first.
        boolean A = inputChargeStatus.get(0) ;
        boolean B = inputChargeStatus.get(1) ;
        boolean C = inputChargeStatus.get(2) ;
        boolean D = inputChargeStatus.get(3) ;

        boolean a = !(C ^ A) | D | B ;
        boolean b = (!(B ^ A)) | (!C) ;
        boolean c = (!B) | C | A ;
        boolean d = (B & (!A)) | D | ((!C) & B) | ((!C) & (!A))
                | (C & (!B) & A) ;
        boolean e = (B & (!A)) | ((!C) & (!A)) ;
        boolean f = D | ((!B) & (!A)) | (C & (!B)) | (C & (!A)) ;
        boolean g = (C ^ B) | (C & (!A)) | D ;

        BitSet firingCharge = new BitSet(7) ;
        if (a)
            firingCharge.set(0) ;
        if (b)
            firingCharge.set(1) ;
        if (c)
            firingCharge.set(2) ;
        if (d)
            firingCharge.set(3) ;
        if (e)
            firingCharge.set(4) ;
        if (f)
            firingCharge.set(5) ;
        if (g)
            firingCharge.set(6) ;

        ((DCDSevenSegmentDisplayUI) gate.getUI()).display(firingCharge) ;

    }

    public void populatePortCollections() {

        for (int i = 0; i < 4; i++)
            inputPortCollection.addPort(new Port(inputPortCollection, i)) ;
    }

    // // This method is redundant .. an SevenSegmentDisplay can't fire .. can
    // it !!
    // public void fire()
    // {
    // outputPortCollection.getPort(0).activateConnector(state,1);
    // }
}