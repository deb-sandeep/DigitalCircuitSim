package com.sandy.apps.dcs.testing ;

import org.xml.sax.* ;
import java.io.* ;
import org.w3c.dom.* ;

import com.sun.org.apache.xerces.internal.parsers.DOMParser ;

public class DOMParserTest {

    public static void main( String[] args ) throws Exception {

        FileInputStream in = new FileInputStream( "Functional.xml" ) ;
        org.xml.sax.InputSource inputSource = new org.xml.sax.InputSource( in ) ;
        DOMParser parser = new DOMParser() ;
        parser.parse( inputSource ) ;
        org.w3c.dom.Document document = parser.getDocument() ;
        // System.out.println(document.getDoctype());
        Element element = document.getDocumentElement() ;
        NodeList nodeList = element.getChildNodes() ;
        for( int i = 0 ; i < nodeList.getLength() ; i++ ) {
            if( !nodeList.item( i ).getNodeName().equals( "#text" ) )
            ;
        }
        // System.out.println(nodeList);
        // System.out.println(nodeList.getLength());
        // for(int i=0;i<nodeList.getLength();i++)
        // {
        // Node n=nodeList.item(i);
        // System.out.println("[S][]:"+n.getNodeName());
        // if(n.getNodeName().equals("std_code"))
        // {
        // NodeList nl=n.getChildNodes();
        // System.out.println(nl.item(0).getNodeValue());
        //
        // }
        // }

    }
}