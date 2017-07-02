package com.lonton.enums;

public enum BasicType {

    Boolean("Boolean"), Character("Character"), String("String"), Integer("Integer"), Byte("Byte"), Short(
            "Short"), Long("Long"), Float("Float"), Double("Double");

    public String simpleTypeName;

    BasicType(String simpleTypeName) {
        this.simpleTypeName = simpleTypeName;
    }

}
