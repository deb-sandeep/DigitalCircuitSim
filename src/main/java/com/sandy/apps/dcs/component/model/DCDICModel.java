
package com.sandy.apps.dcs.component.model ;

import java.awt.Graphics ;
import java.awt.event.MouseEvent ;
import java.util.Enumeration ;
import java.util.Observable ;
import java.util.Vector ;

import com.sandy.apps.dcs.common.DCDUtility ;
import com.sandy.apps.dcs.component.DCDClock ;
import com.sandy.apps.dcs.component.DCDComponent ;
import com.sandy.apps.dcs.component.DCDFlipFlop ;
import com.sandy.apps.dcs.component.DCDIC ;
import com.sandy.apps.dcs.component.DCDInputGate ;
import com.sandy.apps.dcs.component.DCDPulseInputGate ;
import com.sandy.apps.dcs.component.DCDTag ;
import com.sandy.apps.dcs.cor.Chain ;
import com.sandy.apps.dcs.ui.CircuitController ;

public class DCDICModel extends DCDGateModel implements CircuitController {

    private DCDIC ic ;

    private Vector inputGates = new Vector() ;

    private Vector ics = new Vector() ;

    private Vector flipFlops = new Vector() ;

    private Chain chainHead ;

    private Vector tags = null ;

    private PortCollection internalInputPortCollection ;

    private PortCollection internalOutputPortCollection ;

    private String longDescription = "" ;

    public DCDICModel(DCDIC gate) {

        super(gate) ;
        this.ic = gate ;
    }

    // This will be used when the Model needs to be created and
    // populated independently and the added to and DCDIC.
    // While adding is the setDCDIC method of this instance will be
    // called.
    public DCDICModel() {

    }

    public void setTags(Vector tags) {

        this.tags = tags ;
    }

    public void setDCDIC(DCDIC ic) {

        this.ic = ic ;
        // THis sets the gate of the super class DCDGateModel.
        super.setDCDGate(ic) ;
    }

    public DCDComponent getComponentAtPoint(MouseEvent e) {

        Chain c = chainHead ;
        while (c != null) {
            if (c.isSelected(e))
                return (DCDComponent) c ;
            c = c.getNextLink() ;
        }
        return (null) ;
    }

    public void setInternalInputPortCollection(PortCollection portCollection) {

        this.internalInputPortCollection = portCollection ;
        // Here I have to initialize the external port collection.
        int numPorts = portCollection.getNumPorts() ;
        for (int i = 0; i < numPorts; i++) {
            Port port = portCollection.getPort(i) ;
            Port newPort = new Port(inputPortCollection, port.getID()) ;
            inputPortCollection.addPort(newPort) ;
        }
    }

    public void setInternalOutputPortCollection(PortCollection portCollection) {

        this.internalOutputPortCollection = portCollection ;
        int numPorts = portCollection.getNumPorts() ;
        for (int i = 0; i < numPorts; i++) {
            Port port = portCollection.getPort(i) ;
            Port newPort = new Port(outputPortCollection, port.getID()) ;
            outputPortCollection.addPort(newPort) ;
        }
    }

    public void fireExternal(boolean state, int idOfPort, int typeOfCharge) {

        DCDUtility utility = DCDUtility.getInstance() ;
        Port port = outputPortCollection.getPortWithID(idOfPort) ;
        port.activateConnector(state, typeOfCharge) ;
        // Enumeration en = outputPortCollection.getPorts();
        // while (en.hasMoreElements())
        // {
        // Port p = (Port)en.nextElement();
        // if (p.getID() != idOfPort)
        // {
        // p.activateConnectorWithCurrentCharge();
        // }
        // }

    }

    public void fireInternal(boolean state, int idOfPort, int typeOfCharge) {

        Port port = internalInputPortCollection.getPortWithID(idOfPort) ;
        port.activateConnector(state, typeOfCharge) ;
    }

    // The UI needs to be updated if the model changes.
    // This method will be coded later.
    public void update(Observable arg0, Object arg1) {

    }

    // In the case of DCDICModels the truthtable need not be
    // populated. This mehod exists so as not to make this class
    // an abstract class.
    public void populateTruthTable() {

    }

    // In case of a IC there is nothing called as input ports and
    // putput ports, all are ports .. ie ....
    public void populatePortCollections() {

    }

    // I am not able to see at present how and when can a IC have
    // a clock within itself.
    public void addClock(DCDClock clock) {

    }

    // An IC will not have any InputGates inside it.
    public void addInputGate(DCDInputGate gate) {

        inputGates.addElement(gate) ;
    }

    public void addPulseInputGate(DCDPulseInputGate gate) {

    }

    public void addFlipFlop(DCDFlipFlop flipFlop) {

        flipFlops.addElement(flipFlop) ;
    }

    public void addIC(DCDIC ic) {

        ics.addElement(ic) ;
    }

