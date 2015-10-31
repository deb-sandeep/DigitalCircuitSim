// Source file: d:/sandy/dcd/DCD/ui/DCDController.java

package com.sandy.apps.dcs.ui ;

import java.awt.Graphics ;
import java.awt.Point ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.awt.event.KeyAdapter ;
import java.awt.event.KeyEvent ;
import java.awt.event.MouseAdapter ;
import java.awt.event.MouseEvent ;
import java.awt.event.MouseMotionAdapter ;
import java.awt.geom.Point2D ;
import java.io.File ;
import java.util.Enumeration ;
import java.util.Vector ;

import javax.swing.AbstractButton ;
import javax.swing.JOptionPane ;

import com.sandy.apps.dcs.component.DCDClock ;
import com.sandy.apps.dcs.component.DCDComponent ;
import com.sandy.apps.dcs.component.DCDConnector ;
import com.sandy.apps.dcs.component.DCDFlipFlop ;
import com.sandy.apps.dcs.component.DCDGate ;
import com.sandy.apps.dcs.component.DCDIC ;
import com.sandy.apps.dcs.component.DCDInputGate ;
import com.sandy.apps.dcs.component.DCDPulseInputGate ;
import com.sandy.apps.dcs.component.DCDSteadyInputGate ;
import com.sandy.apps.dcs.component.DCDTag ;
import com.sandy.apps.dcs.component.DCDText ;
import com.sandy.apps.dcs.component.factory.ComponentFactory ;
import com.sandy.apps.dcs.component.factory.DCDICFactory ;
import com.sandy.apps.dcs.component.model.DCDFlipFlopModel ;
import com.sandy.apps.dcs.component.model.DCDICModel ;
import com.sandy.apps.dcs.component.model.Port ;
import com.sandy.apps.dcs.component.model.PortInfo ;
import com.sandy.apps.dcs.component.view.DCDGateUI ;
import com.sandy.apps.dcs.component.view.DCDICUI ;
import com.sandy.apps.dcs.util.Chain ;
import com.sandy.apps.dcs.util.DCDUtility ;

/**
 * This class has three internal handlers which handle the events on the event
 * generating components. This is NOT a visual component.
 */
public class DCDController implements ActionListener, CircuitController {

    private DCDCanvas        canvas ;

    private DCDToolBar       toolbar ;

    private Chain            chainHead ;

    private DCDComponent     selectedComponent ;

    private Vector           inputGates             = new Vector() ;

    private Vector           pulseInputGates        = new Vector() ;

    private Vector           flipFlops              = new Vector() ;

    private Vector           clocks                 = new Vector() ;

    private Vector           ics                    = new Vector() ;

    private Vector           tags                   = new Vector() ;

    private DCDCircuitSaver  circuitSaver ;

    private DCDCircuitLoader circuitLoader ;

    // These are used by the multi input gate functionality.
    private int              numInputGates          = 2 ;

    private boolean          numInputGatesSpecified = false ;

    private DCDUtility       utility                = DCDUtility.getInstance() ;

    private String           circuitDescription     = "" ;

    private class KeyHandler extends KeyAdapter {

        // If the VK_DELETE key is pressed we have to delete
        // the selected component from the chain of responsibility.
        public void keyPressed( KeyEvent e ) {

            if( e.getKeyCode() == KeyEvent.VK_DELETE ) {
                if( selectedComponent != null ) {
                    if( selectedComponent instanceof DCDSteadyInputGate ) inputGates
                            .remove( selectedComponent ) ;
                    removeSelectedComponentFromChain() ;
                    selectedComponent = null ;
                    setSelectedComponent( (DCDComponent) chainHead ) ;
                }
            }
            if( e.getKeyCode() == KeyEvent.VK_F6 ) {
                // Use the F6 key to jump between the components.
                if( selectedComponent != null ) {
                    // If this component is the last in the chain
                    // or else this is the only component in the chain
                    // thens set the next component as the chain head
                    if( selectedComponent.getNextLink() == null ) {
                        setSelectedComponent( (DCDComponent) chainHead ) ;
                    }
                    else setSelectedComponent( (DCDComponent) selectedComponent
                            .getNextLink() ) ;
                }
                else {
                    selectedComponent = (DCDComponent) chainHead ;
                }
            }
            if( e.getKeyCode() == KeyEvent.VK_F7 ) initiatePainting( canvas
                    .getGraphics() ) ;
        }
    }

