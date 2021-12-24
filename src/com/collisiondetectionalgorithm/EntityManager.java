package com.collisiondetectionalgorithm;


import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class EntityManager {




    static public ArrayList<Entity> Entities = new ArrayList<Entity>();
    static public ArrayList<Area> Areas = new ArrayList<Area>();


    static private boolean modifying = true;


    public static Random rand = new Random(0);


    public static Entity MakeRandomEntity(){



        Entity e = MakeEntity(new Vector2(rand.nextInt(Main.canvas.getWidth()-300),rand.nextInt(Main.canvas.getHeight()-200)),new Vector2(rand.nextInt(15)+5,rand.nextInt(15)+5));
        e.Velocity = new Vector2(rand.nextInt(80)-40,rand.nextInt(80)-40);

        return e;
    }


    public static Entity MakeEntity(Vector2 Position, Vector2 Size)
    {
        Entity e = new Entity(Position,Size);
         Entities.add(e);
         return e;
    }


    static int lastID=0;

    public static int GetNextId() {
        lastID++;
        return lastID;


    }

    public static void Update(float deltaseconds)
    {

        ///draw uses Last lists in oder to not cause simoultaneous modification exceptions

        modifying = true;


        for (Entity entity : Entities)
        {
            entity.Update(deltaseconds);

        }


        Area root = new Area(new Vector2(0,0), new Vector2(Main.canvas.getWidth(),Main.canvas.getHeight()), Entities);

        Areas = root.Subdivide(true);



        for (Area a: Areas){

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
                HandleBorderCollision(entity);//this bit could probably be done better since right now every entity is checked for border collision
            }

        }
        modifying = false;


    }



    public static void Draw(Graphics g)
    {

        while(modifying){//wait for ticker to finish current tick
            try {
                Thread.sleep(1);

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

        }
        ArrayList<Entity> CachedEntities = new ArrayList<Entity>(Entities);
        ArrayList<Area> CachedAreas = new ArrayList<Area>(Areas);


        for (Area a : CachedAreas)
        {
            a.Draw(g);

        }
        for (Entity entity : CachedEntities)
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
          Yoverlap = -(e2.Position.y + e2.Size.y - e1.Position.y);
      }

        if (e1.Position.x<e2.Position.x) {
            Xoverlap = e1.Position.x + e1.Size.x - e2.Position.x;
        }
        else{
            Xoverlap = -(e2.Position.x + e2.Size.x - e1.Position.x);
        }





        //perform the collision in the axis of least overlap since that's the overlap that most recently happen

        if(Math.abs(Xoverlap) > Math.abs(Yoverlap)){
            float e1vel = e1.Velocity.y;
            e1.Velocity.y = e2.Velocity.y;
            e2.Velocity.y = e1vel;
            e1.Position.y -= Yoverlap/2;
            e2.Position.y += Yoverlap/2;


        }else{
            float e1vel =  e1.Velocity.x;
            e1.Velocity.x = e2.Velocity.x;
            e2.Velocity.x = e1vel;
            e1.Position.x -= Xoverlap/2;
            e2.Position.x += Xoverlap/2;
        }

    }


    public static void HandleBorderCollision(Entity e){
        if(e.Position.x <= 0){
            e.Velocity.x = -e.Velocity.x;
            float overlap = 0 - e.Position.x;
            e.Position.x += overlap;



        }
        else if(e.Position.x+e.Size.x >= Main.canvas.getWidth()){

            e.Velocity.x = -e.Velocity.x;
            float overlap = e.Position.x+e.Size.x - Main.canvas.getWidth();
            e.Position.x -= overlap;
        }



        if(e.Position.y <= 0){
            e.Velocity.y = -e.Velocity.y;
            float overlap = 0 - e.Position.y;
            e.Position.y += overlap;



        }
        else if(e.Position.y+e.Size.y >= Main.canvas.getHeight()) {

            e.Velocity.y = -e.Velocity.y;
            float overlap = e.Position.y+e.Size.y - Main.canvas.getHeight();
            e.Position.y -= overlap;


        }

    }





}
