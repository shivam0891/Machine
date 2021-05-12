package com.dunzo;

import com.dunzo.exceptions.MachineExceptions;
import com.dunzo.model.Beverage;
import com.dunzo.model.Inventory;
import com.dunzo.model.Machine;
import com.dunzo.service.MachineService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Getter
@Setter
public class InitializeData {

    private MachineService machineService;

    private ObjectMapper mapper;

    private Machine machine;

    private Inventory inventory;

   public InitializeData() throws Exception {
       machineService = new MachineService();
       mapper = new ObjectMapper();
       machine = new Machine();
       inventory =Inventory.getInstance();
   }

   public void setData(String path) throws Exception {
       Random rand = new Random();

       Map<String, Object> inputMap = mapper.readValue(new File(
               path), new TypeReference<Map<String, Object>>() {
       });

       inputMap = (Map<String, Object>)inputMap.get("machine");
       machine.setOutlets((Map<String, Object>)inputMap.get("outlets"));
       if(Integer.parseInt(machine.getOutlets().get("count_n").toString()) <1)
       {
           throw new MachineExceptions(400,"No machine counter is left");
       }

       inventory.setIngredients(((Map<String, Integer>)inputMap.get("total_items_quantity")));

       List<Beverage> beverageList = new ArrayList<>();

       Map<String,Object> beveragesMap=(Map<String,Object>)inputMap.get("beverages");
       for(Map.Entry<String,Object> beverageMapentry : beveragesMap.entrySet()) {
           Beverage beverage = new Beverage();
           beverage.setBeverageName(beverageMapentry.getKey());
           beverage.setIngredients(mapper.convertValue(beverageMapentry.getValue(),Map.class));
           beverageList.add(beverage);
       }

       int numberOfBeverage = beverageList.size();

       while(numberOfBeverage>0) {
           int index = rand.nextInt(numberOfBeverage);
           machineService.makeBeverage(inventory,beverageList.get(index));
           beverageList.set(index, beverageList.get(numberOfBeverage-1));
           numberOfBeverage--;
       }
   }
}
