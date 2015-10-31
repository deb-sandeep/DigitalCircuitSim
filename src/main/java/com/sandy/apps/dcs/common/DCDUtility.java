package com.sandy.apps.dcs.common ;

import java.awt.Color ;
import java.awt.Component ;
import java.awt.Cursor ;
import java.awt.Dimension ;
import java.awt.Graphics2D ;
import java.awt.Point ;
import java.awt.Rectangle ;
import java.awt.Toolkit ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.awt.event.ItemEvent ;
import java.awt.event.ItemListener ;
import java.awt.event.MouseEvent ;
import java.awt.geom.Point2D ;
import java.io.File ;
import java.io.FileWriter ;
import java.io.InputStream ;
import java.net.URL ;
import java.util.BitSet ;
import java.util.Enumeration ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;
import java.util.MissingResourceException ;
import java.util.PropertyResourceBundle ;

import javax.help.HelpBroker ;
import javax.help.HelpSet ;
import javax.swing.AbstractButton ;
import javax.swing.ImageIcon ;
import javax.swing.JFileChooser ;
import javax.swing.JOptionPane ;
import javax.swing.JTextArea ;
import javax.swing.JToggleButton ;

import com.sandy.apps.dcs.component.DCDComponent ;
import com.sandy.apps.dcs.cor.Chain ;
import com.sandy.apps.dcs.factory.DCDICFactory ;
import com.sandy.apps.dcs.ui.DCDCanvas ;
import com.sandy.apps.dcs.ui.DCDController ;
import com.sandy.apps.dcs.ui.DCDFrame ;
import com.sandy.apps.dcs.ui.DCDPropertiesDialog ;
import com.sandy.apps.dcs.ui.DCDToolBar ;

public class DCDUtility implements ItemListener, ActionListener {

    private static DCDUtility   instance ;

    private Color               backgroundColor          = Color.white ;

    private Color               linkColor                = Color.black ;

    private Color               statusbarBackgroundColor = Color.gray ;

    private Color               statusbarForegroundColor = Color.black ;

    private Color               selectedLinkColor        = Color.red ;

    private Color               selectedGateColor        = Color.red ;

    private Color               normalGateColor          = Color.black ;

    private Color               crossHairColor           = Color.green ;

    private Color               textColor                = Color.black ;

    private Color               gridColor                = new Color( 100, 100,
                                                                 100 ) ;

    private int                 canvasWidth              = 2000 ;

    private int                 canvasHeight             = 2000 ;

    private HelpSet             hs                       = null ;

    private HelpBroker          hb                       = null ;

    private boolean             isGridOn                 = true ;

    private String              selectedToggleButtonID   = "" ;

    private DCDFrame            frame ;

    private DCDCanvas           canvas ;

    private DCDController       controller ;

    private DCDToolBar          toolBar ;

    private JTextArea           descriptionArea ;

    private JTextArea           messageArea ;

    private JTextArea           addedInformationArea ;

    private DCDPropertiesDialog propertiesDialog         = null ;

    private Map<String, Color>  colorLookupTable         = new HashMap<String, Color>() ;

    // This is the point at which the huge cross hair is drawn in the
    // connector mode.
    private Point2D             oldPoint                 = null ;

    // This will store the current file that is being displayed
    // on the workspace. This information is being kept here
    // in the Utility class because the information will be
    // used both by the CircuitSaver and the CircuitLoader classes.
    private File                currentFile              = null ;

    private String              currentICFile            = null ;

    private DCDUtility() {

        fillColorLookupTable() ;
        readINI() ;

    }

    // This function fills up a hash table which associates
    // a String with a Color instance.
    private void fillColorLookupTable() {

        colorLookupTable.put( "BLACK", Color.black ) ;
        colorLookupTable.put( "BLUE", Color.blue ) ;
        colorLookupTable.put( "CYAN", Color.cyan ) ;
        colorLookupTable.put( "DARKGRAY", Color.darkGray ) ;
        colorLookupTable.put( "GRAY", Color.gray ) ;
        colorLookupTable.put( "GREEN", Color.green ) ;
        colorLookupTable.put( "LIGHTGRAY", Color.lightGray ) ;
        colorLookupTable.put( "MAGENTA", Color.magenta ) ;
        colorLookupTable.put( "ORANGE", Color.orange ) ;
        colorLookupTable.put( "PINK", Color.pink ) ;
        colorLookupTable.put( "RED", Color.red ) ;
        colorLookupTable.put( "WHITE", Color.white ) ;
        colorLookupTable.put( "YELLOW", Color.yellow ) ;
    }

