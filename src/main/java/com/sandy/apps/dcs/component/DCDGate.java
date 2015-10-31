package com.sandy.apps.dcs.component ;

import java.awt.event.MouseEvent ;
import java.awt.geom.Point2D ;
import java.io.Serializable ;
import java.util.Enumeration ;
import java.util.Hashtable ;

import org.w3c.dom.Node ;
import org.w3c.dom.NodeList ;

import com.sandy.apps.dcs.common.DCDUtility ;
import com.sandy.apps.dcs.component.model.DCDComponentModel ;
import com.sandy.apps.dcs.component.model.DCDGateModel ;
import com.sandy.apps.dcs.component.model.Port ;
import com.sandy.apps.dcs.component.model.PortInfo ;
import com.sandy.apps.dcs.component.view.DCDComponentUI ;
import com.sandy.apps.dcs.component.view.DCDGateUI ;
import com.sandy.apps.dcs.cor.Chain ;
import com.sandy.apps.dcs.factory.DCDConnectorUIFactory ;

public abstract class DCDGate extends DCDComponent implements Serializable {

    private DCDGateUI    ui ;

    private DCDGateModel model ;

    private DCDTag       tag ;

    protected int        numInputPorts  = 0 ;

    protected int        numOutputPorts = 0 ;

    // This is the constructor that is being used by the factories
    // to create the instance of the appropriate gates.
    protected DCDGate() {

    }

    protected DCDGate( Chain c, DCDGateModel model, DCDGateUI ui ) {

        super( c, model, ui ) ;
        this.ui = ui ;
        this.model = model ;

    }

    public void setModel( DCDGateModel model ) {

        super.setModel( model ) ;
        this.model = model ;
    }

    public DCDComponentModel getModel() {

        return ( this.model ) ;
    }

    public void setUI( DCDGateUI ui ) {

        super.setUI( ui ) ;
        this.ui = ui ;
    }

    public DCDComponentUI getUI() {

        return ( this.ui ) ;
    }

    // If the gate receives mouse drag events it means that this
    // gate is selected. And mouse drag can have only one implication
    // on a selected gate and that is to drag it.

    // Thus it makes a lot of sense to handle the mouse drag event in the
    // super class of all the gates. This way we don't have to
    // implement the dragging scenario in the subclasses till we desperately
    // feel the need.
    public void handleMouseDrag( MouseEvent e ) {

        // Just forward the drag event to the ui who is in a better position
        // to handle the drag event.
        // If the component is floating then only it can be dragged on
        // the screen. Floating means that all the ports of this gate are
        // not connected to any of the connectors.
        if( model.isFloating() ) {
            ui.handleMouseDragEvent( e ) ;
        }
    }

    // This implies that the mouse has been released from this component.
    public void handleMouseRelease( MouseEvent e ) {

        // Forward this event to the ui who is in a better position to handle
        // the event.
        ui.handleMouseReleaseEvent( e ) ;
    }

    // This function is used to set the initial position of the
    // gate on the screen. This is so because when the gate is created
    // on the workspace it needs to know its initial position.
    // Since in the creation process we can't pass the point of click
    // we use the setter method as soon as possible after creation.This helps
    // in setting the position of the gate
    public void setInitialPosition( int x, int y ) {

        // Redirect this freshly acquired knowledge to the ui.
        ui.setInitialPosition( x, y ) ;
    }

    // This function returns whether the gate is ready to accept the
    // new connector at the gate closest to the mouse click.
    // This means that the ui should be queried first to get the port
    // closest to the click. Then the model should be queried whether
    // the port is ready to accept the connector.
    //
    // This should first ask the ui for the PortInfo. If the port info
    // is not null then it should ask the model wether the port at
    // the position can absorb the connection. If yes then it should
    // return a true. else false.
    public boolean isReadyToAcceptNewConnector( MouseEvent e, PortInfo visitor ) {

        PortInfo portInfo = ui.getPortInfo( e, visitor ) ;
        if( portInfo == null ) return ( false ) ;
        boolean available = model.isPortAvailable( portInfo ) ;
        return ( available ) ;
    }

