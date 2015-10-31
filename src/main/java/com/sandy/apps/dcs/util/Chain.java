// Source file: d:/sandy/dcd/DCD/cor/Chain.java

package com.sandy.apps.dcs.util ;

import java.awt.Graphics ;
import java.awt.event.MouseEvent ;

/**
 * This interface in realized by all the DCD components . This gives rise to the
 * chain of responsibility pattern.
 */
public interface Chain {

    public Chain getNextLink() ;

    public void setNextLink( Chain c ) ;

    public boolean isSelected( MouseEvent event ) ;

    public boolean handleMouseOver( MouseEvent e ) ;

    public void paint( Graphics g ) ;

    // This will return an node of the XML file corrosponding to
    // the gate.
    public String getSavingInformation() ;
}
