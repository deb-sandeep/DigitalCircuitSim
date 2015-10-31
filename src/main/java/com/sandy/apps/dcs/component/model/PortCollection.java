
package com.sandy.apps.dcs.component.model ;

import java.io.Serializable ;
import java.util.BitSet ;
import java.util.Enumeration ;
import java.util.Hashtable ;
import java.util.Vector ;

import org.w3c.dom.NamedNodeMap ;
import org.w3c.dom.Node ;
import org.w3c.dom.NodeList ;

import com.sandy.apps.dcs.common.DCDUtility ;
import com.sandy.apps.dcs.component.DCDConnector ;

/**
 * This represents a collection of Ports.
 */
public class PortCollection implements Serializable {

    // This is a list of all the ports contained in this collection.
    private Vector ports = new Vector() ;

    // This is the activation state of this collection.
    private BitSet activationStatus = new BitSet() ;

    // This is the charge status of the ports in this collection.
    private BitSet chargeStatus = new BitSet() ;

    // This is the DCDGate to which this collection belongs
    private DCDGateModel parentGateModel ;

    // This is the type of the PortCollection.. this may be the input type
    // or the output type.
    private int type ;

    // These are two static variables which define the types of PortCollections.
    public static final int INPUT = 0 ;

    public static final int OUTPUT = 1 ;

    public static final int NOTYPE = 2 ;

    public static final int IC_INTERNAL_OUTPUT = 3 ;

    // In a IC all the ports are mixed.

    public PortCollection(DCDGateModel gateModel, int type) {

        this.parentGateModel = gateModel ;
        this.type = type ;
    }

    // This will be used to make a solitary port collection which is
    // just a collection of ports and doesn't belong to any gate.
    // The use will be evidient when we will try to make the port
    // collection from the tags while saving the circuit as an IC.
    public PortCollection() {

    }

    public PortCollection(DCDGateModel gateModel, int type, BitSet initialCharge) {

        this.parentGateModel = gateModel ;
        this.type = type ;
        chargeStatus = initialCharge ;
    }

    public void setChargeStatus(BitSet chargeStatus) {

        this.chargeStatus = chargeStatus ;
    }

    public int getNumPorts() {

        return ports.size() ;
    }

    public Enumeration getPorts() {

        return (ports.elements()) ;
    }

    // This returns a port at a given index.
    public Port getPort(int id) {

        Port p = (Port) ports.elementAt(id) ;
        return (p) ;
    }

    // This returns the port in the port collection which
    // has the id == id passed. This function makes it possible
    // for the portcollection to have unordered ports.
    public Port getPortWithID(int id) {

        int numPorts = ports.size() ;
        for (int i = 0; i < numPorts; i++) {
            Port p = (Port) ports.elementAt(i) ;
            if (p.getID() == id)
                return p ;
        }
        return null ;
    }

    // This adds a port to the existing collection of ports.
    public void addPort(Port port) {

        ports.addElement(port) ;
    }

    // This is called upon by the ith port if it has been activated
    // by some connector.
    // In case of an IC activation, the scenario is a bit different.
    // In case of an IC we neednot check whether all the input ports
    // have been activated befor firing the IC. We have to directly
    // tell the IC model to fire the fire the IC or do whatever
    // action it requries to take.
    public void activate(boolean state, int idOfPort, int typeOfCharge) {

        // What the hell .. we can't backfire .. can we ??
        if (this.type == OUTPUT)
            return ;

        // IC firing logic. -STARTS

        // Control will enter inside this part if this instance is the input
        // port colleciton
        // of the IC or the internal output port collection of the IC.
        if (parentGateModel instanceof DCDICModel) {
            if (this.type == PortCollection.IC_INTERNAL_OUTPUT) {
                // Get the output port collection of the IC and then get the
                // port
                // with the id equal to idOfPort .. fire the port.
                ((DCDICModel) parentGateModel).fireExternal(state, idOfPort,
                        typeOfCharge) ;
            }
            else if (this.type == PortCollection.INPUT) {
                ((DCDICModel) parentGateModel).fireInternal(state, idOfPort,
                        typeOfCharge) ;
            }
            return ;
        }
        // IC firing logic. -ENDS

        // First we have to set the corrosponding bit in the activation state
        if (state == true)
            chargeStatus.set(idOfPort) ;
        else
            chargeStatus.clear(idOfPort) ;

        activationStatus.set(idOfPort) ;
        // Then we have to check wether this input collection is completely
        // activated .. if yes fire the gate.The after firing we have
        // to set the gate's activation status to unactivated.
        int size = ports.size() ;
        for (int i = 0; i < size; i++) {
            // Here we are checking whether the RS Flip Flop is a clock
            // or not. This is so because non clocked RS Flip Flops are used
            // to build RAMs. Hence we are trying to see if there is any
            // connector attached to the clock port of the RSFlip Flop... if not
            // then we can assume that the flip flop is not clocked.
            if (i == 1)
                if (parentGateModel instanceof DCDRSFlipFlopModel)
                    if (parentGateModel.getInputPortCollection().getPortWithID(
                            1).getDCDConnector() == null) {
                        chargeStatus.set(1) ;
                        ((DCDFlipFlopModel) parentGateModel)
                                .transferChargeToBuffer() ;
                        continue ;
                    }
            if (activationStatus.get(i) == false) {
                return ;
            }
        }

        // This means that it is fully charged.
        String parentGateName = parentGateModel.getDCDGate().getName() ;
        if (parentGateName.startsWith("D@"))
            DCDUtility.getInstance().setMessageString(
                    "Activating "
                            + parentGateName
                            + " I/P:"
                            + DCDUtility.getInstance().printCharge(
                                    chargeStatus, ports.size())) ;
        parentGateModel.fire(chargeStatus) ;

        // Only for IC we don't have to clear the activation status .. but for
        // all else
        // we better do that.
        if (!((parentGateModel instanceof DCDICModel) || (parentGateModel instanceof DCDFlipFlopModel)))
            activationStatus = new BitSet() ;
        // chargeStatus = new BitSet();
    }

