package com.sandy.apps.dcs.ui ;

import javax.swing.* ;
import javax.swing.border.* ;

import com.sandy.apps.dcs.common.DCDUtility ;

public class DCDMenuBar extends JMenuBar {

    private JMenu     fileMenu           = new JMenu( "File" ) ;

    private JMenu     viewMenu           = new JMenu( "View" ) ;

    private JMenu     helpMenu           = new JMenu( "Help" ) ;

    private JMenu     editMenu           = new JMenu( "Edit" ) ;

    private JMenu     optionsMenu        = new JMenu( "Options" ) ;

    private JMenuItem newMenuItem        = new JMenuItem( "New" ) ;

    private JMenuItem openMenuItem       = new JMenuItem( "Open" ) ;

    private JMenuItem saveMenuItem       = new JMenuItem( "Save" ) ;

    private JMenuItem saveAsMenuItem     = new JMenuItem( "Save As" ) ;

    private JMenuItem saveAsICMenuItem   = new JMenuItem( "Save As IC" ) ;

    private JMenuItem exitMenuItem       = new JMenuItem( "Exit" ) ;

    private JMenuItem helpMenuItem       = new JMenuItem( "Help" ) ;

    private JMenuItem aboutMenuItem      = new JMenuItem( "About" ) ;

    private JMenuItem propertiesMenuItem = new JMenuItem( "Properties" ) ;

    public DCDMenuBar( DCDController controller ) {

        setUpMenuBar() ;
        setUpListeners( controller ) ;
        setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED ) ) ;
    }

    private void setUpMenuBar() {

        fileMenu.add( newMenuItem ) ;
        fileMenu.add( openMenuItem ) ;
        fileMenu.add( saveMenuItem ) ;
        fileMenu.add( saveAsMenuItem ) ;
        fileMenu.add( saveAsICMenuItem ) ;
        fileMenu.add( exitMenuItem ) ;

        add( fileMenu ) ;

        optionsMenu.add( propertiesMenuItem ) ;

        add( optionsMenu ) ;

        helpMenu.add( helpMenuItem ) ;
        helpMenu.add( aboutMenuItem ) ;

        add( helpMenu ) ;

    }

    private void setUpListeners( DCDController controller ) {

        saveMenuItem.setActionCommand( "SAVE" ) ;
        saveMenuItem.addActionListener( controller ) ;

        saveAsMenuItem.setActionCommand( "SAVE_AS" ) ;
        saveAsMenuItem.addActionListener( controller ) ;

        saveAsICMenuItem.setActionCommand( "SAVE AS IC" ) ;
        saveAsICMenuItem.addActionListener( controller ) ;

        exitMenuItem.setActionCommand( "EXIT" ) ;
        exitMenuItem.addActionListener( controller ) ;

        openMenuItem.setActionCommand( "OPEN" ) ;
        openMenuItem.addActionListener( controller ) ;

        newMenuItem.setActionCommand( "NEW" ) ;
        newMenuItem.addActionListener( controller ) ;

        // Help Menu Items
        helpMenuItem.setActionCommand( "HELP" ) ;
        helpMenuItem.addActionListener( DCDUtility.getInstance() ) ;

        aboutMenuItem.setActionCommand( "ABOUT" ) ;
        aboutMenuItem.addActionListener( controller ) ;

        // Options
        propertiesMenuItem.setActionCommand( "SET_PROPERTIES" ) ;
        propertiesMenuItem.addActionListener( DCDUtility.getInstance() ) ;
    }
}