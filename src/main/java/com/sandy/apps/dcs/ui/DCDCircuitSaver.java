package com.sandy.apps.dcs.ui ;

import java.awt.Cursor ;
import java.io.File ;
import java.io.FileOutputStream ;
import java.io.IOException ;
import java.util.Enumeration ;
import java.util.Vector ;

import javax.swing.JFileChooser ;

import com.sandy.apps.dcs.component.DCDConnector ;
import com.sandy.apps.dcs.component.DCDGate ;
import com.sandy.apps.dcs.component.DCDIC ;
import com.sandy.apps.dcs.component.DCDInputGate ;
import com.sandy.apps.dcs.component.DCDLED ;
import com.sandy.apps.dcs.component.DCDTag ;
import com.sandy.apps.dcs.component.DCDText ;
import com.sandy.apps.dcs.util.Chain ;
import com.sandy.apps.dcs.util.DCDUtility ;

// This is the class which saves the circuit information.
// The circuit information is saved in the form of a XML file.
// The DTD that is being used is of the following fomat.
// <!-- edited with XML Spy v2.5 NT - http://www.xmlspy.com -->
// <!-- This is the DTD which describes the saving format of the application -->
// <!-- This is the root node of the saving file format -->
// <!-- ### CIRCUIT ###-->
// <!ELEMENT DCDCircuit (Description?,Connectors,Gates,ICs?)>
// <!-- ### DESCRIPTION ###-->
// <!-- This is a short description of the circuit. This might also include the
// developers notes -->
// <!ELEMENT Description ( #PCDATA )>
// <!-- ### CONNECTORS ###-->
// <!-- This is the collection DCDConnector which is used to connect the Ports
// of the gates and the IC's -->
// <!ELEMENT Connectors (DCDConnector)*>
// <!-- ### DCD CONNECTORS ###-->
// <!ELEMENT DCDConnector (ID,DCDConnectorUI?)>
// <!ATTLIST DCDConnector isBranch ( true | false ) "false">
// <!ATTLIST DCDConnector parentID CDATA #IMPLIED>
// <!-- ### DCD CONNECTOR UI ###-->
// <!ELEMENT DCDConnectorUI (Point)*>
// <!-- ### POINT ###-->
// <!ELEMENT Point EMPTY>
// <!ATTLIST Point x CDATA #REQUIRED y CDATA #REQUIRED>
// <!-- ### GATES ###-->
// <!-- This is the collection DCDGates which is used to make the circuit -->
// <!ELEMENT Gates (DCDGate)*>
// <!-- ### DCD GATE ###-->
// <!ELEMENT DCDGate (ID,DCDGateModel,DCDGateUI?)>
// <!ATTLIST DCDGate numInputPorts CDATA #REQUIRED>
// <!ATTLIST DCDGate type (AND|OR|XOR|NOT|NAND) #REQUIRED>
// <!-- ### DCD GATE MODEL ###-->
// <!ELEMENT DCDGateModel (InputPorts,OutputPorts)>
// <!-- ### INPUT PORTS ###-->
// <!ELEMENT InputPorts (PortCollection)>
// <!-- ### OUTPUT PORTS ###-->
// <!ELEMENT OutputPorts (PortCollection)>
// <!-- ### DCD GATE UI ###-->
// <!ELEMENT DCDGateUI (Point)>
// <!-- ### PORT COLLECTION ###-->
// <!ELEMENT PortCollection (Port)*>
// <!ATTLIST PortCollection NumPorts CDATA #REQUIRED>
// <!-- ### CONNECTOR ID LIST ###-->
// <!ELEMENT ConnectorIDList (ID)*>
// <!-- ### CONNECTOR UI ###-->
// <!ELEMENT ConnectorUI (Point)*>
// <!-- ### POINT ###-->
// <!ELEMENT Point EMPTY>
// <!ATTLIST Point x CDATA #REQUIRED y CDATA #REQUIRED>
// <!-- ### PORT ###-->
// <!ELEMENT Port (ID,ConnectorID)>
// <!ELEMENT ConnectorID EMPTY>
// <!ATTLIST ConnectorID value CDATA #REQUIRED>
// <!-- ### ICs ###-->
// <!-- These are the ICs that are used in the circuit. -->
// <!ELEMENT ICS (DCDIC)*>
// <!-- ### DCD IC ###-->
// <!ELEMENT DCDIC (DCDCircuit|DCDICFile)>
// <!-- ### DCD IC FILE ###-->
// <!ELEMENT DCDICFile EMPTY>
// <!ATTLIST DCDFile Name CDATA #REQUIRED>
// <!-- ### ID ###-->
// <!-- This is element which describes the ID of the gates in the circuit -->
// <!ELEMENT ID EMPTY>
// <!ATTLIST ID value CDATA #REQUIRED>
public class DCDCircuitSaver {

