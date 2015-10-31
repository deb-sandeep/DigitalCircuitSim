package com.sandy.apps.dcs.ui ;

import javax.swing.* ;

import com.sandy.apps.dcs.common.* ;

import java.awt.event.* ;
import java.awt.* ;

// This dialog is used to collect the name and the relevant information from the
// gate.
public class DCDNameDialog extends JDialog implements ActionListener {

    private JTextArea            longDescriptionArea   = new JTextArea( 20, 10 ) ;

    private JTextField           nameField             = new JTextField( 10 ) ;

    private JTextField           shortDescriptionField = new JTextField( 10 ) ;

    private JButton              okButton              = new JButton( "OK" ) ;

    private JButton              cancelButton          = new JButton( "Cancel" ) ;

    private JLabel               label1                = new JLabel( "I D  :" ) ;

    private JLabel               label2                = new JLabel( "S D  :" ) ;

    private JLabel               label3                = new JLabel(
                                                               "Long Desc :" ) ;

    private String               name                  = null ;

    private String               shortDescription      = null ;

    private String               longDescription       = null ;

    private static DCDNameDialog instance              = null ;

    // Making this class a singleton class so that only one instance is
    // available
    // at all times.
    private DCDNameDialog( String title ) {

        super( DCDUtility.getInstance().getFrame(), title, true ) ;
        setUpGUI() ;
    }

    public static DCDNameDialog getInstance() {

        if( instance == null ) instance = new DCDNameDialog( "Information " ) ;
        return instance ;
    }

    private void setUpGUI() {

        JPanel backPanel = new JPanel() ;
        backPanel.setLayout( new BoxLayout( backPanel, BoxLayout.Y_AXIS ) ) ;

        longDescriptionArea.setLineWrap( true ) ;
        longDescriptionArea.setWrapStyleWord( true ) ;

        JPanel p1 = new JPanel() ;
        // p1.setLayout(new BoxLayout(p1,BoxLayout.X_AXIS));
        p1.add( label1 ) ;
        p1.add( nameField ) ;
        // p1.add(Box.createHorizontalGlue());

        JPanel p2 = new JPanel() ;
        // p2.setLayout(new BoxLayout(p2,BoxLayout.X_AXIS));
        p2.add( label2 ) ;
        p2.add( shortDescriptionField ) ;
        // p2.add(Box.createHorizontalGlue());

        JPanel p3 = new JPanel() ;
        p3.setLayout( new BoxLayout( p3, BoxLayout.X_AXIS ) ) ;
        p3.add( Box.createHorizontalGlue() ) ;
        p3.add( okButton ) ;
        p3.add( cancelButton ) ;

        backPanel.add( p1 ) ;
        backPanel.add( Box.createRigidArea( new Dimension( 0, 5 ) ) ) ;
        backPanel.add( p2 ) ;
        backPanel.add( Box.createRigidArea( new Dimension( 0, 5 ) ) ) ;
        label3.setAlignmentX( SwingConstants.LEFT ) ;
        backPanel.add( label3 ) ;
        backPanel.add( new JScrollPane( longDescriptionArea ) ) ;
        backPanel.add( Box.createRigidArea( new Dimension( 0, 5 ) ) ) ;
        backPanel.add( p3 ) ;
        backPanel.add( Box.createRigidArea( new Dimension( 0, 5 ) ) ) ;

        getContentPane().add( backPanel ) ;
        setBounds( 500, 400, 200, 250 ) ;

        okButton.addActionListener( this ) ;
        cancelButton.addActionListener( this ) ;

    }

    public void show( String n, String sd, String ld ) {

        nameField.setText( n ) ;
        shortDescriptionField.setText( sd ) ;
        longDescriptionArea.setText( ld ) ;
        setVisible( true ) ;
    }

    public void actionPerformed( ActionEvent e ) {

        JButton button = (JButton) e.getSource() ;
        String str = button.getText() ;

        if( str.equals( "OK" ) ) {
            this.name = nameField.getText() ;
            this.shortDescription = shortDescriptionField.getText() ;
            this.longDescription = longDescriptionArea.getText() ;
        }
        if( str.equals( "Cancel" ) ) {
            this.name = "" ;
            this.shortDescription = "" ;
            this.longDescription = "" ;
        }
        setVisible( false ) ;
    }

    public String getName() {

        return this.name ;
    }

    public String getShortDescription() {

        return this.shortDescription ;
    }

    public String getLongDescription() {

        return this.longDescription ;
    }

    // This is the name label.. i.e the topmost label ...
    public void setNameLabel( String str ) {

        label1.setText( str ) ;
    }

    public void setShortDescriptionLabel( String str ) {

        label2.setText( str ) ;
    }

    public void setLongDescriptionLabel( String str ) {

        label3.setText( str ) ;
    }

}
