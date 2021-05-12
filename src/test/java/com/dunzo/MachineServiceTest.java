package com.dunzo;

import com.dunzo.exceptions.MachineExceptions;
import com.dunzo.service.MachineService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MachineServiceTest {

    private InitializeData initializeData;

    @Before
    public void setup() throws Exception {
        initializeData = new InitializeData();
    }

    @Test
    public void makeBeverageTest() throws Exception {
        initializeData.setData("src/main/java/input.json");
        Assert.assertEquals(initializeData.getInventory().isLocked(), false);
    }

    @Test(expected = MachineExceptions.class)
    public void makeBeverageTestForNoCounter() throws Exception {
        initializeData.setData("src/main/java/input2.json");
    }
}
