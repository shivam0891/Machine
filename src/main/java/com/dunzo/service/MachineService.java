package com.dunzo.service;

import com.dunzo.exceptions.InventoryException;
import com.dunzo.model.Beverage;
import com.dunzo.model.Inventory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MachineService {

    public void makeBeverage(Inventory inventory, Beverage beverage) throws Exception {
        if(inventory.isLocked())
        {
            throw new InventoryException(400,"Inventory is already locked");
        }
        raiseAlert(inventory);
        inventory.setLocked(true);
        Map<String,Integer> inventoryItems = inventory.getIngredients();
        Map<String,Integer> beverageIncrediatents = beverage.getIngredients();
        List<String> unavailableItems = new ArrayList<>();
        List<String> unsufficientItems = new ArrayList<>();
        for(Map.Entry<String,Integer> beverageMap : beverageIncrediatents.entrySet()){
            if(!inventoryItems.containsKey(beverageMap.getKey())){
                unavailableItems.add(beverageMap.getKey());
            }
            else if(inventoryItems.containsKey(beverageMap.getKey()) && (inventoryItems.get(beverageMap.getKey()) < beverageMap.getValue())) {
                unsufficientItems.add(beverageMap.getKey());
            }
        }
        if(unavailableItems.size()>0 || unsufficientItems.size()>0)
        {
            for(String string: unavailableItems){
                System.out.println(beverage.getBeverageName() + " " + "cannot be prepared because " + string + " is not available");
            }
            for(String string: unsufficientItems){
                System.out.println(beverage.getBeverageName() + " " + "cannot be prepared because " + string + " is not sufficient");
            }
            // edge case : in some cases we print all reasons in seperate line why this beverage cant be made
            inventory.setLocked(false);
        }else{
            for(Map.Entry<String,Integer> beverageMap : beverageIncrediatents.entrySet()){
                Integer itemCount = inventoryItems.get(beverageMap.getKey());
                if(itemCount-beverageMap.getValue()>0) {
                    inventoryItems.put(beverageMap.getKey(), itemCount - beverageMap.getValue());
                }
            }
            System.out.println(beverage.getBeverageName() + " is prepared ");
            inventory.setLocked(false);
        }
        raiseAlert(inventory);

    }
    private void raiseAlert(Inventory inventory)
    {
        int inventoryItems = inventory.getIngredients().size();
        if(inventoryItems<3){ //this will be configurable
            System.out.println("Alert!!! less items left");
        }

        for(Map.Entry<String,Integer> inventoryMap : inventory.getIngredients().entrySet()) {
            if(inventoryMap.getValue()<50){
                System.out.println("Alert!!! insufficient capacity of :" + inventoryMap.getKey() + " only " + inventoryMap.getValue() + " left");
            }

        }

    }

}
