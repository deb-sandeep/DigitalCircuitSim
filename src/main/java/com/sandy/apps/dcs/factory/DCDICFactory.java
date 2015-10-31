package com.sandy.apps.dcs.factory ;

import java.awt.geom.Point2D ;
import java.io.File ;
import java.io.FileReader ;
import java.io.InputStream ;
import java.util.ArrayList ;
import java.util.Enumeration ;
import java.util.Hashtable ;
import java.util.List ;
import java.util.Vector ;

import org.w3c.dom.Document ;
import org.w3c.dom.Element ;
import org.w3c.dom.NamedNodeMap ;
import org.w3c.dom.Node ;
import org.w3c.dom.NodeList ;
import org.xml.sax.InputSource ;

import com.sandy.apps.dcs.common.DCDUtility ;
import com.sandy.apps.dcs.component.DCDConnector ;
import com.sandy.apps.dcs.component.DCDIC ;
import com.sandy.apps.dcs.component.DCDTag ;
import com.sandy.apps.dcs.component.model.DCDICModel ;
import com.sandy.apps.dcs.component.model.Port ;
import com.sandy.apps.dcs.component.model.PortCollection ;
import com.sandy.apps.dcs.component.view.DCDICUI ;
import com.sun.org.apache.xerces.internal.parsers.DOMParser ;

// This is a singleton class which is used to load IC's from
// the DOM node that the Circuit factory traverses.
public class DCDICFactory {

    private static DCDICFactory instance          = null ;

    private static List<String> primordialICNames = new ArrayList<String>() ;

    static {
        primordialICNames.add( "FullAdder" ) ;
        primordialICNames.add( "HalfAdder" ) ;
        primordialICNames.add( "4BitAdder" ) ;
        primordialICNames.add( "3BitCounter" ) ;
        primordialICNames.add( "SevenSegment" ) ;
        primordialICNames.add( "BCDAdder" ) ;
        primordialICNames.add( "4X1MUX" ) ;
        primordialICNames.add( "BCDCounter" ) ;
        primordialICNames.add( "3X8Decoder" ) ;
        primordialICNames.add( "BinaryCell" ) ;
        primordialICNames.add( "8X8RAM" ) ;
        primordialICNames.add( "Mod6Counter" ) ;
    }

    private DCDICFactory() {

    }

    public static DCDICFactory getInstance() {

        if( instance == null ) instance = new DCDICFactory() ;
        return ( instance ) ;
    }

    public static List<String> getPrimordialICNames() {

        return primordialICNames ;
    }

    public static boolean isPrimordialIC( String icName ) {

        return ( primordialICNames.contains( icName ) ) ;
    }

    // Here the node is the ICs node in the circuit file. A Circuit
    // is made up of connectors, gates and ICs. ICs are inturn made
    // up of connectors, gates and IC's thus ICs can be thought of
    // as a circuit without the input and output gates.
    // connectorHash is the hash of all the connectors in the circuit.
    public Enumeration getDCDICsFromDOMNode( Node node, Hashtable connectorHash ) {

        NodeList nodeList = null ;
        int length = 0 ;

        Vector ics = new Vector() ;

        nodeList = node.getChildNodes() ;
        length = nodeList.getLength() ;

        for( int i = 0 ; i < length ; i++ ) {
            String nodeName = nodeList.item( i ).getNodeName() ;
            if( !nodeName.equals( "#text" ) ) {
                Node n = nodeList.item( i ) ;
                if( nodeName.equals( "DCDIC" ) ) {
                    DCDIC ic = getDCDICFromDOMNode( n, connectorHash ) ;
                    ics.addElement( ic ) ;
                }
            }
        }
        return ( ics.elements() ) ;
    }

    // This is the DCDIC node. Many such nodes exist inside the ICs node
    // in the circuit file. Each IC refers to an external ic file.
    // The external IC file may be an primordial IC.. like Adder,
    // Multiplexer or IC which has been bundled with the release.
    // On the other hand the IC node might refer to an external file
    // in which case the application takes the responsibility of loading
    // the IC file from the system property called DCD.ic.path which
    // should be supplied at the command prompt or thru an menu command.
    //
    private DCDIC getDCDICFromDOMNode( Node node, Hashtable connectorHash ) {

        NamedNodeMap attributes = null ;
        int attrLength = 0 ;
        String fileName = null ;
        DCDIC ic = null ;

        // First get the attributes of the IC
        attributes = node.getAttributes() ;
        attrLength = attributes.getLength() ;

        for( int i = 0 ; i < attrLength ; i++ ) {
            Node attribute = attributes.item( i ) ;
            String attributeName = attribute.getNodeName() ;

            if( attributeName.equals( "fileName" ) ) {
                fileName = attribute.getNodeValue().trim() ;
                if( isPrimordialIC( fileName ) ) {
                    ic = getPrimordialDCDIC( fileName ) ;
                }
                else {
                    ic = getDCDICFromFile( fileName ) ;
                }
            }
            else if( attributeName.equals( "name" ) ) {
                if( ic != null ) ic.setName( attribute.getNodeValue().trim() ) ;
            }

        }

        // Now get the child nodes.. these will be the model and the UI
        // respectively. Model means the PortCollection information. This
        // means how the IC is connected to the external circuit. This will
        // contain the information as to how the ports are connected
        // to the connectors of the circuit to which the IC belongs.
        // The UI will contain the information as to the locaiton of the IC
        // on the workspace. This will happen if and only if the ic is not
        // null.
        if( ic != null ) {

            NodeList nodeList = null ;
            nodeList = node.getChildNodes() ;

            for( int i = 0 ; i < nodeList.getLength() ; i++ ) {
                String nodeName = nodeList.item( i ).getNodeName() ;
                if( !nodeName.equals( "#text" ) ) {
                    Node childNode = nodeList.item( i ) ;
                    if( nodeName.equals( "DCDGateModel" ) ) {
                        ic.updateModelFromDOMNode( childNode, connectorHash ) ;
                    }
                    if( nodeName.equals( "DCDGateUI" ) ) {
                        ic.updateUIFromDOMNode( childNode ) ;
                    }

                }
            }
        }
        else {
            System.out.println( "[S][The IC is null ..take care.]:" ) ;
        }
        return ( ic ) ;
    }