    // This inner class listens to the mouse events on the canvas
    private class MouseHandler extends MouseAdapter {

        // Whenever we get the mouse pressed event we try to update
        // the selected component.
        public void mousePressed( MouseEvent e ) {

            // Get the selected component because all the events
            // will be redirected to the selected component till
            // the time the component gets deselected.
            String toggleStatus = DCDUtility.getInstance()
                    .getSelectedToggleButtonID() ;
            if( toggleStatus.equals( "SELECTOR" ) ) {
                DCDComponent comp = (DCDComponent) getComponentAtPoint( e ) ;
                setSelectedComponent( comp ) ;
            }
            else if( toggleStatus.equals( "STEADY_INPUT" )
                    || toggleStatus.equals( "PULSE_INPUT" ) ) {
                DCDComponent comp = (DCDComponent) getComponentAtPoint( e ) ;
                if( ( comp instanceof DCDSteadyInputGate )
                        || ( comp instanceof DCDPulseInputGate ) ) {
                    setSelectedComponent( comp ) ;
                }
            }
            // If the connector is selected then if the component at the
            // point of the click is a connector then make this connector the
            // selected component.
            else if( toggleStatus.equals( "CONNECTOR" ) ) {
                DCDComponent comp = (DCDComponent) getComponentAtPoint( e ) ;

                if( comp != null ) {
                    if( comp instanceof DCDConnector ) setSelectedComponent( comp ) ;
                    else if( comp instanceof DCDGate ) {
                        // Ask the component wether its ready to absorb the
                        // connector.
                        // This is a visitor class which visits all the relevant
                        // instances
                        // and accumulated the information relating to the port
                        // .. ie
                        // the location and the associated port and the id etc.
                        PortInfo portInfo = new PortInfo() ;
                        if( ( (DCDGate) comp ).isReadyToAcceptNewConnector( e,
                                portInfo ) ) {
                            DCDConnector connector = ComponentFactory
                                    .getInstance().getDCDConnector(
                                            portInfo.getPortLocation() ) ;
                            ;
                            connector.setSelected( true ) ;
                            setSelectedComponent( connector ) ;
                            Port port = portInfo.getPort() ;
                            port.setDCDConnector( connector ) ;
                            connector.setStartingPort( port ) ;
                        }
                        else {
                            // Let the user know that this component can't
                            // accept the connector
                            DCDUtility
                                    .showMessage( "This component can't absorb this new connector" ) ;
                        }
                    }
                }
                else {
                    // Have to let the user know that this is not valid.
                    DCDUtility
                            .showMessage( "ERROR: A Connector must start from either of the following\n  1. Connector\n  2. Gate\n  3. Power source" ) ;
                }
            }

        }

        public void mouseReleased( MouseEvent e ) {

            if( selectedComponent != null ) selectedComponent
                    .handleMouseRelease( e ) ;
        }

