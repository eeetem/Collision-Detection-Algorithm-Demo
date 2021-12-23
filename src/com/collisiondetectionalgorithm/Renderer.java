package com.collisiondetectionalgorithm;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Renderer extends Thread{


    public float FPS;

    private final Canvas canvas;

    public Renderer(Canvas canvas){

        this.canvas = canvas;

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

            long MSperFrame = end - start;
            FPS = 1000f/MSperFrame;


        }
    }
}
