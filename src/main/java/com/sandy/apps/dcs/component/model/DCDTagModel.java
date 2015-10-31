// Source file: d:/sandy/dcd/DCD/component/model/DCDDefaultConnectorModel.java

package com.sandy.apps.dcs.component.model ;

import java.util.* ;
import java.io.* ;

import com.sandy.apps.dcs.component.* ;

public class DCDTagModel extends DCDComponentModel implements Serializable {

    private DCDTag tag ;

    private Port port ;

    private String type = DCDTag.INPUT ;

    private String name = "" ;

    public DCDTagModel() {

    }

    public DCDTagModel(DCDTag tag, Port port, String name, String type) {

        super(tag) ;
        this.tag = tag ;
        this.port = port ;
        this.type = type ;
        this.name = name ;
    }

    public DCDConnector getDCDConnector() {

        if (port != null)
            return port.getDCDConnector() ;
        return (null) ;
    }

    public int getTagID() {

        return port.getID() ;
    }

    public void setDCDConnector(DCDConnector dcdConnector) {

        if (port != null)
            port.setDCDConnector(dcdConnector) ;
    }

    public void update(Observable arg0, Object arg1) {

    }

    public void setPort(Port port) {

        this.port = port ;
    }

    public Port getPort() {

        return (this.port) ;
    }

    public String getName() {

        return this.name ;
    }

    public String getType() {

        return this.type ;
    }

}