        public void mouseClicked( MouseEvent e ) {

            // Have to check for the right mouse click
            // I can trap the click here and pass on to the component
            // just below the click point.
            // TO BE IMPLEMENTED.
            if( e.getModifiers() == MouseEvent.BUTTON3_MASK ) {
                DCDComponent comp = (DCDComponent) getComponentAtPoint( e ) ;
                if( comp != null && selectedComponent == comp ) {
                    Point d = utility.getInstance().getFrame().getLocation() ;
                    int x = e.getPoint().x
                            + utility.getDCDCanvas().getLocationOnScreen().x
                            - d.x ;
                    int y = e.getPoint().y
                            + utility.getDCDCanvas().getLocationOnScreen().y
                            - d.y ;

                    comp.showPopupMenu( new Point( x, y ), utility.getFrame() ) ;
                }
            }
            else {
                String toggleStatus = DCDUtility.getInstance()
                        .getSelectedToggleButtonID() ;
                boolean flag = false ;
                if( toggleStatus.equals( "STEADY_INPUT" )
                        || toggleStatus.equals( "PULSE_INPUT" ) ) {
                    DCDComponent comp = (DCDComponent) getComponentAtPoint( e ) ;
                    if( comp instanceof DCDSteadyInputGate ) {
                        ( (DCDSteadyInputGate) comp ).toggleState() ;
                        fireAllInputGates() ;
                        flag = true ;
                    }
                    if( comp instanceof DCDPulseInputGate ) {
                        ( (DCDPulseInputGate) comp ).fire() ;
                        fireAllInputGates() ;
                        flag = true ;
                    }

                }
                if( !flag ) {
                    if( toggleStatus.equals( "XOR" )
                            || toggleStatus.equals( "AND" )
                            || toggleStatus.equals( "NAND" )
                            || toggleStatus.equals( "NOT" )
                            || toggleStatus.equals( "STEADY_INPUT" )
                            || toggleStatus.equals( "LED" )
                            || toggleStatus.equals( "OR" )
                            || toggleStatus.equals( "CLOCK" )
                            || toggleStatus.equals( "JKFLIPFLOP" )
                            || toggleStatus.equals( "TFLIPFLOP" )
                            || toggleStatus.equals( "DFLIPFLOP" )
                            || toggleStatus.equals( "RSFLIPFLOP" )
                            || toggleStatus.equals( "IC" )
                            || toggleStatus.equals( "SEVENSEGMENTDISPLAY" )
                            || toggleStatus.equals( "PULSE_INPUT" )
                            || toggleStatus.equals( "TEXT" ) ) {
                        DCDComponent comp = null ;
                        if( toggleStatus.equals( "IC" ) ) {
                            DCDICFactory icFactory = DCDICFactory.getInstance() ;
                            String currentICFile = utility.getCurrentICFile() ;
                            if( currentICFile != null ) {
                                if( icFactory.isPrimordialIC( currentICFile ) ) comp = icFactory
                                        .getPrimordialDCDIC( currentICFile ) ;
                                else comp = icFactory
                                        .getDCDICFromFile( currentICFile ) ;
                                utility.addLinkToChain( comp ) ;
                            }
                            else {
                                utility.showMessage( "Please select an IC" ) ;
                            }
                        }
                        else if( toggleStatus.equals( "TEXT" ) ) {
                            String message = JOptionPane
                                    .showInputDialog( "Plese enter the text" ) ;
                            if( message != null ) {
                                comp = new DCDText( null, message ) ;
                                utility.addLinkToChain( comp ) ;
                            }
                        }
                        else {
                            if( numInputGatesSpecified ) {
                                comp = ComponentFactory.getInstance()
                                        .getDCDGate( toggleStatus,
                                                numInputGates ) ;
                                numInputGatesSpecified = false ;
                            }
                            else comp = ComponentFactory.getInstance()
                                    .getDCDGate( toggleStatus ) ;
                        }

                        if( comp != null ) {
                            if( comp instanceof DCDGate ) ( (DCDGate) comp )
                                    .setInitialPosition( e.getX(), e.getY() ) ;
                            else if( comp instanceof DCDText ) ( (DCDText) comp )
                                    .setLocation( e.getPoint() ) ;
                            comp.setSelected( true ) ;
                            setSelectedComponent( comp ) ;
                            if( comp instanceof DCDClock ) {
                                clocks.addElement( comp ) ;
                            }
                            else if( comp instanceof DCDSteadyInputGate ) {
                                inputGates.addElement( comp ) ;
                            }
                            else if( comp instanceof DCDPulseInputGate ) {
                                pulseInputGates.addElement( comp ) ;
                            }
                            else if( comp instanceof DCDFlipFlop ) flipFlops
                                    .addElement( comp ) ;
                            else if( comp instanceof DCDIC ) ics
                                    .addElement( comp ) ;
                        }

                    }
                }
            }

        }
    }

    private class MouseMotionHandler extends MouseMotionAdapter {

        public void mouseDragged( MouseEvent e ) {

            if( selectedComponent != null ) selectedComponent
                    .handleMouseDrag( e ) ;
        }

