package com.telran.classwork;

import com.telran.classwork.core.Application;
import com.telran.classwork.core.ApplicationContext;
import com.telran.classwork.logic.*;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = Application.run("com.telran", new HashMap<>(
                Map.of(Messenger.class, WhatsappMessenger.class)
        ));

        MoneyTransfer transfer = context.getObject(MoneyTransfer.class);
        BankAccount b1 = new BankAccount();
        BankAccount b2 = new BankAccount();
        transfer.startTransfer(b1,b2,1000_000);
    }
}
