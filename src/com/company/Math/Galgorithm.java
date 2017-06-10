package com.company.Math;

import java.util.ArrayList;
import java.util.concurrent.atomic.DoubleAccumulator;

import com.company.Gui;
import com.company.MasterAlgoritm;
import com.company.Math.Constants;
import com.company.Initialization;
import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;


public class Galgorithm{

    // hardcode, later must be equals really countour count from frontend
//    public static int contourCount = 7;
    public static int contourCount = 50;

    public static ArrayList rectanglesData = new ArrayList();
//    public static String [][] cardCutting = new String[Constants.MAP][Constants.MAP];
    public static String [][] cardCutting = new String[30][30];
    public static ArrayList<String> transaction = new ArrayList<String>();
    public static boolean [][] structureMatrix = new boolean[contourCount+1][contourCount+1];
    public static String[] current_population;
    public static String[] gui_contours = new String[4];
    public static double[] gui_figure = new double[4];
    public static ArrayList<ArrayList<String>> tgBestList = new ArrayList<ArrayList<String>>();
    public static ArrayList<String> kpBestList = new ArrayList<>();
    public static String [][] all_statistics;
    public static String [][] fitness_statistics;
    public static ArrayList<Double> history_best_finder = new ArrayList<Double>();
    public static ArrayList<Double> final_best_finder = new ArrayList<Double>();
    public static Double winner_fitness;
    public static String winner_route;
    public static String winner_message = "";
    public static long elite_total, crossover_total,mutation_total,selection_total,tg_total,kp_total,statistics_total, winner_total, initial_total, figure_initialization_total, proccess_total;




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
//        Initialization.figureInit(Constants.FIGURE_FOURTH);
        Initialization.figureInit(Constants.FIGURE_FIVES);
        Initialization.figureInit(Constants.FIGURE_SIXTH);
//        Initialization.figureInit(Constants.FIGURE_SEVENTH);


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

    public static void gui_initial(String [] figures){

        long start, stop;
        start = System.currentTimeMillis();
        System.out.println("INFO: gui_initialization");

        // заполняем 0, для принятия решений по размещению в дальнейшем(Initialization.writeToCardCutting())
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                cardCutting[i][j] = "0";
            }
        }

        for (String item : figures){
            gui_contours = item.split(",");
            for (int i = 0; i < gui_contours.length; i++){
                gui_figure[i] = Double.parseDouble(gui_contours[i]);
            }
            Initialization.figureInit(gui_figure);

        }

        stop = System.currentTimeMillis();
        figure_initialization_total= stop - start;

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
//        int target_kp = Constants.minKP;
        int target_kp = 1;
        int target_tg = Constants.minTG;
        double target_so = Constants.minSO;
        double target_ps = Constants.minPS;
        String data = "";

        // попытки шаг 4
        for (int kp = 0; kp < target_kp; kp++){
            // шаг 5. Формирование начальной популяции kp-ой попытки
            current_population = MasterAlgoritm.getInitialPopulation(target_rp);
            MasterAlgoritm.bindPopulationsFromDifferentKP(current_population, kp ,target_rp);
            // генерации шаг 6
            for (int tg = 1; tg <= target_tg; tg++){
                //выделение элиты
                current_population = MasterAlgoritm.sortElite(target_so, target_rp, current_population);

                //Кроссинговер
//                data = MasterAlgoritm.doCrossover(target_rp, target_ps, current_population);
            }
            System.out.println(data);
        }

        /** проверка заполненности общей бызы популяций */
