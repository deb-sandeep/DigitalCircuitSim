package com.sandy.apps.dcs.ui ;

import javax.swing.* ;

import com.sandy.apps.dcs.common.* ;

import java.awt.event.* ;
import java.awt.* ;

/**
 * Insert the type's description here. Creation date: (11/16/2000 4:04:06 PM)
 * 
 * @author: Administrator
 */
public class DCDPropertiesDialog extends JDialog implements ActionListener {

    private JButton    ivjJButton1           = null ;                    // Background
                                                                          // color

    private JButton    ivjJButton10          = null ;

    private JButton    ivjJButton11          = null ;

    private JButton    ivjJButton2           = null ;

    private JButton    ivjJButton3           = null ;

    private JButton    ivjJButton4           = null ;

    private JButton    ivjJButton5           = null ;

    private JButton    ivjJButton6           = null ;

    private JButton    ivjJButton7           = null ;

    private JButton    ivjJButton8           = null ;

    private JButton    ivjJButton9           = null ;

    private JCheckBox  ivjJCheckBox1         = null ;

    private JPanel     ivjJDialogContentPane = null ;

    private JLabel     ivjJLabel1            = null ;

    private JLabel     ivjJLabel10           = null ;

    private JLabel     ivjJLabel2            = null ;

    private JLabel     ivjJLabel3            = null ;

    private JLabel     ivjJLabel4            = null ;

    private JLabel     ivjJLabel5            = null ;

    private JLabel     ivjJLabel6            = null ;

    private JLabel     ivjJLabel7            = null ;

    private JLabel     ivjJLabel8            = null ;

    private JLabel     ivjJLabel9            = null ;

    private JTextField ivjJTextField1        = null ;

    private JTextField ivjJTextField2        = null ;

    private DCDUtility utility               = DCDUtility.getInstance() ;

    private Color      backgroundColor       = null ;

    private Color      selectedLinkColor     = null ;

    private Color      normalLinkColor       = null ;

    private Color      statusBGColor         = null ;

    private Color      statusFGColor         = null ;

    private Color      textColor             = null ;

    private Color      normalGateColor       = null ;

    private Color      selectedGateColor     = null ;

    private Color      gridColor             = null ;

    private boolean    gridOn                = utility.getIsGridOn() ;

    private int        canvasWidth           = -1 ;

    private int        canvasHeight          = -1 ;

    /**
     * DCDPropertiesDialog constructor comment.
     */
    public DCDPropertiesDialog() {

        super() ;
        initialize() ;
    }

    /**
     * DCDPropertiesDialog constructor comment.
     * 
     * @param owner
     *            java.awt.Frame
     */
    public DCDPropertiesDialog( java.awt.Frame owner ) {

        super( owner ) ;
        initialize() ;
    }

    /**
     * DCDPropertiesDialog constructor comment.
     * 
     * @param owner
     *            java.awt.Frame
     * @param title
     *            java.lang.String
     */
    public DCDPropertiesDialog( java.awt.Frame owner, String title ) {

        super( owner, title ) ;
    }

    /**
     * DCDPropertiesDialog constructor comment.
     * 
     * @param owner
     *            java.awt.Frame
     * @param title
     *            java.lang.String
     * @param modal
     *            boolean
     */
    public DCDPropertiesDialog( java.awt.Frame owner, String title,
            boolean modal ) {

        super( owner, title, modal ) ;
    }

    /**
     * DCDPropertiesDialog constructor comment.
     * 
     * @param owner
     *            java.awt.Frame
     * @param modal
     *            boolean
     */
    public DCDPropertiesDialog( java.awt.Frame owner, boolean modal ) {

        super( owner, modal ) ;
    }