    // These are the vectors ( temporary storage ) where we will
    // store the DCDComponents before we ask them to
    private Vector       connectors ;

    private Vector       gates ;

    private Vector       ics ;

    private Vector       messages ;

    private JFileChooser fileChooser      = new JFileChooser(
                                                  new File( "" )
                                                          .getAbsolutePath() ) ;

    private String       name             = "" ;

    private String       shortDescription = "" ;

    private String       longDescription  = "" ;

    public DCDCircuitSaver() {

    }

    public void clearInfo() {

        name = "" ;
        shortDescription = "" ;
        longDescription = "" ;
    }

    public void setInfo( String name, String shortDescription,
            String longDescription ) {

        this.name = name ;
        this.shortDescription = shortDescription ;
        this.longDescription = longDescription ;
    }

    // We have to ask the user for the place where he wants to save the file.
    // Param tags => This is the vector of tags that the user has created
    // for saving the corcuit as an IC.
    public void save( Chain chainHead, boolean saveAsIC, Vector tags ) {

        DCDUtility utility = DCDUtility.getInstance() ;
        utility.getFrame().setCursor( new Cursor( Cursor.WAIT_CURSOR ) ) ;
        File currentFile = utility.getCurrentFile() ;
        if( currentFile == null ) {
            segregateComponents( chainHead, saveAsIC ) ;
            int choice = fileChooser.showSaveDialog( utility.getFrame() ) ;
            if( choice == JFileChooser.APPROVE_OPTION ) {
                currentFile = fileChooser.getSelectedFile() ;
                save( currentFile, saveAsIC, tags ) ;
                utility.setCurrentFile( currentFile ) ;
            }
        }
        else {
            segregateComponents( chainHead, saveAsIC ) ;
            save( currentFile, saveAsIC, tags ) ;
        }
        utility.getFrame().setCursor( new Cursor( Cursor.DEFAULT_CURSOR ) ) ;
    }

    private void getCircuitInfo( boolean saveAsIC ) {

        DCDNameDialog nameDialog = DCDNameDialog.getInstance() ;
        nameDialog.setTitle( "Enter circuit info" ) ;
        if( saveAsIC ) {
            nameDialog.setNameLabel( "IC Name" ) ;
        }
        nameDialog.show( name, shortDescription, longDescription ) ;
        longDescription = nameDialog.getLongDescription() ;
        shortDescription = nameDialog.getShortDescription() ;
        name = nameDialog.getName() ;
    }

    public void saveCircuitAs( Chain chainHead, boolean saveAsIC, Vector tags ) {

        DCDUtility utility = DCDUtility.getInstance() ;
        utility.getFrame().setCursor( new Cursor( Cursor.WAIT_CURSOR ) ) ;

        segregateComponents( chainHead, saveAsIC ) ;
        int choice = fileChooser.showSaveDialog( utility.getFrame() ) ;
        if( choice == JFileChooser.APPROVE_OPTION ) {

            File currentFile = fileChooser.getSelectedFile() ;
            save( currentFile, saveAsIC, tags ) ;
            utility.setCurrentFile( currentFile ) ;
        }

        utility.getFrame().setCursor( new Cursor( Cursor.DEFAULT_CURSOR ) ) ;
    }