    public void addLinkToChain(Chain c) {

        c.setNextLink(chainHead) ;
        chainHead = c ;
    }

    // This will require initializing all the flip flop this
    // ic contains and also recursively initlialize all the
    // ICs that this ic contains.
    public void initialize() {

        // First initialize the flip flops
        Enumeration en = flipFlops.elements() ;
        for (; en.hasMoreElements();) {
            DCDFlipFlop temp = (DCDFlipFlop) en.nextElement() ;
            temp.initialize() ;
        }

        en = flipFlops.elements() ;
        while (en.hasMoreElements()) {
            DCDFlipFlop flipFlop = (DCDFlipFlop) en.nextElement() ;
            flipFlop.transferChargeToBuffer() ;
        }

        // Secondly initialize the ics that this ic contains.
        en = ics.elements() ;
        for (; en.hasMoreElements();) {
            DCDIC temp = (DCDIC) en.nextElement() ;
            temp.initialize() ;
        }
    }

    public int getNumPorts() {

        return (internalInputPortCollection.getNumPorts() + internalOutputPortCollection
                .getNumPorts()) ;
    }

    // This I have to code.
    public boolean isPortAvailable(PortInfo portInfo) {

        int portID = portInfo.getPortID() ;

        // Check whether this port is a input or a output port.
        Port port = internalInputPortCollection.getPortWithID(portID + 1) ;
        if (port != null) {
            Port p = inputPortCollection.getPortWithID(portID + 1) ;
            if (p.getDCDConnector() == null) {
                portInfo.setPort(p) ;
                return true ;
            }
        }

        if (port == null) {
            port = internalOutputPortCollection.getPortWithID(portID + 1) ;
            if (port != null) {
                Port p = outputPortCollection.getPortWithID(portID + 1) ;
                if (p.getDCDConnector() == null) {
                    portInfo.setPort(p) ;
                    return true ;
                }
            }
        }
        return false ;
    }

    public void initiatePainting(Graphics g) {

        Chain c = chainHead ;
        while (c != null) {
            c.paint(g) ;
            c = c.getNextLink() ;
        }

        Enumeration en = tags.elements() ;
        while (en.hasMoreElements()) {
            DCDTag tag = (DCDTag) en.nextElement() ;
            tag.paint(g) ;
        }
    }

    public void transferChargeToBuffer() {

        Enumeration en = flipFlops.elements() ;
        while (en.hasMoreElements()) {
            DCDFlipFlop flipFlop = (DCDFlipFlop) en.nextElement() ;
            flipFlop.transferChargeToBuffer() ;
        }
    }

    public DCDTag getTagWithID(int id) {

        Enumeration en = tags.elements() ;
        while (en.hasMoreElements()) {
            DCDTag tag = (DCDTag) en.nextElement() ;
            if (tag.getTagID() == id)
                return tag ;
        }
        return null ;
    }

    public String[][] getTableDataForPorts() {

        String data[][] = new String[tags.size()][2] ;

        Enumeration en = tags.elements() ;
        int i = 0 ;
        while (en.hasMoreElements()) {
            DCDTag tag = (DCDTag) en.nextElement() ;
            data[i][0] = String.valueOf(tag.getTagID()) ;
            data[i][1] = tag.getShortDescription() ;
            i++ ;
        }

        return data ;
    }

    public void setCircuitDescription(String str) {

        this.longDescription = str ;
    }

    public String getLongDescription() {

        return longDescription ;
    }

    public void fireAllUnfiredFlipFlops() {

        Enumeration en = flipFlops.elements() ;
        while (en.hasMoreElements()) {
            DCDFlipFlop flipFlop = (DCDFlipFlop) en.nextElement() ;
            DCDFlipFlopModel model = (DCDFlipFlopModel) flipFlop.getModel() ;
            if (model.getFiredStatus() == false) {

                DCDUtility.getInstance().setMessageString(
                        "Firing a ff inside an IC") ;
                model.fireWithExistingCharge() ;
            }
        }

        en = ics.elements() ;
        while (en.hasMoreElements()) {
            DCDIC flipFlop = (DCDIC) en.nextElement() ;
            DCDICModel model = (DCDICModel) flipFlop.getModel() ;
            model.fireAllUnfiredFlipFlops() ;
        }

    }

    public void clearFiringStatusOfAllFlipFlops() {

        Enumeration en = flipFlops.elements() ;
        while (en.hasMoreElements()) {
            DCDFlipFlop flipFlop = (DCDFlipFlop) en.nextElement() ;
            DCDFlipFlopModel model = (DCDFlipFlopModel) flipFlop.getModel() ;
            model.setFiredStatus(false) ;
        }

        en = ics.elements() ;
        while (en.hasMoreElements()) {
            DCDIC flipFlop = (DCDIC) en.nextElement() ;
            DCDICModel model = (DCDICModel) flipFlop.getModel() ;
            model.clearFiringStatusOfAllFlipFlops() ;
        }
    }

}