package com.telran.classwork.diexample;

public class TestService {
    public static void main(String[] args) {
        ServiceA a = new ServiceA();
        ServiceB b = new ServiceB(a);
        b.otherLogic();
    }
}
