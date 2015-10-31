package com.sandy.apps.dcs.ui ;

import javax.swing.* ;

import com.sandy.apps.dcs.common.* ;
import com.sandy.apps.dcs.component.* ;
import com.sandy.apps.dcs.cor.* ;
import com.sandy.apps.dcs.exception.* ;
import com.sandy.apps.dcs.factory.* ;
import com.sandy.apps.dcs.ui.* ;

import java.io.* ;
import java.util.* ;
import java.awt.* ;

// This is the class which saves the circuit information.
// The circuit information is saved in the form of a XML file.
public class DCDCircuitLoader {

    private DCDController controller ;

    private JFileChooser  fileChooser = new JFileChooser(
                                              new File( "" ).getAbsolutePath() ) ;

    public DCDCircuitLoader( DCDController controller ) {

        this.controller = controller ;

    }

    public void load() {

        DCDUtility utility = DCDUtility.getInstance() ;
        File currentFile = utility.getCurrentFile() ;

        if( currentFile != null || controller.getChainHead() != null ) {
            // This means that there is one file that is currently
            // opened in the workspace. Hence ask for permission
            // to close it before loading a new file.
            int choice = JOptionPane.showConfirmDialog( utility.getFrame(),
                    "Save the current file in the workspace" ) ;
            if( choice == JOptionPane.YES_OPTION ) {
                // OK Save the current file loaded in the workspace.
                controller.saveCircuit() ;
                controller.cleanWorkspace() ;
            }
            else if( choice == JOptionPane.NO_OPTION ) {
                // Nah !! No need to save the file ... proceed.
                controller.cleanWorkspace() ;
            }
            else if( choice == JOptionPane.CANCEL_OPTION ) {
                // Brr !! Return fast.
                return ;
            }
        }

        int choice = fileChooser.showOpenDialog( utility.getFrame() ) ;
        if( choice == JFileChooser.APPROVE_OPTION ) {
            File file = fileChooser.getSelectedFile() ;
            utility.showStatusMessage( "Loading file .... please wait" ) ;
            utility.getFrame().setCursor( new Cursor( Cursor.WAIT_CURSOR ) ) ;
            try {
                DCDCircuitFactory.loadDCDCircuitFromFile( file, controller ) ;
                controller.initiatePainting( utility.getGraphics() ) ;
                controller.getCircuitSaver().setInfo( "", "",
                        controller.getCircuitDescription() ) ;
            }
            catch( DCDFileNotFoundException exception ) {
                // This means that some referenced file is missing.
                // Extract the specific message from the Exception object.
                String message = exception.getMessage() ;
                JOptionPane.showMessageDialog( null, message ) ;
                utility.getFrame().setCursor(
                        new Cursor( Cursor.DEFAULT_CURSOR ) ) ;
                return ;
            }
            catch( Exception e ) {
                JOptionPane.showMessageDialog( null,
                        "There was some error in loading the file" ) ;
                e.printStackTrace() ;
                utility.getFrame().setCursor(
                        new Cursor( Cursor.DEFAULT_CURSOR ) ) ;
                return ;
            }
            utility.getFrame().setCursor( new Cursor( Cursor.DEFAULT_CURSOR ) ) ;
            utility.showStatusMessage( "File loaded succesfully" ) ;
            utility.setCurrentFile( file ) ;
        }
    }
}