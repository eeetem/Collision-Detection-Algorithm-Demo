package com.collisiondetectionalgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main {


    public static Canvas canvas;
    public static JFrame frame;
    public static Graphics graphics;
    public static BufferStrategy bs;

    public static final float TargetTickRate = 144;
    public static final float MSperTick = 1000 / TargetTickRate;

    public static Entity e1;
    public static Entity e2;

    public static void main(String[] args) {


        canvas = new Canvas();

        canvas.setPreferredSize(new Dimension(1280,720));
        // canvas.setMinimumSize(new Dimension(128,72));
        canvas.setMaximumSize(new Dimension(1280,720));
        canvas.setMinimumSize(new Dimension(1280,720));

        frame = new JFrame("Collision Detection Algorithm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        graphics = bs.getDrawGraphics();






        for (int i = 0; i < 100; i++) {
            EntityManager.MakeRandomEntity();

        }




        Loop();

    }

    private static void Loop(){

        while(true){


            long start = System.currentTimeMillis();


            EntityManager.Update(MSperTick/1000);

            graphics.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
            graphics.setColor(Color.black);
            graphics.fillRect(0,0, canvas.getWidth(), canvas.getHeight());

            EntityManager.Draw(graphics);

            bs.show();

            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;

            if (timeElapsed >= MSperTick)
            {
                System.out.printf("Lag Warning: Current tick took "+ timeElapsed+"ms to process which is bigger than the target "+MSperTick+"ms\n");
            }
            else
            {
                try {
                    Thread.sleep((int) (MSperTick - timeElapsed));

                }  catch(InterruptedException ex)
                    {
                        Thread.currentThread().interrupt();
                    }
            }

        }





        }

    }