    // This is called if and only if the gate of which this is a
    // ouputPortCollection is completely activated and fires.
    public void fire(BitSet outputCharge) {

        // This should ask each port to fire passing it the
        // proper charge from the outputCharge.
        String parentGateName = parentGateModel.getDCDGate().getName() ;
        if (parentGateName.startsWith("D@"))
            DCDUtility.getInstance().setMessageString(
                    "Firing "
                            + parentGateName
                            + " O/P:"
                            + DCDUtility.getInstance().printCharge(
                                    outputCharge, ports.size())) ;

        int size = ports.size() ;
        for (int i = 0; i < size; i++) {
            // ((Port)ports.elementAt(i)).activateConnector(outputCharge.get(i),DCDInputGateModel.STEADY);
            getPortWithID(i).activateConnector(outputCharge.get(i),
                    DCDInputGateModel.STEADY) ;
        }
        // Set the status of the gates to false.
        activationStatus = new BitSet() ;
        chargeStatus = outputCharge ;
    }

    public void activatePortWithID(boolean charge, int idOfPort,
            int chargeStatus) {

        Port port = this.getPortWithID(idOfPort) ;
        if (port != null) {
            port.activateConnector(charge, chargeStatus) ;
        }
    }

    public void clearActivationStatus(int portID) {

        activationStatus.clear(portID) ;
    }

    public void cleanUp() {

        int size = ports.size() ;
        // Loop thru the ports.
        for (int i = 0; i < size; i++) {
            ((Port) ports.elementAt(i)).cleanUp() ;
        }
    }

    public String getSavingInformation() {

        StringBuffer sBuf = new StringBuffer() ;
        sBuf.append("\t\t\t\t\t<PortCollection NumPorts=\"" + ports.size()
                + "\" >\n") ;
        for (Enumeration e = getPorts(); e.hasMoreElements();) {
            Port port = (Port) e.nextElement() ;
            sBuf.append("\t\t\t\t\t" + port.getSavingInformation() + "\n") ;
        }
        sBuf.append("\t\t\t\t\t</PortCollection>") ;
        return (sBuf.toString()) ;
    }

    public boolean isFloating() {

        for (Enumeration e = getPorts(); e.hasMoreElements();) {
            if (!((Port) e.nextElement()).isFloating())
                return (false) ;
        }
        return (true) ;
    }

    public boolean getCharge(int portID) {

        return (chargeStatus.get(portID)) ;
    }

    // This method starts operating one level above the PortCollection
    // node .. hence we need to hunt for the PortCollection node here itself.
    // Note.. before IC was introduced.. PortCollection was the only
    // node inside InputPorts and OutputPorts hence we always took
    // the first node assuming it to be the PortCollection node. After the
    // IC was introduced we have to hunt for the node outself.
    public static void populatePortCollectionFromDOMNode(Node node,
            Hashtable connectorHash, PortCollection portCollection) {

        Node portCollectionNode = null ;
        NodeList nodeList = null ;
        int nodeLength = 0 ;

        nodeList = node.getChildNodes() ;

        for (int j = 0; j < nodeList.getLength(); j++) {
            String nodeName = nodeList.item(j).getNodeName() ;
            if (!nodeName.equals("#text")) {
                portCollectionNode = nodeList.item(j) ;
                if (nodeName.equals("PortCollection")) {
                    nodeList = portCollectionNode.getChildNodes() ;
                    nodeLength = nodeList.getLength() ;

                    for (int i = 0; i < nodeLength; i++) {
                        nodeName = nodeList.item(i).getNodeName() ;
                        Node n = nodeList.item(i) ;
                        if (nodeName.equals("Port")) {
                            int portID = 0 ;
                            String connectorID = null ;
                            NodeList childNodeList = n.getChildNodes() ;
                            int length = childNodeList.getLength() ;

                            for (int k = 0; k < length; k++) {
                                Node childNode = childNodeList.item(k) ;
                                String childNodeName = childNode.getNodeName() ;
                                if (childNodeName.equals("ID")) {
                                    NamedNodeMap att = childNode
                                            .getAttributes() ;
                                    portID = new Integer(att.item(0)
                                            .getNodeValue().trim()).intValue() ;
                                }
                                else if (childNodeName.equals("ConnectorID")) {
                                    NamedNodeMap att = childNode
                                            .getAttributes() ;
                                    connectorID = att.item(0).getNodeValue()
                                            .trim() ;
                                }
                            }

                            DCDConnector connector = (DCDConnector) connectorHash
                                    .get(connectorID) ;
                            if (connector != null) {
                                Port port = portCollection
                                        .getPortWithID(portID) ;

                                port.setDCDConnector(connector) ;
                                Port starting = connector.getStartingPort() ;
                                if (starting == null) {
                                    connector.setStartingPort(port) ;
                                }
                                else {
                                    connector.setEndingPort(port) ;
                                }
                            }

                        }
                    }
                    return ;
                }
            }
        }

    }

}