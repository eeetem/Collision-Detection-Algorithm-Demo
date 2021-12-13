package com.collisiondetectionalgorithm;

import java.awt.*;
import java.util.Comparator;
import java.util.Random;

public class Entity{
    Vector2 Size;
    Vector2 Position;
    Vector2 Velocity;
    Color color;


    public Entity(Vector2 Position, Vector2 Size){
        this.Position = Position;//position is top left corner of the rectangle not centre
        this.Size = Size;

        Random rand = new Random();

        //since the backround is black make sure the randomc color isnt black by setting minimum of 50 in each rgb value
        color = new Color(Math.max(rand.nextInt(255),50),Math.max(rand.nextInt(255),50),Math.max(rand.nextInt(255),50));


    }
    public void Update(float deltaseconds){
        Position.x += Velocity.x*deltaseconds;
        Position.y += Velocity.y*deltaseconds;

    }

    public void Draw(Graphics g){

        g.setColor(color);
        g.fillRect((int) Position.x , (int) Position.y, (int) Size.x, (int) Size.y);

    }



}
class Xcomparator implements Comparator<Entity>{
    @Override
    public int compare(Entity o1, Entity o2) {
        return (int) (o1.Position.x - o2.Position.x);
    }
}
class Ycomparator implements Comparator<Entity>{
    @Override
    public int compare(Entity o1, Entity o2) {
        return (int) (o1.Position.y - o2.Position.y);
    }
}