    public void actionPerformed( ActionEvent e ) {

        AbstractButton b = (AbstractButton) e.getSource() ;
        String command = b.getActionCommand() ;
        if( command.equals( "PROPERTIES_SET" ) ) {
            // This is encountered when the OKAY button of the setproperties
            // dialog is pressed.
        }
        else if( command.equals( "SET_PROPERTIES" ) ) {
            // This is encountered when the menu item is pressed.
            if( propertiesDialog == null ) propertiesDialog = new DCDPropertiesDialog() ;
            propertiesDialog
                    .setLocation( getCenterLocation( propertiesDialog ) ) ;
            propertiesDialog.setVisible( true ) ;
        }
        else if( command.equals( "HELP" ) ) {
            if( hs == null ) {
                createHelpSet() ;
                hb = hs.createHelpBroker() ;
            }
            if( hb != null ) hb.setDisplayed( true ) ;
        }
    }

    private void createHelpSet() {

        ClassLoader loader = this.getClass().getClassLoader() ;
        URL url ;
        try {
            url = HelpSet.findHelpSet( loader, "DCDHelp" ) ;
            if( url == null ) System.out.println( "The HelpSet URL is null" ) ;
            else hs = new HelpSet( loader, url ) ;
        }
        catch( Exception ee ) {
            System.out.println( "Trouble in createHelpSet;" ) ;
            ee.printStackTrace() ;
            return ;
        }
    }

    public static DCDUtility getInstance() {

        if( instance == null ) instance = new DCDUtility() ;
        return ( instance ) ;
    }

    public Color getCrossHairColor() {

        return this.crossHairColor ;
    }

    public void setCrossHairColor( Color crossHairColor ) {

        this.crossHairColor = crossHairColor ;
    }

    public void itemStateChanged( ItemEvent e ) {

        // 1. First we have to erase the cross hair . .. if one remains
        // on the screen.
        // if(oldPoint != null)
        // {
        // Graphics2D g= getGraphics();
        // g.setColor(getCrossHairColor());
        // g.setXORMode(getBackgroundColor());
        // drawCrossHair(oldPoint, g, getFrame().getBounds());
        // oldPoint=null;
        // }

        // 2. Change the toggle status.
        JToggleButton button = (JToggleButton) e.getSource() ;
        if( button.isSelected() ) {
            selectedToggleButtonID = button.getActionCommand() ;
            if( !selectedToggleButtonID.equals( "CONNECTOR" ) ) canvas
                    .setCursor( new Cursor( Cursor.CROSSHAIR_CURSOR ) ) ;
            else {
                canvas.setCursor( new Cursor( Cursor.DEFAULT_CURSOR ) ) ;
            }
            // Disable the multi input button if the present
            // selected toggle button is not one of the following.
            if( selectedToggleButtonID.equals( "OR" )
                    || selectedToggleButtonID.equals( "AND" )
                    || selectedToggleButtonID.equals( "XOR" )
                    || selectedToggleButtonID.equals( "NAND" ) ) {
                toolBar.enableButton( "MULTI_INPUT" ) ;
            }
            else {
                toolBar.disableButton( "MULTI_INPUT" ) ;
            }

            if( selectedToggleButtonID.equals( "IC" ) ) {
                getICInformationFromUser() ;
            }
        }

    }

