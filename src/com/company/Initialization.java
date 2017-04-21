package com.company;

import com.company.Math.Galgorithm;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;

public class Initialization {

//    public static ArrayList rectanglesData = new ArrayList();

    public static void getMinLengthFromZero(int[] figure){

//        System.out.print("request: ");
//        for (int items : figure){
//            System.out.print(items);
//        }
//        System.out.println("\n");

        int temlate_figure [] = new int [8];


                // занесение всех координат прямоугольника во временный массив
        temlate_figure[0] = figure[0];
        temlate_figure[1] = figure[1];
        temlate_figure[2] = figure[2];
        temlate_figure[3] = figure[1];
        temlate_figure[4] = figure[0];
        temlate_figure[5] = figure[3];
        temlate_figure[6] = figure[2];
        temlate_figure[7] = figure[3];

        // для хранения в json, занесем в массивы координаты
        int[] coordinate1 = {temlate_figure[0],temlate_figure[1]};
        int[] coordinate2 = {temlate_figure[2],temlate_figure[3]};
        int[] coordinate3 = {temlate_figure[4],temlate_figure[5]};
        int[] coordinate4 = {temlate_figure[6],temlate_figure[7]};


//        System.out.print("response: ");
//        for (int item : coordinate1){
//            System.out.print(item);
//        }
//        System.out.println("\n");

        // подсчет расстояния до каждой координаты фигуры от начала координат(теорема Пифогора)
        double hypotenuse1 = Math.sqrt(Math.pow(temlate_figure[0],2) + Math.pow(temlate_figure[1],2));
        double hypotenuse2 = Math.sqrt(Math.pow(temlate_figure[2],2) + Math.pow(temlate_figure[3],2));
        double hypotenuse3 = Math.sqrt(Math.pow(temlate_figure[4],2) + Math.pow(temlate_figure[5],2));
        double hypotenuse4 = Math.sqrt(Math.pow(temlate_figure[6],2) + Math.pow(temlate_figure[7],2));

//        System.out.println("hypotenuses: "+hypotenuse1 +", "+hypotenuse2+", "+hypotenuse3+", "+hypotenuse4);

        double[] hyp = {hypotenuse1,hypotenuse2,hypotenuse3,hypotenuse4};



        // выбираем наименьшее расстояние до фигуры
        //TODO: hyp & all_hipotenuses
        double[] all_hypotenuses = {hypotenuse1,hypotenuse2,hypotenuse3,hypotenuse4};
        for (int i = 0; i < all_hypotenuses.length; i++){
            if (all_hypotenuses[i] > all_hypotenuses[i++]){
                double temp = all_hypotenuses[i];
                all_hypotenuses[i]=all_hypotenuses[i++];
                all_hypotenuses[i++]=temp;
                i = -1;
            }
        }

        double minHipotenuse = all_hypotenuses[0];

//        System.out.println("minHipotenuse: "+minHipotenuse);

        String rectangle = convertToJson(coordinate1,coordinate2,coordinate3,coordinate4,hyp,minHipotenuse);

        // записываем отдельные объекты по каждому прямоугольку в список
        Galgorithm.rectanglesData.add(rectangle);

    }

    public static String convertToJson(int[] coordinate1, int[] coordinate2, int[] coordinate3, int[] coordinate4, double[] hypoteunse, double minHypotenuse) {
        System.out.println("INFO: start convertToJson");

        JSONObject figureObj = new JSONObject();
        JSONObject figure = new JSONObject();
        JSONArray card = new JSONArray();

        figure.put("x1", coordinate1[0]);
        figure.put("y1", coordinate1[1]);
        figure.put("x2", coordinate2[0]);
        figure.put("y2", coordinate2[1]);
        figure.put("x3", coordinate3[0]);
        figure.put("y3", coordinate3[1]);
        figure.put("x4", coordinate4[0]);
        figure.put("y4", coordinate4[1]);
        figure.put("zero_xy1", hypoteunse[0]);
        figure.put("zero_xy2", hypoteunse[1]);
        figure.put("zero_xy3", hypoteunse[2]);
        figure.put("zero_xy4", hypoteunse[3]);
        figure.put("min_hypotenuse", minHypotenuse);

        card.add(figure);
        figureObj.put("figures", card);

        String result = figure.toString();
        System.out.println(result);

        return result;

    }

    public static void readFromJson(String rectanglesData){

        JSONParser parser = new JSONParser();
        Object obj = null;

        try {
            obj = parser.parse(rectanglesData);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONObject parse_result = (JSONObject) obj;

        Object min_hyp = parse_result.get("min_hypotenuse");
        System.out.println("DF: "+min_hyp);
    }

}
