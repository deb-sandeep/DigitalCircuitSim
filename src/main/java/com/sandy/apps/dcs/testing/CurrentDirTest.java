package com.sandy.apps.dcs.testing ;

import java.io.* ;
import javax.swing.* ;

public class CurrentDirTest {

    public static void main( String[] args ) {

        JFileChooser fileChooser = new JFileChooser() ;
        fileChooser.showOpenDialog( null ) ;
        File file = fileChooser.getSelectedFile() ;
        System.out.println( file.getPath() ) ;
        System.out.println( new File( "" ).getAbsolutePath() ) ;
    }
}
