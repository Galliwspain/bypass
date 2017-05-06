package com.company.Math;

 public class Constants{

     static final double[] ORIGIN = {0,0};
     // FORMAT {x1,y1,x3,y3} = {a_x, a_y, c_x, c_y}
     static final double[] FIGURE_FIRST = {0, 1, 2, 2};
     static final double[] FIGURE_SECOND = {0, 3, 3, 7};
     static final double[] FIGURE_THIRD = {1, 4, 2, 5};
//     static final double[] FIGURE_FOURTH = {6, 3, 9, 8};
     static final double[] FIGURE_FIVES = {4, 0, 5, 2};
     static final double[] FIGURE_SIXTH = {4, 3, 6, 4};
//     static final double[] FIGURE_SEVENTH = {7, 4, 8, 5};

     static final int MAP = 10;

     // размер популяции
     static final int minRP = 20;
     static final int maxRP = 100;

     // число генераций
     static final int minTG = 20;
     static final int maxTG = 100;

     // вероятность скрещивания
     static final double minPS = 0.7;
     static final double maxPS = 0.9;

     // вероятность мутации
     static final double minPM = 0.05;
     static final double maxPM = 0.1;

     // степень обновления популяции
     static final double minSO = 0.95;
     static final double maxSO = 1;

     // количество попыток
     static final int minKP = 3;
     static final int maxKP = 5;


 }