    // This will try to load an external IC. This should be so implemented
    // so that the application can pick up the file like something like
    // using the classpath.
    public DCDIC getDCDICFromFile( String fileName ) {

        DOMParser parser = null ;
        Document document = null ;
        Element element = null ;
        NodeList nodeList = null ;
        try {
            parser = new DOMParser() ;
            parser.parse( new InputSource(
                    new FileReader( new File( fileName ) ) ) ) ;
            document = parser.getDocument() ;
            element = document.getDocumentElement() ;
            DCDIC ic = getDCDICFromDOMElement( element ) ;
            ic.setFileName( fileName ) ;
            return ic ;
        }
        catch( Exception e ) {
            System.out.println( "[S][Some error in parsing the file]:"
                    + fileName ) ;
            e.printStackTrace() ;
        }
        return ( null ) ;

    }

    // This will try to load the IC which are shipped with
    // the installable. These IC's will include basic
    // IC's like, Adders, Multiplexers, Counters etc.
    public DCDIC getPrimordialDCDIC( String icName ) {

        InputStream in = null ;
        DOMParser parser = null ;
        Document document = null ;
        Element element = null ;

        try {
            in = DCDUtility.getInstance().getICResourceAsStream( icName ) ;
            parser = new DOMParser() ;
            parser.parse( new InputSource( in ) ) ;
            document = parser.getDocument() ;
            element = document.getDocumentElement() ;
            DCDIC ic = getDCDICFromDOMElement( element ) ;
            if( ic != null ) ic.setFileName( icName ) ;
            return ( ic ) ;
        }
        catch( Exception e ) {
            System.out
                    .println( "[S][Some error in parsing the IC from the file]:"
                            + icName ) ;
            e.printStackTrace() ;
        }
        return null ;
    }

    // This method loads the IC from DOM element which has been created from
    // the digestion of the .ic file. The IC that is being created in theis
    // function
    // has a dummy model and a dummy UI which is being populated in the
    // later stages.
    private DCDIC getDCDICFromDOMElement( Element element ) {

        // DCDICModel implements CircuitController hance we can use
        // the DCDCircuitFactory to load the IC.
        DCDICModel model = new DCDICModel() ;
        PortCollection internalInputPortCollection = new PortCollection( model,
                PortCollection.NOTYPE ) ;
        PortCollection internalOutputPortCollection = new PortCollection(
                model, PortCollection.IC_INTERNAL_OUTPUT ) ;

        // We call on the circuit factory to load the IC into the model
        // and we also get the hash of connectors so that we can
        // resolve the internal port collection. Here the model is considered as
        // a CircuitController.
        Hashtable connectorHash = null ;
        connectorHash = DCDCircuitFactory.loadDCDCircuitFromDOMElement(
                element, model ) ;
        String portInformation = getPortInformationFromDOMElement( element ) ;
        Vector tags = populateAndResolvePortCollectionsFromTagCollectionNode(
                element, internalInputPortCollection,
                internalOutputPortCollection, connectorHash ) ;
        model.setInternalInputPortCollection( internalInputPortCollection ) ;
        model.setInternalOutputPortCollection( internalOutputPortCollection ) ;
        model.setTags( tags ) ;

        DCDIC ic = new DCDIC() ;
        ic.setNumInputPorts( internalInputPortCollection.getNumPorts() ) ;
        ic.setNumOutputPorts( internalOutputPortCollection.getNumPorts() ) ;
        ic.setModel( model ) ;
        ic.setPortInformation( portInformation ) ;
        DCDICUI ui = new DCDICUI( ic ) ;
        ic.setUI( ui ) ;

        return ( ic ) ;

    }