        public void mouseMoved( MouseEvent e ) {

            // This should show the name of the component in the status bar.
            DCDComponent component = getComponentAtPoint( e ) ;
            if( component != null ) {
                if( component instanceof DCDIC ) {
                    PortInfo info = new PortInfo() ;
                    info = ( (DCDICUI) component.getUI() )
                            .getPortInfo( e, info ) ;
                    DCDTag tag = ( (DCDICModel) component.getModel() )
                            .getTagWithID( info.getPortID() + 1 ) ;
                    if( tag != null ) {
                        utility.setDescriptionString( component
                                .getLongDescription() ) ;
                        utility.showStatusMessage( component.getName() + " : <"
                                + component.getShortDescription() + ">" ) ;
                        utility.setAddedInformationString( "Port ID    ="
                                + tag.getTagID() + "\n" + "Desc  ="
                                + tag.getShortDescription() + "\n"
                                + "L Desc = " + tag.getLongDescription() ) ;
                    }
                }
                else {
                    utility.setDescriptionString( component
                            .getLongDescription() ) ;
                    utility.showStatusMessage( component.getName() + " : <"
                            + component.getShortDescription() + ">" ) ;
                    utility.setAddedInformationString( "" ) ;
                }
            }
            else {
                utility.showStatusMessage( "" ) ;
                utility.setDescriptionString( circuitDescription ) ;
                utility.setAddedInformationString( "" ) ;
            }
        }
    }

    // This is where the button clicks on the Menubar or the
    // tool bar are registered. This is especially the listener for the
    // Run and the Exit button.
    public void actionPerformed( ActionEvent e ) {

        AbstractButton button = (AbstractButton) e.getSource() ;
        String actionCommand = button.getActionCommand() ;
        if( actionCommand.equals( "EXIT" ) ) {
            // In future do some processing here
            utility.savePropertiesInformation() ;
            System.exit( 0 ) ;
        }
        else if( actionCommand.equals( "RUN" ) ) {
            // Run the application. This will requrire initializing
            // all the flipFlops and then firing all the gates and
            // in the end trigerring the clock.
            runApplication() ;

        }
        else if( actionCommand.equals( "REFRESH" ) ) {
            // Repaint the canvas()
            canvas.repaint() ;
        }
        else if( actionCommand.equals( "SAVE" ) ) {
            saveCircuit() ;
        }
        else if( actionCommand.equals( "SAVE_AS" ) ) {
            saveCircuitAs() ;
        }
        else if( actionCommand.equals( "SAVE AS IC" ) ) {
            saveCircuitAsIC() ;
        }
        else if( actionCommand.equals( "OPEN" ) ) {
            loadCircuit() ;
        }
        else if( actionCommand.equals( "NEW" ) ) {
            circuitSaver.clearInfo() ;
            drawNewCircuit() ;
        }
        else if( actionCommand.equals( "MULTI_INPUT" ) ) {
            handleMultiInputRequest() ;
        }
        else if( actionCommand.equals( "DELETE" ) ) {
            deleteComponent( selectedComponent ) ;
        }
        else if( actionCommand.equals( "TAG_AS_IC_PORT" ) ) {
            getICPortInformation() ;
        }
        else if( actionCommand.equals( "ABOUT" ) ) {
            DCDAboutDialog dialog = new DCDAboutDialog( utility.getFrame() ) ;
            dialog.setLocation( utility.getCenterLocation( dialog ) ) ;
            dialog.setVisible( true ) ;
        }

    }

    // This function handles the request for multi input for some
    // of the DCDGates.
    private void handleMultiInputRequest() {

        // Validation is requried.
        String str = JOptionPane.showInputDialog( DCDUtility.getInstance()
                .getFrame(), "Input the number of gates " ) ;
        if( str == null ) return ;
        else if( str.equals( "" ) ) { return ; }
        try {
            numInputGates = new Integer( str ).intValue() ;
            numInputGatesSpecified = true ;
        }
        catch( NumberFormatException e1 ) {
            DCDUtility.showMessage( "Please enter a valid number" ) ;
        }
    }

