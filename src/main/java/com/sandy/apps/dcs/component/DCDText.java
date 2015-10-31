package com.sandy.apps.dcs.component ;

import java.awt.event.* ;
import java.awt.geom.* ;
import java.util.* ;
import java.io.* ;

import com.sandy.apps.dcs.common.* ;
import com.sandy.apps.dcs.component.model.* ;
import com.sandy.apps.dcs.component.view.* ;
import com.sandy.apps.dcs.cor.Chain ;

// This is a text message that can be displayed on the screen.
// This can be used for displaying important messages on the screen.
public class DCDText extends DCDComponent {

    private DCDTextUI ui ;

    private String    message = "" ;

    public DCDText() {

    }

    public DCDText( Chain c ) {

        super( c, null, null ) ;
        ui = new DCDTextUI( this, null ) ;
        setUI( ui ) ;
    }

    public DCDText( Point2D location ) {

        ui = new DCDTextUI( this, location ) ;
        setModel( null ) ;
        setUI( ui ) ;
    }

    public DCDText( Point2D location, String text ) {

        ui = new DCDTextUI( this, location ) ;
        this.message = text ;
        setModel( null ) ;
        setUI( ui ) ;
    }

    public void setUI( DCDTextUI ui ) {

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

    public void handleMouseDrag( MouseEvent e ) {

        ui.handleMouseDragEvent( e ) ;
    }

    public void cleanUp() {

    }

    public String getType() {

        return "TEXT" ;
    }

    public String getSavingInformation() {

        StringBuffer buffer = new StringBuffer() ;
        buffer.append( "\t\t<DCDMessage value=\"" + message + "\""
                + ui.getSavingInformation() + "/>\n" ) ;
        return buffer.toString() ;
    }

    public String getText() {

        return message ;
    }

    public void setText( String message ) {

        this.message = message ;
    }

    public void setLocation( Point2D location ) {

        ui.setLocation( location ) ;
    }
}
