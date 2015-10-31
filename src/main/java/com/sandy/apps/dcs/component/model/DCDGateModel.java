
package com.sandy.apps.dcs.component.model ;

import java.io.Serializable ;
import java.util.BitSet ;
import java.util.Enumeration ;
import java.util.Hashtable ;

import org.w3c.dom.Node ;
import org.w3c.dom.NodeList ;

import com.sandy.apps.dcs.component.DCDGate ;

public abstract class DCDGateModel extends DCDComponentModel implements
        Serializable {

    // The gate to which this model belongs to.
    private DCDGate gate ;

    // This is the hashtable which stores the characteristic
    // of the gate. The keys represent all the possible input
    // combination of charges and the values represent the
    // output charges for the corrosponding input charges.
    // NOTE: These are being made protected because these might
    // be populated differently in the sub classes of this class.
    protected Hashtable truthTable = new Hashtable() ;

    // The following are the collection of the ports. There are two
    // port collections .. one representing the input port collection
    // and the second representing the output port collection.

    // This is the collection of input ports.
    protected PortCollection inputPortCollection ;

    // This is the collection of output ports.
    protected PortCollection outputPortCollection ;

    protected DCDGateModel(DCDGate gate) {

        super(gate) ;
        this.gate = gate ;
        inputPortCollection = new PortCollection(this, PortCollection.INPUT) ;
        outputPortCollection = new PortCollection(this, PortCollection.OUTPUT) ;
        // This way we are taking the responsibility here so that we
        // don't have to take care of calling the populateTruthTable
        // explicitly in the sub classes.
        populateTruthTable() ;
        populatePortCollections() ;
    }

    protected DCDGateModel() {

        inputPortCollection = new PortCollection(this, PortCollection.INPUT) ;
        outputPortCollection = new PortCollection(this, PortCollection.OUTPUT) ;
    }

    // This should first lookup the charge status in the lookup table
    // and then fire the output port collection with the looked up
    // charge bitset.
    public void fire(BitSet inputChargeStatus) {

        BitSet output = getOutputCharge(inputChargeStatus) ;
        outputPortCollection.fire(output) ;
    }

    // This does a lookup on the truth table and gets the corrosponding
    // output charge.
    public BitSet getOutputCharge(BitSet inputCharge) {

        return ((BitSet) truthTable.get(inputCharge)) ;
    }

    public boolean isPortAvailable(PortInfo portInfo) {

        int portType = portInfo.getPortType() ;
        int portID = portInfo.getPortID() ;
        if (portType == PortInfo.INPUT_PORT) {
            Port port = inputPortCollection.getPort(portID) ;
            if (port.getDCDConnector() == null) {
                portInfo.setPort(port) ;
                return (true) ;
            }
            return (false) ;
        }
        else if (portType == PortInfo.OUTPUT_PORT) {
            Port port = outputPortCollection.getPort(portID) ;
            if (port.getDCDConnector() == null) {
                portInfo.setPort(port) ;
                return (true) ;
            }
            return (false) ;
        }

        return (false) ;
    }

    public void cleanUp() {

        // This will ask the input and the output port collections to
        // release the connectors connected to the ports.
        if (inputPortCollection != null)
            inputPortCollection.cleanUp() ;
        if (outputPortCollection != null)
            outputPortCollection.cleanUp() ;
    }

    // This abstract function forces the sub classes to populate the
    // truth table.
    public abstract void populateTruthTable() ;

    public abstract void populatePortCollections() ;

    public DCDGate getDCDGate() {

        return this.gate ;
    }

    public void setDCDGate(DCDGate gate) {

        this.gate = gate ;
        super.setDCDComponent(gate) ;
    }

    public String getSavingInformation() {

        StringBuffer sBuf = new StringBuffer() ;
        sBuf.append("\t\t\t<DCDGateModel>\n") ;
        sBuf.append("\t\t\t\t<InputPorts>\n") ;
        sBuf.append("\t\t\t\t\t" + inputPortCollection.getSavingInformation()
                + "\n") ;
        sBuf.append("\t\t\t\t</InputPorts>\n") ;
        sBuf.append("\t\t\t\t<OutputPorts>\n") ;
        sBuf.append("\t\t\t\t\t" + outputPortCollection.getSavingInformation()
                + "\n") ;
        sBuf.append("\t\t\t\t</OutputPorts>\n") ;
        sBuf.append("\t\t\t</DCDGateModel>\n") ;
        return (sBuf.toString()) ;
    }

    public void populatePortCollectionsFromDOMNode(Node node,
            Hashtable connectorHash) {

        NodeList nodeList = null ;
        int nodeLength = 0 ;

        nodeList = node.getChildNodes() ;
        nodeLength = nodeList.getLength() ;

        for (int i = 0; i < nodeLength; i++) {
            String nodeName = nodeList.item(i).getNodeName() ;
            Node n = nodeList.item(i) ;
            if (nodeName.equals("InputPorts")) {
                PortCollection.populatePortCollectionFromDOMNode(n,
                        connectorHash, inputPortCollection) ;
            }
            else if (nodeName.equals("OutputPorts")) {
                PortCollection.populatePortCollectionFromDOMNode(n,
                        connectorHash, outputPortCollection) ;
            }
        }
    }

    public Enumeration getInputPorts() {

        return inputPortCollection.getPorts() ;
    }

    public Enumeration getOutputPorts() {

        return outputPortCollection.getPorts() ;
    }

    public int getNumInputPorts() {

        return inputPortCollection.getNumPorts() ;
    }

    // This will return a true if both the inputPortCollection and
    // the outputPortCollection's ports are not connected to any
    // connectors.
    public boolean isFloating() {

        return (inputPortCollection.isFloating() && outputPortCollection
                .isFloating()) ;
    }

    public PortCollection getInputPortCollection() {

        return (inputPortCollection) ;
    }

    public PortCollection getOutputPortCollection() {

        return (outputPortCollection) ;
    }

}
