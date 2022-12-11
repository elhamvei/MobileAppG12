package com.example.renitus.helper;

import com.example.renitus.Entities.item_modal;

import java.util.ArrayList;

public class CartList extends ArrayList<item_modal> {
    private static CartList instance = null;
    protected CartList() {
    }
    public static CartList getInstance() {
        if(instance == null) {
            instance = new CartList();
        }
        return instance;
    }
}
