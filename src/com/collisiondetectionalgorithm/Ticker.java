package com.collisiondetectionalgorithm;

import java.awt.*;

public class Ticker extends Thread {

    public static final float TargetTickRate = 300;
    public static final float MSperTick = 1000 / TargetTickRate;

    public static long lastTickTime;

    @Override
    public void run() {
        while (true) {
            long start = System.currentTimeMillis();


            EntityManager.Update(MSperTick / 1000);


            long finish = System.currentTimeMillis();
            lastTickTime = finish - start;


            if (lastTickTime >= MSperTick) {
                System.out.printf("Lag Warning: Current tick took " + lastTickTime + "ms to process which is bigger than the target " + MSperTick + "ms\n");
            } else {
                try {
                    Thread.sleep((int) (MSperTick - lastTickTime));

                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

            }
        }
    }
}