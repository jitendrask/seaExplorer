package com.sea.probe.probe.model;

public enum Direction {
    N("N"),
    E("E"),
    W("W"),
    S("S");

    private final String name;
    Direction(String name){
        this.name = name;
    }
}
