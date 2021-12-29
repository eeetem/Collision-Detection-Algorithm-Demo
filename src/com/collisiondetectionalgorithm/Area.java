package com.collisiondetectionalgorithm;


import java.awt.*;
import java.util.ArrayList;

public class Area {
    Vector2 Position;
    Vector2 Size;
    ArrayList<Entity> ContainedEntities;

    public static boolean DisableKDPartitioning = false;
    public static boolean HideAreas = false;

    public Area(Vector2 position, Vector2 size, ArrayList<Entity> containedEntities) {
        Position = position;
        Size = size;
        ContainedEntities = containedEntities;
    }

    public void Draw(Graphics g){

        if(HideAreas) return;


        g.setColor(Color.white);
        g.drawRect((int) Position.x , (int) Position.y, (int)  Size.x, (int) Size.y);

      //  String Contents = "";

       // for (Entity e: ContainedEntities) {
      //      Contents += e.ID+", ";

      //  }


       // g.drawString("Entities Contained: "+Contents,(int) Position.x , (int) Position.y+10);



    }
    public ArrayList<Area> Subdivide(boolean AxisFlag){

        ArrayList<Area> returnAreas = new ArrayList<>();

        if(DisableKDPartitioning){
            returnAreas.add(this);
            return returnAreas;

        }

        EntityInterface entityInterface;
        //if axis flag is true do a split on X axis - otherwise Y
        //a delegate/interface is used in order to not have an axisflag check every time we need to get the coordinates
        if(AxisFlag){
            entityInterface = new XAxisGetter();
            ContainedEntities.sort(new Xcomparator());
        }else {
            entityInterface = new YAxisGetter();
            ContainedEntities.sort(new Ycomparator());
        }


        float MedianCoordinate;

        int middle = ContainedEntities.size()/2;



        if(ContainedEntities.size() % 2 == 0){

            MedianCoordinate = (entityInterface.GetPosition(ContainedEntities.get(middle)) +  entityInterface.GetPosition(ContainedEntities.get(middle-1)))/2;

        }else{
            MedianCoordinate = entityInterface.GetPosition(ContainedEntities.get(middle));
        }

        ArrayList<Entity> Entities1 = new ArrayList<Entity>();
        ArrayList<Entity> Entities2 = new ArrayList<Entity>();




        for (int i = 0; i < ContainedEntities.size(); i++) {
            Entity e = ContainedEntities.get(i);
            float EntityCoordinate = entityInterface.GetPosition(e);
            float EntitySize = entityInterface.GetSize(e);



            ///since the entities are sorted not by position but by "highest coordinate"(size+position) once we find 1 entity that's part of 2nd area -  we can add all further ones
            if (EntityCoordinate + EntitySize < MedianCoordinate) continue;


            Entities2.addAll(ContainedEntities.subList(i,ContainedEntities.size()));
            Entities1.addAll(ContainedEntities.subList(0,i));


                for (Entity e2 : Entities2) {//this isnt the best efficency wide since there's a lot of checks being done - but atleast 1 half is filled with barelly any checks
                    if(entityInterface.GetPosition(e2) <= MedianCoordinate && !Entities1.contains(e2)){
                        Entities1.add(e2);
                    }
                }
            break;

        }










        //if the contents of the child areas are the same as this one - the split is poitless so just return yourself
        if(Entities1.size() == ContainedEntities.size() || Entities2.size() == ContainedEntities.size()){
            returnAreas.add(this);
            return returnAreas;
        }


        Area area1;
        Area area2;

        float DistanceFromStartToMedian;
        float DistanceFromMedianToEdge;

        if(AxisFlag) {

            DistanceFromMedianToEdge = (Position.x + Size.x) - MedianCoordinate;
            DistanceFromStartToMedian = MedianCoordinate - Position.x;

            area1 = new Area(Position,new Vector2(DistanceFromStartToMedian, Size.y),Entities1);
            area2 = new Area(new Vector2(Position.x+DistanceFromStartToMedian,Position.y),new Vector2(DistanceFromMedianToEdge, Size.y),Entities2);
        }
        else{
            DistanceFromMedianToEdge = (Position.y + Size.y) - MedianCoordinate;
            DistanceFromStartToMedian = MedianCoordinate - Position.y;

            area1 = new Area(Position,new Vector2(Size.x, DistanceFromStartToMedian),Entities1);
            area2 = new Area(new Vector2(Position.x,Position.y+DistanceFromStartToMedian),new Vector2(Size.x, DistanceFromMedianToEdge),Entities2);
        }


        returnAreas.addAll(area1.Subdivide(!AxisFlag));
        returnAreas.addAll(area2.Subdivide(!AxisFlag));


        return returnAreas;


    }












}

