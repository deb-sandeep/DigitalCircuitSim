package com.sandy.apps.dcs.component.factory ;

import java.awt.geom.Point2D ;
import java.util.Enumeration ;
import java.util.Vector ;

import org.w3c.dom.NamedNodeMap ;
import org.w3c.dom.Node ;
import org.w3c.dom.NodeList ;

import com.sandy.apps.dcs.component.DCDText ;

// This class loads the messages from the DOM node.
public class DCDMessageFactory {

    public Enumeration getDCDMessagesDOMNode( Node node ) {

        NodeList nodeList = null ;
        int length = 0 ;

        Vector messages = new Vector() ;

        nodeList = node.getChildNodes() ;
        length = nodeList.getLength() ;

        for( int i = 0 ; i < length ; i++ ) {
            String nodeName = nodeList.item( i ).getNodeName() ;
            if( !nodeName.equals( "#text" ) ) {
                Node n = nodeList.item( i ) ;

                if( nodeName.equals( "DCDMessage" ) ) {
                    DCDText dcdText = getDCDMessageFromDOMNode( n ) ;
                    messages.addElement( dcdText ) ;
                }
            }
        }
        return ( messages.elements() ) ;
    }

    private DCDText getDCDMessageFromDOMNode( Node node ) {

        NamedNodeMap attributes = null ;
        int attrLength = 0 ;
        DCDText ic = null ;
        String message = "" ;
        int x = 0 ;
        int y = 0 ;

        attributes = node.getAttributes() ;
        attrLength = attributes.getLength() ;

        for( int i = 0 ; i < attrLength ; i++ ) {
            Node attribute = attributes.item( i ) ;
            String attributeName = attribute.getNodeName() ;

            if( attributeName.equals( "value" ) ) {
                message = attribute.getNodeValue().trim() ;
            }
            else if( attributeName.equals( "x" ) ) {
                x = Integer.parseInt( attribute.getNodeValue().trim() ) ;
            }
            else if( attributeName.equals( "y" ) ) {
                y = Integer.parseInt( attribute.getNodeValue().trim() ) ;
            }

        }
        DCDText text = new DCDText( new Point2D.Double( x, y ), message ) ;
        return text ;

    }

}