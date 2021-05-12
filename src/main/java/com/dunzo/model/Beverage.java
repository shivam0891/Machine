package com.dunzo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Beverage {

    private String beverageName;
    private Map<String,Integer> ingredients;
}