    public void getICInformationFromUser() {

        List<String> primordialICNames = DCDICFactory.getPrimordialICNames() ;
        if( !primordialICNames.contains( "LET ME SELECT" ) ) {
            primordialICNames.add( 0, "LET ME SELECT" ) ;

        }

        Object[] possibleValues = primordialICNames.toArray() ;
        String selectedValue = (String) JOptionPane.showInputDialog( null,
                "Choose one", "Input", JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, possibleValues[0] ) ;
        if( selectedValue != null ) {
            if( selectedValue.equals( "LET ME SELECT" ) ) {
                DCDUtility utility = DCDUtility.getInstance() ;
                JFileChooser fileChooser = new JFileChooser(
                        new File( "" ).getAbsolutePath() ) ;
                int choice = fileChooser.showOpenDialog( utility.getFrame() ) ;
                if( choice == JFileChooser.APPROVE_OPTION ) {
                    File file = fileChooser.getSelectedFile() ;
                    currentICFile = new String( file.getAbsolutePath() ) ;
                }
            }
            else currentICFile = new String( selectedValue ) ;

        }
        else currentICFile = null ;

    }

    public String getSelectedToggleButtonID() {

        return ( this.selectedToggleButtonID ) ;

    }

    public static ImageIcon loadIconResource( String s ) {

        return loadIconResource( s, s ) ;
    }

    protected static ImageIcon loadIconResource( String s, String s1 ) {

        try {
            URL url = DCDUtility.class.getResource( imageName( s ) ) ;
            if( url == null ) {
                return null ;
            }
            else {
                return new ImageIcon( url, s1 + " " ) ;
            }
        }
        catch( Exception exception ) {
            System.out.println( "Exception in loadIconResource" ) ;
            exception.printStackTrace() ;
            return new ImageIcon( s1 ) ;
        }
    }

    public static InputStream getICResourceAsStream( String s ) {

        try {
            InputStream in = DCDUtility.class.getResourceAsStream( icName( s ) ) ;
            return in ;
        }
        catch( Exception exception ) {
            System.out.println( "Exception in loading IC" ) ;
            exception.printStackTrace() ;
        }
        return null ;
    }

    protected static String imageName( String s ) {

        return "/com/sandy/apps/dcs/images/toolbar/" + s + ".gif" ;
    }

    // This will return the path of the primordial IC.
    protected static String icName( String s ) {

        return "/com/sandy/apps/dcs/ics/" + s + ".ic" ;
    }

    // This function reads the ini file and fills up the variables.
    // If the ini file is not present then a message is popped and
    // default values are set, which are returned by the getter methods.
    private void readINI() {

        PropertyResourceBundle nvps = null ;

        // load the property file
        try {
            InputStream in = DCDUtility.class
                    .getResourceAsStream( "/DCD.properties" ) ;
            nvps = new PropertyResourceBundle( in ) ;
        }
        catch( Exception e ) {
            JOptionPane
                    .showMessageDialog( frame,
                            "Configuration file not found.. \nSetting all properties to defaultValues" ) ;
            System.out.println( e ) ;
            return ;
        }

        try {
            backgroundColor = getColor( nvps.getString( "BACKGROUND_COLOR" )
                    .toUpperCase() ) ;
        }
        catch( MissingResourceException e ) {
            e.printStackTrace() ;
        }
        try {
            linkColor = getColor( nvps.getString( "LINK_COLOR" ).toUpperCase() ) ;
        }
        catch( MissingResourceException e ) {
        }
        try {
            selectedLinkColor = getColor( nvps.getString( "SELECTEDLINK_COLOR" )
                    .toUpperCase() ) ;
        }
        catch( MissingResourceException e ) {
        }
        try {
            normalGateColor = getColor( nvps.getString( "NORMAL_GATE_COLOR" )
                    .toUpperCase() ) ;
        }
        catch( MissingResourceException e ) {
        }
        try {
            selectedGateColor = getColor( nvps
                    .getString( "SELECTED_GATE_COLOR" ).toUpperCase() ) ;
        }
        catch( MissingResourceException e ) {
        }
        try {
            statusbarBackgroundColor = getColor( nvps.getString(
                    "STATUSBAR_BACKGROUND_COLOR" ).toUpperCase() ) ;
        }
        catch( MissingResourceException e ) {
        }
        try {
            statusbarForegroundColor = getColor( nvps.getString(
                    "STATUSBAR_FOREGROUND_COLOR" ).toUpperCase() ) ;
        }
        catch( MissingResourceException e ) {
        }
        try {
            crossHairColor = getColor( nvps.getString( "CROSS_HAIR_COLOR" )
                    .toUpperCase() ) ;
        }
        catch( MissingResourceException e ) {
        }
        try {
            gridColor = getColor( nvps.getString( "GRID_COLOR" ).toUpperCase() ) ;
        }
        catch( MissingResourceException e ) {
        }
        try {
            textColor = getColor( nvps.getString( "TEXT_COLOR" ).toUpperCase() ) ;
        }
        catch( MissingResourceException e ) {
        }
        try {
            isGridOn = ( new Boolean( nvps.getString( "GRID_ON" ) ) )
                    .booleanValue() ;
        }
        catch( MissingResourceException e ) {
        }
        try {
            canvasWidth = ( new Integer( nvps.getString( "CANVAS_WIDTH" ) ) )
                    .intValue() ;
        }
        catch( MissingResourceException e ) {
        }
        try {
            canvasHeight = ( new Integer( nvps.getString( "CANVAS_HEIGHT" ) ) )
                    .intValue() ;
        }
        catch( MissingResourceException e ) {
        }
    }

