package com.company.Math;

import com.company.Math.Constants;
import com.company.Initialization;

import java.util.ArrayList;

import static com.company.Math.Constants.MAP;

public class Galgorithm{

    public static ArrayList rectanglesData = new ArrayList();
    public static int [][] cardCutting = new int[MAP][MAP];


    public static void initial(){


        System.out.println("INFO: initialization");

        Initialization.figureInit(Constants.FIGURE_FIRST);
        Initialization.figureInit(Constants.FIGURE_SECOND);
        Initialization.figureInit(Constants.FIGURE_THIRD);
        Initialization.figureInit(Constants.FIGURE_FOURTH);

        // можно проверить корректность записанных данных
//        for (Object item : rectanglesData){
//        for(int i=0;i<rectanglesData.size();i++){
//            System.out.println("rectanglesData: "+rectanglesData.get(i));
//        }

        for (int i = 0; i < MAP; i++) {
            // Цикл по второй размерности выводит колонки - вывод одной строки
            for (int j = 0; j < MAP; j++) {
                // Используем оператор print - без перехода на следующую строку
                System.out.print(cardCutting[i][j]);
            }
            // Переход на следующую строку
            System.out.println();
        }


    }
}
