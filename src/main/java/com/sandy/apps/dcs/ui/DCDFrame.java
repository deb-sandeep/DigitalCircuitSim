package com.sandy.apps.dcs.ui ;

import javax.swing.* ;

import com.sandy.apps.dcs.common.* ;

import java.awt.event.* ;
import java.awt.* ;

public class DCDFrame extends JFrame {

    // This inner class represents the Status bar in the application.
    // This class has been made a inner class because it makes more
    // sense for the customized frame to look after the status bar.
    // This will also facilitate the fact that the frame can take proper
    // action when the status bar is visible or not.

    // A direct consiquence of this is that we will have to request the
    // frame to show any status message.

    // IF this class were a JPanel then we could have multiple labels
    // inside it. But this sounds more futuristic.
    // private class DCDStatusBar extends JLabel
    // {
    // DCDStatusBar()
    // {
    // super("Welcome to world of walrus");
    // //CHECK=> Look at this why its not working
    // setBackground(DCDUtility.getInstance().getStatusbarBackgroundColor());
    // setMinimumSize(new Dimension(100,50));
    // }
    // void showMessage(String message){setText(message);}
    // void clearMessage(){setText("");}
    // }

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

                // TODO => Ask for confirmation.
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
