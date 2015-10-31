// Source file: d:/sandy/dcd/DCD/ui/DCDCanvas.java

package com.sandy.apps.dcs.ui ;

import java.awt.* ;

import javax.swing.* ;

import com.sandy.apps.dcs.common.* ;

public class DCDCanvas extends JPanel {

    private CircuitController controller ;

    public DCDCanvas() {

        setBackground( DCDUtility.getInstance().getBackgroundColor() ) ;
    }

    public Graphics getGraphics() {

        return ( super.getGraphics() ) ;
    }

    public void paint( Graphics g ) {

        super.paint( g ) ;
        DCDUtility utility = DCDUtility.getInstance() ;
        if( utility.getIsGridOn() ) drawGrid( g, utility ) ;
        controller.initiatePainting( g ) ;
    }

    private void drawGrid( Graphics g, DCDUtility utility ) {

        Color c = utility.getGridColor() ;
        int width = utility.getCanvasWidth() ;
        int height = utility.getCanvasHeight() ;
        g.setColor( c ) ;
        for( int x = 0 ; x < width ; x += 10 ) {
            g.drawLine( x, 0, x, height ) ;
        }
        for( int y = 0 ; y < height ; y += 10 ) {
            g.drawLine( 0, y, width, y ) ;
        }
    }

    public void setController( CircuitController controller ) {

        this.controller = controller ;
    }
}
