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
    public static boolean flag_crossover = false;




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
        // флаг для сообщения, что маршрут похожий на элитный уже найден и отложен, чтобы дубли не складывать
        boolean flag = false;
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
                // проверяем похож ли потомок на элиту и найден ли уже потомок
                if(source_population[j].equals(elite_route) && !flag){
                    elite_list.add(source_population[j]);
                    flag = true;
                }else{
                    cut_population.add(source_population[j]);
                }
            }
            flag = false;
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

        int key = -1;
        for (int k=0;k<fitness_f.size();k++){
            if (best_ff == fitness_f.get(k)){
                key=k;
                break;
            }
        }
        // для модалки в gui
        beastr = "Лучшее значение целевой функции : "+best_ff+"  Лучший маршрут: "+population[key];


        fitness_f.clear();
        String return_info = population[key];

        return return_info;
    }

    public static String getBestRouteFitness(String[] population){
        // выбираем хромосому с лучшим значением
        for (int i = 0; i < population.length; i++){
            fitnessFunction(String.valueOf(population[i]));
        }
        double best_ff = fitnessSortBest(fitness_f);

        int key = -1;
        for (int k=0;k<fitness_f.size();k++){
            if (best_ff == fitness_f.get(k)){
                key=k;
                break;
            }
        }
        // для модалки в gui
        beastr = "Лучшее значение целевой функции : "+best_ff+"  Лучший маршрут: "+population[key];


        fitness_f.clear();
        String return_info = population[key] + "/" + best_ff;

        return return_info;
    }

    public static ArrayList<String> getBestRoutesList(int target_count, String[] population){
       ArrayList <Double> best_target_count = new ArrayList<>();
       ArrayList <String> line_of_best_routes = new ArrayList<>();

        for (int i = 0; i < population.length; i++){
            fitnessFunction(String.valueOf(population[i]));
        }
        best_target_count = fitnessSortBestSelection(target_count, fitness_f);
        // достаем маршруты с лучшими значениями


        for(int i =0; i<best_target_count.size();i++){
            for (int j = 0; j<fitness_f.size();j++) {
                if (best_target_count.get(i)==fitness_f.get(j)){
                    line_of_best_routes.add(population[j]);
                    break;
                }
            }
        }
        fitness_f.clear();
        return line_of_best_routes;
    }

    public static String[] crossover(int current_size, double ps, String[] population){
        // выбираем хромосому с лучшим значением
        String best_parent = getBestRoute(population);
        ArrayList<String> candidates = new ArrayList<String>();
        ArrayList<String> new_family = new ArrayList<String>();
        String[] return_population_crossover;
        //TODO: порядковый номер i-ой итерации для поиска второго родителя по rp
        for (int i = 0; i < population.length;i++){
            if (!population[i].equals(best_parent)){
                //если rnd<ps, то выбираем i-ую хромосому для скрешивания
                if (ps > Math.random()){
                    candidates.add(population[i]);
                }else{
                    // не повезло - оставляем в членах семьи
                    new_family.add(population[i]);
                }
            }else if(!flag_crossover){
                flag_crossover=true;
                // не скрешиваем дубли, нет смысла
            }else{new_family.add(population[i]);}
        }
        flag_crossover=false;
        for (String item : candidates){
            new_family.add(doCrossover(best_parent, item));
            new_family.add(item);

        }
        // добавляем лучшего родителя
        new_family.add(best_parent);

        return_population_crossover = new String[new_family.size()];

        for (int i = 0; i<new_family.size();i++){
            return_population_crossover[i]=new_family.get(i);
        }

        return return_population_crossover;
    }

    public static String doCrossover(String best_parent, String candidate_parent){
        String best_child;
        String first_child = "";
        String second_child = "";
        String[] first_parent = new String[best_parent.length()];
        String[] second_parent = new String[candidate_parent.length()];
        String stash_first_gen;
        String[] child_candidates = new String[2];


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
        for (int i=0;i<first_parent.length-1;i++) {
            first_child = first_child.concat(String.valueOf(first_parent[i])+":");
        }
        first_child=first_child.concat("0");

        for (int i=0;i<second_parent.length-1;i++) {
            second_child = second_child.concat(String.valueOf(second_parent[i])+":");
        }
        second_child=second_child.concat("0");

        child_candidates[0]=first_child;
        child_candidates[1]=second_child;

        best_child = getBestRoute(child_candidates);

        return best_child;
    }

    public static String[] mutation(int rp, double pm, String[] population){
        int count_of_gen = population[0].split(":").length;
        int rnd_first;
        int rnd_second;
        ArrayList<String> candidates_mutation = new ArrayList<>();
        ArrayList<String> family = new ArrayList<>();
        ArrayList<String> after_mutation = new ArrayList<>();
        String temp_chromosome = "";
        String[] return_population_mutation;

        do {
            rnd_first = random.nextInt(count_of_gen - 1) + 1;
            rnd_second = random.nextInt(count_of_gen - 1) + 1;
        }while (rnd_first==rnd_second);

        for(int i = 0; i<population.length;i++) {
            if (pm > Math.random()) {
                candidates_mutation.add(population[i]);
            }else{
                family.add(population[i]);
            }
        }
        if (!candidates_mutation.isEmpty()) {
            // каждого претендента преобразуем в массив, промутируем, вернем в строку
            for (String item : candidates_mutation) {
                // заносим в массив
                String[] mut_chromosome = item.split(":");
                // вытаскиваем нужные значения
                String changer_first = mut_chromosome[rnd_first];
                String changer_second = mut_chromosome[rnd_second];
                // меняем нужные значения
                mut_chromosome[rnd_first] = changer_second;
                mut_chromosome[rnd_second] = changer_first;
                // преобразуем в строку нужного формата
                for (int i = 0; i < mut_chromosome.length - 1; i++) {
                    temp_chromosome = temp_chromosome.concat(mut_chromosome[i] + ":");
                }
                temp_chromosome = temp_chromosome.concat("0");
                // добавляем в список промутированных
                after_mutation.add(temp_chromosome);
                temp_chromosome = "";
            }
            for(String item : after_mutation){
                family.add(item);
            }
        }
        after_mutation.clear();

        return_population_mutation = new String[family.size()];
        for (int item = 0; item<family.size();item++){
            return_population_mutation[item] = family.get(item);
        }
        return return_population_mutation;

    }

    public static String[] selection (int rp, String[] population){
        ArrayList<String> best_family_members = new ArrayList<>();
        String [] return_population_selection = new String[rp];
        // вычисляем, сколько должно остаться особей после селекции
        int target_count = rp - elite_list.size();
        best_family_members = getBestRoutesList(target_count,population);
        // добавляем элиту
        for (int i = 0;i<elite_list.size();i++) {
            best_family_members.add(elite_list.get(i));
        }
        for(int i =0;i<best_family_members.size();i++){
            return_population_selection[i]=best_family_members.get(i);
        }
        elite_list.clear();
        return return_population_selection;
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
        double temp;
        for (int j=0;j<fitness_f.size();j++) {
            // -j оптимизация, с каждой итерацией самый большой из непередвинутых, предвигается в конец
            for (int i = 0; i < fitness_f.size()-1-j; i++) {
                if (fitness_f.get(i) > fitness_f.get(i + 1)) {
                    temp = fitness_f.get(i);
                    fitness_f.set(i, fitness_f.get(i + 1));
                    fitness_f.set(i + 1, temp);
                }
            }
        }
        return fitness_f.get(0);
    }

    public static ArrayList<Double> fitnessSortBestSelection(int rp, ArrayList<Double> fitness_f){
        ArrayList<Double> return_list = new ArrayList<>(fitness_f.size());
        double temp;
        for (int j=0; j<fitness_f.size();j++){
            // -j оптимизация, с каждой итерацией самый большой из непередвинутых, предвигается в конец
        for (int i = 0; i < fitness_f.size()-1-j; i++) {

            if (fitness_f.get(i) > fitness_f.get(i + 1)) {
                    temp = fitness_f.get(i);
                    fitness_f.set(i, fitness_f.get(i + 1));
                    fitness_f.set(i + 1, temp);
            }

        }
        }
        for(int i =0; i<rp;i++){
            return_list.add(fitness_f.get(i));
        }

        return return_list;
    }
}
