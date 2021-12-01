package com.telran.classwork.logic;

import com.telran.classwork.core.InjectByType;
import com.telran.classwork.core.ObjectFactory;

public class MoneyTransfer {

    @InjectByType
    private Messenger messenger;
    @InjectByType
    private BalanceChecker balanceChecker;
    @InjectByType
    private Recommendator recommendator;

    public void startTransfer(BankAccount from, BankAccount to, double amount){
        messenger.sendMessage("Transfer Start");
        recommendator.recommend();
        balanceChecker.check(from,amount);
        transfer(from, to, amount);
        messenger.sendMessage("Money transferred");
    }
    
    private void transfer(BankAccount from , BankAccount to, double amount){
        System.out.println("Transferring money... All money transfer!");
    }
}
