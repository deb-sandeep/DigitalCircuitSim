// Source file: d:/sandy/dcd/DCD/component/model/DCDDefaultConnectorModel.java

package com.sandy.apps.dcs.component.model ;

import java.util.* ;
import java.io.* ;

import com.sandy.apps.dcs.component.DCDConnector ;

/**
 * This is the default model of the Connector component. This is supposed to
 * store the connectivity data.
 */
public class DCDDefaultConnectorModel extends DCDComponentModel implements
        Serializable {

    private DCDConnector dcdConnector ;

    // This contains all the branches that are emanating from this
    // connector. When this connector will be deleted the
    // corrosponding branch connectors will be deleted.
    // This vector will always contain instances of type DCDConnector.
    private Vector branches = new Vector() ;

    private boolean isBranch = false ;

    // Each connector will be connected to one starting port
    // and one ending port. In case of a branch there will be
    // no starting port because a branch starts from a connector
    // In case of a hanging connector there will be no ending port
    // Thus we have to check for these conditions when firing.
    private Port startingPort ;

    private Port endingPort ;

    /**
     *
     *
     */
    public DCDDefaultConnectorModel() {

        super() ;
    }

    public DCDDefaultConnectorModel(DCDConnector connector) {

        super(connector) ;
        this.dcdConnector = connector ;
    }

    public DCDConnector getDCDConnector() {

        return this.dcdConnector ;
    }

    public void setDCDConnector(DCDConnector dcdConnector) {

        this.dcdConnector = dcdConnector ;
    }

    public void update(Observable arg0, Object arg1) {

    }

    public void addBranch(DCDConnector connector) {

        branches.addElement(connector) ;
    }

    public void setBranch(boolean b) {

        this.isBranch = b ;
    }

    public boolean isBranch() {

        return (this.isBranch) ;

    }

    public Vector getBranches() {

        return (branches) ;
    }

    // This method is called when the connector is activated
    // by a output gate.
    public void activate(boolean state, int type) {

        // TODO => we will have to see if there is a leg being
        // connected to the end of this connector. If
        // yes we will have to activat it and then we have
        // to call activate on all the associated branch
        // connector.
        // Sandeep [ 19-07-2000 ] There is one more thing that we have to see.
        // If its the endingport that is getting activated then
        // we have to take care .. I mean till now I was assuming
        // that the connector will always get activated from the
        // starting port. Which was quite an restrictive assumption.
        if (endingPort != null)
            endingPort.activateGate(state, type) ;
        if (startingPort != null)
            startingPort.activateGate(state, type) ;
        int size = branches.size() ;
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                ((DCDConnector) branches.elementAt(i)).activate(state, type) ;
            }
        }
    }

    // The getter and setter methods for the input ports.
    public Port getStartingPort() {

        return this.startingPort ;
    }

    public void setStartingPort(Port startingPort) {

        this.startingPort = startingPort ;
    }

    // The getter and setter for the ouput ports.
    public Port getEndingPort() {

        return this.endingPort ;
    }

    public void setEndingPort(Port endingPort) {

        this.endingPort = endingPort ;
    }

    public void releasePorts() {

        if (startingPort != null) {
            startingPort.setDCDConnector(null) ;
            startingPort = null ;
        }
        if (endingPort != null) {
            endingPort.setDCDConnector(null) ;
            endingPort = null ;
        }
    }

    public void removePort(Port port) {

        if (startingPort == port)
            startingPort = null ;
        else if (endingPort == port) {
            endingPort = null ;
        }
    }

    public String getSavingInformation() {

        int numBranches = branches.size() ;
        StringBuffer sBuf = new StringBuffer() ;
        if (numBranches > 0) {
            sBuf.append("\t\t\t<DCDConnectorModel>\n") ;
            sBuf.append(getConnectorIDList()) ;
            sBuf.append("\t\t\t</DCDConnectorModel>\n") ;
        }
        else {
            sBuf.append("\t\t\t<DCDConnectorModel />\n") ;
        }
        return (sBuf.toString()) ;

    }

    private String getConnectorIDList() {

        StringBuffer sBuf = new StringBuffer() ;
        sBuf.append("\t\t\t\t<ConnectorIDList>\n") ;
        for (Enumeration e = branches.elements(); e.hasMoreElements();) {
            DCDConnector connector = (DCDConnector) e.nextElement() ;
            sBuf.append("\t\t\t\t\t<ID value=\"" + connector.getInternalID()
                    + "\" />\n") ;
        }
        sBuf.append("\t\t\t\t</ConnectorIDList>\n") ;
        return (sBuf.toString()) ;

    }

}
