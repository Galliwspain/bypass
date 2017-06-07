package com.company;

import com.company.Math.Galgorithm;

import java.util.ArrayList;
import java.util.Random;

public class MasterAlgoritm {

    public static ArrayList<Integer> template_route = new ArrayList<>(0);
    public static Random random = new Random();
    public static String [] init_population;
    public static String [][] common_population = new String[10][150];
    public static ArrayList<Double> length_route = new ArrayList<>();
    public static ArrayList<Double> fitness_f = new ArrayList<Double>();
    public static ArrayList<Double> copy_fitness_f = new ArrayList<Double>();
    public static ArrayList<String> elite_list = new ArrayList<String>();
    public static String beastr;




    public static String [] getInitialPopulation(int current_rp){

        // инициализирую массив, известным числом особей в популяции, чтобы не было null'ов
        init_population = new String[current_rp];
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
        for (int figure = 0; figure < template_route.size(); figure++){
            // [0] - пустота
           chromosome = chromosome.concat(String.valueOf(template_route.get(figure))+":");
        }
        // для красоты добавляем 0 в конце - возврат в начало координат
        chromosome = chromosome.concat("0");
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

        ArrayList<String> cut_population = new ArrayList<String>();
        String [] return_population = new String[new_population.length - (int)ki];

        for (int np = 0; np < new_population.length; np++){
            cut_population.add(new_population[np]);
        }
//        String[] cut_population = new_population;
        String elite_route;
        for (int i = 0; i<(int)ki; i++){
            //записываем для поиска в уже обрезанной популяции
            String[] source_population = new String[cut_population.size()];
            for (int source = 0; source<cut_population.size();source++){
                source_population[source]=cut_population.get(source);
            }
            elite_route = getBestRoute(source_population);
            cut_population.clear();
            for (int j = 0; j < source_population.length; j++){
                // если данный маршрут не выбран, в качестве элиты, то оставляем его в популяции
                //TODO: хорошо бы сделать equals, но new_population.length =100, и много  null'ов
                if(source_population[j]==(elite_route)){
                    elite_list.add(source_population[j]);
                }else{
                    cut_population.add(source_population[j]);
                }
            }
        }

        // переводим arrayList в массив
        for (int r = 0;r<cut_population.size();r++){
            return_population[r] = cut_population.get(r);
        }

        return return_population;
    }

    public static String getBestRoute(String[] population){
        // выбираем хромосому с лучшим значением
        for (int i = 0; i < population.length; i++){
            fitnessFunction(String.valueOf(population[i]));
        }
        double best_ff = fitnessSortBest(fitness_f);
//        System.out.println("best_fitness: "+best_ff);
        int key = -1;
        for (int k=0;k<fitness_f.size();k++){
            if (best_ff == fitness_f.get(k)){
                key=k;
                break;
            }
        }
        // для модалки в gui
        beastr = "to alert";

        return population[key];
    }

    public static int Crossover(int current_size, double ps, String[] population){
        // выбираем хромосому с лучшим значением
        String best_parent = getBestRoute(population);
        ArrayList<String> candidates = new ArrayList<String>();
        ArrayList<String> new_family = new ArrayList<String>();

        //        beastr = "Лучшее значение целевой функции : "+best_ff+"  Лучший маршрут: "+population[key];

        //TODO: порядковый номер i-ой итерации для поиска второго родителя по rp
        for (int i = 0; i < current_size;i++){
            if (!population[i].equals(best_parent)){
                //если rnd<ps, то выбираем i-ую хромосому для скрешивания
                if (ps > Math.random()){
                    candidates.add(population[i]);
                }
            }
        }
        for (String item : candidates){
            new_family.add(doCrossover(best_parent, item));

        }

        return new_family.size();
    }

    public static String doCrossover(String best_parent, String candidate_parent){
        String child;
        String[] first_parent = new String[best_parent.length()];
        String[] second_parent = new String[candidate_parent.length()];
        String stash_first_gen;

        first_parent = best_parent.split(":");
        second_parent = candidate_parent.split(":");

        if (!first_parent[1].equals(second_parent[1])){
            // 1. прячем обменный ген первого родителя
            stash_first_gen = first_parent[1];
            // 2. меняем первые гены у родителей
            first_parent[1] = second_parent[1];
            second_parent[1] = stash_first_gen;
            // 3. ищем повторы для дальнейшего соблюдения уникальности генов в маршруте
            //исключаем 0-ли вначале и конце и первый ген
            for (int i = 2;i<first_parent.length-2;i++){
                if(first_parent[1].equals(first_parent[i])){
                    // 4. в stash_first_gen лежит недостающий в первом родителе ген
                    first_parent[i] = stash_first_gen;
                }
            }
            // 5. ищем повторы для дальнейшего соблюдения уникальности генов в маршруте
            //исключаем 0-ли вначале и конце и первый ген
            for (int i = 2;i<second_parent.length-2;i++){
                if(second_parent[1].equals(second_parent[i])){
                    // 6. в first_parent[1] лежит недостающий ген во втором родителе
                    second_parent[i] = first_parent[1];
                }
            }
        }




        return child;
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

        for (int i = 1; i < temp_route.length -1; i++){

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
                    //TODO:нужен переход к следующему i иначе повторное отправление фигуры
                    //TODO: почистить length
                }
            }
        }
        template_fitness = 0;
        for(double item : length_route){
            template_fitness+=item;
        }

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
        // если ели эта фигура первая(есть только 1-ый периметр double_rectangle[4])
        if (length_route.size() == 1){
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