    public static void showMessage( String message ) {

        JOptionPane.showMessageDialog( null, message ) ;
    }

    public void showStatusMessage( String message ) {

        frame.showStatusMessage( message ) ;
    }

    // Getter and setter methods
    public Color getBackgroundColor() {

        return this.backgroundColor ;
    }

    public void setBackgroundColor( Color backgroundColor ) {

        this.backgroundColor = backgroundColor ;
        canvas.setBackground( backgroundColor ) ;
    }

    public Color getNormalGateColor() {

        return ( normalGateColor ) ;
    }

    public void setNormalGateColor( Color color ) {

        normalGateColor = color ;
    }

    public Color getSelectedGateColor() {

        return ( selectedGateColor ) ;
    }

    public void setSelectedGateColor( Color color ) {

        selectedGateColor = color ;
    }

    public Color getTextColor() {

        return this.textColor ;
    }

    public Color getConnectorColor() {

        return this.linkColor ;
    }

    public void setConnectorColor( Color connectorColor ) {

        this.linkColor = connectorColor ;
    }

    public void setTextColor( Color color ) {

        this.textColor = color ;
    }

    public DCDFrame getFrame() {

        return this.frame ;
    }

    public void setFrame( DCDFrame frame ) {

        this.frame = frame ;
    }

    public Color getStatusbarBackgroundColor() {

        return this.statusbarBackgroundColor ;
    }

    public void setStatusbarBackgroundColor( Color statusbarBackgroundColor ) {

        this.statusbarBackgroundColor = statusbarBackgroundColor ;
        frame.setStatusbarBackgroundColor( statusbarBackgroundColor ) ;
    }

    public Color getStatusbarForegroundColor() {

        return this.statusbarForegroundColor ;
    }

    public void setStatusbarForegroundColor( Color statusbarForegroundColor ) {

        this.statusbarForegroundColor = statusbarForegroundColor ;
        frame.setStatusbarForegroundColor( statusbarForegroundColor ) ;
    }

    public void setCanvas( DCDCanvas c ) {

        canvas = c ;
    }

    public Graphics2D getGraphics() {

        return ( (Graphics2D) canvas.getGraphics() ) ;
    }

    public Color getSelectedLinkColor() {

        return this.selectedLinkColor ;
    }

    public void setSelectedLinkColor( Color selectedLinkColor ) {

        this.selectedLinkColor = selectedLinkColor ;
    }

    public Color getLinkColor() {

        return this.linkColor ;
    }

    public void setLinkColor( Color linkColor ) {

        this.linkColor = linkColor ;
    }

    public void setController( DCDController controller ) {

        this.controller = controller ;
    }

    public DCDComponent componentAtMouseClick( MouseEvent e ) {

        return controller.getComponentAtPoint( e ) ;
    }

    public Chain getChainHead() {

        return ( controller.getChainHead() ) ;
    }

    public void addLinkToChain( DCDComponent component ) {

        controller.addLinkToChain( component ) ;
    }

    public void setSelectedComponent( DCDComponent c ) {

        controller.setSelectedComponent( c ) ;
    }

    public void deleteDCDComponent( DCDComponent component ) {

        controller.deleteComponent( component ) ;
    }

