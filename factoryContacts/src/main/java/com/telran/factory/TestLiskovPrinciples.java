package com.telran.factory;

public class TestLiskovPrinciples {


    static  class A {
        final void work(Object value){
            if(value instanceof String){
                System.out.println("Do some");
            }
        }
    }

    static class B extends A{

//        @Override
//        void work(Object value) {
//
//        }
    }
}
