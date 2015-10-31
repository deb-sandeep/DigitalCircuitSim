package com.sandy.apps.dcs.factory ;

import java.util.Enumeration ;
import java.util.Hashtable ;

import org.w3c.dom.NamedNodeMap ;
import org.w3c.dom.Node ;
import org.w3c.dom.NodeList ;

import com.sandy.apps.dcs.component.DCDConnector ;
import com.sandy.apps.dcs.component.model.DCDDefaultConnectorModel ;
import com.sandy.apps.dcs.component.view.DCDDefaultConnectorUI ;

public class DCDConnectorFactory {

    // This method populates the connector hash with the
    // connectors internal ID and the reference to the instance
    // if the connector. This implements the visitor pattern.
    public static Enumeration getDCDConnectorsFromDOMNode( Node node,
            Hashtable connectorHash ) {

        NodeList nodeList = null ;
        int length = 0 ;
        // connectorHash = new Hashtable();
        nodeList = node.getChildNodes() ;
        length = nodeList.getLength() ;

        for( int i = 0 ; i < length ; i++ ) {
            String nodeName = nodeList.item( i ).getNodeName() ;
            if( !nodeName.equals( "#text" ) ) {
                Node n = nodeList.item( i ) ;
                if( nodeName.equals( "DCDConnector" ) ) {
                    DCDConnector conn = getDCDConnectorFromDOMNode( n ) ;
                    connectorHash.put( conn.getInternalID(), conn ) ;
                }
            }
        }
        return ( resolveReferences( connectorHash ) ) ;
    }

    // The passed hashtable has
    // Key : The connector ID
    // Value : The DCDConnector instance
    // I have to loop thru the values and resolve the reference that it
    // contains to the connector.
    private static Enumeration resolveReferences( Hashtable connectorHash ) {

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
    private static DCDConnector getDCDConnectorFromDOMNode( Node node ) {

        NamedNodeMap attributes = null ;
        NodeList nodeList = null ;
        int nodeLength = 0 ;
        int attrLength = 0 ;
        boolean isBranch = false ;
        String parentID = "" ;
        String id = "" ;
        String name = "" ;
        String shortDesc = "" ;
        String longDesc = "" ;

        DCDConnector connector = null ;
        DCDDefaultConnectorUI ui = null ;
        DCDDefaultConnectorModel model = null ;

        nodeList = node.getChildNodes() ;
        nodeLength = nodeList.getLength() ;

        // First get the attributes of the Connector.
        attributes = node.getAttributes() ;
        attrLength = attributes.getLength() ;
        for( int i = 0 ; i < attrLength ; i++ ) {

            Node attribute = attributes.item( i ) ;
            String attributeName = attribute.getNodeName() ;
            if( attributeName.equals( "isBranch" ) ) {
                isBranch = new Boolean( attribute.getNodeValue() )
                        .booleanValue() ;
            }
            else if( attributeName.equals( "parentID" ) ) {
                if( !isBranch ) System.out
                        .println( "[S][What the hell is this doin here.. this is not a brnach]:" ) ;
                else {
                    parentID = attribute.getNodeValue() ;
                }
            }
            else if( attributeName.equals( "name" ) ) {
                name = attribute.getNodeValue() ;
            }

        }
        // Now we have to load the UI with the information of the lines.
        nodeList = node.getChildNodes() ;
        nodeLength = nodeList.getLength() ;

        for( int i = 0 ; i < nodeLength ; i++ ) {
            String nodeName = nodeList.item( i ).getNodeName() ;
            if( !nodeName.equals( "#text" ) ) {
                Node n = nodeList.item( i ) ;
                if( nodeName.equals( "ID" ) ) {
                    NamedNodeMap att = n.getAttributes() ;
                    id = att.item( 0 ).getNodeValue().trim() ;
                }
                if( nodeName.equals( "ShortDescription" ) ) {
                    shortDesc = n.getChildNodes().item( 0 ).getNodeValue()
                            .trim() ;
                }
                if( nodeName.equals( "LongDescription" ) ) {
                    longDesc = n.getChildNodes().item( 0 ).getNodeValue()
                            .trim() ;
                }
                if( nodeName.equals( "DCDConnectorUI" ) ) {
                    ui = DCDConnectorUIFactory.getDCDConnectorUIFromDOMNode( n ) ;
                }
            }
        }
        // Now we can create the Connector
        connector = new DCDConnector() ;
        if( isBranch ) connector.setParentID( parentID ) ;

        // Now I have to make the model of the connector.
        // I think I have to add a new constructor .. let see.
        model = new DCDDefaultConnectorModel() ;
        model.setDCDConnector( connector ) ;
        model.setBranch( isBranch ) ;
        // Setting the ui
        ui.setDCDConnector( connector ) ;

        connector.setDCDDefaultConnectorModel( model ) ;
        connector.setDCDDefaultConnectorUI( ui ) ;
        connector.setInternalID( id ) ;
        connector.setName( name ) ;
        connector.setShortDescription( shortDesc ) ;
        connector.setLongDescription( longDesc ) ;
        return ( connector ) ;
    }

}