    // This method will be called when this component is being deleted
    // from the workspace. At this moment the component can perform any
    // cleanup actions that it wants to do. Clean up actions might
    // include erasing the component from the screen and removing this
    // component from the underlying web of component connections.
    public void cleanUp() {

        // This call to the super class clean up method ensures that the
        // UI cleans up all the traces from the screen.
        super.cleanUp() ;
        // TODO=> Have to inform the model that this component is being
        // deleted. The model should then take care as to release
        // the connections and do other nessary cleanup actions.
        model.cleanUp() ;
        if( tag != null ) {
            tag.setModel( null ) ;
            tag.setUI( null ) ;
            DCDUtility.getInstance().getDCDController().removeTag( tag ) ;
        }

    }

    public int getNumInputPorts() {

        return this.numInputPorts ;
    }

    public void setNumInputPorts( int numInputPorts ) {

        this.numInputPorts = numInputPorts ;
    }

    public int getNumOutputPorts() {

        return this.numOutputPorts ;
    }

    public void setNumOutputPorts( int numOuputPorts ) {

        this.numOutputPorts = numOuputPorts ;
    }

    // This method is called by the GateFactory class .
    // This populates the initial position of the gate which
    // it extracts from the DOM node.
    public void updateUIFromDOMNode( Node node ) {

        int tlx = 0 ;
        int tly = 0 ;

        NodeList nodeList = null ;
        int nodeLength = 0 ;

        nodeList = node.getChildNodes() ;
        nodeLength = nodeList.getLength() ;

        for( int i = 0 ; i < nodeLength ; i++ ) {
            Node n = nodeList.item( i ) ;
            String nodeName = n.getNodeName() ;
            if( nodeName.equals( "Point" ) ) {
                Point2D p = DCDConnectorUIFactory.getPoint2DFromDOMNode( n ) ;
                tlx = (int) p.getX() ;
                tly = (int) p.getY() ;
            }
        }
        ui.setInitialPosition( tlx, tly ) ;

    }

    // This method is called up by the GateFactory class to update
    // the default model that is created when the gate is initialized.
    public void updateModelFromDOMNode( Node node, Hashtable connectorHash ) {

        model.populatePortCollectionsFromDOMNode( node, connectorHash ) ;
    }

    // This is where the saving information relating to the gate
    // is computed and returned. The logic is placed in teh
    // super class because all the gates have the same structure
    // of the saving information which can be computed from this
    // level.
    public String getSavingInformation() {

        StringBuffer sBuf = new StringBuffer() ;
        sBuf.append( "\t\t<DCDGate type=\"" + getType() + "\" numInputPorts=\""
                + model.getNumInputPorts() + "\" name=\"" + getName() + "\">\n" ) ;
        sBuf.append( "\t\t\t<ID value=\"" + getHashCode() + "\" />\n" ) ;
        if( !getShortDescription().equals( "" ) ) {
            sBuf.append( "\t\t\t<ShortDescription>\n" ) ;
            sBuf.append( getShortDescription() ) ;
            sBuf.append( "\t\t\t</ShortDescription>\n" ) ;
        }

        if( !getLongDescription().equals( "" ) ) {
            sBuf.append( "\t\t\t<LongDescription>\n" ) ;
            sBuf.append( getLongDescription() ) ;
            sBuf.append( "\t\t\t</LongDescription>\n" ) ;
        }
        sBuf.append( model.getSavingInformation() + "\n" ) ;
        sBuf.append( ui.getSavingInformation() + "\n" ) ;
        sBuf.append( "\t\t</DCDGate>\n" ) ;
        return ( sBuf.toString() ) ;

    }

    public Enumeration getInputPorts() {

        return model.getInputPorts() ;
    }

    public Enumeration getOutputPorts() {

        return model.getOutputPorts() ;
    }

    public String getInternalID() {

        return this.internalID ;
    }

    public void setInternalID( String internalID ) {

        this.internalID = internalID ;
    }

    // This returns the taggable port of the gate.
    // In case of a InputPort this will be the first port
    // in the output port collection and in case of the OutputPorts
    // this will be the first of the InputPorts .. I don't know
    // what the hell is going to happen for output gates which
    // have more than one input ports.
    // In case of gates other than input or output gates, I am afraid
    // this is going to return a null.

    // THIS METHOD WILL BE OVERWRITTEN IN THE INPUT GATE AND DCDLED TO
    // RETURN THE RELEVANT PORT INFORMATION.
    public Port getTaggablePort() {

        return ( null ) ;
    }

    public DCDTag getDCDTag() {

        return this.tag ;
    }

    public void setDCDTag( DCDTag tag ) {

        this.tag = tag ;
    }

}