package com.company;

import com.company.Math.Galgorithm;

import java.util.ArrayList;
import java.util.Random;

public class MasterAlgoritm {

    public static ArrayList<Integer> template_route = new ArrayList<>(0);
    public static Random random = new Random();
    public static String [] init_population = new String [100];
    public static String [][] common_population = new String[10][150];
    public static ArrayList<Double> length_route = new ArrayList<>();
    public static ArrayList<Double> fitness_f = new ArrayList<Double>();
    public static ArrayList<Double> copy_fitness_f = new ArrayList<Double>();




    public static String [] getInitialPopulation(int current_rp){

            for (int i = 0; i < current_rp; i++) {
                init_population[i] = "0";
            }

            for (int j = 0; j < current_rp; j++) {
                template_route.add(0);
                init_population[j] = getRandomChromosome();
            }
        return init_population;
    }

    public static String getRandomChromosome(){
        int gen_temp;
        int rectagles_count = Galgorithm.rectanglesData.size();
        String chromosome = "";

       for (int size = 0; size < rectagles_count; size++){
           do {
               gen_temp = random.nextInt(rectagles_count) +1;
           }
           while (!consistInRoute(gen_temp));

           template_route.add(gen_temp);
        }

        // преобразование в строку
        for (int figure = 1; figure < template_route.size(); figure++){
            // [0] - пустота
           chromosome = chromosome.concat(":"+String.valueOf(template_route.get(figure)));
        }
        template_route.clear();
        return chromosome;

    }

    public static boolean consistInRoute(int gen){

       for (int j = 0; j < template_route.size(); j++){
            if(template_route.get(j) == gen){
                return false;
            }
        }
        return true;
    }

    public static void bindPopulationsFromDifferentKP(String[] new_population, int current_kp, int current_rp){


            for (int j = 0; j < new_population.length; j++) {
                common_population[current_kp][j] = new_population[j];
            }

    }

    public static String[] sortElite(double so, int rp, String[] new_population){
        // количество элитных
        double ki = (1 - so) * rp;

        return new_population;
    }

    public static String doCrossover(int rp, double ps, String[] population){
        // выбираем хромосому с лучшим значением
        for (int i = 0; i < population.length; i++){
            fitnessFunction(String.valueOf(population[i]));
        }
        copy_fitness_f.addAll(fitness_f);
        double best_ff = fitnessSortBest(fitness_f);
//        System.out.println("best_fitness: "+best_ff);
        int key = -1;
        for (int k=0;k<fitness_f.size();k++){
            if (best_ff == fitness_f.get(k)){
                key=k;
                break;
            }
        }
//        System.out.println(population[key]);

        String beastr = "best_fitness : "+best_ff+"  best_route: "+population[key];

        // порядковый номер i-ой итерации для поиска второго родителя по rp

        // 0<=rnd<1

        // если rnd<ps, то выбираем i-ую хромосому
        return beastr;

    }

    public static void fitnessFunction(String route){

        String[] temp_route;
//        System.out.println("2313: "+temp_route[3]);
        temp_route = String.valueOf(route).split(":");
//        System.out.println("2313: "+temp_route[3]);

        int id;
        int id_next;
        double [] double_rectangle = new double[20];
        double [] double_rectangle_next = new double[20];
        int [] int_rectangle;
        int [] int_rectangle_next = new int[20];
        double template_fitness=0;
        double length_betwen_contours = 0;
        double length_contours = 0;

        for (int i = 1; i < String.valueOf(route).split(":").length; i++){

            // получение айди фигуры из базы фигур для сравнения с текущей популяцией(фигурой)
            for (int j = 0; j < Galgorithm.rectanglesData.size(); j++) {
                int_rectangle = Initialization.readIntFromJson(Galgorithm.rectanglesData.get(j).toString());
                id = int_rectangle[0];
                if(id == Integer.parseInt(temp_route[i])){
                    double_rectangle = Initialization.readDoubleFromJson(Galgorithm.rectanglesData.get(j).toString());

                    // получение следующей фигуры в маршруте
                    for (int k=0;k < Galgorithm.rectanglesData.size();k++){
                        int_rectangle_next = Initialization.readIntFromJson(Galgorithm.rectanglesData.get(k).toString());
                        id_next = int_rectangle_next[0];
                        // иначе просит несуществующее java.lang.ArrayIndexOutOfBoundsException: 7
                        if (i < temp_route.length-1){
                        if(id_next == Integer.parseInt(temp_route[i+1])){
                            double_rectangle_next = Initialization.readDoubleFromJson(Galgorithm.rectanglesData.get(k).toString());
                            break;
                        }
                    }
                    }
                    getLengthBetwenContours(temp_route,int_rectangle,double_rectangle,int_rectangle_next,double_rectangle_next);
                    //TODO: почистить length
                }
            }
        }
        template_fitness = 0;
        for(double item : length_route){template_fitness+=item;}

        fitness_f.add(template_fitness);
        length_route.clear();
    }

    public static void getLengthBetwenContours(String[] route, int[] int_rectangle, double[] double_rectangle, int[] int_rectangle_next, double[] double_rectangle_next){
        /** int[] instructions = {figure_id,a_x,a_y, b_x, b_y, c_x,c_y, d_x, d_y, origin_x, origin_y}
         *  double[] instructions = {z_a,z_b,z_c,z_d,bp,hyp} */

        double x_x;
        double y_y;

        //добавим периметр фигуры bp
        length_route.add(double_rectangle[4]);
        // если ели эта фигура первая(в route лишнее : вначале)
        if (length_route.isEmpty()){
            // берем расстояние от 0,0 до точки врезки - default = z->a
            length_route.add(double_rectangle[0]);
        }if(length_route.size() < route.length - 1){
            // считаем до следующего
            x_x = int_rectangle[1] - int_rectangle_next[1];
            if(x_x<0){x_x=-1*x_x;}
            y_y = int_rectangle[2] - int_rectangle_next[2];
            if(y_y<0){y_y=-1*y_y;}
            length_route.add(Math.sqrt(Math.pow(x_x,2) + Math.pow(y_y,2)));


        }else {length_route.add(double_rectangle[0]);}
    }

    public static double fitnessSortBest(ArrayList<Double> fitness_f){
        for (int i = 0; i < fitness_f.size(); i++){
            if (fitness_f.get(i) > fitness_f.get(i++)){
                double temp = fitness_f.get(i);
                fitness_f.set(i,fitness_f.get(i++));
                fitness_f.set(i++, temp);
                i = -1;
            }
        }
        return fitness_f.get(0);
    }
}
