package com.telran.classwork.diexample;

public class ServiceB {
    ServiceA serviceA;

    public ServiceB(ServiceA serviceA) {
        this.serviceA = serviceA;
    }

    public void setServiceA(ServiceA serviceA) {
        this.serviceA = serviceA;
    }

    public void otherLogic(){
        //logic
        serviceA.logic();
    }
}
