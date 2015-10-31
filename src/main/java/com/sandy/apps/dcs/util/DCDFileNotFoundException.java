package com.sandy.apps.dcs.util ;

// This exception will be thrown when a referenced file is not \
// found by the loading modules.
public class DCDFileNotFoundException extends RuntimeException {

    public DCDFileNotFoundException( String message ) {

        super( message ) ;
    }
}