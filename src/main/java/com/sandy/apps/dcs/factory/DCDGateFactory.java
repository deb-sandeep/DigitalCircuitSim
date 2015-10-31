package com.sandy.apps.dcs.factory ;

import java.util.Enumeration ;
import java.util.Hashtable ;
import java.util.Vector ;

import org.w3c.dom.NamedNodeMap ;
import org.w3c.dom.Node ;
import org.w3c.dom.NodeList ;

import com.sandy.apps.dcs.component.DCDANDGate ;
import com.sandy.apps.dcs.component.DCDClock ;
import com.sandy.apps.dcs.component.DCDConnector ;
import com.sandy.apps.dcs.component.DCDDFlipFlop ;
import com.sandy.apps.dcs.component.DCDGate ;
import com.sandy.apps.dcs.component.DCDJKFlipFlop ;
import com.sandy.apps.dcs.component.DCDLED ;
import com.sandy.apps.dcs.component.DCDNANDGate ;
import com.sandy.apps.dcs.component.DCDNOTGate ;
import com.sandy.apps.dcs.component.DCDORGate ;
import com.sandy.apps.dcs.component.DCDPulseInputGate ;
import com.sandy.apps.dcs.component.DCDRSFlipFlop ;
import com.sandy.apps.dcs.component.DCDSevenSegmentDisplay ;
import com.sandy.apps.dcs.component.DCDSteadyInputGate ;
import com.sandy.apps.dcs.component.DCDTFlipFlop ;
import com.sandy.apps.dcs.component.DCDXORGate ;
import com.sandy.apps.dcs.component.model.DCDGateModel ;
import com.sandy.apps.dcs.component.view.DCDGateUI ;

public class DCDGateFactory {

    private static DCDGateFactory instance = null ;

    // This function loads the gates and resolves the connector reference from
    // the
    // hash table passes as the parameter. The hashtable that is passed
    // contains the ConnectorID as the key and the reference to the connector
    // instance as the value.
    private DCDGateFactory() {

    }

    public static DCDGateFactory getInstance() {

        if( instance == null ) instance = new DCDGateFactory() ;
        return ( instance ) ;
    }

    public Enumeration getDCDGatesFromDOMNode( Node node,
            Hashtable connectorHash ) {

        NodeList nodeList = null ;
        int length = 0 ;

        Vector gates = new Vector() ;
        nodeList = node.getChildNodes() ;
        length = nodeList.getLength() ;

        for( int i = 0 ; i < length ; i++ ) {
            String nodeName = nodeList.item( i ).getNodeName() ;
            if( !nodeName.equals( "#text" ) ) {
                Node n = nodeList.item( i ) ;
                if( nodeName.equals( "DCDGate" ) ) {
                    DCDGate gate = getDCDGateFromDOMNode( n, connectorHash ) ;
                    gates.addElement( gate ) ;
                }
            }
        }
        return ( gates.elements() ) ;
    }

    // The passed hashtable has
    // Key : The connector ID
    // Value : The DCDConnector instance
    // I have to loop thru the values and resolve the reference that it
    // contains to the connector.
    private Enumeration resolveReferences( DCDGate gate, Hashtable connectorHash ) {

        for( Enumeration e = connectorHash.elements() ; e.hasMoreElements() ; ) {
            DCDConnector connector = null ;
            DCDConnector parentConnector = null ;
            String parentID = null ;

            connector = (DCDConnector) e.nextElement() ;
            if( connector.isBranch() ) {
                parentID = connector.getParentID() ;
                parentConnector = (DCDConnector) connectorHash.get( parentID ) ;
                connector.setParent( parentConnector ) ;
                parentConnector.addBranch( connector ) ;
            }

        }
        return ( connectorHash.elements() ) ;
    }

    // <!-- ### DCD CONNECTORS ###-->
    // <!ELEMENT DCDConnector (ID,DCDConnectorUI?)>
    // <!ATTLIST DCDConnector isBranch ( true | false ) "false">
    // <!ATTLIST DCDConnector parentID CDATA #IMPLIED>
    // <!-- ### DCD CONNECTOR UI ###-->
    // <!ELEMENT DCDConnectorUI (Point)*>
    // <!-- ### POINT ###-->
    // <!ELEMENT Point EMPTY>
    // <!ATTLIST Point x CDATA #REQUIRED y CDATA #REQUIRED>
    private DCDGate getDCDGateFromDOMNode( Node node, Hashtable connectorHash ) {

        NamedNodeMap attributes = null ;
        int attrLength = 0 ;
        String typeOfGate = null ;
        DCDGate gate = null ;

        // First get the attributes of the Connector.
        attributes = node.getAttributes() ;
        attrLength = attributes.getLength() ;
        for( int i = 0 ; i < attrLength ; i++ ) {
            Node attribute = attributes.item( i ) ;
            String attributeName = attribute.getNodeName() ;
            if( attributeName.equals( "type" ) ) {
                typeOfGate = attribute.getNodeValue().trim() ;
            }
        }

        gate = getDCDGateFromDOMNode( node, connectorHash, typeOfGate ) ;

        return ( gate ) ;
    }

    // This is a method which takes a node and the connector hash
    // and a String representing the type of the gate and returns
    // the appropriate type of the gate based on the string.

