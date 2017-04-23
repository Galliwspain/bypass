package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;



    public class Gui extends JFrame {
        JLabel[] label = new JLabel[5];
        JTextField[] field = new JTextField[5];
        JButton button = new JButton("Send");
        JButton plus = new JButton("Plus");
        JButton close_button = new JButton("Exit");
        int correct_answers = 0;
        int [] results = new int[5];

        Gui(){
            super("Обход прямоугольников");
            setSize(650,650);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);


            Container c = getContentPane();
            c.setLayout(new FlowLayout(FlowLayout.CENTER, 20,30));

            JTextField field_8;

            field_8= new JTextField(15);



            createTasks();
        }

        public void createTasks(){

//            Container c = getContentPane();
//            c.setLayout(new FlowLayout(FlowLayout.CENTER, 20,30));
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
            JTextField field_3;
            JTextField field_4;
            JTextField field_5;
            JTextField field_6;
            JTextField field_7;
            JTextField field_8;
            JTextField field_9;
            JTextField field_10;

            JButton successButton;

            label_1 = new JLabel("Введите точки фигуры в формате {a_x, a_y, c_x, c_y}");
            label_2 = new JLabel("Введите точки фигуры в формате {a_x, a_y, c_x, c_y}");
            label_3 = new JLabel("Введите точки фигуры в формате {a_x, a_y, c_x, c_y}");
            label_4 = new JLabel("Введите точки фигуры в формате {a_x, a_y, c_x, c_y}");
            label_5 = new JLabel("Введите точки фигуры в формате {a_x, a_y, c_x, c_y}");
            label_6 = new JLabel("Введите точки фигуры в формате {a_x, a_y, c_x, c_y}");
            label_7 = new JLabel("Введите точки фигуры в формате {a_x, a_y, c_x, c_y}");
            label_8 = new JLabel("Введите точки фигуры в формате {a_x, a_y, c_x, c_y}");
            label_9 = new JLabel("Введите точки фигуры в формате {a_x, a_y, c_x, c_y}");
            label_10 = new JLabel("Введите точки фигуры в формате {a_x, a_y, c_x, c_y}");

            field_1= new JTextField(15);
            field_2= new JTextField(15);
            field_3= new JTextField(15);
            field_4= new JTextField(15);
            field_5= new JTextField(15);
            field_6= new JTextField(15);
            field_7= new JTextField(15);
            field_8= new JTextField(15);
            field_9= new JTextField(15);
            field_10= new JTextField(15);

            successButton = new JButton("OK");

            this.add(label_1);
            this.add(field_1);
            this.add(label_2);
            this.add(field_2);
            this.add(label_3);
            this.add(field_3);
            this.add(label_4);
            this.add(field_4);
            this.add(label_5);
            this.add(field_5);
            this.add(label_6);
            this.add(field_6);
            this.add(label_7);
            this.add(field_7);
//            c.add(label_8);
//            c.add(field_8);
//            c.add(label_9);
//            c.add(field_9);
//            c.add(label_10);
//            c.add(field_10);


            this.setVisible(true);
            this.add(plus);
            this.add(button);

            Handler hd = new Handler();
            button.addActionListener(hd);

            Handler plus = new Handler();
            button.addActionListener(plus);
        }

        class Handler implements ActionListener{

            public void actionPerformed(ActionEvent e){

                if(e.getSource() == plus){
                }


                ArrayList<String> data_from_gui = new ArrayList<>();

                if(e.getSource() == button){
                    for (int i=0; i<field.length; i++) {
                        if (!field[i].getText().isEmpty()) {
                            data_from_gui.add(field[i].getText());
                        }
                    }
                    System.out.println("data_from_json: "+ data_from_gui.get(0));

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