    public Enumeration getAllComponentsAtPoint( MouseEvent e ) {

        return ( controller.getAllComponentsAtPoint( e ) ) ;
    }

    public DCDComponent getComponentAtPoint( MouseEvent e ) {

        return ( controller.getComponentAtPoint( e ) ) ;

    }

    public DCDController getDCDController() {

        return ( controller ) ;
    }

    public Point2D getOldCrossHairPoint() {

        return ( oldPoint ) ;
    }

    public void setOldCrossHairPoint( Point2D p ) {

        this.oldPoint = p ;
    }

    public void drawCrossHair( Point2D p, Graphics2D g, Rectangle rect ) {

        g.drawLine( 0, (int) p.getY(), rect.width, (int) p.getY() ) ;
        g.drawLine( (int) p.getX(), 0, (int) p.getX(), rect.height ) ;
    }

    public boolean getIsGridOn() {

        return this.isGridOn ;
    }

    public void setIsGridOn( boolean isGridOn ) {

        this.isGridOn = isGridOn ;
    }

    public Color getGridColor() {

        return this.gridColor ;
    }

    public void setGridColor( Color gridColor ) {

        this.gridColor = gridColor ;
    }

    public DCDToolBar getToolBar() {

        return this.toolBar ;
    }

    public void setToolBar( DCDToolBar toolBar ) {

        this.toolBar = toolBar ;
    }

    public DCDCanvas getDCDCanvas() {

        return this.canvas ;
    }

    public void setDCDCanvas( DCDCanvas canvas ) {

        this.canvas = canvas ;
    }

    public File getCurrentFile() {

        return this.currentFile ;
    }

    public void setCurrentFile( File currentFile ) {

        this.currentFile = currentFile ;
        if( currentFile == null ) frame.setTitle( "" ) ;
        else frame.setTitle( currentFile.getAbsolutePath() ) ;
    }

    public void sleep( int milli ) {

        try {
            Thread.sleep( milli ) ;
        }
        catch( InterruptedException e ) {
        }
    }

    public String getCurrentICFile() {

        return this.currentICFile ;
    }

    public void setCurrentICFile( String currentICFile ) {

        this.currentICFile = currentICFile ;
    }

    public int getCanvasHeight() {

        return this.canvasHeight ;
    }

    public void setCanvasHeight( int canvasHeight ) {

        this.canvasHeight = canvasHeight ;
    }

    public int getCanvasWidth() {

        return this.canvasWidth ;
    }

    public void setCanvasWidth( int canvasWidth ) {

        this.canvasWidth = canvasWidth ;
    }

    public JTextArea getMessageArea() {

        return this.messageArea ;
    }

    public void setMessageArea( JTextArea messageArea ) {

        this.messageArea = messageArea ;
    }

    public JTextArea getDescriptionArea() {

        return this.descriptionArea ;
    }

    public void setDescriptionArea( JTextArea descriptionArea ) {

        this.descriptionArea = descriptionArea ;
    }

    public void setAddedInformationArea( JTextArea descriptionArea ) {

        this.addedInformationArea = descriptionArea ;
    }

    public String getMessageString() {

        if( messageArea != null ) return messageArea.getText() ;

        return null ;
    }

    public void setMessageString( String str ) {

        messageArea.append( "\n" + str ) ;
    }

    public String getDescriptionString() {

        if( descriptionArea != null ) return descriptionArea.getText() ;

        return null ;
    }

    public void setDescriptionString( String str ) {

        descriptionArea.setText( str ) ;
    }

    public void setAddedInformationString( String str ) {

        addedInformationArea.setText( str ) ;
    }

    // This function is just for debugging purposes.
    public String printCharge( BitSet b, int numBits ) {

        StringBuffer buffer = new StringBuffer() ;
        buffer.append( "[ " ) ;
        for( int i = 0 ; i < numBits ; i++ ) {
            if( b.get( i ) ) buffer.append( " 1 " ) ;
            else buffer.append( " 0 " ) ;
        }
        buffer.append( " ]" ) ;
        return ( buffer.toString() ) ;
    }

    public void fireAllUnfiredFlipFlops() {

        controller.fireAllUnfiredFlipFlops() ;
    }

