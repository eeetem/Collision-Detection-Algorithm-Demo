package com.collisiondetectionalgorithm;


import java.awt.*;
import java.util.ArrayList;

public class Area {
    Vector2 Position;
    Vector2 Size;
    ArrayList<Entity> ContainedEntities;

    public static boolean DisableKDPartitioning = false;

    public Area(Vector2 position, Vector2 size, ArrayList<Entity> containedEntities) {
        Position = position;
        Size = size;
        ContainedEntities = containedEntities;
    }

    public void Draw(Graphics g){

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

        //if axis flag is true do a split on X axis - otherwise Y

        if(AxisFlag) ContainedEntities.sort(new Xcomparator());
        else ContainedEntities.sort(new Ycomparator());


        float MedianCoordinate;

        int middle = ContainedEntities.size()/2;



        if(ContainedEntities.size() % 2 == 0){

            if(AxisFlag) MedianCoordinate = (ContainedEntities.get(middle).Position.x +  ContainedEntities.get(middle-1).Position.x)/2;
            else MedianCoordinate = (ContainedEntities.get(middle).Position.y +  ContainedEntities.get(middle-1).Position.y)/2;

        }else{
            if(AxisFlag) MedianCoordinate = ContainedEntities.get(middle).Position.x;
            else MedianCoordinate = ContainedEntities.get(middle).Position.y;
        }

        ArrayList<Entity> Entities1 = new ArrayList<Entity>();
        ArrayList<Entity> Entities2 = new ArrayList<Entity>();









//the double loop isnt very clean - but it's better performance wise to do this rather than do an axisflag check every iteration
    if(AxisFlag){
        for (int i = 0; i < ContainedEntities.size(); i++) {
            Entity e = ContainedEntities.get(i);
            float EntityCoordinate = e.Position.x;
            float EntitySize = e.Size.x;



            if(CheckEntityAreaSplit(MedianCoordinate, Entities1, Entities2, i, EntityCoordinate, EntitySize,AxisFlag)){

                break;
            }

        }
    }else{
        for (int i = 0; i < ContainedEntities.size(); i++) {
            Entity e = ContainedEntities.get(i);
            float EntityCoordinate = e.Position.y;

            float EntitySize = e.Size.y;


            if(CheckEntityAreaSplit(MedianCoordinate, Entities1, Entities2, i, EntityCoordinate, EntitySize,AxisFlag)){

                break;
            }

        }}









        //if the contents of the child areas are the same as this one - the split is poitless so just return yourself
        if(Entities1.size() == ContainedEntities.size() || Entities2.size() == ContainedEntities.size()){
            returnAreas.add(this);
            return returnAreas;
        }


        Area area1;
        Area area2;

        float DistanceFromStartToMedian;
        float DistanceFromMedianToEdge;
        if(AxisFlag) DistanceFromMedianToEdge = (Position.x + Size.x) - MedianCoordinate;
        else DistanceFromMedianToEdge = (Position.y + Size.y) - MedianCoordinate;

        if(AxisFlag) DistanceFromStartToMedian = MedianCoordinate - Position.x;
        else DistanceFromStartToMedian = MedianCoordinate - Position.y;





        if(AxisFlag){
            area1 = new Area(Position,new Vector2(DistanceFromStartToMedian, Size.y),Entities1);
            area2 = new Area(new Vector2(Position.x+DistanceFromStartToMedian,Position.y),new Vector2(DistanceFromMedianToEdge, Size.y),Entities2);

        }else{
            area1 = new Area(Position,new Vector2(Size.x, DistanceFromStartToMedian),Entities1);
            area2 = new Area(new Vector2(Position.x,Position.y+DistanceFromStartToMedian),new Vector2(Size.x, DistanceFromMedianToEdge),Entities2);
        }
        returnAreas.addAll(area1.Subdivide(!AxisFlag));
        returnAreas.addAll(area2.Subdivide(!AxisFlag));


        return returnAreas;


    }

    private boolean CheckEntityAreaSplit(float medianCoordinate, ArrayList<Entity> entities1, ArrayList<Entity> entities2, int i, float entityCoordinate, float entitySize,boolean axisFlag) {
        ///since the entities are sorted not by position but by "highest coordinate"(size+position) once we find 1 entity that's part of 2nd area -  we can add all further ones
        if (entityCoordinate + entitySize < medianCoordinate) return false;


            entities2.addAll(ContainedEntities.subList(i,ContainedEntities.size()));
            entities1.addAll(ContainedEntities.subList(0,i));


            //again the double loop isnt great but performance wise it's better not to have an axisflag check every itteration
            if(axisFlag){
            for (Entity e2 : entities2) {//this isnt the best efficency wide since there's a lot of checks being done - but atleast 1 half is filled with barelly any checks
                if(e2.Position.x <= medianCoordinate && !entities1.contains(e2)){
                    entities1.add(e2);
                }
            }}else{

                for (Entity e2 : entities2) {//this isnt the best efficency wide since there's a lot of checks being done - but atleast 1 half is filled with barelly any checks
                    if (e2.Position.y <= medianCoordinate && !entities1.contains(e2)) {
                        entities1.add(e2);
                    }
                }
            }
            return  true;
    }
}

