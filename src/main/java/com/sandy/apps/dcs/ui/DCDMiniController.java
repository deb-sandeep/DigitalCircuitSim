// This module will act as the mini controllers for the IC's.
// Actually this is a patch up for the design flaw.... DCDController
// if properly coded could have been used in this place. Anyway

package com.sandy.apps.dcs.ui ;

import java.awt.Graphics ;
import java.util.Vector ;

import com.sandy.apps.dcs.util.Chain ;

public class DCDMiniController {

    private Chain  chainHead ;

    private Vector flipFlops = new Vector() ;

    // This function initiates painting of all the components in the
    // screen. The logic that it follows is that it traverses the
    // chain of responsibility and asks the individual components
    // to draw themselves on the screen.
    public void initiatePainting( Graphics g ) {

        Chain c = chainHead ;
        while( c != null ) {
            c.paint( g ) ;
            c = c.getNextLink() ;
        }
    }

    public void initialize() {

    }

}