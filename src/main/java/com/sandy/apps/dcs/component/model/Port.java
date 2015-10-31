
package com.sandy.apps.dcs.component.model ;

import java.io.Serializable ;

import com.sandy.apps.dcs.component.DCDComponent ;
import com.sandy.apps.dcs.component.DCDConnector ;

/**
 * This class will have a reference to the connector that is starting from this
 * port. A port is one leg of a gate.
 */
public class Port implements Serializable {

    // This is the connector that is attached to this port.
    // NOTE: From a single leg only a single connector may start.
    // If need arises that a multiple connectors should be
    // connected to a leg,then they can of course be
    // made branches of the connector starting from the port.
    private DCDConnector connector ;

    // This is the ID of this connector. The port collection has
    // many ports so when this port communicates with the collection
    // it needs to send its id along so that the collection may
    // recognize which port tried to communicate.
    private int id ;

    // This is the port collection to which this port belongs
    // to. This is important to note that, a port can't exist
    // alone. It has to be part of a PortCollection
    private PortCollection portCollection ;

    // This is used by ports which are only output ports.
    private boolean currentCharge = false ;

    public Port(PortCollection portCollection, int id) {

        this.portCollection = portCollection ;
        this.id = id ;
    }

    public Port(DCDConnector connector, int id) {

        this.connector = connector ;
        this.id = id ;
    }

    public DCDConnector getDCDConnector() {

        return this.connector ;
    }

    public void setDCDConnector(DCDConnector connector) {

        this.connector = connector ;
    }

    // Either this port can activate a connector
    // or this port can be activated by a connector.
    // This depends upon the situation. When this port
    // is a input port then this will be activated by
    // a connector and when this is a output port this
    // will activate a connector.So there are two methods
    // one for activating the connector emanating from this.
    // and the second to activate the gate to which this port
    // belongs to.

    // The type decides what type of charge is supposed to be
    // activating the connector.
    // This is called if this is a output port.
    public void activateConnector(boolean activationState, int type) {

        // This should activate the connector.
        currentCharge = activationState ;
        if (connector != null)
            connector.activate(activationState, type) ;
    }

    public void activateConnectorWithCurrentCharge() {

        if (connector != null)
            connector.activate(currentCharge, DCDInputGateModel.STEADY) ;
    }

    // This is called when charge is transferred to the port
    // by a connector.
    public void activateGate(boolean activationState, int type) {

        portCollection.activate(activationState, id, type) ;
    }

    // This will set the port of the connector associated to null.
    public void cleanUp() {

        if (connector != null)
            connector.removePort(this) ;
    }

    public String getSavingInformation() {

        StringBuffer sBuf = new StringBuffer() ;
        sBuf.append("<Port>\n") ;
        sBuf.append("\t\t\t\t\t<ID value=\"" + id + "\" />\n") ;
        sBuf.append("\t\t\t\t\t<ConnectorID value=\""
                + ((connector != null) ? ((DCDComponent) connector)
                        .getHashCode() : "null") + "\" />\n") ;
        sBuf.append("\t\t\t\t</Port>\n") ;
        return (sBuf.toString()) ;
    }

    public boolean isFloating() {

        if (connector == null)
            return true ;
        return (false) ;

    }

    public int getID() {

        return (id) ;
    }

}