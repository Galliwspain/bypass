package com.company;

import com.company.Math.Galgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;





    public class Gui extends JFrame {

        public static long total_time;
        JLabel[] label = new JLabel[5];
        JTextField[] field = new JTextField[5];
        JButton accept = new JButton("Вычислить");
        JButton def = new JButton("5 случайных фигур");
        JButton big_def = new JButton("25 фигур с вложенностью");
        JButton big_def_17 = new JButton("17 случайных фигур");
        JButton big_def_25 = new JButton("25 фигур без вложенности");
        JButton big_def_37 = new JButton("37 случайных фигур");
        JLabel label_1;
        JLabel label_2;
        JLabel label_3;
        JLabel label_4;
        JLabel label_5;
        JLabel label_6;
        JLabel label_7;
        JLabel label_8;
        JLabel label_9;
        JLabel label_10;

        JTextField field_1;
        JTextField field_2;


        public static String[] params = new String[6];
        public static String[] figures = new String[6];
        public static Boolean parsed = new Boolean(false);


        Gui(){
            super("Обход прямоугольников");
            setSize(650,650);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);


            Container c = getContentPane();
            c.setLayout(new FlowLayout(FlowLayout.CENTER, 20,30));
//            description.setLayout(new BoxLayout(description,BoxLayout.X_AXIS));
//            description.setLayout(new GridLayout());



//            Container fields = getContentPane();
//            fields.setLayout(new FlowLayout(FlowLayout.CENTER,20,30));


//            JPanel c = new JPanel();
//            c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));







            createTasks();
        }

        public void createTasks(){

//            Container c = getContentPane();
//            c.setLayout(new FlowLayout(FlowLayout.CENTER, 20,30));
//            JLabel label_1;
//            JLabel label_2;
//            JLabel label_3;
//            JLabel label_4;
//            JLabel label_5;
//            JLabel label_6;
//            JLabel label_7;
//            JLabel label_8;
//            JLabel label_9;
//
//            JTextField field_1;
//            JTextField field_2;

            label_1 = new JLabel("Параметры:");
            label_2 = new JLabel("Размер популяции - RP");
            label_3 = new JLabel("Число генераций - TG");
            label_4 = new JLabel("Вероятность скрещивания - PS");
            label_5 = new JLabel("Вероятность мутации - PM");
            label_6 = new JLabel("Степень обновления популяции - SO");
            label_7 = new JLabel("Количество попыток - KP");
            label_8 = new JLabel("Введите параметры в формате: RP;TG;PS;PM;SO;KP");
            label_9 = new JLabel("Введите точки фигуры в формате a_x, a_y, c_x, c_y,разделяя фигуры \";\"");
            label_10 = new JLabel("");


            field_1= new JTextField(35);
            field_2= new JTextField(35);


            //отрисовка
            this.add(label_1);
            this.add(label_2);
            this.add(label_3);
            this.add(label_4);
            this.add(label_5);
            this.add(label_6);
            this.add(label_7);
            this.add(label_8);
            this.add(field_1);
            this.add(label_9);
            this.add(field_2);
            this.setVisible(true);

            this.add(def);
            this.add(big_def);
            this.add(big_def_17);
            this.add(big_def_25);
            this.add(big_def_37);
            this.add(accept);
            this.add(label_10);



            Handler next = new Handler();
            accept.addActionListener(next);

            Handler def_data = new Handler();
            def.addActionListener(def_data);

            Handler big_def_data = new Handler();
            big_def.addActionListener(big_def_data);

            Handler big_def_017 = new Handler();
            big_def_17.addActionListener(big_def_017);

            Handler big_def_025 = new Handler();
            big_def_25.addActionListener(big_def_025);

            Handler big_def_037 = new Handler();
            big_def_37.addActionListener(big_def_037);
        }

        class Handler implements ActionListener{

            public void actionPerformed(ActionEvent e){

                if(e.getSource() == def){
                    field_1.setText("40;20;0.7;0.05;0.95;3");
                    /** {0, 1, 2, 2};
                        {0, 3, 3, 7};
                        {1, 4, 2, 5};
                        {4, 0, 5, 2};
                        {4, 3, 6, 4};
                     */
                    field_2.setText("0,1,2,2;0,3,3,7;1,4,2,5;4,0,5,2;4,3,6,4");
//                    params = field_1.getText().split(";");
//                    figures = field_2.getText().split(";");
                }

                if(e.getSource() == big_def){
                    field_1.setText("20;20;0.7;0.05;0.95;3");

                    field_2.setText(
                            "0,1,2,2;0,3,3,7;1,4,2,6;4,0,5,2;4,3,7,10;" +
                                    "5,4,6,9;0,8,1,10;0,11,8,18;0,12,1,14;0,15,7,17;"+
                                    "2,12,7,14;9,0,10,8;9,9,10,18;11,0,12,8;11,9,12,18;"+
                                    "13,0,15,2;13,3,15,5;13,6,15,8;13,9,15,11;13,12,15,14;"+
                                    "13,15,15,17;13,18,15,20;0,19,12,20;16,0,20,20;17,1,19,19"
                    );
                }

                if(e.getSource() == big_def_17){
                    field_1.setText("60;25;0.5;0.15;0.95;3");

                    field_2.setText(
                            "1,1,19,2;1,19,19,18;1,3,2,17;18,3,19,17;3,3,17,4;" +
                                    "3,16,17,17;3,5,4,15;16,5,17,15;5,5,15,6;5,14,15,15;"+
                                    "7,7,13,8;7,12,13,13;7,9,8,11;12,9,13,11;9,9,11,11;"+
                                    "5,7,6,13;14,7,15,13"
                    );
                }

                if(e.getSource() == big_def_25){
                    field_1.setText("60;25;0.5;0.15;0.95;3");

                    field_2.setText(
                            "1,1,3,3;1,4,3,6;1,7,3,9;1,10,3,12;1,13,3,15;" +
                                    "1,16,3,18;1,19,20,20;4,1,8,6;4,7,8,12;4,13,8,18;"+
                                    "9,1,10,8;9,9,10,16;9,17,20,18;11,1,12,8;11,9,12,16;"+
                                    "13,1,20,2;13,3,15,5;13,6,15,8;13,9,15,11;13,12,15,14;"+
                                    "13,15,20,16;16,13,20,14;16,11,20,12;16,9,20,10;16,3,20,8"
                    );
                }

                if(e.getSource() == big_def_37){
                    field_1.setText("60;25;0.5;0.15;0.95;3");

                    field_2.setText(
                            "1,1,4,2;1,3,4,4;1,5,4,6;1,7,4,8;1,9,4,10;" +
                                    "1,11,4,12;1,13,4,14;1,15,4,16;1,17,4,18;1,19,4,20;"+
                                    "5,1,6,20;9,1,10,8;9,9,10,20;11,1,12,8;11,9,12,20;"+
                                    "13,1,15,2;13,3,15,5;13,6,15,8;13,9,15,11;13,12,15,14;"+
                                    "7,1,8,20;13,15,15,17;13,18,15,20;16,1,18,2;16,3,18,4;"+
                                    "16,5,18,6;16,7,18,8;16,9,18,10;16,11,18,12;16,13,18,14;"+
                                    "16,15,18,16;16,17,18,18;16,19,18,20;19,1,20,6;19,7,20,7;"+
                                    "19,14,20,20"
                    );
                }


                ArrayList<String> data_from_gui = new ArrayList<>();

                if(e.getSource() == accept){
                    if (!(field_1.getText().isEmpty() & field_2.getText().isEmpty())){
                        params = field_1.getText().split(";");
                        figures = field_2.getText().split(";");
                        Galgorithm.contourCount = figures.length;
                        // данные получены
                        parsed = true;

                        // включаем таймер
                        long start, stop;
                        start = System.currentTimeMillis();
                        Galgorithm.gui_initial(figures);
                        Galgorithm.gui_process(params);
//                        Galgorithm.initial();
//                        Galgorithm.process();
                        stop = System.currentTimeMillis();
                        total_time = stop - start;
                        accept.setVisible(false);
                        def.setVisible(false);
                        big_def.setVisible(false);
                        big_def_17.setVisible(false);
                        big_def_25.setVisible(false);
                        big_def_37.setVisible(false);
                        JOptionPane.showMessageDialog(null, Galgorithm.winner_message);
                        label_10.setText(MasterAlgoritm.beastr);

                    }


//                    for (int i=0; i<field.length; i++) {
//                        if (!field[i].getText().isEmpty()) {
//                            data_from_gui.add(field[i].getText());
//                        }
//                    }
//                    System.out.println("data_from_json: "+ data_from_gui.get(0));

//                    switch(correct_answers){
//                        case 5: JOptionPane.showMessageDialog(null, "Your mark is 5. Congratulations");
//                            break;
//                        case 4: JOptionPane.showMessageDialog(null, "Your mark is 4");
//                            break;
//                        case 3: JOptionPane.showMessageDialog(null, "Your mark is 3 :(");
//                            break;
//                        default: JOptionPane.showMessageDialog(null, "Your mark is bad... :'(");
//                            break;
//                    }
//
//                    correct_answers = 0;


                }
//                for (int j=0;j<5;j++){
//                    field[j].setText("");
//                    field[j].validate();
//                }

                // createTasks();
            }
        }


    }

