package com.sandy.apps.dcs.component ;

import java.awt.Component ;
import java.awt.Graphics ;
import java.awt.Point ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.awt.event.MouseEvent ;
import java.io.Serializable ;

import com.sandy.apps.dcs.component.model.DCDComponentModel ;
import com.sandy.apps.dcs.component.view.DCDComponentUI ;
import com.sandy.apps.dcs.ui.DCDNameDialog ;
import com.sandy.apps.dcs.util.Chain ;
import com.sandy.apps.dcs.util.DCDUtility ;

/**
 * Every component is a link in the chain of reponsibility. This ensures that
 * all the components get a chance to handle t he events propagated in the chain
 * by the controller.
 */

// NOTE: Any class that inherits from this class must implement the
// following methods.
// 1) isSelected(MouseEvent e) from the Chain interface
// NOTE: The inherited classes may also override the mouse related classes
// if it wants to process these events to do something meaningful.
public abstract class DCDComponent implements Chain, Serializable,
        ActionListener {

    // The componen which is the next in the chain of responsibility.
    private Chain             nextLink ;

    // The data model for this component.
    private DCDComponentModel model ;

    // The renderer of this component. This class incorporated the rendering
    // logic for the component.
    private DCDComponentUI    ui ;

    // This is the name of this component. Every component can have a name
    // to identify it.
    private String            name             = "" ;

    private String            shortDescription = "" ;

    private String            longDescription  = "" ;

    private boolean           isSelected ;

    protected String          internalID       = "0" ;

    private static int        ID               = 0 ;

    public DCDComponent() {

        setName( getType() ) ;
    }

    private DCDComponent( Chain chain ) {

        setName( getType() ) ;
        setNextLink( chain ) ;
    }

    public String getInternalID() {

        return this.internalID ;
    }

    public void setInternalID( String internalID ) {

        this.internalID = internalID ;
    }

    public DCDComponent( Chain chain, DCDComponentModel model, DCDComponentUI ui ) {

        this( chain ) ;
        this.model = model ;
        this.ui = ui ;
        setName( getType() ) ;
    }

    // GETTER AND SETTER METHODS
    public void setNextLink( Chain chain ) {

        nextLink = chain ;
    }

    public Chain getNextLink() {

        return ( nextLink ) ;
    }

    // This is called when an event propagates thru the chain.
    // When a mouse is pressed the controller wants to know as
    // to which component is selected. This is because all the
    // subsiquent mouse events have to be handeled by the selected
    // component. The subsiquent events mainly include MouseDragEvents.

    // The general logic of this function should be to determine wether
    // this mouse click occured near it. If yes return a true else
    // return a false.
    public boolean isSelected( MouseEvent event ) {

        return ( ui.isSelected( event ) ) ;
    }

    // <<DEBATE>>
    // NOTE: Does it make more sense to declare these methods as abstract??
    // This will force the subclasses to implement these methods.
    // I argue against this because there might be some compoents
    // which might not need to handle these clicks on it. So it may
    // safely ignore these clicks. So it makes more sense to declare
    // these methods and give them empty body here.

    // This is called on this compoenent if this is the selected component
    // This can be used to pop up the popup menus.
    public void handleMouseClick( MouseEvent e ) {

    }

    // This is redirected to this instance if this is selected and
    // mouse drag occured.
    public void handleMouseDrag( MouseEvent e ) {

    }

    // This component should be notified when the mouse has been released.
    public void handleMouseRelease( MouseEvent e ) {

    }

    // This is a realization of the Chain interface.
    public boolean handleMouseOver( MouseEvent e ) {

        return ( false ) ;
    }

    // This method will be called when this component is being deleted
    // from the workspace. At this moment the component can perform any
    // cleanup actions that it wants to do. Clean up actions might
    // include erasing the component from the screen and removing this
    // component from the underlying web of component connections.
    public void cleanUp() {

        ui.paint( DCDUtility.getInstance().getGraphics(),
                DCDComponentUI.DELETED ) ;
        // TODO=> Have to inform the model that this component is being
        // deleted. The model should then take care as to release
        // the connections and do other nessary cleanup actions.
    }

    public DCDComponentModel getModel() {

        return this.model ;
    }

    public void setModel( DCDComponentModel model ) {

        this.model = model ;
    }

    public DCDComponentUI getUI() {

        return this.ui ;
    }

    public void setUI( DCDComponentUI ui ) {

        this.ui = ui ;
    }

    public void actionPerformed( ActionEvent e ) {

        // Here we have to refresh the name of the component on the screen.
        DCDNameDialog nameDialog = DCDNameDialog.getInstance() ;
        nameDialog.show( name, shortDescription, longDescription ) ;
        setName( nameDialog.getName() ) ;
        setShortDescription( nameDialog.getShortDescription() ) ;
        setLongDescription( nameDialog.getLongDescription() ) ;
    }

    public void paint( Graphics g ) {

        if( isSelected() ) ui.paint( g, DCDComponentUI.SELECTED ) ;
        else ui.paint( g, DCDComponentUI.NORMAL ) ;
    }

    public void setSelected( Graphics g, boolean selected ) {

        isSelected = selected ;
        if( selected ) ui.paint( g, DCDComponentUI.SELECTED ) ;
        else ui.paint( g, DCDComponentUI.DESELECTED ) ;
    }

    public void showPopupMenu( Point p, Component component ) {

        ui.showPopupMenu( p, component ) ;
    }

    public void setSelected( boolean selected ) {

        this.isSelected = selected ;
    }

    public boolean isSelected() {

        return ( this.isSelected ) ;

    }

    public String getName() {

        return this.name ;
    }

    public void setName( String name ) {

        this.name = name ;
    }

    public String getSavingInformation() {

        // For testing purposes returning null
        return ( "Nah !! Not implemented here .. stupid" ) ;
    }

    public static void incrementID() {

        ID++ ;
    }

    protected abstract String getType() ;

    // This method will be overridden by all the classes which
    // will extend from this class and will represent the
    // components visible on the screen.
    // The need for this arises because each component needs to have
    // a unique ID. The ID will be a function of the location
    // of the gate on the screen.
    public String getHashCode() {

        return ( getType() + ui.getHashCode() ) ;
    }

    public String getShortDescription() {

        if( shortDescription == null ) return "" ;
        return this.shortDescription ;
    }

    public void setShortDescription( String shortDescription ) {

        this.shortDescription = shortDescription ;
    }

    public String getLongDescription() {

        if( longDescription == null ) return "" ;
        return this.longDescription ;
    }

    public void setLongDescription( String longDescription ) {

        this.longDescription = longDescription ;
    }
}
