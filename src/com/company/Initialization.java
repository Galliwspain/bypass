package com.company;

import com.company.Math.Galgorithm;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class Initialization {
    // факт вложенности контура
    public static String input = "none";


    public static void figureInit(double[] figure){

//        System.out.print("request: ");
//        for (int items : figure){
//            System.out.print(items);
//        }
//        System.out.println("\n");

        /** на данном этапе важно,чтобы figure приходила в формате {x1,y1,x3,y3} = {a_x, a_y, c_x, c_y} - противоположные углы прямоугольника*/

        double temlate_figure [] = new double[8];


        // занесение всех координат прямоугольника во временный массив
        temlate_figure[0] = figure[0];
        temlate_figure[1] = figure[1];
        temlate_figure[2] = figure[0];
        temlate_figure[3] = figure[3];
        temlate_figure[4] = figure[2];
        temlate_figure[5] = figure[3];
        temlate_figure[6] = figure[2];
        temlate_figure[7] = figure[1];

        // для хранения в json, занесем в массивы координаты
        double[] coordinate1 = {temlate_figure[0],temlate_figure[1]}; // л н угол
        double[] coordinate2 = {temlate_figure[2],temlate_figure[3]}; // л в угол
        double[] coordinate3 = {temlate_figure[4],temlate_figure[5]}; // п в угол
        double[] coordinate4 = {temlate_figure[6],temlate_figure[7]}; // п н угол


        // подсчет периметра фигуры
        double figure_bypass = getPerimetr(figure);

//        System.out.print("response: ");
//        for (int item : coordinate1){
//            System.out.print(item);
//        }
//        System.out.println("\n");

        // подсчет расстояния до каждой координаты фигуры от начала координат(теорема Пифогора) sqrt(Xn^2 + Yn^2)
        double hypotenuse1 = Math.sqrt(Math.pow(temlate_figure[0],2) + Math.pow(temlate_figure[1],2));
        double hypotenuse2 = Math.sqrt(Math.pow(temlate_figure[2],2) + Math.pow(temlate_figure[3],2));
        double hypotenuse3 = Math.sqrt(Math.pow(temlate_figure[4],2) + Math.pow(temlate_figure[5],2));
        double hypotenuse4 = Math.sqrt(Math.pow(temlate_figure[6],2) + Math.pow(temlate_figure[7],2));

//        System.out.println("hypotenuses: "+hypotenuse1 +", "+hypotenuse2+", "+hypotenuse3+", "+hypotenuse4);

        double[] hyp = {hypotenuse1,hypotenuse2,hypotenuse3,hypotenuse4};



        // выбираем наименьшее расстояние до фигуры
        //TODO: hyp & all_hipotenuses refactor/ make a function sort()
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

        // записываем отдельные объекты по каждому прямоугольку в список
        String rectangle = convertToJson(coordinate1,coordinate2,coordinate3,coordinate4,figure_bypass,hyp,minHipotenuse);
        // распологаем фигуры на карте раскроя
        if (Initialization.writeToCardCuttingTransaction(rectangle)){
            writeToCardCuttingAllowed(rectangle);
            switch (input){
                case "none": break;
                default:
                    // получаем запись о вложенности, занеосим в матрицу, очищаем запись
                    Galgorithm.structureMatrix[Integer.parseInt(input.split(":")[0])][Integer.parseInt(input.split(":")[1])] = true;
                    input="none";
            }
            Galgorithm.rectanglesData.add(rectangle);
        }
        Galgorithm.transaction.clear();
    }

    public static double getPerimetr(double[] corners){
        if (corners.length == 4){
            double side1 = corners[2] - corners[0];
            double side2 = corners[3] - corners[1];
            double perimetr = 2*(side1+side2);
            return perimetr;
        }

        return 0;
    }

    public static String convertToJson(double[] coordinate1, double[] coordinate2, double[] coordinate3, double[] coordinate4, double bypass_f ,double[] hypoteunse, double minHypotenuse) {
        System.out.println("INFO: start convertToJson");

        String a = String.valueOf(coordinate1[0] +";"+ coordinate1[1]);
        String b = String.valueOf(coordinate2[0] +";"+ coordinate2[1]);
        String c = String.valueOf(coordinate3[0] +";"+ coordinate3[1]);
        String d = String.valueOf(coordinate4[0] +";"+ coordinate4[1]);

        JSONObject figureObj = new JSONObject();
        JSONObject figure = new JSONObject();
        JSONObject id_figure = new JSONObject();
        JSONArray card = new JSONArray();

        figure.put("a", a);
        figure.put("b", b);
        figure.put("c", c);
        figure.put("d", d);
        figure.put("figure_origin", a);
        figure.put("figure_bypass", bypass_f);
        figure.put("zero_xy1", hypoteunse[0]);
        figure.put("zero_xy2", hypoteunse[1]);
        figure.put("zero_xy3", hypoteunse[2]);
        figure.put("zero_xy4", hypoteunse[3]);
        figure.put("min_hypotenuse", minHypotenuse);

        // выдаем порядковый номер контуру
        int figure_id = Galgorithm.rectanglesData.size() + 1;

        id_figure.put("id", figure_id);

        card.add(figure);
        card.add(id_figure);
        figureObj.put("figure_data", card);

        String result = figureObj.toString();
        System.out.println(result);

        return result;

    }

    // функция, записывающая транзакции для нанесения области фигуры на карту раскроя
    public static boolean writeToCardCuttingTransaction(String contur) {

        int[] instructions = readFromJson(contur);

        for (int item : instructions) {
            System.out.print(item);
        }
        System.out.println();
        for (int i = instructions[1]; i <= instructions[3]; i++) {
            for (int j = instructions[2]; j <= instructions[4]; j++) {
                // в нужную ячейку записать figure_id
                // предварительно проверив, что она пустая или подходит для смежности
                String  cell = Galgorithm.cardCutting[i][j];
                String previous_cell = j > 0 ? Galgorithm.cardCutting[i][j-1] : "X";
                String[] content_previous_cell = previous_cell.split("/");
                switch (cell) {
                    case "0":
                        Galgorithm.transaction.add(i+":"+j);
//                        Galgorithm.cardCutting[i][j] = String.valueOf(instructions[0]);
                        break;

                    default:
                        // если в данной ячейке уже одно значение И в предыдущую планируется добавление данной транзакции
                        if(cell.split("/").length == 1 && Galgorithm.transaction.contains(i+":"+(j-1)))
//                                previous_cell.split("/").length == 2 &&
//                                (content_previous_cell[0].equals(String.valueOf(instructions[0])) || content_previous_cell[1].equals(String.valueOf(instructions[0]))))
                        {
                            // проверяем, не внутренняя ли это фигура
                            // нужно прочитать значение ячейки, которая была перед origin(по j) данной фигуры, НО там может быть как одно значение, так и два
                            int item_counts = Galgorithm.cardCutting[instructions[1]][instructions[2]-1].split("/").length;
                            switch (item_counts){
                                case 1:
                                    if (Galgorithm.cardCutting[instructions[1]][instructions[2]-1].equals(Galgorithm.cardCutting[instructions[1]][instructions[2]]))
                                    {
                                        Galgorithm.transaction.add(i+":"+j);
                                        // текущей фигуры id : id фигуры стоящий на j-1 от "а" текущей фигуры(имеем право, так как доказали,что это вложенность)
                                        input=instructions[0]+":"+Galgorithm.cardCutting[instructions[1]][instructions[2]-1];
                                        break;
                                    }else{
                                        Galgorithm.transaction.clear();
                                        input="none";
                                        return false;
                                    }
                                    // если 2 значения в ячейке
                                default:
                                    String first_value = (Galgorithm.cardCutting[instructions[1]][instructions[2]-1]).split("/")[0];
                                    String second_value = (Galgorithm.cardCutting[instructions[1]][instructions[2]-1]).split("/")[1];
                                    if (Galgorithm.cardCutting[instructions[1]][instructions[2]].equals(first_value) ||
                                            Galgorithm.cardCutting[instructions[1]][instructions[2]].equals(second_value))
                                    {
                                        Galgorithm.transaction.add(i+":"+j);
                                        // текущей фигуры id : id стоящий в текущей позиции(доказали, что именно в эту фигуру вложенность)
                                        input=instructions[0]+":"+Galgorithm.cardCutting[instructions[1]][instructions[2]];
                                        break;
                                    }else{
                                        Galgorithm.transaction.clear();
                                        input="none";
                                        return false;
                                    }
                            }

                        }
                        // если в данной ячейке уже больше одного значения
                        else if (cell.split("/").length > 1){
                            Galgorithm.transaction.clear();
                            input="none";
                            return false;
                        }else {
                            Galgorithm.transaction.add(i+":"+j);
//                            Galgorithm.cardCutting[i][j] = cell.concat("/" + String.valueOf(instructions[0]));
                        }


                }

            }
        }
        return true;
    }

    // функция, записывающая область фигуры на карту раскроя
    public static boolean writeToCardCuttingAllowed(String contur){

        int[] instructions = readFromJson(contur);

        for (String item : Galgorithm.transaction){
            String temp = Galgorithm.cardCutting [Integer.parseInt(item.split(":")[0])][Integer.parseInt(item.split(":")[1])];
            Galgorithm.cardCutting [Integer.parseInt(item.split(":")[0])][Integer.parseInt(item.split(":")[1])]
                    = temp.equals("0")
                    ? String.valueOf(instructions[0]) : temp.concat("/" + String.valueOf(instructions[0]));
        }
        return true;
    }

    // функция, читающая из "БД" основные данные по контуру
    public static int[] readFromJson(String rectanglesData){

        JSONParser parser = new JSONParser();
        Object obj = null;

        try {
            obj = parser.parse(rectanglesData);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // получаем список [] figure_data
        JSONObject parse_result = (JSONObject) obj;
        JSONArray parse_array = (JSONArray) parse_result.get("figure_data");

        // получаем counter_id
        Long parse_id = ((Long)((JSONObject) parse_array.get(1)).get("id"));
        int figure_id = Integer.parseInt(parse_id.toString());

        // получаем точки a & c для дальнейшго определения области фигуры
        // a
        // так как в json храним точки в double, то сначала парсим в double, затем берем целую часть(Временно для целочисленных значений)
        String a_params = ((JSONObject)parse_array.get(0)).get("a").toString();
        String[] a = a_params.split("\"")[0].split(";");
        int a_x = (int) Double.parseDouble(a[0]);
        int a_y = (int) Double.parseDouble(a[1]);

        //c
        String c_params = ((JSONObject)parse_array.get(0)).get("c").toString();
        String[] c = c_params.split("\"")[0].split(";");
        int c_x = (int) Double.parseDouble(c[0]);
        int c_y = (int) Double.parseDouble(c[1]);

        int[] instructions = {figure_id,a_x,a_y,c_x,c_y};

        return instructions;

    }

    // функция, читающая из "БД" ALL INT данные по контуру
    public static int[] readIntFromJson(String rectanglesData){

        JSONParser parser = new JSONParser();
        Object obj = null;

        try {
            obj = parser.parse(rectanglesData);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // получаем список [] figure_data
        JSONObject parse_result = (JSONObject) obj;
        JSONArray parse_array = (JSONArray) parse_result.get("figure_data");

        // получаем counter_id
        Long parse_id = ((Long)((JSONObject) parse_array.get(1)).get("id"));
        int figure_id = Integer.parseInt(parse_id.toString());

        // получаем точки a & c для дальнейшго определения области фигуры
        // a
        // так как в json храним точки в double, то сначала парсим в double, затем берем целую часть(Временно для целочисленных значений)
        String a_params = ((JSONObject)parse_array.get(0)).get("a").toString();
        String[] a = a_params.split("\"")[0].split(";");
        int a_x = (int) Double.parseDouble(a[0]);
        int a_y = (int) Double.parseDouble(a[1]);

        String b_params = ((JSONObject)parse_array.get(0)).get("b").toString();
        String[] b = b_params.split("\"")[0].split(";");
        int b_x = (int) Double.parseDouble(b[0]);
        int b_y = (int) Double.parseDouble(b[1]);

        //c
        String c_params = ((JSONObject)parse_array.get(0)).get("c").toString();
        String[] c = c_params.split("\"")[0].split(";");
        int c_x = (int) Double.parseDouble(c[0]);
        int c_y = (int) Double.parseDouble(c[1]);

        String d_params = ((JSONObject)parse_array.get(0)).get("d").toString();
        String[] d = d_params.split("\"")[0].split(";");
        int d_x = (int) Double.parseDouble(d[0]);
        int d_y = (int) Double.parseDouble(d[1]);

        String figure_origin = ((JSONObject)parse_array.get(0)).get("figure_origin").toString();
        String[] figure = figure_origin.split("\"")[0].split(";");
        int origin_x = (int) Double.parseDouble(figure[0]);
        int origin_y = (int) Double.parseDouble(figure[1]);

        int[] instructions = {figure_id,a_x,a_y, b_x, b_y, c_x,c_y, d_x, d_y, origin_x, origin_y};

        return instructions;

    }

    // функция, читающая из "БД" ALL данные по контуру
    public static double[] readDoubleFromJson(String rectanglesData){

        JSONParser parser = new JSONParser();
        Object obj = null;

        try {
            obj = parser.parse(rectanglesData);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // получаем список [] figure_data
        JSONObject parse_result = (JSONObject) obj;
        JSONArray parse_array = (JSONArray) parse_result.get("figure_data");


        String zero_a = ((JSONObject)parse_array.get(0)).get("zero_xy1").toString();
        double z_a = Double.parseDouble(zero_a);

        String zero_b = ((JSONObject)parse_array.get(0)).get("zero_xy2").toString();
        double z_b = Double.parseDouble(zero_b);

        String zero_c = ((JSONObject)parse_array.get(0)).get("zero_xy3").toString();
        double z_c = Double.parseDouble(zero_c);

        String zero_d = ((JSONObject)parse_array.get(0)).get("zero_xy4").toString();
        double z_d = Double.parseDouble(zero_d);

        String bypass = ((JSONObject)parse_array.get(0)).get("figure_bypass").toString();
        double bp = Double.parseDouble(bypass);

        String hypotenuse = ((JSONObject)parse_array.get(0)).get("min_hypotenuse").toString();
        double hyp = Double.parseDouble(hypotenuse);

        double[] instructions = {z_a,z_b,z_c,z_d,bp,hyp};

        return instructions;

    }

}