    // This method runs the application.
    public void runApplication() {

        Enumeration en = ics.elements() ;
        while( en.hasMoreElements() ) {
            DCDIC ic = (DCDIC) en.nextElement() ;
            ic.initialize() ;
        }

        en = flipFlops.elements() ;
        while( en.hasMoreElements() ) {
            DCDFlipFlop flipFlop = (DCDFlipFlop) en.nextElement() ;
            flipFlop.initialize() ;
        }

        // Firing all the input gates once.
        fireAllInputGates() ;

        en = flipFlops.elements() ;
        while( en.hasMoreElements() ) {
            DCDFlipFlop flipFlop = (DCDFlipFlop) en.nextElement() ;
            flipFlop.transferChargeToBuffer() ;
        }

        en = clocks.elements() ;
        while( en.hasMoreElements() ) {
            DCDClock clock = (DCDClock) en.nextElement() ;
            clock.fire() ;
        }
    }

    public void fireAllInputGates() {

        Enumeration en = inputGates.elements() ;
        while( en.hasMoreElements() ) {
            DCDInputGate inputGate = (DCDInputGate) en.nextElement() ;
            inputGate.fire() ;
        }

        en = pulseInputGates.elements() ;
        while( en.hasMoreElements() ) {
            DCDPulseInputGate gate = (DCDPulseInputGate) en.nextElement() ;
            gate.fireCurrentState() ;
        }
    }

    // This is where the dialog is popped and the information regarding
    // the port of the IC.. ie the name and the port number is collected
    // from the user.
    private void getICPortInformation() {

        // Validation is requried.
        DCDNameDialog nameDialog = DCDNameDialog.getInstance() ;
        nameDialog.show( "", "", "" ) ;
        int idOfPort = 0 ;
        ;

        try {
            idOfPort = new Integer( nameDialog.getName().trim() ).intValue() ;
        }
        catch( NumberFormatException e1 ) {
            DCDUtility.showMessage( "Please enter the port number of the IC" ) ;
        }
        // Check if the selected component is an instance of InputGate
        // if so .. check wether its already tagged.
        if( selectedComponent != null ) {
            DCDGate gate = (DCDGate) selectedComponent ;
            DCDTag tag = gate.getDCDTag() ;
            if( tag != null ) {
                JOptionPane.showMessageDialog( DCDUtility.getInstance()
                        .getFrame(), "This input gate is already tagged" ) ;
                return ;
            }
            else {
                DCDGateUI ui = (DCDGateUI) gate.getUI() ;
                Point2D locationOnScreen = ui.getLocationOnScreen() ;
                Port port = gate.getTaggablePort() ;
                DCDConnector connector = port.getDCDConnector() ;
                Port newPort = new Port( connector, idOfPort ) ;
                if( gate instanceof DCDInputGate ) tag = new DCDTag( newPort,
                        locationOnScreen, gate, DCDTag.INPUT ) ;
                else tag = new DCDTag( newPort, locationOnScreen, gate,
                        DCDTag.OUTPUT ) ;
                ( (DCDGate) gate ).setDCDTag( tag ) ;
                gate.paint( DCDUtility.getInstance().getGraphics() ) ;
                addTag( tag ) ;
                tag.setShortDescription( nameDialog.getShortDescription() ) ;
                tag.setLongDescription( nameDialog.getLongDescription() ) ;
            }
        }
    }

    public void saveCircuit() {

        System.out.println( "[S][Here]:Saving" ) ;
        circuitSaver.save( chainHead, false, null ) ;
    }

    public void saveCircuitAs() {

        circuitSaver.saveCircuitAs( chainHead, false, null ) ;
    }

    private void saveCircuitAsIC() {

        circuitSaver.save( chainHead, true, tags ) ;
    }

    public void loadCircuit() {

        circuitLoader.load() ;
    }

