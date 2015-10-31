package com.sandy.apps.dcs.ui ;

import javax.swing.* ;
import java.awt.event.* ;

/**
 * Insert the type's description here. Creation date: (11/16/2000 11:18:24 PM)
 * 
 * @author: Administrator
 */
class DCDAboutDialog extends JDialog {

    private JButton ivjJButton1          = null ;

    private JPanel  ivjJFrameContentPane = null ;

    private JLabel  ivjJLabel1           = null ;

    private JLabel  ivjJLabel2           = null ;

    private JLabel  ivjJLabel3           = null ;

    private JLabel  ivjJLabel4           = null ;

    /**
     * DCDAboutDialog constructor comment.
     */
    public DCDAboutDialog() {

        super() ;
        initialize() ;
    }

    /**
     * DCDAboutDialog constructor comment.
     * 
     * @param title
     *            java.lang.String
     */
    public DCDAboutDialog( JFrame frame ) {

        super( frame ) ;
        initialize() ;
    }

    /**
     * Return the JButton1 property value.
     * 
     * @return JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JButton getJButton1() {

        if( ivjJButton1 == null ) {
            try {
                ivjJButton1 = new JButton() ;
                ivjJButton1.setName( "JButton1" ) ;
                ivjJButton1.setText( "OK" ) ;
                ivjJButton1.setBounds( 171, 170, 85, 25 ) ;
                // user code begin {1}
                ivjJButton1.addActionListener( new ActionListener() {

                    public void actionPerformed( ActionEvent e ) {

                        setVisible( false ) ;
                        dispose() ;
                    }
                } ) ;
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJButton1 ;
    }

    /**
     * Return the JFrameContentPane property value.
     * 
     * @return JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JPanel getJFrameContentPane() {

        if( ivjJFrameContentPane == null ) {
            try {
                ivjJFrameContentPane = new JPanel() ;
                ivjJFrameContentPane.setName( "JFrameContentPane" ) ;
                ivjJFrameContentPane.setLayout( null ) ;
                getJFrameContentPane().add( getJButton1(),
                        getJButton1().getName() ) ;
                getJFrameContentPane().add( getJLabel1(),
                        getJLabel1().getName() ) ;
                getJFrameContentPane().add( getJLabel2(),
                        getJLabel2().getName() ) ;
                getJFrameContentPane().add( getJLabel3(),
                        getJLabel3().getName() ) ;
                getJFrameContentPane().add( getJLabel4(),
                        getJLabel4().getName() ) ;
                // user code begin {1}
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJFrameContentPane ;
    }

    /**
     * Return the JLabel1 property value.
     * 
     * @return JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JLabel getJLabel1() {

        if( ivjJLabel1 == null ) {
            try {
                ivjJLabel1 = new JLabel() ;
                ivjJLabel1.setName( "JLabel1" ) ;
                ivjJLabel1.setFont( new java.awt.Font( "dialog", 1, 24 ) ) ;
                ivjJLabel1.setText( "Digital Circuit Simulator " ) ;
                ivjJLabel1.setBounds( 33, 14, 372, 43 ) ;
                ivjJLabel1.setForeground( new java.awt.Color( 255, 0, 0 ) ) ;
                ivjJLabel1.setHorizontalAlignment( SwingConstants.CENTER ) ;
                // user code begin {1}
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJLabel1 ;
    }

    /**
     * Return the JLabel2 property value.
     * 
     * @return JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JLabel getJLabel2() {

        if( ivjJLabel2 == null ) {
            try {
                ivjJLabel2 = new JLabel() ;
                ivjJLabel2.setName( "JLabel2" ) ;
                ivjJLabel2.setFont( new java.awt.Font( "dialog", 1, 24 ) ) ;
                ivjJLabel2.setText( "D C D - 1.0" ) ;
                ivjJLabel2.setBounds( 135, 58, 154, 42 ) ;
                ivjJLabel2.setHorizontalAlignment( SwingConstants.CENTER ) ;
                // user code begin {1}
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJLabel2 ;
    }

    /**
     * Return the JLabel3 property value.
     * 
     * @return JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JLabel getJLabel3() {

        if( ivjJLabel3 == null ) {
            try {
                ivjJLabel3 = new JLabel() ;
                ivjJLabel3.setName( "JLabel3" ) ;
                ivjJLabel3.setText( "Digital Circuit Simulator by:" ) ;
                ivjJLabel3.setBounds( 79, 103, 164, 23 ) ;
                // user code begin {1}
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJLabel3 ;
    }

    /**
     * Return the JLabel4 property value.
     * 
     * @return JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JLabel getJLabel4() {

        if( ivjJLabel4 == null ) {
            try {
                ivjJLabel4 = new JLabel() ;
                ivjJLabel4.setName( "JLabel4" ) ;
                ivjJLabel4.setFont( new java.awt.Font( "dialog", 2, 14 ) ) ;
                ivjJLabel4.setText( "Sandeep Deb" ) ;
                ivjJLabel4.setBounds( 170, 135, 93, 28 ) ;
                // user code begin {1}
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJLabel4 ;
    }

    /**
     * Called whenever the part throws an exception.
     * 
     * @param exception
     *            java.lang.Throwable
     */
    private void handleException( java.lang.Throwable exception ) {

        /* Uncomment the following lines to print uncaught exceptions to stdout */
        // System.out.println("--------- UNCAUGHT EXCEPTION ---------");
        // exception.printStackTrace(System.out);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {

        try {
            // user code begin {1}
            // user code end
            setName( "DCDAboutDialog" ) ;
            setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE ) ;
            setSize( 422, 230 ) ;
            setTitle( "About DCD" ) ;
            setModal( true ) ;
            setResizable( false ) ;
            setContentPane( getJFrameContentPane() ) ;
            addWindowListener( new java.awt.event.WindowAdapter() {

                public void windowClosing( WindowEvent e ) {

                    setVisible( false ) ;
                    dispose() ;
                } ;
            } ) ;
        }
        catch( java.lang.Throwable ivjExc ) {
            handleException( ivjExc ) ;
        }
        // user code begin {2}
        // user code end
    }

    /**
     * main entrypoint - starts the part when it is run as an application
     * 
     * @param args
     *            java.lang.String[]
     */
    public static void main( java.lang.String[] args ) {

        try {
            DCDAboutDialog aDCDAboutDialog ;
            aDCDAboutDialog = new DCDAboutDialog() ;
            aDCDAboutDialog
                    .addWindowListener( new java.awt.event.WindowAdapter() {

                        public void windowClosing( java.awt.event.WindowEvent e ) {

                            System.exit( 0 ) ;
                        } ;
                    } ) ;
            aDCDAboutDialog.setVisible( true ) ;
        }
        catch( Throwable exception ) {
            System.err.println( "Exception occurred in main() of JFrame" ) ;
            exception.printStackTrace( System.out ) ;
        }
    }
}
