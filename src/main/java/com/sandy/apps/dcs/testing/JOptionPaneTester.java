package com.sandy.apps.dcs.testing ;

import javax.swing.* ;
import java.awt.event.* ;

public class JOptionPaneTester {

    public static void main( String[] s ) {

        Object[] possibleValues = { "LET ME SELECT", "First", "Second", "Third" } ;
        Object selectedValue = JOptionPane.showInputDialog( null, "Choose one",
                "Input", JOptionPane.INFORMATION_MESSAGE, null, possibleValues,
                possibleValues[0] ) ;
        System.out.println( selectedValue ) ;
    }
}