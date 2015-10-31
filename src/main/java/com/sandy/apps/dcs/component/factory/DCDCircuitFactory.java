package com.sandy.apps.dcs.component.factory ;

import java.io.File ;
import java.io.FileReader ;
import java.util.Enumeration ;
import java.util.Hashtable ;

import org.w3c.dom.Document ;
import org.w3c.dom.Element ;
import org.w3c.dom.Node ;
import org.w3c.dom.NodeList ;
import org.xml.sax.InputSource ;

import com.sandy.apps.dcs.component.DCDClock ;
import com.sandy.apps.dcs.component.DCDFlipFlop ;
import com.sandy.apps.dcs.component.DCDIC ;
import com.sandy.apps.dcs.component.DCDInputGate ;
import com.sandy.apps.dcs.component.DCDPulseInputGate ;
import com.sandy.apps.dcs.component.DCDSteadyInputGate ;
import com.sandy.apps.dcs.ui.CircuitController ;
import com.sandy.apps.dcs.util.Chain ;
import com.sun.org.apache.xerces.internal.parsers.DOMParser ;

// This is the factory class for loading the DCDCircuit from the .dcd
// file. When I talk of loading a complete circuit I mean also populating
// the values in the DCDController. The values that have to be populated
// in the DCDController are the following:
// 1) Vector of inputGates
// 2) ChainHead .. this is the chain of responsibility of the Chain elements.
// Just with these two piece of information we can get the circuit running
// once we have loaded it.
public class DCDCircuitFactory {

    // This method takes the responsibility of loading the
    // DCDCircuit from the file specified as the input. The
    // information extracted from the file are fed into the
    // DCDController. So that by the end of the loading
    // process the application is ready to run the new DCDCircuit that
    // is loaded.

    // <!ELEMENT DCDCircuit (Description?,Connectors,Gates,ICs?)>
    // This hashtable is a visitor that stores the following information.
    // Key : The connector ID.
    // Value : A reference to the connector instance.
    // This hashtable is used for easy lookup of the connector.... while loading
    // the
    // circuit.
    // private static Hashtable connectorHash = new Hashtable();

    public static void loadDCDCircuitFromFile( File file,
            CircuitController controller ) throws Exception {

        // Declaring the variables.
        DOMParser parser = null ;
        Document document = null ;
        Element element = null ;

        parser = new DOMParser() ;
        parser.parse( new InputSource( new FileReader( file ) ) ) ;
        document = parser.getDocument() ;
        element = document.getDocumentElement() ;
        loadDCDCircuitFromDOMElement( element, controller ) ;

    }

    // This function returns a Hashtable of connector hashes because
    // this hashtable will be used by the IC factory while loading the IC.
    public synchronized static Hashtable loadDCDCircuitFromDOMElement(
            Element element, CircuitController controller ) {

        NodeList nodeList = null ;
        String description = null ;
        nodeList = element.getChildNodes() ;
        Hashtable connectorHash = new Hashtable() ;
        for( int i = 0 ; i < nodeList.getLength() ; i++ ) {
            String nodeName = nodeList.item( i ).getNodeName() ;
            if( !nodeName.equals( "#text" ) ) {
                Node node = nodeList.item( i ) ;
                if( nodeName.equals( "LongDescription" ) ) {
                    description = node.getChildNodes().item( 0 ).getNodeValue()
                            .trim() ;
                    controller.setCircuitDescription( description ) ;
                }
                if( nodeName.equals( "Connectors" ) ) {
                    loadConnectors( node, controller, connectorHash ) ;
                }
                if( nodeName.equals( "Gates" ) ) {
                    loadGates( node, controller, connectorHash ) ;
                }
                if( nodeName.equals( "ICs" ) ) {
                    loadICs( node, controller, connectorHash ) ;
                }
                if( nodeName.equals( "DCDMessages" ) ) {
                    loadDCDMessages( node, controller ) ;
                }
            }
        }
        return ( connectorHash ) ;

    }

    private static void loadConnectors( Node node,
            CircuitController controller, Hashtable connectorHash ) {

        // It can be assumed that the ConnectorFactory will take the
        // responsibility of resolving all the references to the
        // child and the parent connectors.
        Enumeration e = DCDConnectorFactory.getDCDConnectorsFromDOMNode( node,
                connectorHash ) ;
        // Now we just have to thread the connectors in the chain of
        // responsibility.
        for( ; e.hasMoreElements() ; ) {
            Chain c = (Chain) e.nextElement() ;
            controller.addLinkToChain( c ) ;
            // DCDComponent.incrementID();
        }
    }

    // This function will load the gates from the gate collection in the
    // gate collection. This is real ummmmmmmmmm.
    // What do I have to do ?
    // Gates node will have multiple nodes of the type DCDGate inside it.
    // we can pass the node to the DCDGateFactory and ask for the gate.
    // Then we can resolve the connector references here in a static method
    // of this function and can add the gate to the chain in the controller.
    private static void loadGates( Node node, CircuitController controller,
            Hashtable connectorHash ) {

        // NOTE: Here the connectorHash has been already populated while
        // extracting the nodes from the xml file.
        Enumeration e = DCDGateFactory.getInstance().getDCDGatesFromDOMNode(
                node, connectorHash ) ;
        // Now we just have to thread the connectors in the chain of
        // responsibility.
        for( ; e.hasMoreElements() ; ) {
            Chain c = (Chain) e.nextElement() ;
            controller.addLinkToChain( c ) ;
            // DCDComponent.incrementID();
            if( c instanceof DCDSteadyInputGate ) controller
                    .addInputGate( (DCDInputGate) c ) ;
            if( c instanceof DCDPulseInputGate ) controller
                    .addPulseInputGate( (DCDPulseInputGate) c ) ;
            if( c instanceof DCDFlipFlop ) controller
                    .addFlipFlop( (DCDFlipFlop) c ) ;
            if( c instanceof DCDClock ) controller.addClock( (DCDClock) c ) ;
        }
    }

    public static void loadICs( Node node, CircuitController controller,
            Hashtable connectorHash ) {

        Enumeration e = DCDICFactory.getInstance().getDCDICsFromDOMNode( node,
                connectorHash ) ;
        for( ; e.hasMoreElements() ; ) {
            Chain c = (Chain) e.nextElement() ;
            controller.addLinkToChain( c ) ;
            controller.addIC( (DCDIC) c ) ;
        }
    }

    public static void loadDCDMessages( Node node, CircuitController controller ) {

        Enumeration e = new DCDMessageFactory().getDCDMessagesDOMNode( node ) ;
        for( ; e.hasMoreElements() ; ) {
            Chain c = (Chain) e.nextElement() ;
            controller.addLinkToChain( c ) ;
        }
    }

    public static void main( String[] args ) throws Exception {

        DCDCircuitFactory.loadDCDCircuitFromFile( new File( "functional.xml" ),
                null ) ;
    }
}