package com.collisiondetectionalgorithm;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Renderer extends Thread{



    private final Canvas canvas;

    public static void SetFPS(int FPS){
        targetFps = FPS;
        MSperFrame = 1000f/FPS;
    }
    public static int targetFps;
    private static float MSperFrame;

    public Renderer(Canvas canvas){

        this.canvas = canvas;

        SetFPS(60);

    }

    @Override
    public void run(){

        canvas.createBufferStrategy(2);
        BufferStrategy bs = canvas.getBufferStrategy();
        Graphics graphics = bs.getDrawGraphics();





        while(true){
            long start = System.currentTimeMillis();


            graphics.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
            graphics.setColor(Color.black);
            graphics.fillRect(0,0, canvas.getWidth(), canvas.getHeight());

            EntityManager.Draw(graphics);
            bs.show();
            long end = System.currentTimeMillis();

            float DrawTime = end - start;



            if (DrawTime <= MSperFrame){

                try {
                    Thread.sleep((int) (MSperFrame - DrawTime));
                    DrawTime = MSperFrame;

                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

            }

            GUI.FPSCounter.setText("FPS: "+Math.round(1000f/DrawTime));


        }
    }
}
