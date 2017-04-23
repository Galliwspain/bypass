package com.company;

import com.company.Math.Galgorithm;

public class Main {

    public static void main(String[] args) {
        System.out.println("INFO: start application");
//        Gui gui = new Gui();
//        gui.setVisible(true);
        Galgorithm.initial();
        Galgorithm.process();
    }
}
