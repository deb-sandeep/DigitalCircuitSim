package com.sandy.apps.dcs.component ;

import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;

import javax.swing.JMenuItem ;

import org.w3c.dom.Node ;

import com.sandy.apps.dcs.component.model.DCDGateModel ;
import com.sandy.apps.dcs.component.model.DCDICModel ;
import com.sandy.apps.dcs.component.view.DCDGateUI ;
import com.sandy.apps.dcs.component.view.DCDICUI ;
import com.sandy.apps.dcs.ui.DCDICExpanderFrame ;
import com.sandy.apps.dcs.util.Chain ;

public class DCDIC extends DCDGate implements ActionListener {

    private DCDICUI    ui ;

    private DCDICModel model ;

    private String     fileName ;

    private String     portInformation ;

    // I don't know when this constructor can be used.
    public DCDIC( Chain c, DCDICModel model, DCDICUI ui ) {

        super( c, model, ui ) ;
        this.ui = ui ;
        this.model = model ;
    }

    public DCDIC() {

        super() ;
    }

    // I am atpresent using this constructor.
    // Using this constructor creates a 2 input and gate.
    // For creating multiple input AND gates use the constructor below.
    public DCDIC( Chain c ) {

        super( c, null, null ) ;
    }

    public DCDIC( Chain c, Node node ) {

    }

    public void setModel( DCDGateModel model ) {

        if( model instanceof DCDICModel ) {
            super.setModel( model ) ;
            this.model = (DCDICModel) model ;
        }
    }

    public void initialize() {

        model.initialize() ;
    }

    public void setUI( DCDGateUI ui ) {

        if( ui instanceof DCDICUI ) {
            super.setUI( ui ) ;
            this.ui = (DCDICUI) ui ;
        }
    }

    public int getNumPorts() {

        return ( model.getNumPorts() ) ;
    }

    public String getType() {

        return ( "IC" ) ;
    }

    // This is executed when the expand menuitem on the popup menu
    // of the IC is pressed. This will show the expanded view of the IC.
    public void actionPerformed( ActionEvent e ) {

        String actionCommand = ( (JMenuItem) e.getSource() ).getActionCommand() ;
        if( actionCommand.equals( "EXPAND" ) ) {
            new DCDICExpanderFrame( fileName, model ).setVisible( true ) ;
        }
        else if( actionCommand.equals( "PORT_INFORMATION" ) ) {
        }
        else if( actionCommand.equals( "NAME" ) ) {
            super.actionPerformed( e ) ;
        }

    }

    public void transferChargeToBuffer() {

        model.transferChargeToBuffer() ;
    }

    public String getFileName() {

        return this.fileName ;
    }

    public void setFileName( String fileName ) {

        this.fileName = fileName ;
    }

    public String getSavingInformation() {

        StringBuffer sBuf = new StringBuffer() ;
        sBuf.append( "\t\t<DCDIC fileName=\"" + fileName + "\" name=\""
                + this.getName() + "\">\n" ) ;
        sBuf.append( ui.getSavingInformation() + "\n" ) ;
        sBuf.append( model.getSavingInformation() + "\n" ) ;
        sBuf.append( "\t\t</DCDIC>\n" ) ;
        return ( sBuf.toString() ) ;
    }

    public String getPortInformation() {

        return this.portInformation ;
    }

    public void setPortInformation( String portInformation ) {

        this.portInformation = portInformation ;
    }

    public String getLongDescription() {

        return model.getLongDescription() ;
    }

}