    private void drawNewCircuit() {

        DCDUtility utility = DCDUtility.getInstance() ;
        File currentFile = utility.getCurrentFile() ;

        if( currentFile != null || chainHead != null ) {
            // This means that there is one file that is currently
            // opened in the workspace. Hence ask for permission
            // to close it before loading a new file.
            int choice = JOptionPane.showConfirmDialog( utility.getFrame(),
                    "Save the current file in the workspace" ) ;
            if( choice == JOptionPane.YES_OPTION ) {
                // OK Save the current file loaded in the workspace.
                saveCircuit() ;
                cleanWorkspace() ;
            }
            else if( choice == JOptionPane.NO_OPTION ) {
                // Nah !! No need to save the file ... proceed.
                cleanWorkspace() ;
            }
            else if( choice == JOptionPane.CANCEL_OPTION ) {
                // Brr !! Return fast.
                return ;
            }
        }

        utility.setCurrentFile( null ) ;
    }

    public void cleanWorkspace() {

        deleteAllComponents() ;
        inputGates = new Vector() ;
        DCDUtility.getInstance().setCurrentFile( null ) ;
        canvas.repaint() ;
    }

    public DCDController( DCDCanvas c ) {

        this.canvas = (DCDCanvas) c ;
        circuitSaver = new DCDCircuitSaver() ;
        circuitLoader = new DCDCircuitLoader( this ) ;
        c.addMouseListener( new MouseHandler() ) ;
        c.addMouseMotionListener( new MouseMotionHandler() ) ;
        DCDUtility.getInstance().getFrame().addKeyListener( new KeyHandler() ) ;
    }

    // This function will be used by the CircuitLoader to update the information
    // about the circuit.
    DCDCircuitSaver getCircuitSaver() {

        return circuitSaver ;
    }

    public void addLinkToChain( Chain c ) {

        c.setNextLink( chainHead ) ;
        chainHead = c ;
    }

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

    public void deSelectAll() {

        setSelectedComponent( null ) ;
    }

    // This function does the following things:
    // If a component ia already selected on the screen the
    // that compoenent is deselected
    // The new component becomes the selected component on the screen.
    public void setSelectedComponent( DCDComponent component ) {

        if( selectedComponent != null ) {
            selectedComponent.setSelected( false ) ;
            selectedComponent.setSelected( canvas.getGraphics(), false ) ;
        }
        selectedComponent = component ;
        if( selectedComponent != null ) {
            selectedComponent.setSelected( true ) ;
            selectedComponent.setSelected( canvas.getGraphics(), true ) ;
        }
    }

    public DCDComponent getSelectedComponent() {

        return ( selectedComponent ) ;
    }

    // Its guaranteed that the selected component if not null
    // inside this method.
    private void removeSelectedComponentFromChain() {

        deleteComponent( selectedComponent ) ;

    }

    // This will delete all the components in the workspace.
    // This will be called by the clearWorkspace method of the
    // DCDController class.
    public void deleteAllComponents() {

        while( chainHead != null ) {
            deleteComponent( (DCDComponent) chainHead ) ;
        }
        selectedComponent = null ;
    }

    // TODO=> Here I have to remove the asociated Tag with the Input Gate if
    // an input gate is being deleted. This is so because a Tag has a internal
    // reference to DCDConnector and so and so forth...

    public void deleteComponent( DCDComponent component ) {

        if( component instanceof DCDClock ) clocks.remove( component ) ;
        else if( component instanceof DCDSteadyInputGate ) inputGates
                .remove( component ) ;
        else if( component instanceof DCDPulseInputGate ) pulseInputGates
                .remove( component ) ;
        else if( component instanceof DCDFlipFlop ) flipFlops
                .remove( component ) ;
        else if( component instanceof DCDIC ) ics.remove( component ) ;

        component.cleanUp() ;
        if( selectedComponent == component ) selectedComponent = null ;
        // If this is the first component in the chain
        if( component == (DCDComponent) chainHead ) {
            chainHead = component.getNextLink() ;
            component.setNextLink( null ) ;
        }
        else {
            Chain c = chainHead ;
            while( c.getNextLink() != null ) {
                if( (DCDComponent) c.getNextLink() == component ) {
                    c.setNextLink( component.getNextLink() ) ;
                    component.setNextLink( null ) ;
                    return ;
                }
                c = c.getNextLink() ;
            }
        }
    }

    public DCDComponent getComponentAtPoint( MouseEvent e ) {

        Chain c = chainHead ;
        while( c != null ) {
            if( c.isSelected( e ) ) return (DCDComponent) c ;
            c = c.getNextLink() ;
        }
        return ( null ) ;
    }

