package com.sandy.apps.dcs.component ;

import java.awt.event.* ;
import java.awt.geom.* ;
import java.util.* ;
import java.io.* ;

import com.sandy.apps.dcs.common.* ;
import com.sandy.apps.dcs.component.model.* ;
import com.sandy.apps.dcs.component.view.* ;
import com.sandy.apps.dcs.cor.Chain ;

// This is a tag.. .that can be used to identify the ports of a IC
// before saving the circuit as an IC.
public class DCDTag extends DCDComponent implements Serializable {

    private DCDTagModel        model ;

    private DCDTagUI           ui ;

    // This can be either a Input or and Output gate.
    private DCDGate            gate ;

    public static final String INPUT  = "INPUT" ;

    public static final String OUTPUT = "OUTPUT" ;

    public DCDTag() {

    }

    // This will create the tag with the port and the location information.
    // The location information will be used by the UI and the
    // Port information will be stored in the model.
    public DCDTag( Port port, Point2D location, DCDGate gate, String type ) {

        model = new DCDTagModel( this, port, "", type ) ;
        ui = new DCDTagUI( this, location ) ;
        this.gate = gate ;
        setModel( model ) ;
        setUI( ui ) ;
    }

    public DCDTag( Port port, Point2D location ) {

        model = new DCDTagModel( this, port, "", "" ) ;
        ui = new DCDTagUI( this, location ) ;
        setUI( ui ) ;
        setModel( model ) ;
    }

    public void setModel( DCDTagModel model ) {

        super.setModel( model ) ;
        this.model = model ;
    }

    public void setUI( DCDTagUI ui ) {

        super.setUI( ui ) ;
        this.ui = ui ;
    }

    public String getHashCode() {

        return ( null ) ;

    }

    public boolean handleMouseOver( MouseEvent e ) {

        return ( false ) ;
    }

    public void handleMouseRelease( MouseEvent e ) {

    }

    // A connector can only handle mouse drags when the SELECTOR
    // toggle button is in a pressed state.
    public void handleMouseDrag( MouseEvent e ) {

    }

    // DCDConnector is overriding the cleanUp of the DCDComponent
    // because this needs to delete the branch components also.
    public void cleanUp() {

    }

    public String getType() {

        if( model == null ) return "TAG" ;
        return ( model.getType() + "_TAG" ) ;
    }

    public DCDGate getDCDGate() {

        return ( this.gate ) ;
    }

    public boolean isSelected() {

        if( gate != null ) return ( gate.isSelected() ) ;
        return false ;
    }

    public Port getPort() {

        return model.getPort() ;
    }

    public String getSavingInformation() {

        StringBuffer buffer = new StringBuffer() ;
        buffer.append( "\t\t<Tag Name=\"" + model.getName() + "\" Type=\""
                + model.getType() + "\">\n" ) ;
        buffer.append( model.getPort().getSavingInformation() ) ;
        buffer.append( ui.getSavingInformation() ) ;
        if( !getShortDescription().equals( "" ) ) {
            buffer.append( "\t\t\t<ShortDescription>\n" ) ;
            buffer.append( getShortDescription() ) ;
            buffer.append( "\t\t\t</ShortDescription>\n" ) ;
        }

        if( !getLongDescription().equals( "" ) ) {
            buffer.append( "\t\t\t<LongDescription>\n" ) ;
            buffer.append( getLongDescription() ) ;
            buffer.append( "\t\t\t</LongDescription>\n" ) ;
        }

        buffer.append( "\t\t</Tag>\n" ) ;
        return buffer.toString() ;
    }

    public int getTagID() {

        return model.getTagID() ;
    }

}
