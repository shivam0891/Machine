package com.dunzo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Inventory { //made it singelton
    private boolean isLocked;
    private Map<String,Integer> ingredients;

    private static Inventory inventoryObject = new Inventory();

    public static Inventory getInstance()
    {
        return inventoryObject;
    }

    private void lockInventory()
    {
        isLocked = true;
    }

    private void releaseInventory()
    {
        isLocked = false;
    }

    private Inventory()
    {
        this.isLocked = false;
    }
}