    // Generically how is it to be done.
    // 1. First get the number of input ports from the attribute of the node
    // 2. Then create the gate with the number of input gates as the
    // construction parameter.
    // 3. Loop thru the child nodes of the node.
    // 3.1 Get the ID and set the internal ID of the gate to the value of the
    // node.
    // 3.2 Get the model node
    private DCDGate getDCDGateFromDOMNode( Node node, Hashtable connectorHash,
            String typeOfGate ) {

        // First we have to parse the node and segregate the infoemation.
        // Actually we will be needing the number of input ports.
        // But this is hidden deep inside the PortCollection node... have
        // to bring it to the top.
        NodeList nodeList = null ;
        NamedNodeMap attributes = null ;
        int attrLength = 0 ;
        int nodeLength = 0 ;
        String name = "" ;

        // These are the reference wchich will refer to the proper
        // instances of the classes. The type of class will be decided
        // upon the String representing the type of gate.
        DCDGate gate = null ;
        DCDGateUI gateUI = null ;
        DCDGateModel gateModel = null ;

        int numInputPorts = 0 ;

        // First get the number of input ports of the gate.
        attributes = node.getAttributes() ;
        attrLength = attributes.getLength() ;
        for( int i = 0 ; i < attrLength ; i++ ) {
            Node attribute = attributes.item( i ) ;
            String attributeName = attribute.getNodeName() ;
            if( attributeName.equals( "numInputPorts" ) ) {
                numInputPorts = new Integer( attribute.getNodeValue().trim() )
                        .intValue() ;
            }
            if( attributeName.equals( "name" ) ) {
                name = attribute.getNodeValue() ;
            }
        }

        // Great .. now that we have the number of input ports
        // we can go on and create the gate and return it.
        gate = getDCDGateInstance( numInputPorts, typeOfGate ) ;
        gate.setName( name ) ;

        // Now we have to parse the ID and set the name of this gate.
        // And also we have to resolve the port connections.
        nodeList = node.getChildNodes() ;
        nodeLength = nodeList.getLength() ;

        for( int i = 0 ; i < nodeLength ; i++ ) {
            String nodeName = nodeList.item( i ).getNodeName() ;
            if( !nodeName.equals( "#text" ) ) {
                Node n = nodeList.item( i ) ;
                if( nodeName.equals( "ID" ) ) {
                    NamedNodeMap att = n.getAttributes() ;
                    gate.setInternalID( att.item( 0 ).getNodeValue().trim() ) ;
                }
                if( nodeName.equals( "ShortDescription" ) ) {
                    gate.setShortDescription( n.getChildNodes().item( 0 )
                            .getNodeValue().trim() ) ;
                }
                if( nodeName.equals( "LongDescription" ) ) {
                    gate.setLongDescription( n.getChildNodes().item( 0 )
                            .getNodeValue().trim() ) ;
                }
                if( nodeName.equals( "DCDGateUI" ) ) {
                    // Instead of creating another UI instance
                    // we can use the default instance and update
                    // its properties.
                    gate.updateUIFromDOMNode( n ) ;
                }
                if( nodeName.equals( "DCDGateModel" ) ) {
                    // Instead of creating a new Model and then
                    // addind it to the gate ... I just have
                    // to update the model that was created when
                    // I had created the Gate.
                    gate.updateModelFromDOMNode( n, connectorHash ) ;
                }

            }
        }
        return ( gate ) ;
    }

    private DCDGate getDCDGateInstance( int numInputPorts, String typeOfGate ) {

        DCDGate gate = null ;
        // Loop over and create the appropriate type of gate depending
        // upon the String value of the typeOfGate
        if( typeOfGate.equals( "AND" ) ) {
            gate = new DCDANDGate( numInputPorts ) ;
        }
        if( typeOfGate.equals( "OR" ) ) {
            gate = new DCDORGate( numInputPorts ) ;
        }
        if( typeOfGate.equals( "NOT" ) ) {
            gate = new DCDNOTGate() ;
        }
        if( typeOfGate.equals( "NAND" ) ) {
            gate = new DCDNANDGate( numInputPorts ) ;
        }
        if( typeOfGate.equals( "STEADY_INPUT" ) ) {
            gate = new DCDSteadyInputGate() ;
        }
        if( typeOfGate.equals( "PULSE_INPUT" ) ) {
            gate = new DCDPulseInputGate() ;
        }
        if( typeOfGate.equals( "LED" ) ) {
            gate = new DCDLED() ;
        }
        if( typeOfGate.equals( "XOR" ) ) {
            gate = new DCDXORGate( numInputPorts ) ;
        }
        if( typeOfGate.equals( "JKFLIPFLOP" ) ) {
            gate = new DCDJKFlipFlop() ;
        }
        if( typeOfGate.equals( "RSFLIPFLOP" ) ) {
            gate = new DCDRSFlipFlop() ;
        }
        if( typeOfGate.equals( "DFLIPFLOP" ) ) {
            gate = new DCDDFlipFlop() ;
        }
        if( typeOfGate.equals( "TFLIPFLOP" ) ) {
            gate = new DCDTFlipFlop() ;
        }
        if( typeOfGate.equals( "CLOCK_INPUT" ) ) {
            gate = new DCDClock() ;
        }
        if( typeOfGate.equals( "SEVEN_SEGMENT_DISPLAY" ) ) {
            gate = new DCDSevenSegmentDisplay() ;
        }
        // TODO => Fill this up with the other if clauses.
        //
        return ( gate ) ;
    }

}