    public void clearFiringStatusOfAllFlipFlops() {

        controller.clearFiringStatusOfAllFlipFlops() ;
    }

    // This return a color object is the string passed is in the HTML format..eg
    // #c0c0c0
    // or else its in the color name format eg red, blue..
    // else it returns a null;
    public Color getColor( String col ) {

        int r = 0 ;
        int g = 0 ;
        int b = 0 ;

        // Check if its in the HTML format... it will be if its saved by the
        // application.
        if( col.startsWith( "#" ) ) {
            try {
                if( col.length() == 7 ) {
                    r = Integer.parseInt( col.substring( 1, 3 ), 16 ) ;
                    g = Integer.parseInt( col.substring( 3, 5 ), 16 ) ;
                    b = Integer.parseInt( col.substring( 5 ), 16 ) ;
                }
            }
            catch( Exception e ) {
                return null ;
            }
            return new Color( r, g, b ) ;
        }
        else {
            // Check if its in name format .. eg red.. etc. This will be the
            // case if the
            // user has overwritten the properties file.
            Color c = (Color) colorLookupTable.get( col ) ;
            return c ;
        }

    }

    private String getColorAsString( Color color ) {

        int r = color.getRed() ;
        int g = color.getGreen() ;
        int b = color.getBlue() ;

        String sR = Integer.toString( r, 16 ).toUpperCase() ;
        String sG = Integer.toString( g, 16 ).toUpperCase() ;
        String sB = Integer.toString( b, 16 ).toUpperCase() ;

        if( sR.length() == 1 ) sR = "0" + sR ;
        if( sG.length() == 1 ) sG = "0" + sG ;
        if( sB.length() == 1 ) sB = "0" + sB ;

        StringBuffer buff = new StringBuffer() ;
        buff.append( "#" ) ;
        buff.append( sR ) ;
        buff.append( sG ) ;
        buff.append( sB ) ;
        return buff.toString() ;
    }

    public void repaintCanvas() {

        canvas.repaint() ;
    }

    public void savePropertiesInformation() {

        try {
            FileWriter fout = new FileWriter( new File( "DCD.properties" ) ) ;

            fout.write( "# This properties file is read by the application on starting.\n" ) ;
            fout.write( "# This contains all the configurable properties of the application. \n" ) ;
            fout.write( "# This file can be overwritten by the user or the application's \n" ) ;
            fout.write( "# UI Options-Properties dialog can be used to set the values\n" ) ;

            fout.write( "BACKGROUND_COLOR="
                    + getColorAsString( backgroundColor ) + "\n" ) ;
            fout.write( "STATUSBAR_BACKGROUND_COLOR="
                    + getColorAsString( statusbarBackgroundColor ) + "\n" ) ;
            fout.write( "STATUSBAR_FOREGROUND_COLOR="
                    + getColorAsString( statusbarForegroundColor ) + "\n" ) ;
            fout.write( "LINK_COLOR=" + getColorAsString( linkColor ) + "\n" ) ;
            fout.write( "SELECTEDLINK_COLOR="
                    + getColorAsString( selectedLinkColor ) + "\n" ) ;
            fout.write( "SELECTED_GATE_COLOR="
                    + getColorAsString( selectedGateColor ) + "\n" ) ;
            fout.write( "NORMAL_GATE_COLOR="
                    + getColorAsString( normalGateColor ) + "\n" ) ;
            fout.write( "GRID_COLOR=" + getColorAsString( gridColor ) + "\n" ) ;
            fout.write( "TEXT_COLOR=" + getColorAsString( textColor ) + "\n" ) ;
            fout.write( "GRID_ON=" + isGridOn + "\n" ) ;
            fout.write( "CANVAS_WIDTH=" + canvasWidth + "\n" ) ;
            fout.write( "CANVAS_HEIGHT=" + canvasHeight + "\n" ) ;

            fout.close() ;
        }
        catch( Exception exception ) {
        }
    }

    public Point getCenterLocation( Component component ) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize() ;
        int x = (int) ( screenSize.width / 2 - component.getSize().getWidth() / 2 ) ;
        int y = (int) ( screenSize.height / 2 - component.getSize().getHeight() / 2 ) ;
        return new Point( x, y ) ;
    }
}