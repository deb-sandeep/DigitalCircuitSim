package com.sandy.apps.dcs.factory ;

import java.awt.geom.Line2D ;
import java.awt.geom.Point2D ;

import org.w3c.dom.NamedNodeMap ;
import org.w3c.dom.Node ;
import org.w3c.dom.NodeList ;

import com.sandy.apps.dcs.component.view.DCDDefaultConnectorUI ;

public class DCDConnectorUIFactory {

    // <!ELEMENT DCDConnectorUI (Point)*>
    public static DCDDefaultConnectorUI getDCDConnectorUIFromDOMNode( Node node ) {

        NodeList nodeList = null ;
        int length = 0 ;
        DCDDefaultConnectorUI ui = new DCDDefaultConnectorUI() ;
        Point2D firstPoint = null ;
        Point2D secondPoint = null ;

        nodeList = node.getChildNodes() ;
        length = nodeList.getLength() ;

        for( int i = 0 ; i < length ; i++ ) {

            String nodeName = nodeList.item( i ).getNodeName() ;
            if( !nodeName.equals( "#text" ) ) {
                Node n = nodeList.item( i ) ;
                if( nodeName.equals( "Point" ) ) {
                    Point2D p = getPoint2DFromDOMNode( n ) ;
                    if( firstPoint == null ) {
                        // ui.setStartPoint(p);
                        firstPoint = p ;
                    }
                    else {
                        secondPoint = p ;
                        ui.addLine( new Line2D.Float( firstPoint, secondPoint ) ) ;
                        firstPoint = secondPoint ;
                    }
                }
            }
        }
        // ui.setEndPoint(secondPoint);
        return ( ui ) ;
    }

    // This creates a Point2D.Float instance from the DOM node.
    // The name of the node is Float.
    public static Point2D getPoint2DFromDOMNode( Node node ) {

        NamedNodeMap attributes = null ;
        float x = 0.0f ;
        float y = 0.0f ;
        int attrLength = 0 ;

        attributes = node.getAttributes() ;
        attrLength = attributes.getLength() ;
        for( int i = 0 ; i < attrLength ; i++ ) {
            Node attribute = attributes.item( i ) ;
            String attributeName = attribute.getNodeName() ;
            if( attributeName.equals( "x" ) ) {
                x = new Float( attribute.getNodeValue() ).floatValue() ;
            }
            if( attributeName.equals( "y" ) ) {
                y = new Float( attribute.getNodeValue() ).floatValue() ;
            }
        }

        return ( new Point2D.Float( x, y ) ) ;
    }

}