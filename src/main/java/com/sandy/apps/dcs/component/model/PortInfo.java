
package com.sandy.apps.dcs.component.model ;

import java.awt.geom.* ;
import java.io.* ;

// This class encapsulates the information relating to a specific
// port on the gate. The ports in a gate are nomenclated as follows.
// INPUT_PORT OUTPUT_PORT
// +--------+
// (0)--| |--(0)
// | Gate |
// (1)--| |--(1)
// | |
// (2)--| |
// +--------+

public class PortInfo implements Serializable {

    public static final int INPUT_PORT = 0 ;

    public static final int OUTPUT_PORT = 1 ;

    // This contains whether it is an input port or output port
    // By default its set to -1, which means that it neither.
    private int portType = -1 ;

    // This is the ID of the port.
    private int portID = -1 ;

    // This is the location of the port on the screen.
    private Point2D portLocation ;

    // This is the reference to the Port
    private Port port ;

    public PortInfo() {

        // This is a null constructor that is used to create an
        // starting visitor.
    }

    public PortInfo(int portType, int portID, Point2D location) {

        this.portType = portType ;
        this.portID = portID ;
        this.portLocation = location ;
    }

    // This class has no setter methods for the port information
    // because the only way the port information can be saved
    // in an instance of this class if thru the constructor.
    public int getPortID() {

        return this.portID ;
    }

    public void setPortID(int i) {

        this.portID = i ;
    }

    public int getPortType() {

        return this.portType ;
    }

    public void setPortType(int t) {

        if (t == this.OUTPUT_PORT || t == this.INPUT_PORT)
            this.portType = t ;
        else
            System.out
                    .println("[S][ERROR:This is not a proper port type]:" + t) ;
    }

    public Point2D getPortLocation() {

        return (this.portLocation) ;
    }

    public void setPortLocation(Point2D p) {

        this.portLocation = p ;
    }

    public Port getPort() {

        return this.port ;
    }

    public void setPort(Port port) {

        this.port = port ;
    }

    public String toString() {

        StringBuffer buffer = new StringBuffer() ;
        buffer.append("\n\t PortID       = " + portID) ;
        buffer.append("\n\t portLocation = " + portLocation) ;
        buffer.append("\n\t portType     = " + portType) ;
        buffer.append("\n\t port         = " + port) ;
        return (buffer.toString()) ;

    }
}