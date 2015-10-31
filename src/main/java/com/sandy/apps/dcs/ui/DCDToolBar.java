// Source file: d:/sandy/dcd/DCD/ui/DCDToolBar.java

package com.sandy.apps.dcs.ui ;

import javax.swing.* ;

import com.sandy.apps.dcs.factory.* ;

import java.util.* ;
import java.awt.* ;

// This will contain the gate and other toggle buttons.
// This will be called upon by the ComponentFactory to get the
// present selected toggle button.
public class DCDToolBar extends JPanel {

    private ButtonGroup   buttonGroup = new ButtonGroup() ;

    private JToggleButton andButton ;

    private JToggleButton orButton ;

    private JToggleButton xorButton ;

    private JToggleButton nandButton ;

    private JToggleButton notButton ;

    private JToggleButton steadyInputButton ;

    private JToggleButton ledButton ;

    private JToggleButton connectorButton ;

    private JToggleButton selectorButton ;

    private JToggleButton clockButton ;

    private JToggleButton jkFlipFlopButton ;

    private JToggleButton rsFlipFlopButton ;

    private JToggleButton tFlipFlopButton ;

    private JToggleButton dFlipFlopButton ;

    private JToggleButton icButton ;

    private JToggleButton sevenSegButton ;

    private JToggleButton pulseInputButton ;

    private JToggleButton textButton ;

    private JButton       runButton ;

    private JButton       exitButton ;

    private JButton       multiInputButton ;

    private JButton       refreshButton ;

    public DCDToolBar() {

        super() ;
        // NO FANCY !!
        // setFloatable(false);
        setUpGUI() ;
    }

    private void setUpGUI() {

        setLayout( new GridLayout( 6, 3, 5, 5 ) ) ;
        orButton = ToggleButtonFactory.getORButton() ;
        buttonGroup.add( orButton ) ;
        add( orButton ) ;

        andButton = ToggleButtonFactory.getANDButton() ;
        buttonGroup.add( andButton ) ;
        add( andButton ) ;

        xorButton = ToggleButtonFactory.getXORButton() ;
        buttonGroup.add( xorButton ) ;
        add( xorButton ) ;

        nandButton = ToggleButtonFactory.getNANDButton() ;
        buttonGroup.add( nandButton ) ;
        add( nandButton ) ;

        notButton = ToggleButtonFactory.getNOTButton() ;
        buttonGroup.add( notButton ) ;
        add( notButton ) ;

        steadyInputButton = ToggleButtonFactory.getSteadyInputButton() ;
        buttonGroup.add( steadyInputButton ) ;
        add( steadyInputButton ) ;

        pulseInputButton = ToggleButtonFactory.getToggleButton( "pulseIP",
                "PULSE_INPUT", "PULSE INPUT" ) ;
        buttonGroup.add( pulseInputButton ) ;
        add( pulseInputButton ) ;

        clockButton = ToggleButtonFactory.getClockButton() ;
        buttonGroup.add( clockButton ) ;
        add( clockButton ) ;

        ledButton = ToggleButtonFactory.getLEDButton() ;
        buttonGroup.add( ledButton ) ;
        add( ledButton ) ;

        connectorButton = ToggleButtonFactory.getConnectorButton() ;
        buttonGroup.add( connectorButton ) ;
        add( connectorButton ) ;

        selectorButton = ToggleButtonFactory.getSelectorButton() ;
        buttonGroup.add( selectorButton ) ;
        add( selectorButton ) ;

        icButton = ToggleButtonFactory.getICButton() ;
        buttonGroup.add( icButton ) ;
        add( icButton ) ;

        sevenSegButton = ToggleButtonFactory.getSevenSegmentDisplayButton() ;
        buttonGroup.add( sevenSegButton ) ;
        add( sevenSegButton ) ;

        // addSeparator();

        multiInputButton = ButtonFactory.getMultiInputButton() ;
        multiInputButton.setEnabled( false ) ;
        add( multiInputButton ) ;

        // addSeparator();

        jkFlipFlopButton = ToggleButtonFactory.getJKFlipFlopButton() ;
        buttonGroup.add( jkFlipFlopButton ) ;
        add( jkFlipFlopButton ) ;

        tFlipFlopButton = ToggleButtonFactory.getTFlipFlopButton() ;
        buttonGroup.add( tFlipFlopButton ) ;
        add( tFlipFlopButton ) ;

        dFlipFlopButton = ToggleButtonFactory.getDFlipFlopButton() ;
        buttonGroup.add( dFlipFlopButton ) ;
        add( dFlipFlopButton ) ;

        rsFlipFlopButton = ToggleButtonFactory.getRSFlipFlopButton() ;
        buttonGroup.add( rsFlipFlopButton ) ;
        add( rsFlipFlopButton ) ;

        textButton = ToggleButtonFactory.getToggleButton( "font", "TEXT",
                "TEXT" ) ;
        buttonGroup.add( textButton ) ;
        add( textButton ) ;

        // addSeparator();

        runButton = ButtonFactory.getRunButton() ;
        add( runButton ) ;

        refreshButton = ButtonFactory.getRefreshButton() ;
        add( refreshButton ) ;

        exitButton = ButtonFactory.getExitButton() ;
        add( exitButton ) ;
    }

    public void disableButton( String buttonID ) {

        if( buttonID.equals( "MULTI_INPUT" ) ) {
            multiInputButton.setEnabled( false ) ;
        }
    }

    public void enableButton( String buttonID ) {

        if( buttonID.equals( "MULTI_INPUT" ) ) {
            multiInputButton.setEnabled( true ) ;
        }
    }

}
