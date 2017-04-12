package com.lonton.classForTest;

public class B {

    public static void main(String[] args) throws ClassNotFoundException {

        String str = "dasda+b+dba";
        String strs[] = str.split("\\+");
        System.out.println(strs[1]);

    }

}
