package com.sandy.apps.dcs.component ;

import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.io.Serializable ;

import javax.swing.JMenuItem ;
import javax.swing.JOptionPane ;

import com.sandy.apps.dcs.component.model.DCDClockModel ;
import com.sandy.apps.dcs.component.model.DCDGateModel ;
import com.sandy.apps.dcs.component.model.DCDSteadyInputGateModel ;
import com.sandy.apps.dcs.component.view.DCDClockUI ;
import com.sandy.apps.dcs.component.view.DCDComponentUI ;
import com.sandy.apps.dcs.component.view.DCDGateUI ;
import com.sandy.apps.dcs.util.Chain ;
import com.sandy.apps.dcs.util.DCDUtility ;

public class DCDClock extends DCDInputGate implements Serializable, Runnable,
        ActionListener {

    private DCDClockUI    ui ;

    private DCDClockModel model ;

    private static int    id        = 0 ;

    private int           frequency = 300 ;

    private Thread        t         = null ;

    private boolean       flag      = true ;

    public DCDClock( Chain c ) {

        super( c, null, null ) ;
        ui = new DCDClockUI( this ) ;
        model = new DCDClockModel( this ) ;
        // model.addObserver(ui);
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public DCDClock() {

        super() ;
        ui = new DCDClockUI( this ) ;
        model = new DCDClockModel( this ) ;
        super.setUI( ui ) ;
        super.setModel( model ) ;
    }

    public void actionPerformed( ActionEvent e ) {

        JMenuItem menuItem = (JMenuItem) e.getSource() ;
        String actionCommand = menuItem.getActionCommand() ;
        if( actionCommand.equals( "STOP" ) ) {
            if( t != null && t.isAlive() ) {
                flag = false ;
                ui.stopPressed() ;
                t = null ;
            }
        }
        else if( actionCommand.equals( "START" ) ) {
            if( t == null ) {
                ui.startPressed() ;
                fire() ;

            }
        }
        else if( actionCommand.equals( "FREQUENCY" ) ) {
            // Validation is requried.
            String str = JOptionPane.showInputDialog( DCDUtility.getInstance()
                    .getFrame(), "Please enter the frequency of the clock" ) ;
            if( str == null ) return ;
            else if( str.equals( "" ) ) { return ; }
            try {
                frequency = new Integer( str ).intValue() ;
            }
            catch( NumberFormatException e1 ) {
                DCDUtility.showMessage( "Please enter a valid number" ) ;
            }
        }
        else if( actionCommand.equals( "NAME" ) ) {
            super.actionPerformed( e ) ;
        }
    }

    public boolean getState() {

        return ( model.getState() ) ;
    }

    public void cleanUp() {

        super.cleanUp() ;
        flag = false ;
    }

    public void setState( boolean b ) {

        model.setState( b ) ;
    }

    public void toggleState() {

        model.toggleState() ;
    }

    public void run() {

        DCDUtility utility = DCDUtility.getInstance() ;
        while( flag ) {
            utility.sleep( frequency ) ;
            ui.paint( utility.getGraphics(), DCDComponentUI.DELETED ) ;
            model.fire() ;
            ui.paint( utility.getGraphics(), DCDComponentUI.NORMAL ) ;

        }
    }

    public String getType() {

        return "CLOCK_INPUT" ;
    }

    public void setModel( DCDGateModel model ) {

        if( model instanceof DCDSteadyInputGateModel ) {
            super.setModel( model ) ;
            this.model = (DCDClockModel) model ;
        }
    }

    public void setUI( DCDGateUI ui ) {

        if( ui instanceof DCDClockUI ) {
            super.setUI( ui ) ;
            this.ui = (DCDClockUI) ui ;
        }
    }

    public void fire() {

        if( t == null ) {
            t = new Thread( this ) ;
            t.setPriority( Thread.MAX_PRIORITY ) ;
            flag = true ;
            t.start() ;
        }
        else if( !t.isAlive() ) {
            t.start() ;
        }
    }

}