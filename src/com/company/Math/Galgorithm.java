package com.company.Math;

import com.company.Math.Constants;
import com.company.Initialization;

import java.util.ArrayList;

import static com.company.Math.Constants.MAP;

public class Galgorithm{

    // hardcode, later must be equals really counter count from frontend
    public static int contourCount = 7;

    public static ArrayList rectanglesData = new ArrayList();
    public static String [][] cardCutting = new String[MAP][MAP];
    public static ArrayList<String> transaction = new ArrayList<String>();
    public static boolean [][] structureMatrix = new boolean[contourCount+1][contourCount+1];



    public static void initial(){


        System.out.println("INFO: initialization");

        // заполняем 0, для принятия решений по размещению в дальнейшем(Initialization.writeToCardCutting())
        for (int i = 0; i < MAP; i++) {
            for (int j = 0; j < MAP; j++) {
                cardCutting[i][j] = "0";
            }
        }

        Initialization.figureInit(Constants.FIGURE_FIRST);
        Initialization.figureInit(Constants.FIGURE_SECOND);
        Initialization.figureInit(Constants.FIGURE_THIRD);
        Initialization.figureInit(Constants.FIGURE_FOURTH);
        Initialization.figureInit(Constants.FIGURE_FIVES);
        Initialization.figureInit(Constants.FIGURE_SIXTH);
        Initialization.figureInit(Constants.FIGURE_SEVENTH);


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

        for (int i = 0; i <= contourCount; i++) {
            // Цикл по второй размерности выводит колонки - вывод одной строки
            for (int j = 0; j <= contourCount; j++) {
                System.out.print(structureMatrix[i][j]);
            }
            System.out.println();
        }

    }
}
