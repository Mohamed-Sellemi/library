package com.sallami.library;

import com.sallami.library.model.Category;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Category c = new Category("Romance");
        String string = c.toString();
        System.out.println(string);
    }
}