    // This function does all the manual work of saving the worspace.
    private void save( File file, boolean saveAsIC, Vector tags ) {

        try {
            getCircuitInfo( saveAsIC ) ;
            FileOutputStream fout = new FileOutputStream( file ) ;
            write( fout, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" ) ;
            if( !saveAsIC ) write( fout, "<DCDCircuit>" ) ;
            else write( fout, "<DCDIC numPorts=\"" + tags.size() + "\" name=\""
                    + name + "\">" ) ;
            writeDescriptionString( fout ) ;
            writeConnectors( fout ) ;
            writeGates( fout ) ;
            writeICs( fout ) ;
            writeMessages( fout ) ;
            if( saveAsIC ) writeTagCollection( fout, tags ) ;
            if( !saveAsIC ) write( fout, "</DCDCircuit>" ) ;
            else write( fout, "</DCDIC>" ) ;
            fout.flush() ;
            fout.close() ;
        }
        catch( IOException e ) {
            System.out.println( "[S][Some problem in writing]:" ) ;
            e.printStackTrace() ;
        }

    }

    private void writeDescriptionString( FileOutputStream fout )
            throws IOException {

        if( shortDescription != null ) {
            write( fout, "\t<ShortDescription>" ) ;
            write( fout, "\t\t" + shortDescription ) ;
            write( fout, "\t</ShortDescription>" ) ;
        }
        if( longDescription != null ) {
            write( fout, "\t<LongDescription>" ) ;
            write( fout, "\t\t" + longDescription ) ;
            write( fout, "\t</LongDescription>" ) ;
        }
    }

    private void writeConnectors( FileOutputStream fout ) throws IOException {

        write( fout, "\t<Connectors>" ) ;

        for( Enumeration e = connectors.elements() ; e.hasMoreElements() ; ) {
            if( e == null ) break ;
            Chain connector = (Chain) e.nextElement() ;
            if( connector == null ) continue ;
            String str = connector.getSavingInformation() ;
            write( fout, str ) ;
        }
        write( fout, "\t</Connectors>" ) ;
    }

    private void writeGates( FileOutputStream fout ) throws IOException {

        write( fout, "\t<Gates>" ) ;
        for( Enumeration e = gates.elements() ; e.hasMoreElements() ; ) {
            Chain gate = (Chain) e.nextElement() ;
            String str = gate.getSavingInformation() ;
            write( fout, str ) ;
        }
        write( fout, "\t</Gates>" ) ;
    }

    private void writeTagCollection( FileOutputStream fout, Vector tags )
            throws IOException {

        write( fout, "\t<TagCollection>" ) ;
        for( Enumeration e = tags.elements() ; e.hasMoreElements() ; ) {
            DCDTag tag = (DCDTag) e.nextElement() ;
            write( fout, tag.getSavingInformation() ) ;
        }
        write( fout, "\t</TagCollection>" ) ;
    }

    private void writeICs( FileOutputStream fout ) throws IOException {

        write( fout, "\t<ICs>" ) ;
        for( Enumeration e = ics.elements() ; e.hasMoreElements() ; ) {
            DCDIC ic = (DCDIC) e.nextElement() ;
            write( fout, ic.getSavingInformation() ) ;
        }
        write( fout, "\t</ICs>" ) ;
    }

    private void writeMessages( FileOutputStream fout ) throws IOException {

        write( fout, "\t<DCDMessages>" ) ;
        for( Enumeration e = messages.elements() ; e.hasMoreElements() ; ) {
            DCDText text = (DCDText) e.nextElement() ;
            write( fout, text.getSavingInformation() ) ;
        }
        write( fout, "\t</DCDMessages>" ) ;
    }

    private void write( FileOutputStream fout, String str ) throws IOException {

        fout.write( str.getBytes(), 0, str.length() ) ;
        fout.write( '\n' ) ;
    }

    // This function segregates all the components in the circuit
    // into logical blocks consisting of gates, connectors and ics.
    private void segregateComponents( Chain chainHead, boolean saveAsIC ) {

        // I am recreating the Vectors here because I need fresh ones !! :-)
        connectors = new Vector() ;
        gates = new Vector() ;
        ics = new Vector() ;
        messages = new Vector() ;

        Chain head = chainHead ;
        while( head != null ) {
            if( head instanceof DCDConnector ) {
                connectors.addElement( head ) ;
            }
            else if( head instanceof DCDIC ) {
                ics.addElement( head ) ;
            }
            else if( head instanceof DCDGate ) {
                // What this piece of code does is that, if the gate currently
                // referred
                // to by the head is an instance of DCDInputGate or LED then
                // doesn't save
                // it for writing to file in case this circuit is being saved as
                // an IC.
                if( !saveAsIC ) {
                    gates.addElement( head ) ;
                }
                else {
                    DCDGate gate = (DCDGate) head ;
                    if( !( ( gate instanceof DCDInputGate ) || ( gate instanceof DCDLED ) ) ) gates
                            .addElement( head ) ;
                }

            }
            else if( head instanceof DCDText ) {
                messages.addElement( head ) ;
            }
            head = head.getNextLink() ;
        }
    }
}