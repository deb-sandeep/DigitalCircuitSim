// Source file: d:/sandy/dcd/DCD/component/DCDConnector.java

package com.sandy.apps.dcs.component ;

import java.awt.event.* ;
import java.awt.geom.* ;
import java.util.* ;
import java.io.* ;

import com.sandy.apps.dcs.common.* ;
import com.sandy.apps.dcs.component.model.* ;
import com.sandy.apps.dcs.component.view.DCDDefaultConnectorUI ;
import com.sandy.apps.dcs.cor.Chain ;

// 3-July-2000 : Tried to move the information about the
// branches to the model of the connector.
// This makes more sense as this is more data related.
public class DCDConnector extends DCDComponent implements Serializable {

    private DCDDefaultConnectorModel model ;

    private DCDDefaultConnectorUI    ui ;

    private DCDConnector             parent ;

    private String                   parentID ;

    // This is the default ID of the Connector
    private static int               ID = 1 ;

    public DCDConnector() {

    }

    public DCDConnector( Chain c ) {

        super( c, null, null ) ;
        model = new DCDDefaultConnectorModel( this ) ;
        ui = new DCDDefaultConnectorUI( this ) ;
        super.setModel( model ) ;
        super.setUI( ui ) ;
    }

    public DCDConnector( Chain c, DCDDefaultConnectorModel model,
            DCDDefaultConnectorUI ui ) {

        super( c, model, ui ) ;
        this.model = model ;
        this.ui = ui ;
    }

    public DCDConnector( Chain c, DCDConnector parent ) {

        this( c ) ;
        this.parent = parent ;
    }

    public String getName() {

        String n = super.getName() ;
        if( !n.equals( "" ) ) return n ;
        else {
            if( parent != null ) return parent.getName() ;
        }
        return "" ;
    }

    public boolean handleMouseOver( MouseEvent e ) {

        return ( false ) ;
    }

    public void handleMouseRelease( MouseEvent e ) {

        // Now check where the hell the user dropped the link.
        // Get the component at the point of release.
        // !! HEll this is always going to return this connector.
        DCDComponent component = DCDUtility.getInstance().getComponentAtPoint(
                e ) ;
        if( component instanceof DCDConnector ) {
            if( component != this ) {
                DCDUtility
                        .showMessage( "ERROR: Connectors can't be dropped on Connectors\nGO PLAY ELSEWHERE !!!" ) ;
                ui.handleMouseReleaseEvent( e ) ;
                DCDUtility.getInstance().deleteDCDComponent( this ) ;
            }
        }
        else if( component instanceof DCDGate ) {
            PortInfo portInfo = new PortInfo() ;
            if( ( (DCDGate) component ).isReadyToAcceptNewConnector( e,
                    portInfo ) ) {
                // Add this component as one of the receptors of the charge
                ui.setEndPoint( portInfo.getPortLocation() ) ;
                ui.handleMouseReleaseEvent( e ) ;
                Port port = portInfo.getPort() ;
                port.setDCDConnector( this ) ;
                this.setEndingPort( port ) ;
                ui.blink() ;
            }
            else {
                DCDUtility
                        .showMessage( "ERROR: Sorry! This component can't absorb this connector" ) ;
                ui.handleMouseReleaseEvent( e ) ;
                DCDUtility.getInstance().deleteDCDComponent( this ) ;
            }
        }
        else {
            ui.handleMouseReleaseEvent( e ) ;
        }

    }

    // A connector can only handle mouse drags when the SELECTOR
    // toggle button is in a pressed state.
    public void handleMouseDrag( MouseEvent e ) {

        if( DCDUtility.getInstance().getSelectedToggleButtonID()
                .equals( "CONNECTOR" ) ) {
            ui.handleMouseDragEvent( e ) ;
        }
    }

    public void addBranch( DCDConnector connector ) {

        model.addBranch( connector ) ;
    }

    public void setBranch( boolean b ) {

        model.setBranch( b ) ;
    }

    public boolean isBranch() {

        return ( model.isBranch() ) ;

    }

    // DCDConnector is overriding the cleanUp of the DCDComponent
    // because this needs to delete the branch components also.
    public void cleanUp() {

        super.cleanUp() ;
        int size = model.getBranches().size() ;
        DCDUtility utility = DCDUtility.getInstance() ;
        for( int i = 0 ; i < size ; i++ ) {
            utility.deleteDCDComponent( (DCDComponent) model.getBranches()
                    .elementAt( i ) ) ;
        }
        model.releasePorts() ;
    }

    public DCDConnector getParent() {

        return this.parent ;
    }

    public void setParent( DCDConnector parent ) {

        this.parent = parent ;
    }

    public void setStartPoint( Point2D startPoint ) {

        ui.setStartPoint( startPoint ) ;
    }

    // This method is called by a port from which this connector
    // is starting.
    public void activate( boolean state, int type ) {

        // Activat the model .Its his headache to take care of
        // all the humdrum of logic.
        model.activate( state, type ) ;

        // We can also call upon the ui to show some ... well
        // GLAMOUR.
    }

    public void setStartingPort( Port startingPort ) {

        model.setStartingPort( startingPort ) ;
    }

    public Port getStartingPort() {

        return ( model.getStartingPort() ) ;

    }

    public Port getEndingPort() {

        return model.getEndingPort() ;
    }

    public void setEndingPort( Port endingPort ) {

        model.setEndingPort( endingPort ) ;
    }

    public void removePort( Port port ) {

        model.removePort( port ) ;
    }

    public String getSavingInformation() {

        // For testing purposes returning null
        StringBuffer sbuf = new StringBuffer() ;
        String parentInfo = ( isBranch() ) ? ( " parentID=\""
                + parent.getHashCode() + "\"" ) : "" ;
        sbuf.append( "\t\t<DCDConnector isBranch=\"" + isBranch() + "\""
                + parentInfo + " name=\"" + getName() + "\">\n" ) ;
        sbuf.append( "\t\t\t<ID value=\"" + getHashCode() + "\"/>\n" ) ;
        if( !getShortDescription().equals( "" ) ) {
            sbuf.append( "\t\t\t<ShortDescription>\n" ) ;
            sbuf.append( getShortDescription() ) ;
            sbuf.append( "\t\t\t</ShortDescription>\n" ) ;
        }

        if( !getLongDescription().equals( "" ) ) {
            sbuf.append( "\t\t\t<LongDescription>\n" ) ;
            sbuf.append( getLongDescription() ) ;
            sbuf.append( "\t\t\t</LongDescription>\n" ) ;
        }
        if( ui != null ) sbuf.append( ui.getSavingInformation() + "\n" ) ;
        sbuf.append( "\t\t</DCDConnector>" ) ;
        return ( sbuf.toString() ) ;
    }

    public String getParentID() {

        return this.parentID ;
    }

    public void setParentID( String parentID ) {

        this.parentID = parentID ;
    }

    public DCDDefaultConnectorModel getDCDDefaultConnectorModel() {

        return this.model ;
    }

    public void setDCDDefaultConnectorModel( DCDDefaultConnectorModel model ) {

        this.model = model ;
        super.setModel( model ) ;
    }

    public DCDDefaultConnectorUI getDCDDefaultConnectorUI() {

        return this.ui ;
    }

    public void setDCDDefaultConnectorUI( DCDDefaultConnectorUI ui ) {

        this.ui = ui ;
        super.setUI( ui ) ;
    }

    public String getType() {

        return ( "CONNECTOR" ) ;
    }

}
