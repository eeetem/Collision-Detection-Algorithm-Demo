package com.collisiondetectionalgorithm;

import javax.print.attribute.standard.Media;
import java.awt.*;
import java.util.ArrayList;


public class Area {
    Vector2 Position;
    Vector2 Size;
    ArrayList<Entity> ContainedEntities;

    public Area(Vector2 position, Vector2 size, ArrayList<Entity> containedEntities) {
        Position = position;
        Size = size;
        ContainedEntities = containedEntities;
    }

    public void Draw(Graphics g){

        g.setColor(Color.white);
        g.drawRect((int) Position.x , (int) Position.y, (int)  Size.x, (int) Size.y);
        g.drawString("Entities Contained: "+ContainedEntities.size(),(int) Position.x , (int) Position.y+10);



    }
    public ArrayList<Area> Subdivide(boolean AxisFlag){

        ArrayList<Area> returnAreas = new ArrayList<>();


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

        for (Entity e : ContainedEntities){
            float EntityCoordinate;
            if(AxisFlag) EntityCoordinate = e.Position.x;
            else EntityCoordinate = e.Position.y;

            float EntitySize;
            if(AxisFlag) EntitySize = e.Size.x;
            else EntitySize = e.Size.y;

            if(EntityCoordinate <= MedianCoordinate){
                Entities1.add(e);
                //dont return since an entity can be part of both created areas
            }
            if(EntityCoordinate+EntitySize >= MedianCoordinate){
                Entities2.add(e);

            }

        }
        //if the contents of the child areas are the same as this one - the split is poitless so just return yourself
        if(Entities1.size() == ContainedEntities.size() || Entities2.size() == ContainedEntities.size()){
            returnAreas.add(this);
            return returnAreas;
        }


        Area area1;
        Area area2;

        float DistanceFromMedianToEdge;
        if(AxisFlag) DistanceFromMedianToEdge = Size.x - MedianCoordinate;
        else DistanceFromMedianToEdge = Size.y - MedianCoordinate;



        if(AxisFlag){
            area1 = new Area(Position,new Vector2(MedianCoordinate, Size.y),Entities1);
            area2 = new Area(new Vector2(Position.x+MedianCoordinate,Position.y),new Vector2(DistanceFromMedianToEdge, Size.y),Entities2);

        }else{
            area1 = new Area(Position,new Vector2(Size.x, MedianCoordinate),Entities1);
            area2 = new Area(new Vector2(Position.x,Position.y+MedianCoordinate),new Vector2(Size.x, DistanceFromMedianToEdge),Entities2);
        }
        returnAreas.addAll(area1.Subdivide(!AxisFlag));
        returnAreas.addAll(area2.Subdivide(!AxisFlag));


        return returnAreas;


    }
}
