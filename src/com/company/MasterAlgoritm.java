package com.company;

import com.company.Math.Constants;
import com.company.Math.Galgorithm;

import java.util.ArrayList;
import java.util.Random;

public class MasterAlgoritm {

    public static ArrayList<Integer> template_route = new ArrayList<>(0);
    public static Random random = new Random();
    public static String [][] population = new String[10][100];


    public static void getInitialPopulation(int current_kp,int minKP, int maxKP, int minRP, int maxRp){

        // TODO: getInitialPopulation нужно присылать конкретные KP & RP

        for (int i = 0; i <= current_kp; i++){
            for (int j = 0; j < minRP; j++){
                population[i][j] = "0";
            }
        }

        for (int i = 0; i <= current_kp; i++) {
            for (int j = 0; j < 1; j++) {
                template_route.add(0);
                population[i][j] = getRandomChromosome(i);
            }
        }
        /** проверка заполненности начальной популяции */
        for (int i = 0; i <= current_kp; i++){
            for (int j = 0; j < minRP; j++){
                System.out.print(population[i][j]);
            }
            System.out.println();
        }

    }

    public static String getRandomChromosome(int chromosome_id){
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

}
