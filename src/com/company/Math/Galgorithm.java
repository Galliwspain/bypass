package com.company.Math;

import java.util.ArrayList;

import com.company.MasterAlgoritm;
import com.company.Math.Constants;
import com.company.Initialization;


public class Galgorithm{

    // hardcode, later must be equals really counter count from frontend
    public static int contourCount = 7;

    public static ArrayList rectanglesData = new ArrayList();
    public static String [][] cardCutting = new String[Constants.MAP][Constants.MAP];
    public static ArrayList<String> transaction = new ArrayList<String>();
    public static boolean [][] structureMatrix = new boolean[contourCount+1][contourCount+1];



    public static void initial(){


        System.out.println("INFO: initialization");

        // заполняем 0, для принятия решений по размещению в дальнейшем(Initialization.writeToCardCutting())
        for (int i = 0; i < Constants.MAP; i++) {
            for (int j = 0; j < Constants.MAP; j++) {
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


        /** CONSOLE проверка корректности записанных данных фигур */
//        for (Object item : rectanglesData){
//        for(int i=0;i<rectanglesData.size();i++){
//            System.out.println("rectanglesData: "+rectanglesData.get(i));
//        }

        /** CONSOLE проверка корректности заполнения карты раскроя */
//        for (int i = 0; i < Constants.MAP; i++) {
//            // Цикл по второй размерности выводит колонки - вывод одной строки
//            for (int j = 0; j < Constants.MAP; j++) {
//                // Используем оператор print - без перехода на следующую строку
//                System.out.print(cardCutting[i][j]);
//            }
//            // Переход на следующую строку
//            System.out.println();
//        }

        /** CONSOLE проверка корректности заполнения структурной матрицы */
//        for (int i = 0; i <= contourCount; i++) {
//            // Цикл по второй размерности выводит колонки - вывод одной строки
//            for (int j = 0; j <= contourCount; j++) {
//                System.out.print(structureMatrix[i][j]);
//            }
//            System.out.println();
//        }

    }

    public static void process(){
        /** старт выполнения*/
        System.out.println("INFO: start process");
        // TODO: размер популяции - rp нужно получить из frontend
        int target_rp = 20;
        int target_kp = Constants.minKP;

        // попытки
        for (int kp = 0; kp < target_kp; kp++){
            // шаг 5. Формирование начальной популяции kp-ой попытки
            MasterAlgoritm.getInitialPopulation(kp, target_rp);
        }

        /** проверка заполненности начальной популяции */
        for (int i = 0; i < target_kp; i++){
            for (int j = 0; j < target_rp; j++){
                System.out.print(MasterAlgoritm.population[i][j]);
            }
            System.out.println();
        }
    }
}