    // This is required if there are a lot of components at a given
    // point, and we need a particular component from those jumble
    // of components. The situation may arise when many of the connectors
    // are overlapping or when we want to get what lies where the connector
    // has been just dropped.
    public Enumeration getAllComponentsAtPoint( MouseEvent e ) {

        Vector components = new Vector() ;
        Chain c = chainHead ;
        while( c != null ) {
            if( c.isSelected( e ) ) components.addElement( c ) ;
            c = c.getNextLink() ;
        }
        return ( components.elements() ) ;
    }

    public Chain getChainHead() {

        return ( chainHead ) ;

    }

    public void addInputGate( DCDInputGate inputGate ) {

        inputGates.add( inputGate ) ;
    }

    public void addPulseInputGate( DCDPulseInputGate gate ) {

        pulseInputGates.addElement( gate ) ;
    }

    public void addFlipFlop( DCDFlipFlop flipFlop ) {

        flipFlops.add( flipFlop ) ;
    }

    public void addClock( DCDClock clock ) {

        clocks.add( clock ) ;
    }

    public void addIC( DCDIC ic ) {

        ics.add( ic ) ;
    }

    public void addTag( DCDTag tag ) {

        tags.addElement( tag ) ;
    }

    public void removeTag( DCDTag tag ) {

        tags.remove( tag ) ;
    }

    public void transferChargesToBufferInFlipFlops() {

        Enumeration en = flipFlops.elements() ;
        while( en.hasMoreElements() ) {
            DCDFlipFlop flipFlop = (DCDFlipFlop) en.nextElement() ;
            flipFlop.transferChargeToBuffer() ;
        }
    }

    public void transferChargesToBufferInICs() {

        Enumeration en = ics.elements() ;
        while( en.hasMoreElements() ) {
            DCDIC ic = (DCDIC) en.nextElement() ;
            ic.transferChargeToBuffer() ;
        }
    }

    // This function is called by the DCDClock after it fires.
    // This is called upon to transfer the state of the system in the ith state
    // to the memory elements. Now the circuit can contain both Flip-Flops in
    // the open
    // and flip-flops inside the ICs. So we need to transfer the charge to the
    // buffer
    // in both open flip-flops and the ICs.

    public void transferChargeToBufferInDCDCircuit() {

        transferChargesToBufferInFlipFlops() ;
        transferChargesToBufferInICs() ;
    }

    public void setCircuitDescription( String str ) {

        this.circuitDescription = str ;
    }

    public String getCircuitDescription() {

        return circuitDescription ;
    }

    public void fireAllUnfiredFlipFlops() {

        Enumeration en = flipFlops.elements() ;
        while( en.hasMoreElements() ) {
            DCDFlipFlop flipFlop = (DCDFlipFlop) en.nextElement() ;
            DCDFlipFlopModel model = (DCDFlipFlopModel) flipFlop.getModel() ;
            if( model.getFiredStatus() == false ) {
                utility.setMessageString( "This is an unfired FF" ) ;
                model.fireWithExistingCharge() ;
            }
        }

        en = ics.elements() ;
        while( en.hasMoreElements() ) {
            DCDIC flipFlop = (DCDIC) en.nextElement() ;
            DCDICModel model = (DCDICModel) flipFlop.getModel() ;
            model.fireAllUnfiredFlipFlops() ;
        }

    }

    public void clearFiringStatusOfAllFlipFlops() {

        Enumeration en = flipFlops.elements() ;
        while( en.hasMoreElements() ) {
            DCDFlipFlop flipFlop = (DCDFlipFlop) en.nextElement() ;
            DCDFlipFlopModel model = (DCDFlipFlopModel) flipFlop.getModel() ;
            model.setFiredStatus( false ) ;
        }

        en = ics.elements() ;
        while( en.hasMoreElements() ) {
            DCDIC flipFlop = (DCDIC) en.nextElement() ;
            DCDICModel model = (DCDICModel) flipFlop.getModel() ;
            model.clearFiringStatusOfAllFlipFlops() ;
        }
    }

}