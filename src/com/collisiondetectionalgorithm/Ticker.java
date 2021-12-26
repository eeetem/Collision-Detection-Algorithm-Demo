package com.collisiondetectionalgorithm;

import java.awt.*;

public class Ticker extends Thread {



    public Ticker(){
        SetTickRate(150);
    }

    public static void SetTickRate(int TickRate){

        MSperTick  = 1000f/TickRate;
    }


    public static float MSperTick;



    @Override
    public void run() {
        while (true) {
            long start = System.currentTimeMillis();


            EntityManager.Update(MSperTick / 1000);


            long finish = System.currentTimeMillis();
            long lastTickTime = finish - start;


            GUI.TickDisplay.setText("Tick Processing Time: "+lastTickTime+"MS");
            if (lastTickTime >= MSperTick) {

                GUI.TickDisplay.setForeground(Color.red);
                GUI.TickDisplay.setText(GUI.TickDisplay.getText() + " (can't keep up with target tickrate)");
                //System.out.printf("Lag Warning: Current tick took " + lastTickTime + "ms to process which is bigger than the target " + MSperTick + "ms\n");
            }
            else
            {
                GUI.TickDisplay.setForeground(Color.black);

                try {
                    Thread.sleep((int) (MSperTick - lastTickTime));

                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

            }
        }
    }
}