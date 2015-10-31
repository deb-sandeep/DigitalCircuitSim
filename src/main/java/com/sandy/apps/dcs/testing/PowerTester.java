package com.sandy.apps.dcs.testing ;

import java.util.* ;

public class PowerTester {

    public static void main( String[] args ) {

        int inputCases = (int) Math.pow( 2, 4 ) ;
        for( int i = 0 ; i < inputCases ; i++ ) {
            int num = i ;
            BitSet inputBitSet = new BitSet( 4 ) ;
            BitSet outputBitSet = new BitSet( 1 ) ;

            boolean firstTrue = false ;
            boolean output1 = false ;
            for( int j = 0 ; j < 4 ; j++ ) {
                int flag = num & 0x0001 ;
                if( flag == 1 ) {
                    inputBitSet.set( j ) ;
                    if( firstTrue == false ) {
                        firstTrue = true ;
                        output1 = true ;
                    }
                    else output1 = false ;
                }
                num = num >> 1 ;
            }

            if( output1 ) outputBitSet.set( 0 ) ;
            System.out.println( "[S][Input]:" + inputBitSet ) ;
            System.out.println( "[S]         [Output]:" + outputBitSet ) ;

        }
    }
}