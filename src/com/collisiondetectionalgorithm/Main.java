package com.collisiondetectionalgorithm;

import javax.swing.*;
import java.awt.*;
public class Main {


    public static Canvas canvas;
    public static JFrame frame;

    public static void main(String[] args) {


        canvas = new Canvas();

        canvas.setPreferredSize(new Dimension(1280, 720));
        // canvas.setMinimumSize(new Dimension(128,72));
        canvas.setMaximumSize(new Dimension(1280, 720));
        canvas.setMinimumSize(new Dimension(1280, 720));

        frame = new JFrame("Collision Detection Algorithm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);


        for (int i = 0; i < 300; i++) {
            EntityManager.MakeRandomEntity();

       }



       // EntityManager.MakeEntity(new Vector2(10,10),new Vector2(5,10)).Velocity = new Vector2(25,20);
        //EntityManager.MakeEntity(new Vector2(100,100),new Vector2(10,5)).Velocity = new Vector2(-10,-13);

        Renderer renderer = new Renderer(canvas);
        Ticker ticker = new Ticker();

        ticker.start();
        renderer.start();





    }

}




