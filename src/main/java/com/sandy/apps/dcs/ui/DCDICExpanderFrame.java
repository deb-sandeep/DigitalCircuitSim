package com.sandy.apps.dcs.ui ;

import java.awt.BorderLayout ;
import java.awt.Component ;
import java.awt.Dimension ;
import java.awt.Point ;
import java.awt.event.MouseAdapter ;
import java.awt.event.MouseEvent ;
import java.awt.event.MouseMotionAdapter ;
import java.awt.event.WindowAdapter ;
import java.awt.event.WindowEvent ;

import javax.swing.JPanel ;
import javax.swing.JScrollPane ;
import javax.swing.JSplitPane ;
import javax.swing.JTable ;
import javax.swing.JTextArea ;

import com.sandy.apps.dcs.component.DCDComponent ;
import com.sandy.apps.dcs.component.model.DCDICModel ;

public class DCDICExpanderFrame extends DCDFrame {

    private DCDCanvas  canvas          = new DCDCanvas() ;

    private JTable     portInfoTable   = null ;

    private JTextArea  descriptionArea = new JTextArea( 50, 50 ) ;

    private DCDICModel icModel         = null ;

    // This inner class listens to the mouse events on the canvas
    private class MouseHandler extends MouseAdapter {

        public void mouseClicked( MouseEvent e ) {

            // Have to check for the right mouse click
            // I can trap the click here and pass on to the component
            // just below the click point.
            // TO BE IMPLEMENTED.
            if( e.getModifiers() == MouseEvent.BUTTON3_MASK ) {
                DCDComponent comp = (DCDComponent) icModel
                        .getComponentAtPoint( e ) ;
                if( comp != null ) {
                    int x = e.getPoint().x + canvas.getLocationOnScreen().x
                            - getLocation().x ;
                    int y = e.getPoint().y + canvas.getLocationOnScreen().y
                            - getLocation().y ;
                    comp.showPopupMenu( new Point( x, y ),
                            (Component) DCDICExpanderFrame.this ) ;
                }
            }
        }
    }

    private class MouseMotionHandler extends MouseMotionAdapter {

        public void mouseMoved( MouseEvent e ) {

            // This should show the name of the component in the status bar.
            DCDComponent component = icModel.getComponentAtPoint( e ) ;
            if( component != null ) {
                // setDescriptionString(component.getLongDescription());
                showStatusMessage( component.getName() + " : <"
                        + component.getShortDescription() + ">" ) ;
                descriptionArea.setText( component.getLongDescription() ) ;
            }
            else {
                showStatusMessage( "" ) ;
                descriptionArea.setText( "" ) ;
                // setDescriptionString("");
            }
        }
    }

    public DCDICExpanderFrame( String icName, DCDICModel icModel ) {

        super( icName ) ;
        this.icModel = icModel ;
        canvas.addMouseListener( new MouseHandler() ) ;
        canvas.addMouseMotionListener( new MouseMotionHandler() ) ;
        canvas.setPreferredSize( new Dimension( 1000, 1000 ) ) ;
        canvas.setController( icModel ) ;
        setUpGUI() ;
        addWindowListener( new WindowAdapter() {

            public void windowClosing( WindowEvent e ) {

                // TODO => Ask for confirmation.
                setVisible( false ) ;
                dispose() ;
            }
        } ) ;
        setBounds( 200, 200, 600, 600 ) ;
    }

    private void setUpGUI() {

        String[][] portData = icModel.getTableDataForPorts() ;
        String[] colNames = { "Port ID", "Desc" } ;

        portInfoTable = new JTable( portData, colNames ) ;

        JPanel cRightPanel = new JPanel() ;

        JSplitPane cLeftPanel = new JSplitPane( JSplitPane.VERTICAL_SPLIT,
                new JScrollPane( portInfoTable ), new JScrollPane(
                        descriptionArea ) ) ;
        cLeftPanel.setDividerLocation( 300 ) ;

        descriptionArea.setLineWrap( true ) ;
        descriptionArea.setWrapStyleWord( true ) ;
        cRightPanel.setLayout( new BorderLayout() ) ;
        cRightPanel.add( new JScrollPane( canvas ) ) ;

        JSplitPane centerPanel = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT,
                cLeftPanel, cRightPanel ) ;
        centerPanel.setDividerLocation( 150 ) ;
        centerPanel.setOneTouchExpandable( true ) ;

        getContentPane().add( centerPanel, "Center" ) ;
    }

}
