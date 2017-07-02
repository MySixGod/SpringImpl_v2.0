package com.lonton.classForTest;

public class A {

    public static void main(String[] args) {

        @SuppressWarnings("unused")
        String a = new String();
        try {
            Class<?> cl = Class.forName("java.lang.String");
            System.out.println(cl.getSimpleName());
        } catch (ClassNotFoundException e) {
            System.out.println("没找到");
        }

    }

}