    private String getPortInformationFromDOMElement( Element element ) {

        Node portInfoNode = element.getElementsByTagName( "PortInformation" )
                .item( 0 ) ;
        if( portInfoNode != null ) return portInfoNode.getNodeValue().trim() ;
        return "There is no port information available" ;

    }

    // This returns a vector of Tags which will be used to display the ports in
    // the
    // blown up IC.
    private Vector populateAndResolvePortCollectionsFromTagCollectionNode(
            Element element, PortCollection internalInputPortCollection,
            PortCollection internalOutputPortCollection, Hashtable connectorHash ) {

        NodeList tempNodeList = element.getElementsByTagName( "TagCollection" ) ;
        Node tagCollectionNode = tempNodeList.item( 0 ) ;
        Node tagNode = null ;
        Vector tags = new Vector() ;

        NodeList nodeList = null ;
        int nodeLength = 0 ;

        nodeList = tagCollectionNode.getChildNodes() ;

        for( int j = 0 ; j < nodeList.getLength() ; j++ ) {
            tagNode = nodeList.item( j ) ;
            String nodeName = tagNode.getNodeName() ;
            if( !nodeName.equals( "#text" ) && nodeName.equals( "Tag" ) ) {
                NamedNodeMap attributes = null ;
                String tagName = null ;
                String tagType = null ;
                String shortDesc = null ;
                String longDesc = null ;
                int attrLength = 0 ;
                NodeList nodeList1 = null ;
                Port port = null ;
                Point2D location = null ;

                attributes = tagNode.getAttributes() ;
                tagName = attributes.getNamedItem( "Name" ).getNodeValue()
                        .trim() ;
                tagType = attributes.getNamedItem( "Type" ).getNodeValue()
                        .trim() ;

                nodeList1 = tagNode.getChildNodes() ;
                int l1 = nodeList1.getLength() ;

                for( int h = 0 ; h < l1 ; h++ ) {
                    Node portNode = nodeList1.item( h ) ;
                    String portNodeName = portNode.getNodeName() ;

                    if( portNodeName.equals( "Port" ) ) {
                        int portID = 0 ;
                        String connectorID = null ;
                        NodeList childNodeList = portNode.getChildNodes() ;
                        int length = childNodeList.getLength() ;

                        for( int k = 0 ; k < length ; k++ ) {
                            Node childNode = childNodeList.item( k ) ;
                            String childNodeName = childNode.getNodeName() ;
                            if( childNodeName.equals( "ID" ) ) {
                                NamedNodeMap att = childNode.getAttributes() ;
                                portID = new Integer( att.item( 0 )
                                        .getNodeValue().trim() ).intValue() ;
                            }
                            else if( childNodeName.equals( "ConnectorID" ) ) {
                                NamedNodeMap att = childNode.getAttributes() ;
                                connectorID = att.item( 0 ).getNodeValue()
                                        .trim() ;
                            }
                        }

                        if( tagType.equals( "INPUT" ) ) {
                            port = new Port( internalInputPortCollection,
                                    portID ) ;
                            internalInputPortCollection.addPort( port ) ;
                        }
                        else if( tagType.equals( "OUTPUT" ) ) {
                            port = new Port( internalOutputPortCollection,
                                    portID ) ;
                            internalOutputPortCollection.addPort( port ) ;
                        }

                        DCDConnector connector = null ;
                        if( connectorHash != null ) connector = (DCDConnector) connectorHash
                                .get( connectorID ) ;
                        else {
                            System.out
                                    .println( "The intenal connector hash of the IC is null" ) ;
                        }
                        if( connector != null ) {
                            port.setDCDConnector( connector ) ;
                            Port starting = connector.getStartingPort() ;
                            if( starting == null ) {
                                connector.setStartingPort( port ) ;
                            }
                            else {
                                connector.setEndingPort( port ) ;
                            }
                        }

                    }
                    if( portNodeName.equals( "Location" ) ) {
                        NodeList childNodeList = portNode.getChildNodes() ;
                        int length = childNodeList.getLength() ;

                        for( int k = 0 ; k < length ; k++ ) {
                            Node childNode = childNodeList.item( k ) ;
                            String childNodeName = childNode.getNodeName() ;
                            if( childNodeName.equals( "Point" ) ) {
                                location = DCDConnectorUIFactory
                                        .getPoint2DFromDOMNode( childNode ) ;

                            }
                        }
                    }

                    if( portNodeName.equals( "ShortDescription" ) ) {
                        shortDesc = portNode.getChildNodes().item( 0 )
                                .getNodeValue().trim() ;
                    }

                    if( portNodeName.equals( "LongDescription" ) ) {
                        longDesc = portNode.getChildNodes().item( 0 )
                                .getNodeValue().trim() ;
                    }
                }
                DCDTag tag = new DCDTag( port, location ) ;
                tag.setShortDescription( shortDesc ) ;
                tag.setLongDescription( longDesc ) ;
                tags.addElement( tag ) ;

            }
        }
        return tags ;
    }

    public static void main( String[] args ) {

        DCDICFactory.getInstance().getDCDICFromFile( "simple.ic" ) ;
    }
}