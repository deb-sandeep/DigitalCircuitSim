package com.sandy.apps.dcs.testing ;

import java.awt.event.* ;
import java.awt.* ;
import javax.swing.* ;

public class FileDialogTest implements ActionListener {

    private JxFrame      frame ;

    private JFileChooser fileDialog = new JFileChooser() ;

    private JButton      save       = new JButton( "Save" ) ;

    // private MyCanvas canvas;

    FileDialogTest() {

        frame = new JxFrame() ;
        frame.getContentPane().setLayout( new FlowLayout() ) ;
        frame.getContentPane().add( save ) ;
        save.addActionListener( this ) ;
        frame.setVisible( true ) ;
    }

    private void setUpGUI() {

        frame.getContentPane().setLayout( new FlowLayout() ) ;
    }

    public void actionPerformed( ActionEvent e ) {

        int i = fileDialog.showSaveDialog( frame ) ;
        if( i == JFileChooser.APPROVE_OPTION )
        ;
    }

    public static void main( String[] args ) {

        new FileDialogTest() ;
    }

}