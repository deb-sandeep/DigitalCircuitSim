package com.sandy.apps.dcs.testing ;

import java.util.* ;

public class BinDecConverterTest {

    public static void main( String[] args ) {

        BitSet set = new BitSet( 4 ) ;
        set.set( 1 ) ;
        set.set( 0 ) ;
        // set.set(3);

        boolean A = set.get( 0 ) ;
        boolean B = set.get( 1 ) ;
        boolean C = set.get( 2 ) ;
        boolean D = set.get( 3 ) ;

        boolean a = !( C ^ A ) | D | B ;
        boolean b = ( !( B ^ A ) ) | ( !C ) ;
        boolean c = ( !B ) | C | A ;
        boolean d = ( B & ( !A ) ) | D | ( ( !C ) & B ) | ( ( !C ) & ( !A ) )
                | ( C & ( !B ) & A ) ;
        boolean e = ( B & ( !A ) ) | ( ( !C ) & ( !A ) ) ;
        boolean f = D | ( ( !B ) & ( !A ) ) | ( C & ( !B ) ) | ( C & ( !A ) ) ;
        boolean g = ( C ^ B ) | ( C & ( !A ) ) | D ;

        System.out.println( "A = " + a ) ;
        System.out.println( "B = " + b ) ;
        System.out.println( "C = " + c ) ;
        System.out.println( "D = " + d ) ;
        System.out.println( "E = " + e ) ;
        System.out.println( "F = " + f ) ;
        System.out.println( "G = " + g ) ;

    }
}