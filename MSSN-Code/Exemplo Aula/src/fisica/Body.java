package fisica;

import processing.core.PApplet;
import processing.core.PVector;

public class Body extends Mover{

    protected float radius;
    private int color;



    public Body (PVector pos, PVector vel, float mass, float radius,int color){
    super (pos,vel,mass);
    this.radius = radius;
    this.color = color;
    }


    public void display (PApplet p){
        p.fill(color);
        p.circle(pos.x, pos.y, 2*radius);
    }
}