//        for (int i = 0; i < target_kp; i++){
//            for (int j = 0; j < target_rp; j++){
//                System.out.print(MasterAlgoritm.common_population[i][j]);
//            }
//            System.out.println();
//        }
//
//        for (int i = 0; i < target_rp; i++){
//            System.out.print(new_population[i]);
//        }
    }

    public static void gui_process(String[] params){
        /** старт выполнения*/
        long start_elite, stop_elite, start_crossover,stop_crossover,start_mutation,stop_mutation,start_selection,stop_selection, start_tg, stop_tg;
        long start_kp,stop_kp, start_statistics, stop_statistics, start_winner, stop_winner, start_initial, stop_initial, start_process, stop_process;
        start_process = System.currentTimeMillis();
        System.out.println("INFO: start gui_process");
        int target_rp = Integer.parseInt(params[0]);
        int target_tg = Integer.parseInt(params[1]);
        double target_ps = Double.parseDouble(params[2]);
        double target_pm = Double.parseDouble(params[3]);
        double target_so = Double.parseDouble(params[4]);
        int target_kp = Integer.parseInt(params[5]);

        all_statistics = new String [target_kp][target_tg];

        // попытки шаг 4
        start_kp = System.currentTimeMillis();
        for (int kp = 0; kp < target_kp; kp++){

            // шаг 5. Формирование начальной популяции kp-ой попытки
            start_initial = System.currentTimeMillis();
            current_population = MasterAlgoritm.getInitialPopulation(target_rp);
            stop_initial = System.currentTimeMillis();
            initial_total=stop_initial-start_initial;
            MasterAlgoritm.bindPopulationsFromDifferentKP(current_population, kp ,target_rp);

            // генерации шаг 6
            start_tg = System.currentTimeMillis();
            for (int tg = 0; tg < target_tg; tg++){

                // Элитизм
                start_elite = System.currentTimeMillis();
                current_population = MasterAlgoritm.sortElite(target_so, target_rp, current_population);
                stop_elite = System.currentTimeMillis();
                elite_total=stop_elite-start_elite;

                //Кроссинговер
                start_crossover = System.currentTimeMillis();
                current_population = MasterAlgoritm.crossover(current_population.length, target_ps, current_population);
                stop_crossover = System.currentTimeMillis();
                crossover_total=stop_crossover-start_crossover;

                // Мутации
                start_mutation = System.currentTimeMillis();
                current_population = MasterAlgoritm.mutation(current_population.length,target_pm, current_population);
                stop_mutation = System.currentTimeMillis();
                mutation_total=stop_mutation-start_mutation;

                // Селекция
                start_selection = System.currentTimeMillis();
                current_population = MasterAlgoritm.selection(target_rp, current_population);
                stop_selection = System.currentTimeMillis();
                selection_total=stop_selection-start_selection;

                // Получение лучшего маршрута и значения фитнес-функции из обработанной популяции
//                tgBestList.add(MasterAlgoritm.getBestRouteFitness(current_population));
                start_statistics = System.currentTimeMillis();
                all_statistics[kp][tg] = MasterAlgoritm.getBestRouteFitness(current_population);
                stop_statistics = System.currentTimeMillis();
                statistics_total=stop_statistics-start_statistics;

                // отделяем значение функции для вычисления
                // Логирование лучшего результата текущей генерации
                System.out.println("KP: "+(kp+1)+" Generation #"+(tg+1)+" Best route - "+all_statistics[kp][tg]);

            }
            stop_tg = System.currentTimeMillis();
            tg_total=stop_tg-start_tg;

        }
        stop_kp = System.currentTimeMillis();
        kp_total=stop_kp-start_kp;


        start_winner = System.currentTimeMillis();
        // загрузка в список для передачи функции выбора лцчшего
        for (int i = 0; i < target_kp; i++){
            for (int j = 0; j < target_tg; j++){
                history_best_finder.add(Double.parseDouble(all_statistics[i][j].split("/")[1]));
                if (j==target_tg-1){
                    final_best_finder.add(MasterAlgoritm.fitnessSortBest(history_best_finder));
                    history_best_finder.clear();
                }
            }
        }

        winner_fitness = MasterAlgoritm.fitnessSortBest(final_best_finder);
        // достаем маршрут по значению фитнес функции
        for (int i = 0; i < target_kp; i++) {
            for (int j = 0; j < target_tg; j++) {
                if(winner_fitness == Double.parseDouble(all_statistics[i][j].split("/")[1])){
                    winner_route = all_statistics[i][j].split("/")[0];
                    break;
                }
            }
        }
        stop_winner = System.currentTimeMillis();
        winner_total=stop_winner-start_winner;

        stop_process = System.currentTimeMillis();
        proccess_total = stop_process-start_process;

        winner_message = "Лучшее значение целевой функции : "+winner_fitness+"  Лучший маршрут: "+ winner_route;


//        for (int i = 0; i < target_kp; i++){
//            for (int j = 0; j < target_tg; j++){
//                System.out.print(all_statistics[i][j]+" | ");
//            }
//            System.out.println();
//        }
//
        for (int i = 0; i < final_best_finder.size();i++){
            System.out.println("Best of KP#"+(i+1)+" = "+final_best_finder.get(i));
        }

        System.out.println("Лучшее значение целевой функции : "+winner_fitness+"  Лучший маршрут: "+winner_route);

        System.out.println("ВРЕМЕННЫЕ ЗАТРАТЫ:\n"
            + "Общее время выполнения: " + (proccess_total+figure_initialization_total) + " millis\n"
                + "Время выполнения инициализации фигур: " + figure_initialization_total + " millis\n"
                + "Время поиска решения: " + proccess_total + " millis\n"
                + "Время выполнения попытки: " + kp_total + " millis\n"
                + "Время выполнения генерации: " + tg_total + " millis\n"
                + "Время выполнения инициализации: " + initial_total + " millis\n"
                + "Время выделения элиты: " + elite_total + " millis\n"
                + "Время выполнения скрещивания: " + crossover_total + " millis\n"
                + "Время выполнения мутации: " + mutation_total + " millis\n"
                + "Время выполнения селекции: " + selection_total + " millis\n"
                + "Время поиска лучшего маршрута в генерации: " + statistics_total + " millis\n"
                + "Время поиска лучшего маршрута за все время: " + winner_total + " millis\n"
        );

        /** проверка заполненности общей бызы популяций */
//        for (int i = 0; i < target_kp; i++){
//            for (int j = 0; j < target_rp; j++){
//                System.out.print(MasterAlgoritm.common_population[i][j]);
//            }
//            System.out.println();
//        }
//
//        for (int i = 0; i < target_rp; i++){
//            System.out.print(new_population[i]);
//        }
    }
}