    public void actionPerformed( ActionEvent e ) {

        JButton b = (JButton) e.getSource() ;
        String actionCommand = b.getActionCommand() ;

        if( actionCommand.equals( "BACKGROUND_COLOR" ) ) {
            Color c = JColorChooser
                    .showDialog( utility.getFrame(),
                            "Choose the background color",
                            utility.getBackgroundColor() ) ;
            if( c != null ) {
                backgroundColor = c ;
                ivjJButton1.setBackground( backgroundColor ) ;
            }

        }
        if( actionCommand.equals( "SELECTED_LINK_COLOR" ) ) {
            Color c = JColorChooser.showDialog( utility.getFrame(),
                    "Choose the selected link color",
                    utility.getSelectedLinkColor() ) ;
            if( c != null ) {
                selectedLinkColor = c ;
                ivjJButton4.setBackground( selectedLinkColor ) ;

            }

        }
        if( actionCommand.equals( "NORMAL_LINK_COLOR" ) ) {
            Color c = JColorChooser.showDialog( utility.getFrame(),
                    "Choose the normal link color", utility.getLinkColor() ) ;
            if( c != null ) {
                normalLinkColor = c ;
                ivjJButton3.setBackground( normalLinkColor ) ;
                utility.setLinkColor( normalLinkColor ) ;
            }

        }
        if( actionCommand.equals( "NORMAL_GATE_COLOR" ) ) {
            Color c = JColorChooser.showDialog( utility.getFrame(),
                    "Choose the normal gate color",
                    utility.getSelectedLinkColor() ) ;
            if( c != null ) {
                normalGateColor = c ;
                ivjJButton7.setBackground( normalGateColor ) ;

            }

        }
        if( actionCommand.equals( "SELECTED_GATE_COLOR" ) ) {
            Color c = JColorChooser.showDialog( utility.getFrame(),
                    "Choose the selected gate color",
                    utility.getSelectedGateColor() ) ;
            if( c != null ) {
                selectedGateColor = c ;
                ivjJButton8.setBackground( selectedGateColor ) ;
            }

        }
        if( actionCommand.equals( "TEXT_COLOR" ) ) {
            Color c = JColorChooser.showDialog( utility.getFrame(),
                    "Choose the text color", utility.getTextColor() ) ;
            if( c != null ) {
                textColor = c ;
                ivjJButton2.setBackground( textColor ) ;
            }

        }
        if( actionCommand.equals( "STATUS_BGCOLOR" ) ) {
            Color c = JColorChooser.showDialog( utility.getFrame(),
                    "Choose the status bar background color",
                    utility.getStatusbarBackgroundColor() ) ;
            if( c != null ) {
                statusBGColor = c ;
                ivjJButton5.setBackground( statusBGColor ) ;
            }

        }
        if( actionCommand.equals( "STATUS_FGCOLOR" ) ) {
            Color c = JColorChooser.showDialog( utility.getFrame(),
                    "Choose the status bar foreground color",
                    utility.getStatusbarForegroundColor() ) ;
            if( c != null ) {
                statusFGColor = c ;
                ivjJButton6.setBackground( statusFGColor ) ;
            }

        }
        if( actionCommand.equals( "GRID_COLOR" ) ) {
            Color c = JColorChooser.showDialog( utility.getFrame(),
                    "Choose the grid color", utility.getGridColor() ) ;
            if( c != null ) {
                gridColor = c ;
                ivjJButton9.setBackground( gridColor ) ;
            }
        }

        if( actionCommand.equals( "PROPERTIES_SET" ) ) {
            try {
                canvasHeight = Integer.parseInt( ivjJTextField1.getText() ) ;
            }
            catch( Exception e1 ) {
                canvasHeight = -1 ;
            }

            try {
                canvasWidth = Integer.parseInt( ivjJTextField2.getText() ) ;
            }
            catch( Exception e2 ) {
                canvasWidth = -1 ;
            }

            gridOn = ivjJCheckBox1.isSelected() ;

            updatePropertiesInformation() ;
            setVisible( false ) ;
        }
        else if( actionCommand.equals( "PROPERTIES_CANCEL" ) ) {
            setVisible( false ) ;
        }

    }

