package com.collisiondetectionalgorithm;


import javax.swing.text.Position;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class EntityManager {

    static public ArrayList<Entity> entities = new ArrayList<Entity>();
    static public ArrayList<Area> areas = new ArrayList<Area>();



    public static Entity MakeRandomEntity(){

        Random rand = new Random();

        Entity e = MakeEntity(new Vector2(rand.nextInt(Main.canvas.getWidth()-600),rand.nextInt(Main.canvas.getHeight()-200)),new Vector2(rand.nextInt(15)+10,rand.nextInt(15)+10));
        e.Velocity = new Vector2(rand.nextInt(100)-50,rand.nextInt(100)-50f);

        return e;
    }


    public static Entity MakeEntity(Vector2 Position, Vector2 Size)
    {
        Entity e = new Entity(Position,Size);
         entities.add(e);
         return e;
    }


    static int lastID=0;

    public static int GetNextId() {
        lastID++;
        return lastID;


    }

    public static void Update(float deltaseconds)
    {

        for (Entity entity : entities)
        {
            entity.Update(deltaseconds);
            HandleBorderCollision(entity);
        }


        Area root = new Area(new Vector2(0,0), new Vector2(Main.canvas.getWidth(),Main.canvas.getHeight()), entities);

        areas = root.Subdivide(true);



        for (Area a: areas){

            ArrayList<Entity> excludelist = new ArrayList<Entity>();

            for (Entity entity : a.ContainedEntities)
            { excludelist.add(entity);

                for (Entity entity2 : a.ContainedEntities)
                {
                    if (excludelist.contains(entity2)){
                        continue;

                    }
                    HandleCollision(entity,entity2);


                }

            }

        }


    }



    public static void Draw(Graphics g)
    {

        for (Area a : areas)
        {
            a.Draw(g);

        }
        for (Entity entity : entities)
        {
            entity.Draw(g);

        }




    }

    public static void HandleCollision(Entity e1, Entity e2){


        //if left size X of e1 is bigger than e2 right side X or vice versa - they can't be touching
      if(e1.Position.x > e2.Position.x +  e2.Size.x || e2.Position.x > e1.Position.x + e1.Size.x){
         return;
     }
        //repeat the same for Y axis
      if(e1.Position.y > e2.Position.y + e2.Size.y || e2.Position.y > e1.Position.y + e1.Size.y){
          return;

      }

      //at this point they must be colliding


        float Xoverlap,Yoverlap;



      if (e1.Position.y<e2.Position.y) {
          Yoverlap = e1.Position.y + e1.Size.y - e2.Position.y;
      }
      else{
          Yoverlap = e2.Position.y + e2.Size.y - e1.Position.y;
      }

        if (e1.Position.x<e2.Position.x) {
            Xoverlap = e1.Position.x + e1.Size.x - e2.Position.x;
        }
        else{
            Xoverlap = e2.Position.x + e2.Size.x - e1.Position.x;
        }





        //perform the collision in the axis of least overlap since that's the overlap that most recently happen

        if(Xoverlap > Yoverlap){
            e1.Velocity.y = -e1.Velocity.y;
            e2.Velocity.y = -e2.Velocity.y;
            e1.Position.y -= Yoverlap/2;
            e2.Position.y += Yoverlap/2;


        }else{
            e1.Velocity.x = -e1.Velocity.x;
            e2.Velocity.x = -e2.Velocity.x;
            e1.Position.x -= Xoverlap/2;
            e2.Position.x += Xoverlap/2;
        }

    }


    public static void HandleBorderCollision(Entity e){
        if(e.Position.x < 0 || e.Position.x+e.Size.x > Main.canvas.getWidth()){
            e.Velocity.x = -e.Velocity.x;


        }

        if(e.Position.y < 0 || e.Position.y+e.Size.y > Main.canvas.getHeight()){
            e.Velocity.y = -e.Velocity.y;


        }





    }





}
