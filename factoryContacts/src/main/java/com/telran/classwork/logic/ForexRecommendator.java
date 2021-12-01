package com.telran.classwork.logic;

import com.telran.classwork.core.InjectByType;

public class ForexRecommendator implements Recommendator {
    @InjectByType
    Messenger messenger;


    @Override
    public void recommend() {
        messenger.sendMessage("By all on forex!");
    }
}
