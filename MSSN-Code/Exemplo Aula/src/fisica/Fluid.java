package fisica;


import processing.core.PApplet;
import processing.core.PVector;

//abstrata.
public class Fluid {

    private float density;
    public Fluid (float density){
        this.density = density;
    }

    public PVector drag(Body b){
        float area = PApplet.pow(b.radius/100,2f)* PApplet.PI;//radius /100
        float speed = b.vel.mag();
        return PVector.mult(b.vel, -0.5f*density*area*speed);
    }
}
