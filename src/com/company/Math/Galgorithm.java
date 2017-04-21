package com.company.Math;

import com.company.Math.Constants;
import com.company.Initialization;

import java.util.ArrayList;

public class Galgorithm{

    public static ArrayList rectanglesData = new ArrayList();


    public static void initial(){


        System.out.println("INFO: initialization");

        Initialization.getMinLengthFromZero(Constants.FIGURE_FIRST);
        Initialization.getMinLengthFromZero(Constants.FIGURE_SECOND);
        Initialization.getMinLengthFromZero(Constants.FIGURE_THIRD);
        Initialization.getMinLengthFromZero(Constants.FIGURE_FOURTH);

        // можно проверить корректность записанных данных
//        for (Object item : rectanglesData){
//        for(int i=0;i<rectanglesData.size();i++){
//            System.out.println("rectanglesData: "+rectanglesData.get(i));
//        }


    }
}