    private void updatePropertiesInformation() {

        if( backgroundColor != null ) {
            utility.setBackgroundColor( backgroundColor ) ;
            backgroundColor = null ;
        }
        if( selectedLinkColor != null ) {
            utility.setSelectedLinkColor( selectedLinkColor ) ;
            selectedLinkColor = null ;
        }
        if( normalLinkColor != null ) {
            utility.setLinkColor( normalLinkColor ) ;
            normalLinkColor = null ;
        }
        if( textColor != null ) {
            utility.setTextColor( textColor ) ;
            textColor = null ;
        }
        if( statusBGColor != null ) {
            utility.setStatusbarBackgroundColor( statusBGColor ) ;
            statusBGColor = null ;
        }
        if( statusFGColor != null ) {
            utility.setStatusbarForegroundColor( statusFGColor ) ;
            statusFGColor = null ;
        }
        if( normalGateColor != null ) {
            utility.setNormalGateColor( normalGateColor ) ;
            normalGateColor = null ;
        }
        if( selectedGateColor != null ) {
            utility.setSelectedGateColor( selectedGateColor ) ;
            selectedGateColor = null ;
        }
        if( gridColor != null ) {
            utility.setGridColor( gridColor ) ;
            gridColor = null ;
        }
        if( canvasHeight != -1 ) {
            utility.setCanvasHeight( canvasHeight ) ;
            canvasHeight = -1 ;
        }
        if( canvasWidth != -1 ) {
            utility.setCanvasWidth( canvasWidth ) ;
            canvasWidth = -1 ;
        }

        utility.setIsGridOn( gridOn ) ;

        utility.repaintCanvas() ;
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
                ivjJButton1.setText( "Color" ) ;
                ivjJButton1.setBounds( 137, 47, 85, 25 ) ;
                ivjJButton1.setActionCommand( "BACKGROUND_COLOR" ) ;
                // user code begin {1}
                ivjJButton1.addActionListener( this ) ;
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
     * Return the JButton10 property value.
     * 
     * @return JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JButton getJButton10() {

        if( ivjJButton10 == null ) {
            try {
                ivjJButton10 = new JButton() ;
                ivjJButton10.setName( "JButton10" ) ;
                ivjJButton10.setText( "OK" ) ;
                ivjJButton10.setBounds( 79, 288, 93, 25 ) ;
                ivjJButton10.setActionCommand( "PROPERTIES_SET" ) ;
                // user code begin {1}
                ivjJButton10.addActionListener( this ) ;
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJButton10 ;
    }

    /**
     * Return the JButton11 property value.
     * 
     * @return JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JButton getJButton11() {

        if( ivjJButton11 == null ) {
            try {
                ivjJButton11 = new JButton() ;
                ivjJButton11.setName( "JButton11" ) ;
                ivjJButton11.setText( "Cancel" ) ;
                ivjJButton11.setBounds( 192, 285, 93, 25 ) ;
                ivjJButton11.setActionCommand( "PROPERTIES_CANCEL" ) ;
                // user code begin {1}
                ivjJButton11.addActionListener( this ) ;
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJButton11 ;
    }

    /**
     * Return the JButton2 property value.
     * 
     * @return JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JButton getJButton2() {

        if( ivjJButton2 == null ) {
            try {
                ivjJButton2 = new JButton() ;
                ivjJButton2.setName( "JButton2" ) ;
                ivjJButton2.setText( "Color" ) ;
                ivjJButton2.setBounds( 138, 82, 85, 25 ) ;
                ivjJButton2.setActionCommand( "TEXT_COLOR" ) ;
                // user code begin {1}
                ivjJButton2.addActionListener( this ) ;
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJButton2 ;
    }

    /**
     * Return the JButton3 property value.
     * 
     * @return JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JButton getJButton3() {

        if( ivjJButton3 == null ) {
            try {
                ivjJButton3 = new JButton() ;
                ivjJButton3.setName( "JButton3" ) ;
                ivjJButton3.setText( "Normal" ) ;
                ivjJButton3.setBounds( 137, 115, 85, 25 ) ;
                ivjJButton3.setActionCommand( "NORMAL_LINK_COLOR" ) ;
                // user code begin {1}
                ivjJButton3.addActionListener( this ) ;
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJButton3 ;
    }

    /**
     * Return the JButton4 property value.
     * 
     * @return JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JButton getJButton4() {

        if( ivjJButton4 == null ) {
            try {
                ivjJButton4 = new JButton() ;
                ivjJButton4.setName( "JButton4" ) ;
                ivjJButton4.setText( "Selected" ) ;
                ivjJButton4.setBounds( 242, 115, 105, 26 ) ;
                ivjJButton4.setActionCommand( "SELECTED_LINK_COLOR" ) ;
                // user code begin {1}
                ivjJButton4.addActionListener( this ) ;
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJButton4 ;
    }

    /**
     * Return the JButton5 property value.
     * 
     * @return JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JButton getJButton5() {

        if( ivjJButton5 == null ) {
            try {
                ivjJButton5 = new JButton() ;
                ivjJButton5.setName( "JButton5" ) ;
                ivjJButton5.setText( "Bground" ) ;
                ivjJButton5.setBounds( 137, 150, 85, 25 ) ;
                ivjJButton5.setActionCommand( "STATUS_BGCOLOR" ) ;
                // user code begin {1}
                ivjJButton5.addActionListener( this ) ;
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJButton5 ;
    }

    /**
     * Return the JButton6 property value.
     * 
     * @return JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JButton getJButton6() {

        if( ivjJButton6 == null ) {
            try {
                ivjJButton6 = new JButton() ;
                ivjJButton6.setName( "JButton6" ) ;
                ivjJButton6.setText( "Foreground" ) ;
                ivjJButton6.setBounds( 242, 147, 106, 26 ) ;
                ivjJButton6.setActionCommand( "STATUS_FGCOLOR" ) ;
                // user code begin {1}
                ivjJButton6.addActionListener( this ) ;
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJButton6 ;
    }

    /**
     * Return the JButton7 property value.
     * 
     * @return JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JButton getJButton7() {

        if( ivjJButton7 == null ) {
            try {
                ivjJButton7 = new JButton() ;
                ivjJButton7.setName( "JButton7" ) ;
                ivjJButton7.setText( "Normal" ) ;
                ivjJButton7.setBounds( 137, 182, 85, 25 ) ;
                ivjJButton7.setActionCommand( "NORMAL_GATE_COLOR" ) ;
                // user code begin {1}
                ivjJButton7.addActionListener( this ) ;
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJButton7 ;
    }

    /**
     * Return the JButton8 property value.
     * 
     * @return JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JButton getJButton8() {

        if( ivjJButton8 == null ) {
            try {
                ivjJButton8 = new JButton() ;
                ivjJButton8.setName( "JButton8" ) ;
                ivjJButton8.setText( "Selected" ) ;
                ivjJButton8.setBounds( 242, 180, 104, 26 ) ;
                ivjJButton8.setActionCommand( "SELECTED_GATE_COLOR" ) ;
                // user code begin {1}
                ivjJButton8.addActionListener( this ) ;
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJButton8 ;
    }

    /**
     * Return the JButton9 property value.
     * 
     * @return JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JButton getJButton9() {

        if( ivjJButton9 == null ) {
            try {
                ivjJButton9 = new JButton() ;
                ivjJButton9.setName( "JButton9" ) ;
                ivjJButton9.setText( "Color" ) ;
                ivjJButton9.setBounds( 137, 214, 85, 25 ) ;
                ivjJButton9.setActionCommand( "GRID_COLOR" ) ;
                // user code begin {1}
                ivjJButton9.addActionListener( this ) ;
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJButton9 ;
    }

    /**
     * Return the JCheckBox1 property value.
     * 
     * @return JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JCheckBox getJCheckBox1() {

        if( ivjJCheckBox1 == null ) {
            try {
                ivjJCheckBox1 = new JCheckBox() ;
                ivjJCheckBox1.setName( "JCheckBox1" ) ;
                ivjJCheckBox1.setText( "On / Off" ) ;
                ivjJCheckBox1.setBounds( 241, 213, 99, 25 ) ;
                // user code begin {1}
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJCheckBox1 ;
    }

    /**
     * Return the JDialogContentPane property value.
     * 
     * @return JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JPanel getJDialogContentPane() {

        if( ivjJDialogContentPane == null ) {
            try {
                ivjJDialogContentPane = new JPanel() ;
                ivjJDialogContentPane.setName( "JDialogContentPane" ) ;
                ivjJDialogContentPane.setLayout( null ) ;
                getJDialogContentPane().add( getJLabel1(),
                        getJLabel1().getName() ) ;
                getJDialogContentPane().add( getJLabel2(),
                        getJLabel2().getName() ) ;
                getJDialogContentPane().add( getJButton1(),
                        getJButton1().getName() ) ;
                getJDialogContentPane().add( getJLabel3(),
                        getJLabel3().getName() ) ;
                getJDialogContentPane().add( getJButton2(),
                        getJButton2().getName() ) ;
                getJDialogContentPane().add( getJLabel4(),
                        getJLabel4().getName() ) ;
                getJDialogContentPane().add( getJButton3(),
                        getJButton3().getName() ) ;
                getJDialogContentPane().add( getJButton4(),
                        getJButton4().getName() ) ;
                getJDialogContentPane().add( getJLabel5(),
                        getJLabel5().getName() ) ;
                getJDialogContentPane().add( getJButton5(),
                        getJButton5().getName() ) ;
                getJDialogContentPane().add( getJButton6(),
                        getJButton6().getName() ) ;
                getJDialogContentPane().add( getJLabel6(),
                        getJLabel6().getName() ) ;
                getJDialogContentPane().add( getJButton7(),
                        getJButton7().getName() ) ;
                getJDialogContentPane().add( getJButton8(),
                        getJButton8().getName() ) ;
                getJDialogContentPane().add( getJLabel7(),
                        getJLabel7().getName() ) ;
                getJDialogContentPane().add( getJButton9(),
                        getJButton9().getName() ) ;
                getJDialogContentPane().add( getJCheckBox1(),
                        getJCheckBox1().getName() ) ;
                getJDialogContentPane().add( getJLabel8(),
                        getJLabel8().getName() ) ;
                getJDialogContentPane().add( getJTextField1(),
                        getJTextField1().getName() ) ;
                getJDialogContentPane().add( getJLabel9(),
                        getJLabel9().getName() ) ;
                getJDialogContentPane().add( getJLabel10(),
                        getJLabel10().getName() ) ;
                getJDialogContentPane().add( getJTextField2(),
                        getJTextField2().getName() ) ;
                getJDialogContentPane().add( getJButton10(),
                        getJButton10().getName() ) ;
                getJDialogContentPane().add( getJButton11(),
                        getJButton11().getName() ) ;
                // user code begin {1}
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJDialogContentPane ;
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
                ivjJLabel1
                        .setText( "Please choose the properties for the applicaiton." ) ;
                ivjJLabel1.setBounds( 29, 17, 378, 21 ) ;
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
     * Return the JLabel10 property value.
     * 
     * @return JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JLabel getJLabel10() {

        if( ivjJLabel10 == null ) {
            try {
                ivjJLabel10 = new JLabel() ;
                ivjJLabel10.setName( "JLabel10" ) ;
                ivjJLabel10.setText( "H" ) ;
                ivjJLabel10.setBounds( 242, 248, 17, 18 ) ;
                // user code begin {1}
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJLabel10 ;
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
                ivjJLabel2.setText( "Background" ) ;
                ivjJLabel2.setBounds( 29, 54, 84, 17 ) ;
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
                ivjJLabel3.setText( "Text" ) ;
                ivjJLabel3.setBounds( 29, 87, 45, 15 ) ;
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
                ivjJLabel4.setText( "Links" ) ;
                ivjJLabel4.setBounds( 29, 122, 45, 15 ) ;
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
     * Return the JLabel5 property value.
     * 
     * @return JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JLabel getJLabel5() {

        if( ivjJLabel5 == null ) {
            try {
                ivjJLabel5 = new JLabel() ;
                ivjJLabel5.setName( "JLabel5" ) ;
                ivjJLabel5.setText( "Status Bar" ) ;
                ivjJLabel5.setBounds( 29, 155, 73, 16 ) ;
                // user code begin {1}
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJLabel5 ;
    }

    /**
     * Return the JLabel6 property value.
     * 
     * @return JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JLabel getJLabel6() {

        if( ivjJLabel6 == null ) {
            try {
                ivjJLabel6 = new JLabel() ;
                ivjJLabel6.setName( "JLabel6" ) ;
                ivjJLabel6.setText( "Gate" ) ;
                ivjJLabel6.setBounds( 29, 189, 45, 15 ) ;
                // user code begin {1}
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJLabel6 ;
    }

    /**
     * Return the JLabel7 property value.
     * 
     * @return JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JLabel getJLabel7() {

        if( ivjJLabel7 == null ) {
            try {
                ivjJLabel7 = new JLabel() ;
                ivjJLabel7.setName( "JLabel7" ) ;
                ivjJLabel7.setText( "Grid" ) ;
                ivjJLabel7.setBounds( 30, 219, 45, 15 ) ;
                // user code begin {1}
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJLabel7 ;
    }

    /**
     * Return the JLabel8 property value.
     * 
     * @return JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JLabel getJLabel8() {

        if( ivjJLabel8 == null ) {
            try {
                ivjJLabel8 = new JLabel() ;
                ivjJLabel8.setName( "JLabel8" ) ;
                ivjJLabel8.setText( "Canvas" ) ;
                ivjJLabel8.setBounds( 30, 248, 45, 15 ) ;
                // user code begin {1}
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJLabel8 ;
    }

    /**
     * Return the JLabel9 property value.
     * 
     * @return JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JLabel getJLabel9() {

        if( ivjJLabel9 == null ) {
            try {
                ivjJLabel9 = new JLabel() ;
                ivjJLabel9.setName( "JLabel9" ) ;
                ivjJLabel9.setText( "W" ) ;
                ivjJLabel9.setBounds( 137, 251, 17, 16 ) ;
                // user code begin {1}
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJLabel9 ;
    }

    /**
     * Return the JTextField1 property value.
     * 
     * @return JTextField
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JTextField getJTextField1() {

        if( ivjJTextField1 == null ) {
            try {
                ivjJTextField1 = new JTextField() ;
                ivjJTextField1.setName( "JTextField1" ) ;
                ivjJTextField1.setBounds( 160, 248, 62, 23 ) ;
                // user code begin {1}
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJTextField1 ;
    }

    /**
     * Return the JTextField2 property value.
     * 
     * @return JTextField
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JTextField getJTextField2() {

        if( ivjJTextField2 == null ) {
            try {
                ivjJTextField2 = new JTextField() ;
                ivjJTextField2.setName( "JTextField2" ) ;
                ivjJTextField2.setBounds( 258, 248, 78, 22 ) ;
                // user code begin {1}
                // user code end
            }
            catch( java.lang.Throwable ivjExc ) {
                // user code begin {2}
                // user code end
                handleException( ivjExc ) ;
            }
        }
        return ivjJTextField2 ;
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
            setName( "DCDPropertiesDialog" ) ;
            setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE ) ;
            setBounds( 200, 200, 362, 350 ) ;
            setModal( true ) ;
            setResizable( false ) ;
            setTitle( "DCD Properties Dialog" ) ;
            setContentPane( getJDialogContentPane() ) ;
        }
        catch( java.lang.Throwable ivjExc ) {
            handleException( ivjExc ) ;
        }
        // user code begin {2}
        // user code end
    }

    /**
     * Starts the application.
     * 
     * @param args
     *            an array of command-line arguments
     */
    public static void main( java.lang.String[] args ) {

        // Insert code to start the application here.
        new DCDPropertiesDialog().setVisible( true ) ;
    }
}
