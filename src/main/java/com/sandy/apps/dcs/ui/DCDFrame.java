package com.sandy.apps.dcs.ui ;

import java.awt.Color ;
import java.awt.Dimension ;
import java.awt.Graphics ;
import java.awt.event.WindowAdapter ;
import java.awt.event.WindowEvent ;

import javax.swing.JComponent ;
import javax.swing.JFrame ;

import com.sandy.apps.dcs.util.DCDUtility ;

public class DCDFrame extends JFrame {

    private DCDStatusBar statusbar ;

    public DCDFrame( String str ) {

        super( str ) ;

        DCDUtility utility = DCDUtility.getInstance() ;
        statusbar = new DCDStatusBar( utility.getStatusbarBackgroundColor(),
                utility.getStatusbarForegroundColor() ) ;
        getContentPane().add( statusbar, "South" ) ;
    }

    public DCDFrame( String str, boolean childFrame ) {

        super( str ) ;
        addWindowListener( new WindowAdapter() {

            public void windowClosing( WindowEvent e ) {
                if( !( DCDFrame.this instanceof DCDICExpanderFrame ) ) DCDUtility
                        .getInstance().savePropertiesInformation() ;
                setVisible( false ) ;
                dispose() ;
            }
        } ) ;
    }

    public DCDFrame() {
        this( "Walrus .." ) ;
    }

    // This is the method that any class that wants to display any
    // status message should call.
    public void showStatusMessage( String message ) {

        if( statusbar.isVisible() ) statusbar.setText( message ) ;
    }

    // This is the method that any class that wants to clear the status
    // bar should call.
    public void clearStatusMessage() {

        if( statusbar.isVisible() ) statusbar.setText( "" ) ;
    }

    public Color getStatusbarBackgroundColor() {

        return statusbar.getBackground() ;
    }

    public void setStatusbarBackgroundColor( Color color ) {

        statusbar.setBackground( color ) ;
    }

    public Color getStatusbarForegroundColor() {

        return statusbar.getForeground() ;
    }

    public void setStatusbarForegroundColor( Color color ) {

        statusbar.setForeground( color ) ;
    }
}

class DCDStatusBar extends JComponent {

    private int      x1, y1, wid, ht ; // set in init() always call...

    private Graphics g ;

    private String   text = "" ;

    public DCDStatusBar() {

        this( Color.green, Color.black ) ;
    }

    public DCDStatusBar( Color bg, Color fore ) {

        setBackground( bg ) ;
        setForeground( fore ) ;
        setMaximumSize( new Dimension( 32767, 25 ) ) ;
        setMinimumSize( new Dimension( 10, 15 ) ) ;
        setPreferredSize( new Dimension( 400, 20 ) ) ;
    }

    private void drawFrame() {

        g.setColor( Color.white ) ;
        g.drawRect( 0, 0, getWidth() - 2, getHeight() - 2 ) ;
        g.setColor( Color.darkGray ) ;
        g.drawRect( 1, 1, getWidth() - 2, getHeight() - 2 ) ;
    }

    private void fillInterior() {

        g.setColor( getBackground() ) ;
        g.fillRect( x1, y1, wid, ht ) ;
    }

    public String getText() {

        return this.text ;
    }

    private void init() {

        x1 = 2 ;
        y1 = 2 ;
        wid = getWidth() - 4 ;
        ht = getHeight() - 4 ;
    }

    public void paint( Graphics g ) {

        this.g = g ;
        init() ;
        drawFrame() ;
        showText() ;
    }

    public void setText( String str ) {

        this.text = str ;
        repaint() ;
    }

    private void showText() {

        fillInterior() ;
        g.setColor( getForeground() ) ;
        g.drawString( text, x1 + 5, y1 + ht - 3 ) ;
    }
}
